package com.hilmiproject.omdutz.mcafee;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.hilmiproject.omdutz.mcafee.ClientPackage.AppDetail;
import com.hilmiproject.omdutz.mcafee.Interface.LoginRespon;
import com.hilmiproject.omdutz.mcafee.VolleyClass.RegisterAndLoginVolley;

public class MainActivity extends AppCompatActivity {

    private ImageView gambar;
    public static boolean isBoatingDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gambar = (ImageView)findViewById(R.id.logo);
        Animation fade = AnimationUtils.loadAnimation(this,R.anim.fade);
        gambar.startAnimation(fade);
        final Handler handler = new Handler();
        long splashDuration = 3000L;
        if(isBoatingDone == true){
            splashDuration = 0L;
        }else isBoatingDone = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation fade2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade2);

                gambar.startAnimation(fade2);
                AppDetail appDetail = new AppDetail(getApplicationContext());
                if(appDetail.isLogin() == true){
                    auth();
                }else{
                    Intent intent = new Intent(MainActivity.this, FirstPage.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left,R.anim.left_exit);
                    finish();
                }
            }
        },splashDuration);


    }
    public void auth(){
        RegisterAndLoginVolley loginVolley = new RegisterAndLoginVolley(this);
        AppDetail appDetail = new AppDetail(this);
        loginVolley.cekLogin(appDetail.getEmail(),
                appDetail.getPassword(), new LoginRespon() {
                    @Override
                    public void sukses() {
                        Intent intent = new Intent(MainActivity.this, Draw.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.left,R.anim.left_exit);
                        finish();
                    }

                    @Override
                    public void gagal(String pesan) {
                        new AppDetail(getApplicationContext()).logout();
                        Toast.makeText(MainActivity.this, "Akun Anda telah diNon-Aktifkan," +
                                        "silahkan hubungin admin KIMS"
                                , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, FirstPage.class);
                        startActivity(intent);
                        finish();
                        //overridePendingTransition(R.anim.left,R.anim.left_exit);
                    }
                });
    }
}
