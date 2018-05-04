    package com.hilmiproject.omdutz.mcafee;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hilmiproject.omdutz.mcafee.VolleyClass.RegisterAndLoginVolley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

    public class Register2 extends Fragment implements TextWatcher{
    private Button  verifikasi;
    private EditText email,password,repassword,rekening;
    private RelativeLayout relativeLayout;
    public static Register2 register2Activity;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_register2,container,false);
            define(view);
            verifikasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    validate(view);
                }
            });
            return view;
        }

        public void validate(View view){
       // RelativeLayout relativeLayout = ((RelativeLayout)findViewById(R.id.relateive));
        if(cekEmail(email.getText().toString()) == false){
            Snackbar.make(relativeLayout,
                    "Format Email Tidak Valid",Snackbar.LENGTH_SHORT).show();
         }else if(password.getText().toString().length() < 6){
            Snackbar.make(((RelativeLayout)view.findViewById(R.id.relateive)),
                    "Password Minimal 6 Karakter",Snackbar.LENGTH_SHORT).show();
        }

        else if(!password .getText().toString().equals(
                repassword.getText().toString()
        )){
            Snackbar.make(((RelativeLayout)view.findViewById(R.id.relateive)),
                    "Password Tidak Sama",Snackbar.LENGTH_SHORT).show();
        }else{
            relativeLayout.setVisibility(View.VISIBLE);
            verifikasi.setVisibility(View.INVISIBLE);
            kirimRegistrasi();
        }
    }

    public void kirimRegistrasi(){
        Map<String,String> dataRegistrasi = new HashMap<>();
       //    dataRegistrasi.put("no_ktp"         ,Register.ktp.getText().toString());
        dataRegistrasi.put("nama_user"      ,Register.nama.getText().toString());
        dataRegistrasi.put("telp"           ,Register.telp.getText().toString());
        dataRegistrasi.put("email"          ,email.getText().toString());
        dataRegistrasi.put("password"       ,password.getText().toString());
        dataRegistrasi.put("id_toko"        ,Register.idtokoDipilih);
        dataRegistrasi.put("jk"      ,Register.jkDipilih);
        dataRegistrasi.put("no_rek",rekening.getText().toString());
        dataRegistrasi.put("re_password"    ,repassword.getText().toString());
        RegisterAndLoginVolley registerAndLoginVolley = new RegisterAndLoginVolley(getContext());
        registerAndLoginVolley.register(dataRegistrasi);
    }

    public boolean cekEmail(String email){
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches();
    }
    public void define(View view){
        verifikasi = (Button)view.findViewById(R.id.tombolverifikasi);
        verifikasi.setEnabled(false);
        password   = (EditText)view.findViewById(R.id.password);
        repassword = (EditText)view.findViewById(R.id.ulangpassword);
        email      = (EditText)view.findViewById(R.id.email);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.relativeloading);
        email.addTextChangedListener(this);
        repassword.addTextChangedListener(this);
        password.addTextChangedListener(this);
        rekening = (EditText) view.findViewById(R.id.rekening);
        register2Activity = Register2.this;

    }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!email.getText().toString().isEmpty()
                    && !verifikasi.getText().toString().isEmpty()
                    && !repassword.getText().toString().isEmpty()){
                verifikasi.setEnabled(true);
            }else{
                verifikasi.setEnabled(false);
            }

        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
