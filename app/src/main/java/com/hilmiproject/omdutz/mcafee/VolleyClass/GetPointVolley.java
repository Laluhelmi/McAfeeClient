package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.util.Log;
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
import com.hilmiproject.omdutz.mcafee.Interface.AmbilPoint;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by L on 25/11/17.
 */

public class GetPointVolley extends MyVolley{

    private String point = null;
    public GetPointVolley(Context context){
        super(context);
    }

    public void point(final AmbilPoint ambilPoint){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Http.url+"point", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    point =  object.getJSONObject("data").getString("point");
                    ambilPoint.setPoint(point);
                }catch (Exception e){
                    Log.d("error broo",e.getMessage());
                    Toast.makeText(context, e.getMessage()+response, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError){
                    Toast.makeText(context, "Periksa Jaringan Anda"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("email",new AppDetail(context).getEmail());
                return map;
            }
        };
        stringRequest.setRetryPolicy(requstTimeOut());
        this.requestQueue.add(stringRequest);
    }
    public DefaultRetryPolicy requstTimeOut(){
        return new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }
}
