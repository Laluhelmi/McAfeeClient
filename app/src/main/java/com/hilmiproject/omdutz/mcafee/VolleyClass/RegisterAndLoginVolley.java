package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hilmiproject.omdutz.mcafee.ClientPackage.AppDetail;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.LoginRespon;
import com.hilmiproject.omdutz.mcafee.Register;
import com.hilmiproject.omdutz.mcafee.RegisterSukses;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by L on 16/11/17.
 */

public class RegisterAndLoginVolley extends MyVolley{

    private List<String> listIdToko;

    public RegisterAndLoginVolley(Context context){
        super(context);
    }

    public void setListNamaToko(final Spinner spinner){
                           listIdToko = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(
                Http.url + "toko", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    List<String> listToko   = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0;i < jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        listToko    .add(object.getString("nama_toko"));
                        listIdToko  .add(object.getString("id_toko"   ));
                    }
                    ArrayAdapter<String> adapter  = new ArrayAdapter<String>(context,
                            android.support.design.R.layout.support_simple_spinner_dropdown_item,
                            listToko);
                    adapter.setDropDownViewResource(
                            android.support.design.R.layout.support_simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        },onVolleyError());
        stringRequest.setRetryPolicy(this.requstTimeOut());
        this.requestQueue.add(stringRequest);
    }
    public List<String> getListIdtoko(){
        return listIdToko;
    }
    public void register(final Map<String,String> data){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Http.url + "user/index_pojo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("registrasi", response);
//                NewRegister.activity.finish();
                Register.progressBar.dismiss();
                context.startActivity(new Intent(context, RegisterSukses.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Register.progressBar.dismiss();
                context.startActivity(new Intent(context, RegisterSukses.class));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return data;
            }
        };
        stringRequest.setRetryPolicy(this.requstTimeOut());
        this.requestQueue.add(stringRequest);
    }

    public void cekLogin(final String email, final String password, final LoginRespon respon){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Http.url + "login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hasil login",response);
                        try{
                            JSONObject object = new JSONObject(response);
                            if(object.getString("status").equals("succes ")){
                                if(new AppDetail(context).isLogin() == false){
                                    JSONObject data = object.getJSONArray("data")
                                            .getJSONObject(0);
                                    String email    = data.getString("email");
                                    String token    = data.getString("token");
                                    AppDetail appDetail = new AppDetail(context);
                                    appDetail.setEmail   (email);
                                    appDetail.setNama(data.getString("nama_user"));
                                    appDetail.setPassword(password);
                                    appDetail.setToken(token);
                                    if(data.getString("no_rek").equals("")){
                                        appDetail.setRek(false);
                                    }else appDetail.setRek(true);
                                    appDetail.setJk(data.getString("j_kelamin"));
                                    appDetail.setStatus(data.getString("status"));
                                }
                                respon.sukses();
                            }else{
                                String pesan = object.getString("message");
                                respon.gagal(pesan);
                            }

                        }catch (Exception e){
                            Log.d("Error",e.getMessage());
                        }
                    }
                },onVolleyError()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("email"        ,email);
                data.put("password"     ,password);
                if(new AppDetail(context).isLogin() == false){
                    data.put("tokenandroid" , FirebaseInstanceId.getInstance().getToken());
                }
                return data;
            }
        };

        stringRequest.setRetryPolicy(this.requstTimeOut());
        this.requestQueue.add(stringRequest);
    }

    public DefaultRetryPolicy requstTimeOut(){
         return new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }
    public Response.ErrorListener onVolleyError(){
          return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError){
                    Toast.makeText(context, "Periksa Jaringan Internet Anda ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
