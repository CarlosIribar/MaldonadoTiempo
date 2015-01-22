package com.iribar.carlos.maldonadotiempo;

import android.util.Log;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Carlos Iribar on 19/01/2015.
 */
public class EstadoTiempo {
    private final String TAG= "Estado del tiempo";
    private String mIcon;
    private long mTiempo;
    private double mHumedad;
    private double mTempertura;
    private double mViento;
    private double mChanceLlover;
    private String mSummary;
    private String mTimeZone;

    public double getViento() {
        Log.d(TAG,mViento+"");
        return mViento;
    }

    public void setViento(double viento) {
        mViento = viento;
    }


    public String getTimeZone() {
        return mTimeZone;
    }

    public String getTiempoConFormato() {
        SimpleDateFormat formatt = new SimpleDateFormat("h:mm a");
        formatt.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dataTime = new Date(getTiempo() * 1000);
        String timeString = formatt.format(dataTime);
        return timeString;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getIcon() {
        return mIcon;
    }
    public int getIconId () {
      int iconId = R.drawable.clear_day;

        if (mIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (mIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (mIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }
        return iconId;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTiempo() {
        return mTiempo;
    }

    public void setTiempo(long tiempo) {
        mTiempo = tiempo;
    }

    public int getHumedad() {
        Log.d(TAG,mHumedad+"");
        return (int) Math.round(mHumedad*100);
    }

    public void setHumedad(double humedad) {
        mHumedad = humedad;
    }

    public int getTempertura() {
        Log.d(TAG,mTempertura+"");
        return (int) Math.round(mTempertura);
    }

    public void setTempertura(double tempertura) {
        mTempertura = tempertura;
    }

    public int getChanceLlover() {

        Log.d(TAG,mChanceLlover+"");
        return (int) Math.round(mChanceLlover *100);
    }

    public void setChanceLlover(double chanceLlover) {
        mChanceLlover = chanceLlover;
    }

    public String getSummary() {
        Log.d(TAG,mSummary);
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
