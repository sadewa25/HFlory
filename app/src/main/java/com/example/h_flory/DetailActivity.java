package com.example.h_flory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class DetailActivity extends AppCompatActivity implements OnChartValueSelectedListener, View.OnClickListener {

    String suhu_public;
    String humidity_public;

    Button btnMati;

    List<Entry> valsComp1 = new ArrayList<Entry>();
    List<Entry> valsComp2 = new ArrayList<Entry>();

    Integer time1 = 0;
    Integer time2 = 0;

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            DetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i(data.toString(),"Sensor");
                    String suhu;
                    String humidity;
                    try {
                        suhu = data.getString("suhu");
                        humidity = data.getString("humidity");

                        suhu_public = suhu;
                        humidity_public = humidity;
                        Log.e(suhu,"suhu");
                        Log.e(humidity,"humidity");
                        TextView mSuhu = findViewById(R.id.tv_itemSuhu);
                        TextView mHumidity = findViewById(R.id.tv_itemHumadity);
                        suhu = "Suhu:" + suhu_public;
                        humidity = "Humidity: " + humidity_public;
                        mSuhu.setText(suhu);
                        mHumidity.setText(humidity);

                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                        Integer suhuFloat= Integer.parseInt(suhu_public);
                        Integer humFloat = Integer.parseInt(humidity_public);

                        setDataHumidity(humFloat,100,humFloat);
                        setDataSuhu(suhuFloat,100,suhuFloat);

                        time1++;
                        time2++;

                        getChartHumidity(0,100);

                        getChartSuhu(0,100);

                    } catch (JSONException e) {
                        return ;
                    }
                }
            });
        }
    };

    private Socket mSocket;
    {
        try {
//            mSocket = IO.socket("http://167.71.214.196:7500/");
            mSocket = IO.socket("http://192.168.0.27:6500/");
        } catch (URISyntaxException e) {}

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView itemName = findViewById(R.id.tv_itemName);
        TextView waktuText = findViewById(R.id.tv_itemTime);
        ImageView imgPhoto = findViewById(R.id.img_item_photo);
        chart = findViewById(R.id.chart1);
        chart2 = findViewById(R.id.chart2);
        btnMati = findViewById(R.id.btn_mati);

        btnMati.setOnClickListener(this);

        itemName.setText("Alat Satu");
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        waktuText.setText(date);
        mSocket.on("iot_push", onNewMessage);
        mSocket.connect();

    }

    private LineChart chart,chart2;

    private void getChartSuhu(int minAxis,int maxAxis){
        // no description text
        chart2.getDescription().setEnabled(false);
//
//        // enable touch gestures
        chart2.setTouchEnabled(true);

        chart2.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        chart2.setDragEnabled(true);
        chart2.setScaleEnabled(true);
        chart2.setDrawGridBackground(false);
        chart2.setHighlightPerDragEnabled(true);

        // set an alternative background color
        chart2.setBackgroundColor(Color.WHITE);
        chart2.setViewPortOffsets(0f, 0f, 0f, 0f);

        Legend l = chart2.getLegend();
        l.setEnabled(false);

        XAxis xAxis = chart2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        YAxis leftAxis = chart2.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(minAxis);
        leftAxis.setAxisMaximum(maxAxis);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        chart2.invalidate();
    }
    private void setDataSuhu(int count, int range, float suhu) {

        valsComp1.add(new Entry(time1, suhu)); // add one entry per hour

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(valsComp1, "Suhu");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);
        // set data
        chart2.setData(data);
    }

    private void getChartHumidity(int minAxis,int maxAxis){
        // no description text
        chart.getDescription().setEnabled(false);
//
//        // enable touch gestures
        chart.setTouchEnabled(true);

        chart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.WHITE);
        chart.setViewPortOffsets(0f, 0f, 0f, 0f);

        Legend l = chart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(minAxis);
        leftAxis.setAxisMaximum(maxAxis);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        chart.invalidate();
    }
    private void setDataHumidity(int count, int range,float humFloat) {

        valsComp2.add(new Entry(time2,humFloat));

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(valsComp2, "Humidity 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);
        // set data
        chart.setData(data);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mati :
                startActivity(new Intent(this,MainActivity.class));
                Toasty.success(this, "Alat Berhasil Dimatikan", Toast.LENGTH_SHORT, true).show();
                finish();
                break;
        }
    }
}
