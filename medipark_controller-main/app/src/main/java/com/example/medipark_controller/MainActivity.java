package com.example.medipark_controller;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {
    public static String[] modeDatas={"M1","M2","M3","M4","M5","M6","M7","M8","M9","M10"};
    public static String[] energyDatas={"5","10","15","20","25","30","35","40","45","50"};
    public static String[] frequencyDatas={"0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9","1.0","1.1"
            ,"1.2","1.3","1.4","1.5","1.6","1.7","1.8","1.9","2.0"};
    public static String[] pulseWidthDatas={"0.3","0.6","0.9","1.2","1.5","1.8","2.1","2.4","2.7","3.0"};


    public final float normalTextSize=50.0f;
    public int backgroundColor=Color.WHITE;
    public int textColor=Color.BLACK;
    static String page="controller";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ViewGroup highestLayout=findViewById(R.id.highest_layout);


        Controller modeController=findViewById(R.id.mode_controller);
        modeController.init(MainActivity.this,modeDatas,R.raw.scroll_sound,"MODE");

        Controller energyController=findViewById(R.id.energy_controller);
        energyController.init(MainActivity.this,energyDatas,R.raw.scroll_sound,"ENERGY");

        Controller frequencyController=findViewById(R.id.frequency_controller);
        frequencyController.init(MainActivity.this,frequencyDatas,R.raw.scroll_sound,"FREQUENCY");

        Controller pulseWidthController=findViewById(R.id.pulse_width_controller);
        pulseWidthController.init(MainActivity.this,pulseWidthDatas,R.raw.scroll_sound,"PULSE WIDTH");

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        SaveModel[] saveModels=new SaveModel[modeDatas.length];
        Gson gson=new Gson();
        for(int i=0;i<modeDatas.length;i++){
            String value=sharedPreferences.getString(modeDatas[i],null);
            saveModels[i]=value==null ? new SaveModel(modeDatas[i]): gson.fromJson(value,SaveModel.class);
        }

        modeController.getPicker().setOnValueChangedListener(new Picker.OnValueChangeListener() {
            @Override
            public void onValueChange(Picker picker, int oldVal, int newVal) {
                energyController.getPicker().setValue(saveModels[newVal].getEnergyIndex());
                frequencyController.getPicker().setValue(saveModels[newVal].getFrequencyIndex());
                pulseWidthController.getPicker().setValue(saveModels[newVal].getPulseWidthIndex());

                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.scroll_sound);
                mediaPlayer.start();

                // 재생이 끝나면 리소스 해제
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
        });
        energyController.getPicker().setOnValueChangedListener(new Picker.OnValueChangeListener() {
            @Override
            public void onValueChange(Picker picker, int oldVal, int newVal) {
                saveModels[modeController.getPicker().getValue()].setEnergyIndex(newVal);
            }
        });
        frequencyController.getPicker().setOnValueChangedListener(new Picker.OnValueChangeListener() {
            @Override
            public void onValueChange(Picker picker, int oldVal, int newVal) {
                saveModels[modeController.getPicker().getValue()].setFrequencyIndex(newVal);
            }
        });
        pulseWidthController.getPicker().setOnValueChangedListener(new Picker.OnValueChangeListener() {
            @Override
            public void onValueChange(Picker picker, int oldVal, int newVal) {
                saveModels[modeController.getPicker().getValue()].setPulseWidthIndex(newVal);
            }
        });


        LayoutInflater inflater=getLayoutInflater();
        ImageButton menuButton=findViewById(R.id.menu_button);
        View controllerLayout=findViewById(R.id.controller_layout);
        View menuHeader=inflater.inflate(R.layout.menu_header_field,highestLayout,false);
        MenuPage menuPage=new MenuPage(this,modeDatas,sharedPreferences);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page.equals("controller")){
                    highestLayout.removeView(controllerLayout);
                    page="menu";
                    highestLayout.addView(menuHeader);
                    highestLayout.addView(menuPage);
                }else if(page.equals("menu")){
                    highestLayout.removeView(menuHeader);
                    highestLayout.removeView(menuPage);
                    page="controller";
                    highestLayout.addView(controllerLayout);
                }
            }
        });

        ImageButton saveButton=findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                for(int i=0;i<modeDatas.length;i++) {
                    EditText memoEditText=(EditText)menuPage.linearLayout.getChildAt(i).findViewById(R.id.memo_text_field);
                    saveModels[i].setMemo(memoEditText.getText().toString());
                    String value = gson.toJson(saveModels[i], SaveModel.class);
                    editor.putString(modeDatas[i], value);
                }
                editor.apply();
                menuPage.sync();
            }
        });

        ImageButton settingButton=findViewById(R.id.setting_button);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View settingDialog=inflater.inflate(R.layout.setting_dialog,null);
                Dialog dialog = new Dialog(MainActivity.this);
                GradientDrawable drawable=new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setColor(Color.WHITE);
                drawable.setCornerRadius(20);
                dialog.getWindow().setBackgroundDrawable(drawable);
                dialog.setContentView(settingDialog);
                dialog.show();
            }
        });

    }


    //editor를 전역객체로 설정할 것인가? 또는 SharedPreferences객체

}