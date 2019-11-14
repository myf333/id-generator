package com.myf.id.bean;

import java.io.Serializable;

/**
 * Created by maoyf0503 on 2019-11-7.
 *
 * @author maoyf0503
 */
public class Id implements Serializable {
    /**机器编号*/
    private long machine;
    /**序号*/
    private long seq;
    /**时间*/
    private long time;
    /**生成方式*/
    private long genMethod;
    /**部署方式*/
    private long type;
    /**版本*/
    private long version;

    public Id(){

    }

    public Id(long machine, long seq, long time, long genMethod, long type, long version) {
        super();
        this.machine = machine;
        this.seq = seq;
        this.time = time;
        this.genMethod = genMethod;
        this.type = type;
        this.version = version;
    }

    public long getMachine() {
        return machine;
    }

    public void setMachine(long machine) {
        this.machine = machine;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getGenMethod() {
        return genMethod;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Id{" +
                "machine=" + machine +
                ", seq=" + seq +
                ", time=" + time +
                ", genMethod=" + genMethod +
                ", type=" + type +
                ", version=" + version +
                '}';
    }
}
