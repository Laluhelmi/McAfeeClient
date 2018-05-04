package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.MyGoodListener;

import java.util.Map;

/**
 * Created by L on 25/11/17.
 */

public class MyVolley {
    protected Context context;
    protected RequestQueue requestQueue;
    public static int post = Request.Method.POST;
    public static int get = Request.Method.GET;

    public MyVolley(Context context){
        this.context      = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }
    public DefaultRetryPolicy requstTimeOut(){
        return new DefaultRetryPolicy(15000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }
    public Response.ErrorListener errorListeners(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError){
                    Toast.makeText(context,
                            "Periksa Jaringan Internet Anda", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    public void doRequest(String url, int method, final MyGoodListener listener, final Map<String,String>
                          map){
        StringRequest stringRequest = new StringRequest(method, Http.url+url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listener.doAny(response);
                }catch (Exception e){
                    Log.d("error",e.getMessage());
                }

            }
        },errorListeners()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        stringRequest.setRetryPolicy(requstTimeOut());
        requestQueue.add(stringRequest);
    }
}
