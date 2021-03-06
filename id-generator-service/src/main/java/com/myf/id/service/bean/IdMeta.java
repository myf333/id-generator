package com.myf.id.service.bean;

/**
 * Created by maoyf0503 on 2019-11-7.
 *
 * @author maoyf0503
 */
public class IdMeta {
    /**机器编码位数*/
    private byte machineBits;
    /**序号位数*/
    private byte seqBits;
    /**时间位数*/
    private byte timeBits;
    /**生成方式位数*/
    private byte genMethodBits;
    /**类型位数*/
    private byte typeBits;
    /**版本位数*/
    private byte versionBits;

    public IdMeta(byte machineBits, byte seqBits, byte timeBits, byte genMethodBits, byte typeBits, byte versionBits) {
        super();
        this.machineBits = machineBits;
        this.seqBits = seqBits;
        this.timeBits = timeBits;
        this.genMethodBits = genMethodBits;
        this.typeBits = typeBits;
        this.versionBits = versionBits;
    }

    public long getSeqBitsStartPos() {
        return machineBits;
    }
    public long getTimeBitsStartPos() {
        return machineBits + seqBits;
    }
    public long getGenMethodBitsStartPos() {
        return machineBits + seqBits + timeBits;
    }
    public long getTypeBitsStartPos() {
        return machineBits + seqBits + timeBits + genMethodBits;
    }
    public long getVersionBitsStartPos() {
        return machineBits + seqBits + timeBits + genMethodBits + typeBits;
    }

    public long getMachineBitsMask() {
        return ~(-1L << machineBits);
    }
    public long getSeqBitsMask() {
        return ~( -1L << seqBits);
    }
    public long getTimeBitsMask() {
        return  ~( -1L << timeBits);
    }
    public long getGenMethodBitsMask() {
        return  ~( -1L << genMethodBits);
    }
    public long getTypeBitsMask() {
        return  ~( -1L << typeBits);
    }
    public long getVersionBitsMask() {
        return  ~( -1L << versionBits);
    }

    public byte getMachineBits() {
        return machineBits;
    }

    public void setMachineBits(byte machineBits) {
        this.machineBits = machineBits;
    }

    public byte getSeqBits() {
        return seqBits;
    }

    public void setSeqBits(byte seqBits) {
        this.seqBits = seqBits;
    }

    public byte getTimeBits() {
        return timeBits;
    }

    public void setTimeBits(byte timeBits) {
        this.timeBits = timeBits;
    }

    public byte getGenMethodBits() {
        return genMethodBits;
    }

    public void setGenMethodBits(byte genMethodBits) {
        this.genMethodBits = genMethodBits;
    }

    public byte getTypeBits() {
        return typeBits;
    }

    public void setTypeBits(byte typeBits) {
        this.typeBits = typeBits;
    }

    public byte getVersionBits() {
        return versionBits;
    }

    public void setVersionBits(byte versionBits) {
        this.versionBits = versionBits;
    }
}
