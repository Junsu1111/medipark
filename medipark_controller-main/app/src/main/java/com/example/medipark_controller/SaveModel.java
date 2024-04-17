package com.example.medipark_controller;

public class SaveModel {
    private String mode;
    private int energyIndex=0;
    private int frequencyIndex=0;
    private int pulseWidthIndex=0;
    private String memo;
    public SaveModel(String mode,int energy, int frequency, int pulseWidth){
        this.mode=mode;
        this.energyIndex=energy;
        this.frequencyIndex=frequency;
        this.pulseWidthIndex=pulseWidth;
    }
    public SaveModel(String mode){
        this.mode=mode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getEnergyIndex() {
        return energyIndex;
    }

    public void setEnergyIndex(int energyIndex) {
        this.energyIndex = energyIndex;
    }

    public int getFrequencyIndex() {
        return frequencyIndex;
    }

    public void setFrequencyIndex(int frequencyIndex) {
        this.frequencyIndex = frequencyIndex;
    }

    public int getPulseWidthIndex() {
        return pulseWidthIndex;
    }

    public void setPulseWidthIndex(int pulseWidthIndex) {
        this.pulseWidthIndex = pulseWidthIndex;
    }
}
