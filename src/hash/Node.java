package hash;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

public class Node implements Runnable {

    private Integer name;
    private String address;
    private Integer power;
    private Integer time;//时间间隔
    private String nineHash;
    private CountDownLatch latch;//线程计数器
    private Vector<String> results;
    private Result zeroResult;
    private Integer difficulty;
    private Integer timeStart;//起始计算时间，刚开始为0，若计算结果未达到3，就增加计算次数，此时改变这个值
    private Integer timeAdd;//增加的秒数，起始值为0

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Result getZeroResult() {
        return zeroResult;
    }

    public void setZeroResult(Result zeroResult) {
        this.zeroResult = zeroResult;
    }

    public Node(Integer name, String address, Integer power) {
        this.name = name;
        this.address = address;
        this.power = power;
    }

    public Vector<String> getResults() {
        return results;
    }

    public void setResults(Vector<String> results) {
        this.results = results;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getNineHash() {
        return nineHash;
    }

    public void setNineHash(String nineHash) {
        this.nineHash = nineHash;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Integer timeStart) {
        this.timeStart = timeStart;
    }

    public Integer getTimeAdd() {
        return timeAdd;
    }

    public void setTimeAdd(Integer timeAdd) {
        this.timeAdd = timeAdd;
    }

    @Override
    public void run() {
        long millis = System.currentTimeMillis();

        String min = HashUtil.getSHA256StrJava(address + 0 + 0 + nineHash);
        int maxZnum = 0;
        for (int i = 0; i < power; i++) {
            for (int j = timeStart; j < time + timeAdd; j++) {
                long time1 = millis + j;
                String target = address + i + time1 + nineHash;
                String result = HashUtil.getSHA256StrJava(target);

                //统计0的情况
                int znum = zeroResult.findZero(zeroResult, result);
                if (znum > maxZnum) {
                    maxZnum = znum;
                }

                if (result.compareTo(min) < 0) {
                    min = result;
                }
            }
        }

        //符合难度系数的才加入
        if (maxZnum >= difficulty) {
            min = min + "##" + name + "##" + power;
            results.add(min);
        }
        latch.countDown();
    }
}
