package core;

import java.math.BigDecimal;

public class CoreBean {
    private String name;
    private int version;
    private BigDecimal bigDecimal;

    @Override
    public String toString() {
        return "CoreBean{" +
                "name='" + name + '\'' +
                ", version=" + version +
                ", bigDecimal=" + bigDecimal +
                '}';
    }

    public CoreBean(String name, int version, BigDecimal bigDecimal) {
        this.name = name;
        this.version = version;
        this.bigDecimal = bigDecimal;
        System.out.println("----------------" + this.toString());
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public CoreBean() {
    }

    public CoreBean(String name, int version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
