package com.hfi.wsTest;

/**
 * Created by zmh on 2016-12-1.
 */
public class Time {

    public Time() {
        this.timeStamp = System.currentTimeMillis();
    }

    /**
     * 当前时间
     */
    private long timeStamp;

    /**
     * 间隔时间
     */
    private long intervalTime;

    /**
     * 间隔时间
     */
    private long maxIntervalTime;


    /**
     * 间隔时间
     */
    private long minIintervalTime;

    /**
     * 间隔时间
     */
    private long avgIintervalTime;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        //计算间隔时间
        intervalTime = timeStamp - this.timeStamp;
        if(intervalTime>maxIntervalTime){
            maxIntervalTime=intervalTime;
        }
        if(minIintervalTime==0||intervalTime<minIintervalTime){
            minIintervalTime=intervalTime;
        }

        this.timeStamp = timeStamp;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime) {


        this.intervalTime = intervalTime;
    }

    public long getMaxIntervalTime() {
        return maxIntervalTime;
    }

    public void setMaxIntervalTime(long maxIntervalTime) {
        this.maxIntervalTime = maxIntervalTime;
    }

    public long getMinIintervalTime() {
        return minIintervalTime;
    }

    public void setMinIintervalTime(long minIintervalTime) {
        this.minIintervalTime = minIintervalTime;
    }

    public long getAvgIintervalTime() {
        return avgIintervalTime;
    }

    public void setAvgIintervalTime(long avgIintervalTime) {
        this.avgIintervalTime = avgIintervalTime;
    }

    @Override
    public String toString() {
        return "Time{" +
                "timeStamp=" + timeStamp +
                ", intervalTime=" + intervalTime +
                ", maxIntervalTime=" + maxIntervalTime +
                ", minIintervalTime=" + minIintervalTime +
                ", avgIintervalTime=" + avgIintervalTime +
                '}';
    }
}
