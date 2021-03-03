package com.example.blingfly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blingfly.viewmodel.AirplaneViewModel;
import com.example.blingfly.viewmodel.DebugDataViewModel;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private AirplaneViewModel mViewModel;
    private DebugDataViewModel dataViewModel;
    //MQTT相关定义
    private String host = "tcp://api.easylink.io";
    private String userName = "android";
    private String passWord = "android";
    private String mqtt_id = "516229828"; //定义成自己的QQ号
    private String mqtt_sub_topic = "516229828"; //为了保证你不受到别人的消息
    private String mqtt_pub_topic = "516229828_pc"; //为了保证你不受到别人的消息
    private ScheduledExecutorService scheduler;
    private MqttClient client;
    private MqttConnectOptions options;
    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //最先执行地方
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AirplaneViewModel.class);
        setContentView(R.layout.activity_menu);

        //初始化mqtt 连接mqtt
        Mqtt_init();
        startReconnect();

        handler = new Handler() {
            @SuppressLint("SetTextI18n")

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1: //开机校验更新回传
                        break;
                    case 2:  // 反馈回传
                        break;
                    case 3: //MQTT 收到消息
                        MqttReceiveHandler(msg);
//                        Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case 30:  //连接失败
                        Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 31:   //连接成功
                        Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                        try {
                            client.subscribe(mqtt_sub_topic, 1);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        //viewmodel观察并发送mqtt报文
        MqttSendHandler();
    }

    private void publishmessageplus(String topic,String message2)
    {
        if (client == null || !client.isConnected()) {
            return;
        }
        MqttMessage message = new MqttMessage();
        message.setPayload(message2.getBytes());
        try {
            client.publish(topic,message);
        } catch (MqttException e) {

            e.printStackTrace();
        }
    }

    private void Mqtt_init()
    {
        try {
            //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(host, mqtt_id,
                    new MemoryPersistence());
            //MQTT的连接设置
            options = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            //设置连接的用户名
            options.setUserName(userName);
            //设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            //设置回调
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    //连接丢失后，一般在这里面进行重连
                    System.out.println("connectionLost----------");
                    //startReconnect();
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里
                    System.out.println("deliveryComplete---------"
                            + token.isComplete());
                }
                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    //subscribe后得到的消息会执行到这里面
                    System.out.println("messageArrived----------");
                    Message msg = new Message();
                    msg.what = 3;   //收到消息标志位
                    msg.obj = topicName + "---" + message.toString();
                    handler.sendMessage(msg);    // hander 回传
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void Mqtt_connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!(client.isConnected()) )  //如果还未连接
                    {
                        client.connect(options);
                        Message msg = new Message();
                        msg.what = 31;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 30;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
    private void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!client.isConnected()) {
                    Mqtt_connect();
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    private void MqttSendHandler(){
        mViewModel.getCtrlState().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                publishmessageplus(mqtt_sub_topic,"CtrlState:"+s+"}");
            }
        });
        mViewModel.getSpeedState().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                publishmessageplus(mqtt_sub_topic,"SpeedState:"+s+"}");
            }
        });
        mViewModel.getGreenMode().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                publishmessageplus(mqtt_sub_topic,"GreenMode:"+String.valueOf(aBoolean)+"}");
            }
        });
        mViewModel.getLimitHeight().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                publishmessageplus(mqtt_sub_topic,"LimitHeight:"+String.valueOf(integer)+"}");
            }
        });
        mViewModel.getLimitDistance().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                publishmessageplus(mqtt_sub_topic,"LimitDistance:"+String.valueOf(integer)+"}");
            }
        });
        mViewModel.getLimitReturnHeight().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                publishmessageplus(mqtt_sub_topic,"LimitReturnHeight:"+String.valueOf(integer)+"}");
            }
        });
        mViewModel.getForwardPWM().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                publishmessageplus(mqtt_sub_topic,"ForwardPWM:"+String.valueOf(aFloat)+"}");
            }
        });
        mViewModel.getUpwardPWM().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                publishmessageplus(mqtt_sub_topic,"UpwardPWM:"+String.valueOf(aFloat)+"}");
            }
        });
        mViewModel.getRightwardPWM().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                publishmessageplus(mqtt_sub_topic,"RightwardPWM:"+String.valueOf(aFloat)+"}");
            }
        });
        mViewModel.getRotatePWM().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                publishmessageplus(mqtt_sub_topic,"RotatePWM:"+String.valueOf(aFloat)+"}");
            }
        });
        mViewModel.getSignalSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == true){
                    publishmessageplus(mqtt_sub_topic,"SignalSent:"+String.valueOf(true)+"}");
                    mViewModel.setSignalSent(false);
                }
            }
        });
        mViewModel.getComGyroCali().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == true){
                    publishmessageplus(mqtt_sub_topic,"ComGyroCali:"+String.valueOf(true)+"}");
                    mViewModel.setComGyroCali(false);
                }
            }
        });
        mViewModel.getComMagCali().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == true){
                    publishmessageplus(mqtt_sub_topic,"ComMagCali:"+String.valueOf(true)+"}");
                    mViewModel.setComMagCali(false);
                }
            }
        });
        mViewModel.getComEscCali().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == true){
                    publishmessageplus(mqtt_sub_topic,"ComEscCali:"+String.valueOf(true)+"}");
                    mViewModel.setComEscCali(false);
                }
            }
        });
        mViewModel.getComShutter().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == true){
                    if (mViewModel.getTakePhoto().getValue() == true)
                        publishmessageplus(mqtt_sub_topic,"ComShutter:"+"photo"+"}");
                    else {
                        publishmessageplus(mqtt_sub_topic,"ComShutter:"+"video"+"}");
                    }
                    mViewModel.setComShutter(false);
                }
            }
        });
    }

    private void MqttReceiveHandler(Message msg){
        String target = msg.obj.toString().substring(14,msg.obj.toString().indexOf(":"));   //14为topic长度加2,第13位为判断viewmodel类
        String parameter = msg.obj.toString().substring(msg.obj.toString().indexOf("}"),msg.obj.toString().indexOf("}"));

        //AirplaneViewModel
        if (msg.obj.toString().substring(13,14).equals("1")) {
            switch (target) {
                case "MachineName":
                    mViewModel.setMachineName(parameter);
                    break;
                case "MachineVer":
                    mViewModel.setMachineVer(parameter);
                    break;
                case "FlightVer":
                    mViewModel.setFlightVer(parameter);
                    break;
                case "Height":
                    mViewModel.setHeight(Float.valueOf(parameter));
                    break;
                case "HeightSpeed":
                    mViewModel.setHeightSpeed(Float.valueOf(parameter));
                    break;
                case "Distance":
                    mViewModel.setDistance(Float.valueOf(parameter));
                    break;
                case "DistanceSpeed":
                    mViewModel.setDistanceSpeed(Float.valueOf(parameter));
                    break;
                case "CalibrateIMU":
                    mViewModel.setCalibrateIMU(Boolean.valueOf(parameter));
                    break;
                case "CalibrateESC":
                    mViewModel.setCalibrateESC(Boolean.valueOf(parameter));
                    break;
                case "TakeOff":
                    mViewModel.setTakeOff(Boolean.valueOf(parameter));
                    break;
            }
        }
        //DebugDataViewModel
        else if (msg.obj.toString().substring(13,14).equals("0")){
            switch (target) {
                case "Accx":
                    dataViewModel.setAccx(Integer.valueOf(parameter));
                    break;
                case "Accy":
                    dataViewModel.setAccy(Integer.valueOf(parameter));
                    break;
                case "Accz":
                    dataViewModel.setAccz(Integer.valueOf(parameter));
                    break;
                case "Gyrox":
                    dataViewModel.setGyrox(Integer.valueOf(parameter));
                    break;
                case "Gyroy":
                    dataViewModel.setGyroy(Integer.valueOf(parameter));
                    break;
                case "Gyroz":
                    dataViewModel.setGyroz(Integer.valueOf(parameter));
                    break;
                case "Magx":
                    dataViewModel.setMagx(Integer.valueOf(parameter));
                    break;
                case "Magy":
                    dataViewModel.setMagy(Integer.valueOf(parameter));
                    break;
                case "Magz":
                    dataViewModel.setMagz(Integer.valueOf(parameter));
                    break;
                case "Pitch":
                    dataViewModel.setPitch(Float.valueOf(parameter));
                case "Yaw":
                    dataViewModel.setYaw(Float.valueOf(parameter));
                case "Roll":
                    dataViewModel.setRoll(Float.valueOf(parameter));
                case "BarHeight":
                    dataViewModel.setBarHeight(Float.valueOf(parameter));
                case "UltrasoundHeight":
                    dataViewModel.setUltrasoundHeight(Float.valueOf(parameter));
                case "FuseHeight":
                    dataViewModel.setFuseHeight(Float.valueOf(parameter));
                case "Pwm1":
                    dataViewModel.setPwm1(Float.valueOf(parameter));
                case "Pwm2":
                    dataViewModel.setPwm2(Float.valueOf(parameter));
                case "Pwm3":
                    dataViewModel.setPwm3(Float.valueOf(parameter));
                case "Pwm4":
                    dataViewModel.setPwm4(Float.valueOf(parameter));
            }
        }
    }
}