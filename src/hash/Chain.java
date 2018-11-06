package hash;

import java.math.BigInteger;
import java.util.Map;

public class Chain {
    private Map<Integer, Block> blockMap;
    private int lastHeight;

    public Map<Integer, Block> getBlockMap() {
        return blockMap;
    }

    public void setBlockMap(Map<Integer, Block> blockMap) {
        this.blockMap = blockMap;
    }

    public int getLastHeight() {
        return lastHeight;
    }

    public void setLastHeight(int lastHeight) {
        this.lastHeight = lastHeight;
    }





    //获取最新3个区块计算后的hash
    public String GetHash () {
        if (lastHeight == 1) {
            return calHash(blockMap.get(1),null,null);
        }

        if (lastHeight == 2) {
            return calHash(blockMap.get(1),blockMap.get(2),null);
        }

        return calHash(blockMap.get(lastHeight-2),blockMap.get(lastHeight-1),blockMap.get(lastHeight));
    }

    public  String calHash(Block b1,Block b2,Block b3){

        String result = "";
        if (b1 != null) {
            result += HashUtil.getSHA256StrJava(b1.getMinHash());
            result += b1.getMediumHash();
            result = HashUtil.getSHA256StrJava(result);
            result += b1.getLargeHash();
            result = HashUtil.getSHA256StrJava(result);
        }

        if (b2 != null){
            result += b2.getMinHash();
            result = HashUtil.getSHA256StrJava(result);
            result += b2.getMediumHash();
            result = HashUtil.getSHA256StrJava(result);
            result += b2.getLargeHash();
            result = HashUtil.getSHA256StrJava(result);
        }

        if (b3 != null){
            result += b3.getMinHash();
            result = HashUtil.getSHA256StrJava(result);
            result += b3.getMediumHash();
            result = HashUtil.getSHA256StrJava(result);
            result += b3.getLargeHash();
            result = HashUtil.getSHA256StrJava(result);
        }
        return result;
    }

    //获取最新1个区块最小的 计算后的hash
    public String GetMinHash(){
        String minH = blockMap.get(lastHeight).getMinHash();
        return HashUtil.getSHA256StrJava(minH);
    }

}
