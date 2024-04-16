package com.example.medipark_controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

public class Controller extends LinearLayout {
    public TextView textView;
    public Picker picker;

    public Controller(Context context) {
        this(context,null);
    }
    public Controller(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public void init(Activity activity,String[] displayedDatas, int soundFileId, String text){
        LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) this.getLayoutParams();
        params.topMargin=15;
        params.height=ViewGroup.LayoutParams.MATCH_PARENT;
        this.setLayoutParams(params);

        textView=new TextView(activity);
        setTextView(textView);
        textView.setText(text);
        this.addView(textView);

        picker=new Picker(activity);
        setPicker(picker);
        picker.initPicker(activity,soundFileId,displayedDatas);
        this.addView(picker);

        Typeface typeface = ResourcesCompat.getFont(activity, R.font.marking_font);
        textView.setTypeface(typeface);
    }

    public void setTextView(TextView textView){
        ViewGroup.MarginLayoutParams params=new ViewGroup.MarginLayoutParams(
                400,
                60
        );
        params.setMargins(0,0,0,20);
        textView.setLayoutParams(params);

        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(23.0f);
    }

    public void setPicker(Picker picker){
        ViewGroup.MarginLayoutParams params =new ViewGroup.MarginLayoutParams(
                400,
                700
        );
        params.setMargins(70,0,70,250);
        picker.setLayoutParams(params);

        picker.setBackgroundColor(Color.BLACK);
        picker.setTextColor(Color.WHITE);
        picker.setSelectedTextColor(Color.WHITE);
    }

    public Picker getPicker(){
        return this.picker;
    }
    public String getSelectedValue(){
        int index=picker.getValue();
        return picker.displayedDatas[index];
    }

}
