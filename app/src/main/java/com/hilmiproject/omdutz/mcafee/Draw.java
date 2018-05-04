package com.hilmiproject.omdutz.mcafee;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hilmiproject.omdutz.mcafee.ClientPackage.AmbilGambar;
import com.hilmiproject.omdutz.mcafee.ClientPackage.AppDetail;
import com.hilmiproject.omdutz.mcafee.Fragment.Lokasi;
import com.hilmiproject.omdutz.mcafee.Fragment.PointReward;
import com.hilmiproject.omdutz.mcafee.Fragment.Riwayat;
import com.hilmiproject.omdutz.mcafee.Fragment.reward.Hadiah;
import com.hilmiproject.omdutz.mcafee.Fragment.reward.Reward;
import com.hilmiproject.omdutz.mcafee.Interface.AfterScan;
import com.hilmiproject.omdutz.mcafee.Interface.AmbilPoint;
import com.hilmiproject.omdutz.mcafee.Interface.LoadScanInfo;
import com.hilmiproject.omdutz.mcafee.Interface.LogoutInterface;
import com.hilmiproject.omdutz.mcafee.Interface.NotifikasiInterface;
import com.hilmiproject.omdutz.mcafee.VolleyClass.GetPointVolley;
import com.hilmiproject.omdutz.mcafee.VolleyClass.LogoutRequest;
import com.hilmiproject.omdutz.mcafee.VolleyClass.Notifikas;
import com.hilmiproject.omdutz.mcafee.VolleyClass.ScanQrcodeVolley;
import com.hilmiproject.omdutz.mcafee.zxking.ZxkingCameraCostum;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;

public class Draw extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView email;
    private TextView nama,notiftext;
    public static TextView point;
    public static TabLayout tabLayout;
    public static String globalPoint;
    private ImageView avatar;
    private RelativeLayout loncengnotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        tabLayout       = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
       // drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        getSupportActionBar().setElevation(0);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // email = (TextView)navigationView.getHeaderView(0).findViewById(R.id.email);
        nama  = (TextView)navigationView.getHeaderView(0).findViewById(R.id.nama);
        point = (TextView)navigationView.getHeaderView(0).findViewById(R.id.point);
        avatar= (ImageView)navigationView.getHeaderView(0).findViewById(R.id.avatar);
        loncengnotif = (RelativeLayout)findViewById(R.id.loncengnotif);
        notiftext = (TextView)findViewById(R.id.textlonceng);
        if(!new AppDetail(this).getJk().equals("Laki-laki")){
            avatar.setImageResource(R.mipmap.ic_launcher3);
        }
        //buat value
        updatePoint();
        nama .setText(buatNamaJadiRapi(new AppDetail(this).getNama()));
        avatarOnclick();;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ini_Fragment,new PointReward());
        fragmentTransaction.commit();
        tabLayout.setVisibility(View.VISIBLE);
        loncengnotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Draw.this, com.hilmiproject.omdutz.mcafee.Notif.class));
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(updateNotifAngka,
                new IntentFilter("updateAngka"));
        permission();
    }
    public void getNotifCount(){
        new Notifikas(this).hitungBanyakNotifikasi(
                new AppDetail(this).getEmail(), new NotifikasiInterface() {
                    @Override
                    public void getData(@NotNull String hasil) {
                        if(Integer.parseInt(hasil) > 0){
                            notiftext.setVisibility(View.VISIBLE);
                            setBadge(getApplicationContext(),Integer.parseInt(hasil));
                            notiftext.setText(hasil);
                        }else{
                            notiftext.setVisibility(View.GONE);
                        }
                    }
                }
        );
    }

    BroadcastReceiver updateNotifAngka = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getNotifCount();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Reward.rewards = null;
        Hadiah.hadiahPojosst = null;
    }

    public void avatarOnclick(){
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Profil.class);
                intent.putExtra("point",point.getText().toString());
                startActivity(intent);
            }
        });
    }
    public void updatePoint(){
        new GetPointVolley(this).point(new AmbilPoint() {
            @Override
            public void setPoint(String p) {
                if(p == "null"){
                    globalPoint = null;
                    point.setText("Point : 0");
                }else{
                    point.setText("Point : "+p);
                    globalPoint = p;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                getSupportFragmentManager().popBackStack(
                        getSupportFragmentManager().getBackStackEntryAt(0).getId(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }else{
                AlertDialog.Builder  builder = new AlertDialog.Builder(Draw.this);
                builder.setMessage("Apakah Anda Yakin Keluar ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Draw.super.onBackPressed();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.draw, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void setTabLayoutGone(){
        if(tabLayout.getVisibility() == View.VISIBLE){
            tabLayout.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        setTabLayoutGone();
        if (id == R.id.reward) {
            getSupportActionBar().setTitle("Point Reward");
            fragmentTransaction.replace(R.id.ini_Fragment,new PointReward()).addToBackStack(null);
            //tabLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.scan) {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_pilih_scan);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            Button manual = (Button)dialog.findViewById(R.id.inputmanual);
            manual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog1 = new Dialog(Draw.this);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.input_qr_manual);
                    dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    ((Button)dialog1.findViewById(R.id.cariqrcode)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EditText qr = (EditText)dialog1.findViewById(R.id.inputqrmanual);
                            String kodeQr = qr.getText().toString();
                            carQrcode(kodeQr);
                            dialog1.dismiss();
                            dialog.dismiss();
                        }
                    });
                    dialog1.show();
                }
            });
            ((Button)dialog.findViewById(R.id.scanqr)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scanCode();
                    dialog.dismiss();
                }
            });
            dialog.show();
            // getSupportActionBar().setTitle("Scan Code");
           // fragmentTransaction.replace(R.id.ini_Fragment, new ScanCode()).addToBackStack(null);
        } else if (id == R.id.lokasi) {
            if(getSupportFragmentManager().findFragmentByTag("lokasi") != null){
                getSupportActionBar().setTitle("Lokasi");
                fragmentTransaction.replace(R.id.ini_Fragment,
                        getSupportFragmentManager().findFragmentByTag("lokasi"),"lokasi")
                       .addToBackStack(null);
            } else {
                getSupportActionBar().setTitle("Lokasi");
                fragmentTransaction.replace(R.id.ini_Fragment, new Lokasi(),"lokasi")
                .addToBackStack(null);
            }
        }else if (id == R.id.FAQ) {
            startActivity(new Intent(getApplicationContext(),FaqActivity.class));
        }
        else if (id == R.id.service){
            startActivity(new Intent(this,CustomerServiceActivity.class));
        }
        else if (id == R.id.histori){
            if(getSupportFragmentManager().findFragmentByTag("riwayat") != null){
                getSupportActionBar().setTitle("Riwayat");
                fragmentTransaction.replace(R.id.ini_Fragment,
                        getSupportFragmentManager().findFragmentByTag("riwayat"),"riwayat")
                        .addToBackStack(null);
            } else {
                getSupportActionBar().setTitle("Riwayat");
                fragmentTransaction.replace(R.id.ini_Fragment, new Riwayat(), "riwayat")
                        .addToBackStack(null);
            }
        }
        else if(id == R.id.logout){
            String token = FirebaseInstanceId.getInstance().getToken();
            new LogoutRequest(this).keluar(token, new LogoutInterface() {
                @Override
                public void lakukanSesuatu() {
                    new AppDetail(Draw.this).logout();
                    Intent intent = new Intent(Draw.this, FirstPage.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left,R.anim.left_exit);
                    finish();
                }
            });
        }
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //fungsi buat scan qr code
    public void scanCode(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        1);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        1);
            }
        }else{
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCameraId(0);
            intentIntegrator.setCaptureActivity(ZxkingCameraCostum.class);
            intentIntegrator.setPrompt("");
            intentIntegrator.initiateScan();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotifCount();
        if(FaqActivity.isPlayedBefore) FaqActivity.isPlayedBefore = false;
        FaqActivity.getLastMinute = 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null && intentResult.getContents() != null){
           carQrcode(intentResult.getContents());
        }
    }
    public void carQrcode(String content){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu ....");
        progressDialog.show();
        ScanQrcodeVolley scanQrcodeVolley = new ScanQrcodeVolley(getApplicationContext());
        scanQrcodeVolley.scanQr(content,
                new AfterScan() {
                    @Override
                    public void afterscan(String respon) {
                        Intent intent2 = new Intent("riwayat_update");
                        LocalBroadcastManager.getInstance(Draw.this).sendBroadcast(intent2);
                        progressDialog.dismiss();
                        getImageAfterScan(respon);
                    }
                });
    }
    public void getImageAfterScan(String respon){
        try {
            JSONObject obj  = new JSONObject(respon);
            JSONObject data = obj.getJSONObject("data");
            JSONObject produk= data.getJSONArray("produk")
                    .getJSONObject(0);
            ImageView gambar;
            updatePoint();
            final Dialog dialog = new Dialog(Draw.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setContentView(R.layout.hasil_scan);
            gambar = (ImageView)dialog.findViewById(R.id.gambar);
            ((TextView)dialog.findViewById(R.id.nama_produk))
                    .setText(produk.getString("nama_produk"));
            ((TextView)dialog.findViewById(R.id.point))
                    .setText("Point : "+produk.getString("point"));
            String url = "http://kimsmembers.com/"+produk.getString("url");
            ((ProgressBar)dialog.findViewById(R.id.loading))
                    .setVisibility(View.GONE);
            ((RelativeLayout)dialog.findViewById(R.id.ket))
                    .setVisibility(View.VISIBLE);
            //gambar.setImageResource(R.drawable.barang);
            ((Button)dialog.findViewById(R.id.ok))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
            dialog.show();
            AmbilGambar ambilGambar = new AmbilGambar(url, gambar,
                    Draw.this, new LoadScanInfo() {
                @Override
                public void setelahScan() {
                    ((ProgressBar)dialog.findViewById(R.id.loading))
                            .setVisibility(View.GONE);
                    ((RelativeLayout)dialog.findViewById(R.id.ket))
                            .setVisibility(View.VISIBLE);
                    ((Button)dialog.findViewById(R.id.ok))
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                }
            });
            ambilGambar.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String buatNamaJadiRapi(String nama){
        int i = 0;
        String namaYangRapi = null;
        for(char a : nama.toCharArray()){
            if(i == 0){
                namaYangRapi = (String.valueOf(a)).toUpperCase();
            }else if (nama.toCharArray()[i-1] == ' '){
                namaYangRapi += (String.valueOf(a)).toUpperCase();
            }else{
                namaYangRapi += (String.valueOf(a));
            }
            i++;
        }
        return namaYangRapi;
    }
    public static void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }
    public void permission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
    }
}
