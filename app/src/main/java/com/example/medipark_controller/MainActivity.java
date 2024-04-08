package com.example.medipark_controller;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        LinearLayout modeLayout=findViewById(R.id.mode_controller);
        LinearLayout energyLayout=findViewById(R.id.energy_controller);
        LinearLayout frequencyLayout=findViewById(R.id.frequency_controller);
        LinearLayout pulseWidthLayout=findViewById(R.id.pulse_width_controller);

        Picker modeController=findViewById(R.id.mode_controller).findViewById(R.id.Picker);
        Picker energyController=findViewById(R.id.energy_controller).findViewById(R.id.Picker);
        Picker frequencyController=findViewById(R.id.frequency_controller).findViewById(R.id.Picker);
        Picker pulseWidthController=findViewById(R.id.pulse_width_controller).findViewById(R.id.Picker);


        TextView modeView=modeLayout.findViewById(R.id.TextView);
        TextView energyView=energyLayout.findViewById(R.id.TextView);
        TextView frequencyView=frequencyLayout.findViewById(R.id.TextView);
        TextView pulseWidthView=pulseWidthLayout.findViewById(R.id.TextView);

        modeView.setText("MODE");
        energyView.setText("ENERGY");
        frequencyView.setText("FREQUENCY");
        pulseWidthView.setText("PULSE WIDTH");


        String[] modeDatas={"M1","M2","M3","M4","M5","M6","M7","M8","M9","M10"};
        modeController.setDisplayedDatas(modeDatas);
        String[] energyDatas={"50","45","40","35","30","25","20","15","10","5"};
        energyController.setDisplayedDatas(energyDatas);
        String[] frequencyDatas={"2.0","1.9","1.8","1.7","1.6","1.5","1.4","1.3","1.2","1.1","1.0"
                                ,"0.9","0.8","0.7","0.6","0.5","0.4","0.3","0.2","0.1"};
        frequencyController.setDisplayedDatas(frequencyDatas);
        String[] pulseWidthDatas={"3.0","2.7","2,4","2.1","1.8","1.5","1.2","0.9","0.6","0.3"};
        pulseWidthController.setDisplayedDatas(pulseWidthDatas);

        Picker[] pickers={modeController,energyController,frequencyController,pulseWidthController};
        for(int i=0;i<pickers.length;i++){
            pickers[i].initPicker(50.0f, Color.RED,Color.BLACK);
            pickers[i].setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    // 값이 변경될 때 소리 재생
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.numberpicker_value_change);
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
        }
    }
}