
package com.hilmiproject.omdutz.mcafee;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hilmiproject.omdutz.mcafee.Interface.LoginRespon;
import com.hilmiproject.omdutz.mcafee.VolleyClass.LupaPassword;
import com.hilmiproject.omdutz.mcafee.VolleyClass.RegisterAndLoginVolley;

public class Login extends Fragment implements TextWatcher{
    private Button          login,register;
    private EditText        email,password;
    private RelativeLayout formlogin,loading;
    

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login,container,false);

        login = (Button)view.findViewById(R.id.tombollogin);
        //register = (Button)view.findViewById(R.id.tombolregister);
        NumberPicker numberPicker;
        email       = (EditText)view.findViewById(R.id.editlogin);
        password    = (EditText)view.findViewById(R.id.passwordlogin);
        formlogin   = (RelativeLayout)view.findViewById(R.id.loginform);
        loading     = (RelativeLayout)view.findViewById(R.id.rloading);
        email       .addTextChangedListener(this);
        password    .addTextChangedListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formlogin.setVisibility(View.INVISIBLE);
                loading  .setVisibility(View.VISIBLE);
                RegisterAndLoginVolley registerAndLoginVolley
                        = new RegisterAndLoginVolley(getContext());
                String e = email    .getText().toString();
                String p = password .getText().toString();
                registerAndLoginVolley.cekLogin(e, p, new LoginRespon() {
                    @Override
                    public void sukses() {
                        startActivity(new Intent(getContext(),Draw.class));
                        getActivity().finish();
                    }
                    @Override
                    public void gagal(String pesan) {
                        formlogin.setVisibility(View.VISIBLE);
                        loading  .setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), pesan,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
      /*  register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getContext(),NewRegister.class);
                startActivity(a);
            }
        });*/
        ((TextView)view.findViewById(R.id.forgot)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.forgot_password);
                ((Button)dialog.findViewById(R.id.kirim))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LupaPassword lupaPassword = new LupaPassword(getContext());
                                String email = ((EditText)dialog.findViewById(R.id.email)).getText()
                                        .toString();
                                lupaPassword.kirimEmail(email);
                                Toast.makeText(getContext(), "Silahkan Cek Email Anda", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        });
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                login.setEnabled(true);
            }else{
                login.setEnabled(false);
            }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}