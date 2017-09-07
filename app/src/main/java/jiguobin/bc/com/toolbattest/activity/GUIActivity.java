package jiguobin.bc.com.toolbattest.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import jiguobin.bc.com.toolbattest.Bean.ErrorDataBaen;
import jiguobin.bc.com.toolbattest.R;

public class GUIActivity extends AppCompatActivity {
    private Context mContext;
    private EventHandler eh;
    private EditText number;
    private String phone;
    private Gson mGson=new Gson();
    private static final String PHONE_CODE_ERROR = "603";//输入的手机号错误
    private static final String YANZHENG_CODE_ERROR = "468";//输入验证码错误
    private static final String SEND_CODE_COUNT_ERROR = "477";//当天发送验证码次数超限

    private TimeCount time,time1;
    private Button duanxin,txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui);
        time = new TimeCount(60000, 1000);//倒计时类
        time1 = new TimeCount(60000, 1000);//倒计时类
        duanxin= (Button) findViewById(R.id.duanxin);
        txt= (Button) findViewById(R.id.txt);
        mContext=this;
        number= (EditText) findViewById(R.id.phone);
        SMSSDK.initSDK(mContext,"1d5c2b1271cf2","bda34207272b458da2bb91465ba5c00b");
        eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    Log.e("TAG","回调完成"+result);
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Log.e("TAG","提交验证码成功");
                        //提交验证码成功
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        Log.e("TAG","获取验证码成功");
                        Intent intent=new Intent(GUIActivity.this,CodeActivity.class);
                        intent.putExtra("phone",phone);
                        startActivity(intent);

                        //获取验证码成功
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        Log.e("TAG","返回支持发送验证码的国家列表");
                        //返回支持发送验证码的国家列表
                    }else if(event ==SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE){
                        Log.d("sss","获取语音验证码成功");
                        if (!TextUtils.isEmpty(phone)){
                            Intent intent = new Intent(mContext, CodeActivity.class);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                        }
                    }
                }else{
                    String message = ((Throwable) data).getMessage();
                    Log.e("TAG", message);
                    final ErrorDataBaen errorDataBaen = mGson.fromJson(
                            message, ErrorDataBaen.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("TAG", errorDataBaen.getStatus());
                            if (errorDataBaen.getStatus().equals(PHONE_CODE_ERROR)) {
                                Snackbar.make(getCurrentFocus(),
                                        "请输入正确的手机号！", Snackbar.LENGTH_SHORT).show();
                            } else if (errorDataBaen.getStatus().equals(YANZHENG_CODE_ERROR)) {
                                Snackbar.make(getCurrentFocus(),
                                        "验证码错误！", Snackbar.LENGTH_SHORT).show();
                            } else if (errorDataBaen.getStatus().equals(SEND_CODE_COUNT_ERROR)) {
                                Snackbar.make(getCurrentFocus(),
                                        "该手机号接收验证码的次数已经超限！", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    protected  void onDestroy(){
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

    public void onClick(View view) {
        Log.e("TAG","onClick");
        phone = number.getText().toString().trim();
        if(!TextUtils.isEmpty(phone)){
            SMSSDK.getVerificationCode("+86",phone);//短信验证
            time.start();//启动倒计时类
        }

    }

    public void phoneonClick(View view) {
        SMSSDK.getVoiceVerifyCode("+86",phone);//语音验证
        time.start();//启动倒计时类
    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            duanxin.setBackgroundColor(Color.parseColor("#B6B6D8"));
            duanxin.setClickable(false);
            duanxin.setText("("+millisUntilFinished / 1000 +") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            duanxin.setText("重新获取验证码");
            duanxin.setClickable(true);
            duanxin.setBackgroundColor(Color.parseColor("#4EB84A"));

        }
    }


}
