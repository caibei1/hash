package hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class HashUtil {

    public HashUtil() {
        throw new RuntimeException("工具类不能新建");
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * 获取去掉-的uuid
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        int p = 0;
        int j = 0;
        char[] buf = new char[32];
        while (p < s.length()) {
            char c = s.charAt(p);
            p += 1;
            if (c == '-')
                continue;
            buf[j] = c;
            j += 1;
        }
        return new String(buf);
    }


    //获取最小num个hash
    public static Map<Integer,String> getMinThree(List<String> results, int num){
        int length = results.size();
        Map<Integer,String> map = new HashMap<>();
        for (int i = 0;i<num;i++){
			int min = i;
			for (int j = i+1;j<length;j++){
				if((results.get(j).compareTo(results.get(min)))<0){
					min = j;
				}
			}
			if(i!=min){
				String h = results.get(i);
				results.set(i,results.get(min));
				results.set(min,h);
			}
			map.put(i,results.get(i));
		}

        return map;
    }

    public static boolean compare(int a,int b){
        return a >= b;
    }

    public static int getMin(int a,int b){
        //return a > b ? b : a;
        if (a > b){
            return b;
        }
        return a;
    }

}