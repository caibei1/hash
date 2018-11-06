package hash;

public class Block {
    private int height;
    private String minHash;
    private String mediumHash;
    private String largeHash;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMinHash() {
        return minHash;
    }

    public void setMinHash(String minHash) {
        this.minHash = minHash;
    }

    public String getMediumHash() {
        return mediumHash;
    }

    public void setMediumHash(String mediumHash) {
        this.mediumHash = mediumHash;
    }

    public String getLargeHash() {
        return largeHash;
    }

    public void setLargeHash(String largeHash) {
        this.largeHash = largeHash;
    }
}
