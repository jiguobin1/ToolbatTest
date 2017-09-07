package jiguobin.bc.com.toolbattest.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import jiguobin.bc.com.toolbattest.Bean.ErrorDataBaen;
import jiguobin.bc.com.toolbattest.R;

public class CodeActivity extends AppCompatActivity {
    @InjectView(R.id.code)
    EditText code;
    @InjectView(R.id.btn_code)
    Button btnCode;
    private  String phone_code;
    private String phone;
    private Gson mGson=new Gson();
    private static final String PHONE_CODE_ERROR = "603";//输入的手机号错误
    private static final String YANZHENG_CODE_ERROR = "468";//输入验证码错误
    private static final String SEND_CODE_COUNT_ERROR = "477";//当天发送验证码次数超限

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        ButterKnife.inject(this);


        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {//回调完成

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CodeActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){ //获取验证码成功

                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){ //返回支持发送验证码的国家列表

                    }else if(event ==SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CodeActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                            }
                        });
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

    @OnClick(R.id.btn_code)
    public void onClick() {//验证验证码
        phone_code = code.getText().toString().trim();
        if(!TextUtils.isEmpty(phone_code)){
            //提交验证码
            Intent intent = getIntent();
            //获取从解析页面传入的url
            phone = intent.getStringExtra("phone");
            SMSSDK.submitVerificationCode("+86",phone,phone_code);
        }
    }
}
