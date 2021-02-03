package core;

import java.math.BigDecimal;

public class BigCoreBean {
    private String name;
    private CoreBean coreBean;

    @Override
    public String toString() {
        return "BigCoreBean{" +
                "name='" + name + '\'' +
                ", coreBean=" + coreBean +
                '}';
    }

    public BigCoreBean(String name, CoreBean coreBean) {
        this.name = name;
        this.coreBean = coreBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoreBean getCoreBean() {
        return coreBean;
    }

    public void setCoreBean(CoreBean coreBean) {
        this.coreBean = coreBean;
    }
}
