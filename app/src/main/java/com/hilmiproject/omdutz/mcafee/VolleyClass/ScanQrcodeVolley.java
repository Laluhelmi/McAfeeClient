package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hilmiproject.omdutz.mcafee.ClientPackage.AppDetail;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.AfterScan;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by L on 24/11/17.
 */

public class ScanQrcodeVolley extends MyVolley{

    public ScanQrcodeVolley(Context context){
        super(context);
    }

    public void scanQr(final String qrCode, AfterScan afterScan){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Http.url+"tabungan/index_post",response(afterScan),errorListener(afterScan)){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String email = new AppDetail(context).getEmail();
                Map<String,String> data = new HashMap<>();
                data.put("email",email);
                data.put("kode_scan",qrCode);

                return data;
            }
        };
        stringRequest.setRetryPolicy(requstTimeOut());
        this.requestQueue.add(stringRequest);
    }
    private Response.Listener<String> response(final AfterScan afterScan){
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    afterScan.afterscan(response);
                    JSONObject object = new JSONObject(response);
                    if(object.getString("status").equals("sukses")){
                        Toast.makeText(context, "sukses", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }
    private Response.ErrorListener errorListener(final AfterScan afterScan){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                afterScan.afterscan(error.getMessage());
                if(error instanceof TimeoutError){
                    Toast.makeText(context, "Periksa Jaringan Internet Anda",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }


}
