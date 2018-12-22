package edu.bjtu.example.sportsdashboard;

import android.content.Intent;
import android.graphics.Paint;
//import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sports.sportclub.DataModel.User;

import edu.bjtu.example.sportsdashboard.third_party_login.Config;
import edu.bjtu.example.sportsdashboard.third_party_login.SinaLoginActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import com.sports.sportclub.api.BmobService;
import com.sports.sportclub.api.Client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//轮播

import com.daimajia.slider.library.SliderLayout;

public class MainActivity extends AppCompatActivity{
    private BmobUser current_user;
    private DrawerLayout drawerLayout;
    private SliderLayout sliderShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "24d2dd9a00667b645f55acaef2d11e16");
        current_user = BmobUser.getCurrentUser();
        if (current_user != null) {
            jump2main();
        } else {
            //设置下划线
            TextView forget_text = findViewById(R.id.forget_text);
            forget_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            //设置监听
            forget_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "该功能未开放", Toast.LENGTH_LONG).show();
                }
            });

            TextView signup_text = findViewById(R.id.register_text);
            signup_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            signup_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, register.class);
                    startActivity(intent);
                }
            });
        }

        /**
         * 初始化微博登录
         */
        initSinaLogin();

        /**
         * 启动微博登录
         */
        //设置下划线
        TextView weiboLogin_text=findViewById(R.id.weiboLogin);
        weiboLogin_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        //设置监听
        weiboLogin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SinaLoginActivity.actionStart(MainActivity.this);
            }
        });
    }

    private void initSinaLogin() {
        WbSdk.install(this,new AuthInfo(this, Config.APP_KEY_SINA, Config.REDIRECT_URL,
                Config.SCOPE));
    }

    public void Login(View view) {
        EditText username_input = findViewById(R.id.username_input);
        EditText password_input = findViewById(R.id.password_input);

        final String username = username_input.getText().toString();
        String password = password_input.getText().toString();

        //使用retrofit实现登录请求
        BmobService service = Client.retrofit.create(BmobService.class);
        Call<ResponseBody> call = service.getUser(username,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code() == 200){
                    showmsg("登陆成功");
                    jump2main();
                }
                else if(response.code() == 400) {
                    showmsg("用户名或密码错误");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showmsg(t.getMessage());
            }
        });
    }

    public boolean Validation(User user){

        return false;
    }
    public void showmsg(String str){
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }

    //跳转至主界面
    public void jump2main(){
        Intent intent = new Intent(this,home.class);
        startActivity(intent);
        finish();
    }


}
