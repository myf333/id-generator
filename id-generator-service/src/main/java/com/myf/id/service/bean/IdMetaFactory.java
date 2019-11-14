package com.myf.id.service.bean;

/**
 * Created by maoyf0503 on 2019-11-7.
 *
 * @author maoyf0503
 */
public class IdMetaFactory {
    /**
     * 10位机器码，20位序号，30位时间，时间单位秒，2位生成方式，1位类型，1位版本
     * */
    private static IdMeta maxPeak = new IdMeta((byte) 10, (byte) 20, (byte) 30, (byte) 2, (byte) 1, (byte) 1);
    /**
     * 10位机器码，10位序号，40位时间，时间单位毫秒，2位生成方式，1位类型，1位版本
     * */
    private static IdMeta minGranularity = new IdMeta((byte) 10, (byte) 10, (byte) 40, (byte) 2, (byte) 1, (byte) 1);

    public static IdMeta getIdMeta(IdType idType){
        if(IdType.MAX_PEAK.equals(idType)){
            return maxPeak;
        }else if(IdType.MIN_GRANULARITY.equals(idType)){
            return minGranularity;
        }
        return null;
    }
}
