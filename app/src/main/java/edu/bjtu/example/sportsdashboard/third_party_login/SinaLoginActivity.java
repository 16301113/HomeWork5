package edu.bjtu.example.sportsdashboard.third_party_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.vise.log.ViseLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import com.alibaba.fastjson.JSON;

import edu.bjtu.example.sportsdashboard.R;
import edu.bjtu.example.sportsdashboard.home;

public class SinaLoginActivity extends AppCompatActivity{

    public static void actionStart(Activity activity){

        Intent intent = new Intent(activity,SinaLoginActivity.class);
        activity.startActivity(intent);
    }

    private ImageView ivHead;
    private TextView ivName;

    /**
     * 新浪微博
     */
    private SsoHandler mSsoHandler;

    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;

    private static final String TAG =  "微博登录界面";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sina_login);
        ivHead = (ImageView)findViewById(R.id.iv_head_url_sina);
        ivName=(TextView)findViewById(R.id.name_weibo);
        //ButterKnife.bind(this);
        //新浪微博
        mSsoHandler = new SsoHandler(SinaLoginActivity.this);
        findViewById(R.id.button_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSsoHandler.authorize(new SelfWbAuthListener());
            }
        });
//        findViewById(R.id.btn_test_http).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OkHttpUtils.get()
//                        .url("http://v.juhe.cn/weather/index")
//                        .addParams("format","1")
//                        .addParams("cityname","深圳")
//                        .addParams("dtype","json")
//                        .addParams("key","77a262c554de40916edc78858221b4a9")
//                        .build()
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onError(okhttp3.Call call, Exception e, int id) {
//                                Log.d(TAG, "onError: 【" + e.getMessage()+"】");
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(String response, int id) {
//                                Log.d(TAG,"response【"+response+"】");
//                            }
//                        });
//            }
//        });

        findViewById(R.id.enter_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SinaLoginActivity.this,home.class);
                startActivity(intent);
            }
        });
    }

    private class SelfWbAuthListener implements com.sina.weibo.sdk.auth.WbAuthListener {
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAccessToken = token;
                    if (mAccessToken.isSessionValid()) {

                        ViseLog.d("授权成功...sina。。。");

                        OkHttpUtils.get()
                                .url("https://api.weibo.com/2/users/show.json")
                                .addParams("access_token", mAccessToken.getToken())
                                .addParams("uid", mAccessToken.getUid())
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(okhttp3.Call call, Exception e, int id) {
                                        ViseLog.d("获取失败：" + e.getMessage());
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        ViseLog.d("response:" + response);
                                        JSONObject jsonObject = JSON.parseObject(response);
                                        String headUrl = jsonObject.getString("profile_image_url");
                                        String name=jsonObject.getString("name");
                                        Glide.with(SinaLoginActivity.this).load(headUrl).into(ivHead);
//                                        Glide.with(SinaLoginActivity.this).load(name).into(ivName);
                                    }
                                });

                    }
                }
            });
        }

        @Override
        public void cancel() {
            ViseLog.d("取消授权---sinal---");
        }

        @Override
        public void onFailure(WbConnectErrorMessage errorMessage) {
            Toast.makeText(SinaLoginActivity.this, errorMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //sina login
        if(mSsoHandler!=null){
            mSsoHandler.authorizeCallBack(requestCode,resultCode,data);
        }
    }

}
