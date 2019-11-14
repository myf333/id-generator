package com.myf.id.service.bean;

/**
 * Created by maoyf0503 on 2019-11-7.
 *
 * @author maoyf0503
 */
public enum  IdType {
    /**最大峰值*/
    MAX_PEAK("max-peak"),
    /**最小粒度*/
    MIN_GRANULARITY("min-granularity");
    private String name;


    IdType(String name){
        this.name = name;
    }

    public long value(){
        switch (this){
            case MAX_PEAK:
                return 0;
            case MIN_GRANULARITY:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static IdType parse(String name) {
        if ("min-granularity".equals(name)) {
            return MIN_GRANULARITY;
        }
        else if ("max-peak".equals(name)) {
            return MAX_PEAK;
        }
        return null;
    }

    public static IdType parse(long type) {
        if (type == 1) {
            return MIN_GRANULARITY;
        }
        else if (type == 0) {
            return MAX_PEAK;
        }
        return null;
    }
}
