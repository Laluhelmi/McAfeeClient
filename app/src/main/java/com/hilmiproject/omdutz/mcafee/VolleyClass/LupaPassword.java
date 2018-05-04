package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by L on 28/11/17.
 */

public class LupaPassword {
    private Context context;
    private RequestQueue requestQueue;

    public LupaPassword(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void kirimEmail(final String email){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Http.url + "forgot"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getString("status").equals("sukses")){
                        Toast.makeText(context, "Silahkan Cek Email Anda", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Email Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    Log.d("eror",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("email",email);
                return data;
            }
        };
        this.requestQueue.add(stringRequest);
    }
}
