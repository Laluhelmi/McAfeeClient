package com.hilmiproject.omdutz.mcafee;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hilmiproject.omdutz.mcafee.ClientPackage.AppDetail;
import com.hilmiproject.omdutz.mcafee.Interface.LoadScanInfo;
import com.hilmiproject.omdutz.mcafee.Interface.ProfilInterface;
import com.hilmiproject.omdutz.mcafee.Pojo.ProfilPojo;
import com.hilmiproject.omdutz.mcafee.VolleyClass.ProfilVolley;

import java.util.HashMap;
import java.util.Map;

public class Profil extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses ...");

        ((TextView)findViewById(R.id.ubahprofil)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Map<String,String> map = new HashMap<>();
                EditText reks = (EditText)findViewById(R.id.norek);
                final String nama   = ((EditText)findViewById(R.id.nama)).getText().toString();
                String notelp = ((EditText)findViewById(R.id.notelp)).getText().toString();
                String rek    = reks.getText().toString();
                if(!rek.equals(null)){
                    new AppDetail(getApplicationContext()).setRek(true);
                }
                map.put("email",new AppDetail(getApplicationContext()).getEmail());
                map.put("nama_user",nama);
                map.put("telp",notelp);
                map.put("an", ((TextView)findViewById(R.id.atasnama)).getText().toString());
                map.put("bank",((EditText)findViewById(R.id.jnisbank)).getText().toString());
                map.put("cabang",((EditText)findViewById(R.id.cabang)).getText().toString());
                map.put("no_rek",rek);
                map.put("ktp",((EditText)findViewById(R.id.atasnama)).getText().toString());
                new ProfilVolley(getApplicationContext()).ubahProfil(map, new LoadScanInfo() {
                    @Override
                    public void setelahScan() {
                        progressDialog.dismiss();
                        Toast.makeText(Profil.this,
                                "Profil Berhasil diubah", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        AppDetail appDetail = new AppDetail(getApplicationContext());
                        appDetail.setNama(nama);
                      //  appDetail.setEmail();
                    }
                });
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!new AppDetail(this).getJk().equals("Laki-laki")){
            ((ImageView)findViewById(R.id.avatar)).setImageResource(R.mipmap.ic_launcher3);
        }
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((TextView)findViewById(R.id.nama_user)).setText(buatNamaJadiRapi(new AppDetail(this).getNama()));
        ((TextView)findViewById(R.id.point)).setText(getIntent()
                .getStringExtra("point"));
        ((TextView)findViewById(R.id.status)).setText("Status : Aktif");
        ProfilVolley profilVolley = new ProfilVolley(this);
        profilVolley.ambilProfil(new ProfilInterface() {
            @Override
            public void displayProfil(ProfilPojo profilPojo) {
                ((TextView)findViewById(R.id.atasnama)).setText(profilPojo.getAtasnama().toUpperCase());
                ((TextView)findViewById(R.id.nama)).setText(buatNamaJadiRapi
                        (new AppDetail(getApplicationContext()).getNama()));
                ((TextView)findViewById(R.id.email)).setText(new AppDetail(getApplicationContext()).getEmail());
                ((TextView)findViewById(R.id.notelp)).setText(profilPojo.getTelp());
                ((TextView)findViewById(R.id.norek)).setText(profilPojo.getNorek());
                ((TextView)findViewById(R.id.namatoko)).setText(profilPojo.getNama_toko());
                ((TextView)findViewById(R.id.jnisbank)).setText(profilPojo.getJenis_bank().toUpperCase());
                ((TextView)findViewById(R.id.cabang)).setText(profilPojo.getCabang().toUpperCase());
                ((TextView)findViewById(R.id.alamattoko)).setText(profilPojo.getAlamattoko());


            }
        },new AppDetail(this).getEmail());
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
}
