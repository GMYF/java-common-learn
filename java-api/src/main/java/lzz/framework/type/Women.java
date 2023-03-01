package lzz.framework.type;

public class Women extends People {
    public Women(String fuse, int age, String name) {
        this.fuse = fuse;
        super.setAge(age);
        super.setName(name);
    }

    private String fuse;

    public String getFuse() {
        return fuse;
    }

    public void setFuse(String fuse) {
        this.fuse = fuse;
    }
}
