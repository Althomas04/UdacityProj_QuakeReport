package com.example.android.quakereport;

/**
 * Created by al.thomas04 on 5/30/2016.
 */
public class quakeInfo {

    private Double mMagnitude;
    private String mLocation;
    private Long mTimeInMillisec;
    private String mUrl;

    public quakeInfo(Double magnitude, String location, Long timeInMillisec, String url){
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMillisec = timeInMillisec;
        mUrl = url;

    }

    public Double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() { return mLocation; }

    public Long getTimeInMillisec() {
        return mTimeInMillisec;
    }

    public String getUrl() { return mUrl; }

}
