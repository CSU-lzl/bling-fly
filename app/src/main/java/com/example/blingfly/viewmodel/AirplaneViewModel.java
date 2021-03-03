package com.example.blingfly.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AirplaneViewModel extends ViewModel{
    private final String appVer = "1.0.0";          //app版本
    //单片机端发送，app端接收
    private MutableLiveData<String> machineName;    //设备名称
    private MutableLiveData<String> machineVer;     //设备版本
    private MutableLiveData<String> flightVer;      //飞控版本
    private MutableLiveData<Float> height;          //飞机当前高度
    private MutableLiveData<Float> heightSpeed;     //飞机高度方向的速度
    private MutableLiveData<Float> distance;        //飞机直线距离
    private MutableLiveData<Float> distanceSpeed;   //飞机直线方向速度
    private MutableLiveData<Boolean> calibrateIMU;  //imu校准
    private MutableLiveData<Boolean> calibrateESC;    //电调校准
    private MutableLiveData<Boolean> takeOff;       //起飞 true为已起飞 false为未起飞

    //app端发送，单片机端接收
    private MutableLiveData<String> ctrlState;     //飞机控制
    private MutableLiveData<String> speedState;    //飞行挡位
    private MutableLiveData<Boolean> greenMode;     //新手模式
    private MutableLiveData<Integer> limitHeight;   //高度限制
    private MutableLiveData<Integer> limitDistance; //距离限制
    private MutableLiveData<Integer> limitReturnHeight; //返航距离限制
    private MutableLiveData<Float> forwardPWM;      //前进方向的pwm 正前负后
    private MutableLiveData<Float> rightwardPWM;      //前进方向的pwm 正右负左
    private MutableLiveData<Float> rotatePWM;      //旋转方向的pwm 正顺负逆
    private MutableLiveData<Float> upwardPWM;      //前进方向的pwm 正上负下
    private MutableLiveData<Boolean> signalSent;       //起飞、降落信号 true为已发送 false为未发送
    private MutableLiveData<Boolean> takePhoto;       //起飞 true为拍照 false为拍视频
    private MutableLiveData<Boolean> comShutter;      //拍照命令
    private MutableLiveData<Boolean> comGyroCali;       //陀螺仪校准命令
    private MutableLiveData<Boolean> comMagCali;       //磁力计校准命令
    private MutableLiveData<Boolean> comEscCali;       //电调校准命令

    public AirplaneViewModel(){
        initData();
    }

    public String getAppVer() {
        return appVer;
    }

    public MutableLiveData<String> getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName.setValue(machineName);
    }

    public MutableLiveData<String> getMachineVer() {
        return machineVer;
    }

    public void setMachineVer(String machineVer) {
        this.machineVer.setValue(machineVer);
    }

    public MutableLiveData<String> getFlightVer() {
        return flightVer;
    }

    public MutableLiveData<Boolean> getGreenMode() {
        return greenMode;
    }

    public void setGreenMode(Boolean greenMode) { this.greenMode.setValue(greenMode); }

    public void setFlightVer(String flightVer) {
        this.flightVer.setValue(flightVer);
    }

    public MutableLiveData<String> getCtrlState() {
        return ctrlState;
    }

    public void setCtrlState(String ctrlState) {
        this.ctrlState.setValue(ctrlState);
    }

    public void changeCtrlState(){
        if (ctrlState.getValue().equals("自稳")){
            ctrlState.setValue("定高");
        }
        else if (ctrlState.getValue().equals("定高")){
            ctrlState.setValue("自稳");
        }
    }

    public MutableLiveData<String> getSpeedState() {
        return speedState;
    }

    public void setSpeedState(String speedState) {
        this.speedState.setValue(speedState);
    }

    public MutableLiveData<Integer> getLimitHeight() {
        return limitHeight;
    }

    public void setLimitHeight(Integer limitHeight) {
        this.limitHeight.setValue(limitHeight+15);
    }

    public MutableLiveData<Integer> getLimitDistance() {
        return limitDistance;
    }

    public void setLimitDistance(Integer limitDistance) {
        this.limitDistance.setValue(limitDistance+20);
    }

    public MutableLiveData<Integer> getLimitReturnHeight() {
        return limitReturnHeight;
    }

    public void setLimitReturnHeight(Integer limitReturnHeight) {
        this.limitReturnHeight.setValue(limitReturnHeight+20);
    }

    public MutableLiveData<Boolean> getCalibrateIMU() {
        return calibrateIMU;
    }

    public void setCalibrateIMU(Boolean calibrateIMU) {
        this.calibrateIMU.setValue(calibrateIMU);
    }

    public MutableLiveData<Boolean> getCalibrateESC() {
        return calibrateESC;
    }

    public void setCalibrateESC(Boolean calibrateESC) {
        this.calibrateESC.setValue(calibrateESC);
    }

    public MutableLiveData<Float> getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height.setValue(height);
    }

    public MutableLiveData<Float> getHeightSpeed() {
        return heightSpeed;
    }

    public void setHeightSpeed(Float heightSpeed) {
        this.heightSpeed.setValue(heightSpeed);
    }

    public MutableLiveData<Float> getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance.setValue(distance);
    }

    public MutableLiveData<Float> getDistanceSpeed() {
        return distanceSpeed;
    }

    public void setDistanceSpeed(Float distanceSpeed) {
        this.distanceSpeed.setValue(distanceSpeed);
    }

    public MutableLiveData<Float> getForwardPWM() {
        return forwardPWM;
    }

    public void setForwardPWM(Float forwardPWM) {
        this.forwardPWM.setValue(forwardPWM);
    }

    public MutableLiveData<Float> getRightwardPWM() {
        return rightwardPWM;
    }

    public void setRightwardPWM(Float rightwardPWM) {
        this.rightwardPWM.setValue(rightwardPWM);
    }

    public MutableLiveData<Float> getRotatePWM() {
        return rotatePWM;
    }

    public void setRotatePWM(Float rotatePWM) {
        this.rotatePWM.setValue(rotatePWM);
    }

    public MutableLiveData<Float> getUpwardPWM() {
        return upwardPWM;
    }

    public void setUpwardPWM(Float upwardPWM) {
        this.upwardPWM.setValue(upwardPWM);
    }

    public MutableLiveData<Boolean> getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(Boolean takeOff) {
        this.takeOff.setValue(takeOff);
    }

    public MutableLiveData<Boolean> getSignalSent() {
        return signalSent;
    }

    public void setSignalSent(Boolean signalSent) {
        this.signalSent.setValue(signalSent);
    }

    public MutableLiveData<Boolean> getTakePhoto() {
        return takePhoto;
    }

    public void setTakePhoto(Boolean takePhoto) {
        this.takePhoto.setValue(takePhoto);
    }

    public MutableLiveData<Boolean> getComShutter() {
        return comShutter;
    }

    public MutableLiveData<Boolean> getComGyroCali() {
        return comGyroCali;
    }

    public MutableLiveData<Boolean> getComMagCali() {
        return comMagCali;
    }

    public MutableLiveData<Boolean> getComEscCali() {
        return comEscCali;
    }

    public void setComGyroCali(Boolean comGyroCali) {
        this.comGyroCali.setValue(comGyroCali);
    }

    public void setComMagCali(Boolean comMagCali) {
        this.comMagCali.setValue(comMagCali);
    }

    public void setComEscCali(Boolean comEscCali) {
        this.comEscCali.setValue(comEscCali);
    }

    public void setComShutter(Boolean comShutter) {
        this.comShutter.setValue(comShutter);
    }

    private void initData(){
        if (machineName == null){
            machineName = new MutableLiveData<>();
            machineName.setValue("N/A");
        }

        if (machineVer == null){
            machineVer = new MutableLiveData<>();
            machineVer.setValue("N/A");
        }

        if (flightVer == null){
            flightVer = new MutableLiveData<>();
            flightVer.setValue("N/A");
        }

        if (greenMode == null){
            greenMode = new MutableLiveData<>();
            greenMode.setValue(true);
        }

        if (ctrlState == null){
            ctrlState = new MutableLiveData<>();
            ctrlState.setValue("自稳");
        }

        if (speedState == null){
            speedState = new MutableLiveData<>();
            speedState.setValue("平稳挡");
        }

        if (limitHeight == null){
            limitHeight = new MutableLiveData<>();
            limitHeight.setValue(15);
        }

        if (limitDistance == null){
            limitDistance = new MutableLiveData<>();
            limitDistance.setValue(20);
        }

        if (limitReturnHeight == null){
            limitReturnHeight = new MutableLiveData<>();
            limitReturnHeight.setValue(20);
        }

        if (calibrateIMU == null){
            calibrateIMU = new MutableLiveData<>();
            calibrateIMU.setValue(false);
        }

        if (calibrateESC == null){
            calibrateESC = new MutableLiveData<>();
            calibrateESC.setValue(true);
        }

        if (height == null){
            height = new MutableLiveData<>();
            height.setValue((float) 0.0);
        }

        if (heightSpeed == null){
            heightSpeed = new MutableLiveData<>();
            heightSpeed.setValue((float) 0.0);
        }

        if (distance == null){
            distance = new MutableLiveData<>();
            distance.setValue((float) 0.0);
        }

        if (distanceSpeed == null){
            distanceSpeed = new MutableLiveData<>();
            distanceSpeed.setValue((float) 0.0);
        }

        if (forwardPWM == null){
            forwardPWM = new MutableLiveData<>();
            forwardPWM.setValue((float) 0.0);
        }

        if (rightwardPWM == null){
            rightwardPWM = new MutableLiveData<>();
            rightwardPWM.setValue((float) 0.0);
        }

        if (rotatePWM == null){
            rotatePWM = new MutableLiveData<>();
            rotatePWM.setValue((float) 0.0);
        }

        if (upwardPWM == null){
            upwardPWM = new MutableLiveData<>();
            upwardPWM.setValue((float) -1.0);
        }

        if (takeOff == null){
            takeOff = new MutableLiveData<>();
            takeOff.setValue(false);
        }

        if (signalSent == null){
            signalSent = new MutableLiveData<>();
            signalSent.setValue(false);
        }

        if (takePhoto == null){
            takePhoto = new MutableLiveData<>();
            takePhoto.setValue(true);
        }

        if (comShutter == null){
            comShutter = new MutableLiveData<>();
            comShutter.setValue(false);
        }

        if (comGyroCali == null){
            comGyroCali = new MutableLiveData<>();
            comGyroCali.setValue(false);
        }

        if (comMagCali == null){
            comMagCali = new MutableLiveData<>();
            comMagCali.setValue(false);
        }

        if (comEscCali == null){
            comEscCali = new MutableLiveData<>();
            comEscCali.setValue(false);
        }
    }
}
