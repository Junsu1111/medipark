package com.example.medipark_controller;

import static com.example.medipark_controller.MainActivity.energyDatas;
import static com.example.medipark_controller.MainActivity.frequencyDatas;
import static com.example.medipark_controller.MainActivity.modeDatas;
import static com.example.medipark_controller.MainActivity.pulseWidthDatas;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.autofill.SaveRequest;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

public class MenuPage extends ScrollView {
    String[] modes;

    LayoutInflater inflater;
    SharedPreferences sharedPreferences;
    public View[] settings;
    public LinearLayout linearLayout=new LinearLayout(this.getContext());

    public MenuPage(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public MenuPage(Context context, String[] modes, SharedPreferences sharedPreferences){
        super(context);
        this.modes=modes;
        this.inflater=LayoutInflater.from(context);
        this.sharedPreferences=sharedPreferences;

        ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        this.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParam=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParam);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        this.setPadding(0,0,0,100);

        this.settings=new View[modes.length];
        for(int i=0;i< modes.length;i++){
            settings[i]=inflater.inflate(R.layout.menu_text_field,linearLayout,false);
            ((TextView)settings[i].findViewById(R.id.mode_text_field)).setText(modes[i]);
            settings[i].setTag(i);
            linearLayout.addView(settings[i]);
        }

        this.addView(linearLayout);
        this.setFillViewport(true);
        this.sync();
    }
    public void sync(){
        Gson gson=new Gson();
        for(int i=0;i<modeDatas.length;i++){
            String json=sharedPreferences.getString(modes[i],null);
            SaveModel saveModel=gson.fromJson(json,SaveModel.class);
            if(saveModel!=null) {
                LinearLayout layout=(LinearLayout)linearLayout.getChildAt(i);
                Log.d("sync",layout.toString());
                ((EditText) (layout.getChildAt(1))).setText(saveModel.getMemo());
                ((TextView) (layout.getChildAt(2))).setText(energyDatas[saveModel.getEnergyIndex()]);
                ((TextView) (layout.getChildAt(3))).setText(frequencyDatas[saveModel.getFrequencyIndex()]);
                ((TextView) (layout.getChildAt(4))).setText(pulseWidthDatas[saveModel.getPulseWidthIndex()]);
            }
        }
    }
}
