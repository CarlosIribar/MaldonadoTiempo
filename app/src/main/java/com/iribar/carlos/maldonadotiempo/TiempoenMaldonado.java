package com.iribar.carlos.maldonadotiempo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TiempoenMaldonado extends ActionBarActivity {

 private static final String TAG ="tiempo Maldonado";
 private EstadoTiempo mEstadoTiempo;

    @InjectView(R.id.timeLabel) TextView mTimeLabel;
    @InjectView(R.id.temperaturaLabel) TextView mTemperaturaLabel;
    @InjectView(R.id.humedadLabel) TextView mHumedadLabel;
    @InjectView(R.id.lluviaLabel) TextView mLluviaLabel;
    @InjectView(R.id.summaryLabel) TextView mSummaryLabel;
    @InjectView(R.id.iconView)     ImageView mIconView;
    @InjectView(R.id.vientoLabel) TextView mVientoLabel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempoen_maldonado);
        ButterKnife.inject(this);

        double  latitude = -34.9;
        double longitud= -54.949999999999990;
        String query="?units=ca&lang=es";

        String apiKey="fcd095731a0f9f26c34e76bd784b21ea/";
        String url = "https://api.forecast.io/forecast/" + apiKey + latitude + "," + longitud+
                query;
        if (isNetAvaible()) {


            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonObject =response.body().string();
                        Log.v(TAG, jsonObject);
                        if (response.isSuccessful()) {

                            mEstadoTiempo = getDetallesTiempo(jsonObject);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG,mEstadoTiempo.getHumedad()+"dfdf");
                                    actulizarVista();
                                }
                            });


                        } else {
                            alertAboutError();
                        }

                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught", e);
                    }
                }
            });

        }
        else {
            Toast.makeText(this,getString(R.string.noConexion),Toast.LENGTH_LONG ).show();
        }

    }

    private void actulizarVista() {
        mTemperaturaLabel.setText(mEstadoTiempo.getTempertura()+"");
        mHumedadLabel.setText(mEstadoTiempo.getHumedad()+" %");
        mLluviaLabel.setText(mEstadoTiempo.getChanceLlover()+"%");
        mVientoLabel.setText(mEstadoTiempo.getViento()+"km/h");
        mSummaryLabel.setText(mEstadoTiempo.getSummary()+"");
        mTimeLabel.setText(mEstadoTiempo.getTiempoConFormato());
        Drawable drawable = getResources().getDrawable(mEstadoTiempo.getIconId());
        mIconView.setImageDrawable(drawable);




    }

    private EstadoTiempo getDetallesTiempo(String jsonObject) throws JSONException {

            JSONObject tiempo = new JSONObject(jsonObject);
            JSONObject tiempoActual = tiempo.getJSONObject("currently");

        EstadoTiempo estadoTiempo = new EstadoTiempo();
        estadoTiempo.setHumedad(tiempoActual.getDouble("humidity"));
        Log.d("ASASS", estadoTiempo.getHumedad() + "");
        estadoTiempo.setTiempo(tiempoActual.getLong("time"));
        estadoTiempo.setTempertura(tiempoActual.getDouble("temperature"));
        estadoTiempo.setChanceLlover(tiempoActual.getDouble("precipProbability"));
        estadoTiempo.setIcon(tiempoActual.getString("icon"));
        estadoTiempo.setSummary(tiempoActual.getString("summary"));
        estadoTiempo.setTimeZone(tiempo.getString("timezone"));
        estadoTiempo.setViento(tiempoActual.getDouble("windSpeed"));

        Log.d(TAG,estadoTiempo.getTiempoConFormato());


           Log.i(TAG,"Este es la zona"+tiempoActual);
        return estadoTiempo;

    }

    private boolean isNetAvaible() {
        
        ConnectivityManager conected = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conected.getActiveNetworkInfo();
        boolean isAvaible= false;

        if (networkInfo != null && networkInfo.isConnected()) {
          isAvaible = true;  // fetch data
        }
        return isAvaible;
    }




    private void alertAboutError() {
        AlertDialogaFragment dialogo =  new AlertDialogaFragment();
        dialogo.show(getFragmentManager(),"error");
    }


}
