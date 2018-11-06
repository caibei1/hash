package hash;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args)  {

        //初始化数据库连接
        C3P0 c3P0 = new C3P0();
        c3P0.init();

        Logger log = Logger.getLogger("log");
        log.setLevel(Level.ALL);
        //加载配置
        Properties property = new Properties();
        try {
            property.load(new FileInputStream("src/hash/conf.properties"));
        } catch (IOException e) {
            System.out.println("读取配置文件异常");
        }
        //获取时间间隔
        String timeStr = property.getProperty("time");
        int time = Integer.parseInt(timeStr);
        //获取节点数
        String nodeNumStr = property.getProperty("nodeNum");
        int nodeNum = Integer.parseInt(nodeNumStr);
        //difficulty获取难度系数
        String difficultyStr = property.getProperty("difficulty");
        int difficulty = Integer.parseInt(difficultyStr);

        log.info("init config");
        log.info("time: " + time);
        log.info("nodeNum: " + nodeNum);
        log.info("difficulty: " + difficulty);

        //规定选票规则
        int[] rule = {12,11,10,9,8,7,6,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5};

        //初始化所有节点
        log.info("init node");
        int Allticket = 0;
        Map<Integer,Node> nodes = new HashMap<>();
        for (int i = 0; i < nodeNum; i++){
            //获取地址
            String addr = HashUtil.getUUID();
            //票  取余10
            int ticket = (i%10)*2;
            Node node = new Node(i,addr,ticket);
            Allticket += ticket;
            node.setDifficulty(difficulty);
            nodes.put(i,node);
        }

        log.info("Allticket:"+Allticket);
        //初始化创世快
        log.info("init Creation block");
        Block c = new Block();
        //随机生成3个hash
        String hash1 = HashUtil.getSHA256StrJava(HashUtil.getUUID());
        String hash2 = HashUtil.getSHA256StrJava(HashUtil.getUUID());
        String hash3 = HashUtil.getSHA256StrJava(HashUtil.getUUID());
        Vector<String> results1 = new Vector<>();
        results1.add(hash1);
        results1.add(hash2);
        results1.add(hash3);
        Map<Integer,String> hashs = HashUtil.getMinThree(results1,3);
        c.setMinHash(hashs.get(0));
        c.setMinHash(hashs.get(1));
        c.setMinHash(hashs.get(2));

        //创世块加入链
        log.info("join in chain");
        Chain chain = new Chain();
        Map<Integer, Block> blockMap = new HashMap<>();
        chain.setLastHeight(1);
        blockMap.put(chain.getLastHeight(),c);
        chain.setBlockMap(blockMap);

        System.out.println("start...");
        //时间
        long lastTime = 0;
        long thisTime = System.currentTimeMillis();
        //开始
        while (true){
            System.out.println("------------------------------------------");
            //统计0的个数
            Result r = new Result();
            r.setZero(0);
            r.setOne(0);
            r.setTwo(0);
            r.setThree(0);
            r.setFore(0);
            r.setFive(0);
            r.setSix(0);
            r.setSeven(0);

            final Vector<String> results = new Vector<>();
            final CountDownLatch latch = new CountDownLatch(nodes.size());

            String nineHash = chain.GetMinHash();
            //遍历节点
            System.out.println("waiting......");
            for (int i = 0;i < nodes.size(); i++){
                Node n = nodes.get(i);
                n.setLatch(latch);
                n.setNineHash(nineHash);
                n.setTime(time);
                n.setResults(results);
                n.setZeroResult(r);
                n.setTimeStart(0);//起始时间
                n.setTimeAdd(0);//增加秒数
                Thread t = new Thread(n);
                t.start();
            }

            //等待所有线程执行完
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //todo
            //增加次数
            int count = 0;//计数
            //把获取到的hash数，和许可票数取小的值，当获取的的值大于等于下一次的许可票数时，退出循环
            //while (!HashUtil.compare(HashUtil.getMin(results.size(),rule[time+count-1]),rule[time+count])){

            //计数器    (HashUtil.compare(HashUtil.getMin(results.size(),rule[time+count-1]),rule[time+count])只让他进一次
            int ifCount = 0;
            while(time+count<11){

                //最后符合要求的票数
                int target = 0;

                if (HashUtil.compare(HashUtil.getMin(results.size(),rule[time+count-1]),rule[time+count])&&ifCount==0){
                    target = HashUtil.getMin(results.size(),rule[time+count-1]);
                    ifCount ++;
                }

                //先存数据
                Connection con = c3P0.getConnection();
                String sql = "insert into hashserver (result,height,times,four,updatetime,timeDiff) values (?,?,?,?,?,?)";
                String[] paras = {target+"",chain.getLastHeight() + 1 +"",time + count+"",r.getFore()+r.getFive()+r.getSix()+r.getSeven()+"",thisTime+"","1000"};
                for(int i = 0; i < paras.length; i++){
                    sql = sql.replaceFirst("\\?",paras[i]);
                }
                System.out.println(sql);
                PreparedStatement ps = null;
                try {
                    ps = con.prepareStatement(sql);
                    ps.executeUpdate(sql);
                    con.close();
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //target 重新置0
                target = 0;
                final CountDownLatch latch1 = new CountDownLatch(nodes.size());
                count++;
                for (int i = 0;i < nodes.size(); i++){
                    Node n = nodes.get(i);
                    n.setLatch(latch1);
                    n.setTimeStart(time+count-1);//起始时间
                    n.setTimeAdd(count);//增加秒数
                    Thread t = new Thread(n);
                    t.start();
                }
                //等待所有线程执行完
                try {
                    latch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //符合难度系数的节点数
            System.out.println("results length: "+results.size());
            //秒数
            System.out.println("time: "+(time+count));

            //获取最小的3个hash
            Map<Integer,String> mins = HashUtil.getMinThree(results,3);
            //创建块
            Block b = new Block();
            b.setMinHash(mins.get(0));
            b.setMediumHash(mins.get(1));
            b.setLargeHash(mins.get(2));
            b.setHeight(chain.getLastHeight()+1);
//            int soutNum = 3;
//            soutNum = soutNum > results.size()? results.size():soutNum;
//            for (int i = 0; i < soutNum; i++){
//                System.out.println(mins.get(i));
//            }

            //加入链
            chain.setLastHeight(chain.getLastHeight()+1);
            Map<Integer, Block> bkm = chain.getBlockMap();
            bkm.put(chain.getLastHeight(),b);
            chain.setBlockMap(bkm);

            //切分出user和power和hash
            String[] split1 = mins.get(0).split("##");
            String hash11 = split1[0];
            String userId1 = split1[1];
            String power1 = split1[2];

            String[] split2 = mins.get(1).split("##");
            String hash12 = split2[0];
            String userId2 = split2[1];
            String power2 = split2[2];

            String[] split3 = mins.get(2).split("##");
            String hash13 = split3[0];
            String userId3 = split3[1];
            String power3 = split3[2];


            //存储数据到数据库
            //先计算时间
            lastTime = thisTime;
            thisTime = System.currentTimeMillis();
            long timeDiff = thisTime-lastTime;

            Connection con = c3P0.getConnection();
            String sql = "insert into hashserver (result,height,times,four,updatetime,timeDiff,userId1,power1,hash1,userId2,power2,hash2,userId3,power3,hash3) values (?,?,?,?,?,?,?,?,'?',?,?,'?',?,?,'?')";
            String[] paras = {"0",chain.getLastHeight()+"",time + count+"",r.getFore()+r.getFive()+r.getSix()+r.getSeven()+"",thisTime+"",timeDiff+"",userId1,power1,hash11,userId2,power2,hash12,userId3,power3,hash13};
            for(int i = 0; i < paras.length; i++){
                sql = sql.replaceFirst("\\?",paras[i]);
            }
            //System.out.println(sql);
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(sql);
                ps.executeUpdate(sql);
                con.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("height: "+b.getHeight());
            System.out.println("MinHas: "+b.getMinHash());
            System.out.println("MediumHash: "+b.getMediumHash());
            System.out.println("LargeHash: "+b.getLargeHash());
            System.out.println(r);

        }
    }
}
