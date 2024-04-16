package com.example.medipark_controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MenuPage extends ScrollView {
    String[] modes;

    LayoutInflater inflater;
    public View[] settings;
    public LinearLayout linearLayout=new LinearLayout(this.getContext());

    public MenuPage(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public MenuPage(Context context, String[] modes){
        super(context);
        this.modes=modes;
        this.inflater=LayoutInflater.from(context);

        ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        this.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParam=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParam);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        this.setPadding(0,0,0,250);

        this.settings=new View[modes.length];
        for(int i=0;i< modes.length;i++){
            settings[i]=inflater.inflate(R.layout.menu_text_field,linearLayout,false);
            ((TextView)settings[i].findViewById(R.id.mode_text_field)).setText(modes[i]);
            linearLayout.addView(settings[i]);
        }
        this.addView(linearLayout);
        this.setFillViewport(true);
    }
}
