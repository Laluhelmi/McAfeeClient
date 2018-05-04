package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Draw;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by L on 04/12/17.
 */

public class ClaimRequest extends MyVolley{


    public ClaimRequest(Context context){
        super(context);
    }
    public void kirimClaim(final String id_user, final String point, final String id_hadiah,
                           final String email){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Http.url + "klaim/index_post/?X-API-KEY=istimewa", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Intent intent = new Intent("reward_update");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                    JSONObject object = new JSONObject(response);
                    Toast.makeText(context, object.getString("status"),
                            Toast.LENGTH_SHORT).show();
                    Integer pointUpdate = Integer.parseInt(Draw.globalPoint)
                                        - Integer.parseInt(point);
                    Draw.point.setText("Point : "+String.valueOf(pointUpdate));
                    Draw.globalPoint = String.valueOf(pointUpdate);
                }catch (Exception e){
                    //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                };
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("id_user",id_user);
                data.put("point"  ,point);
                data.put("id_hadiah",id_hadiah);
                data.put("email",email);
                return data;
            }
        };
        stringRequest.setRetryPolicy(this.requstTimeOut());
        requestQueue.add(stringRequest);
    }


}
