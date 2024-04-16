package com.example.medipark_controller;

public class SaveModel {
    int energyIndex;
    int frequencyIndex;
    int pulseWidthIndex;
    public SaveModel(int energy, int frequency, int pulseWidth){
        this.energyIndex=energy;
        this.frequencyIndex=frequency;
        this.pulseWidthIndex=pulseWidth;
    }
}
