package papa.play.ksd.baba;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends Activity {

    Button complete;
    Button btnCode;
    boolean isOk = false;
    BroadcastReceiver broadcastReceiver;
    MyHandler handler = new MyHandler();
    EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_register);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.register_title);
        complete = (Button)findViewById(R.id.completereg);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,RegisterSuccess.class));

            }
        });
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(broadcastReceiver,new IntentFilter("papa.play.ksd.baba.finish"));

        btnCode = (Button)findViewById(R.id.getcode);
        phone = (EditText)findViewById(R.id.phone);
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.getVerificationCode("86",phone.getText().toString());
            }
        });


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SMSSDK.registerEventHandler(handler);
        SMSSDK.getSupportedCountries();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SMSSDK.unregisterEventHandler(handler);
    }

    private class MyHandler extends EventHandler {
        @Override
        public void onRegister() {
            super.onRegister();
        }

        @Override
        public void beforeEvent(int event, Object data) {
            super.beforeEvent(event, data);
        }

        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    // 返回支持发送验证码的国家列表
                    // ArrayList<HashMap<String, Object>>
                    ArrayList<HashMap<String, Object>> contries =
                            (ArrayList<HashMap<String, Object>>) data;
                    for (HashMap<String, Object> country : contries) {
                        String zone = (String) country.get("zone");
                        String rule = (String) country.get("rule");

                        if (zone.equals("86")) {
                            isOk = true;
                            break;
                        }
                    }

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 请求发送验证码，无返回
                    // null

                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 校验验证码，返回校验的手机和国家代码
                    // HashMap<String, Object>

                } else if (event == SMSSDK.EVENT_GET_CONTACTS) {
                    // 获取手机内部的通信录列表
                    // ArrayList<HashMap<String, Object>>
                } else if (event == SMSSDK.EVENT_SUBMIT_USER_INFO) {
                    // 提交应用内的用户资料
                    // null
                } else if (event == SMSSDK.EVENT_GET_FRIENDS_IN_APP) {
                    // 获取手机通信录在当前应用内的用户列表
                    // ArrayList<HashMap<String, Object>>
                } else if (event == SMSSDK.EVENT_GET_NEW_FRIENDS_COUNT) {
                    // 获取手机通信录在当前应用内的新用户个数
                    // Integer
                }
            } else if (result == SMSSDK.RESULT_ERROR) {
                ((Throwable) data).printStackTrace();
            }
            super.afterEvent(event, result, data);
        }

        @Override
        public void onUnregister() {
            super.onUnregister();
        }
    }
}
