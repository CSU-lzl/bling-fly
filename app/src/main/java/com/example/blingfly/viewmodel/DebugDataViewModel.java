package com.example.blingfly.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DebugDataViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Integer> accx;
    private MutableLiveData<Integer> accy;
    private MutableLiveData<Integer> accz;
    private MutableLiveData<Integer> gyrox;
    private MutableLiveData<Integer> gyroy;
    private MutableLiveData<Integer> gyroz;
    private MutableLiveData<Integer> magx;
    private MutableLiveData<Integer> magy;
    private MutableLiveData<Integer> magz;
    private MutableLiveData<Float> pitch;
    private MutableLiveData<Float> yaw;
    private MutableLiveData<Float> roll;
    private MutableLiveData<Float> barHeight;
    private MutableLiveData<Float> ultrasoundHeight;
    private MutableLiveData<Float> fuseHeight;
    private MutableLiveData<Float> pwm1;
    private MutableLiveData<Float> pwm2;
    private MutableLiveData<Float> pwm3;
    private MutableLiveData<Float> pwm4;

    public DebugDataViewModel(){
        initLiveData();
    }

    public void setAccx(Integer accx) {
        this.accx.setValue(accx);
    }

    public void setAccy(Integer accy) {
        this.accy.setValue(accy);
    }

    public void setAccz(Integer accz) {
        this.accz.setValue(accz);
    }

    public void setGyrox(Integer gyrox) {
        this.gyrox.setValue(gyrox);
    }

    public void setGyroy(Integer gyroy) {
        this.gyroy.setValue(gyroy);
    }

    public void setGyroz(Integer gyroz) {
        this.gyroz.setValue(gyroz);
    }

    public void setMagx(Integer magx) {
        this.magx.setValue(magx);
    }

    public void setMagy(Integer magy) {
        this.magy.setValue(magy);
    }

    public void setMagz(Integer magz) {
        this.magz.setValue(magz);
    }

    public void setPitch(Float pitch) {
        this.pitch.setValue(pitch);
    }

    public void setYaw(Float yaw) {
        this.yaw.setValue(yaw);
    }

    public void setRoll(Float roll) {
        this.roll.setValue(roll);
    }

    public void setBarHeight(Float barHeight) {
        this.barHeight.setValue(barHeight);
    }

    public void setUltrasoundHeight(Float ultrasoundHeight) {
        this.ultrasoundHeight.setValue(ultrasoundHeight);
    }

    public void setFuseHeight(Float fuseHeight) {
        this.fuseHeight.setValue(fuseHeight);
    }

    public void setPwm1(Float pwm1) {
        this.pwm1.setValue(pwm1);
    }

    public void setPwm2(Float pwm2) {
        this.pwm2.setValue(pwm2);
    }

    public void setPwm3(Float pwm3) {
        this.pwm3.setValue(pwm3);
    }

    public void setPwm4(Float pwm4) {
        this.pwm4.setValue(pwm4);
    }

    public MutableLiveData<Integer> getAccx() {
        return accx;
    }

    public MutableLiveData<Integer> getAccy() {
        return accy;
    }

    public MutableLiveData<Integer> getAccz() {
        return accz;
    }

    public MutableLiveData<Integer> getGyrox() {
        return gyrox;
    }

    public MutableLiveData<Integer> getGyroy() {
        return gyroy;
    }

    public MutableLiveData<Integer> getGyroz() {
        return gyroz;
    }

    public MutableLiveData<Integer> getMagx() {
        return magx;
    }

    public MutableLiveData<Integer> getMagy() {
        return magy;
    }

    public MutableLiveData<Integer> getMagz() {
        return magz;
    }

    public MutableLiveData<Float> getPitch() {
        return pitch;
    }

    public MutableLiveData<Float> getYaw() {
        return yaw;
    }

    public MutableLiveData<Float> getRoll() {
        return roll;
    }

    public MutableLiveData<Float> getBarHeight() {
        return barHeight;
    }

    public MutableLiveData<Float> getUltrasoundHeight() {
        return ultrasoundHeight;
    }

    public MutableLiveData<Float> getFuseHeight() {
        return fuseHeight;
    }

    public MutableLiveData<Float> getPwm1() {
        return pwm1;
    }

    public MutableLiveData<Float> getPwm2() {
        return pwm2;
    }

    public MutableLiveData<Float> getPwm3() {
        return pwm3;
    }

    public MutableLiveData<Float> getPwm4() {
        return pwm4;
    }

    private void initLiveData(){
        if (accx == null){
            accx = new MutableLiveData<>();
            accx.setValue(0);
        }
        if (accy == null){
            accy = new MutableLiveData<>();
            accy.setValue(0);
        }
        if (accz == null){
            accz = new MutableLiveData<>();
            accz.setValue(0);
        }
        if (gyrox == null){
            gyrox = new MutableLiveData<>();
            gyrox.setValue(0);
        }
        if (gyroy == null){
            gyroy = new MutableLiveData<>();
            gyroy.setValue(0);
        }
        if (gyroz == null){
            gyroz = new MutableLiveData<>();
            gyroz.setValue(0);
        }
        if (magx == null){
            magx = new MutableLiveData<>();
            magx.setValue(0);
        }
        if (magy == null){
            magy = new MutableLiveData<>();
            magy.setValue(0);
        }
        if (magz == null){
            magz = new MutableLiveData<>();
            magz.setValue(0);
        }
        if (pitch == null){
            pitch = new MutableLiveData<>();
            pitch.setValue((float) 0.0);
        }
        if (yaw == null){
            yaw = new MutableLiveData<>();
            yaw.setValue((float) 0.0);
        }
        if (roll == null){
            roll = new MutableLiveData<>();
            roll.setValue((float) 0.0);
        }
        if (barHeight == null){
            barHeight = new MutableLiveData<>();
            barHeight.setValue((float) 0.0);
        }
        if (ultrasoundHeight == null){
            ultrasoundHeight = new MutableLiveData<>();
            ultrasoundHeight.setValue((float) 0.0);
        }
        if (fuseHeight == null){
            fuseHeight = new MutableLiveData<>();
            fuseHeight.setValue((float) 0.0);
        }
        if (pwm1 == null){
            pwm1 = new MutableLiveData<>();
            pwm1.setValue((float) 0.0);
        }
        if (pwm2 == null){
            pwm2 = new MutableLiveData<>();
            pwm2.setValue((float) 0.0);
        }
        if (pwm3 == null){
            pwm3 = new MutableLiveData<>();
            pwm3.setValue((float) 0.0);
        }
        if (pwm4 == null){
            pwm4 = new MutableLiveData<>();
            pwm4.setValue((float) 0.0);
        }
    }
}