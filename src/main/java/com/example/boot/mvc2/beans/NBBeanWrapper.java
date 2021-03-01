package com.example.boot.mvc2.beans;

public class NBBeanWrapper {
    private Object wrappedInstance;
    //代理？
    private Class<?> wrappedClass;

    public NBBeanWrapper(Object instance) {
        this.wrappedInstance = instance;
        this.wrappedClass = instance.getClass();
    }

    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    public void setWrappedInstance(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Class<?> getWrappedClass() {
        return wrappedClass;
    }

    public void setWrappedClass(Class<?> wrappedClass) {
        this.wrappedClass = wrappedClass;
    }
}
