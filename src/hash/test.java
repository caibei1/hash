package hash;

public class test {
    public static void main (String[]args){
        String a = "qq?q??";
       // a = a.replaceAll("\\?","w");
        a = a.replaceFirst("\\?","w");
        System.out.println(a);

        String sql = "insert into hash (height,times,zero,one,two,three,four,five,six,seven) values (?,?,?,?,?,?,?,?,?,?)";
        int[] paras = {1,1 + 1,1,1,1,1,1,1,1,1};
        for(int i = 0; i < paras.length; i++){
            sql = sql.replaceFirst("\\?",paras[i]+"");
        }
        System.out.println(sql);
    }
}
