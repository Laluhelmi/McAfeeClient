package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.HadiahInterface;
import com.hilmiproject.omdutz.mcafee.Pojo.HadiahPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 01/12/17.
 */

public class HadiahVolley extends MyVolley{

    public HadiahVolley(Context context){
        super(context);
    }
    public void dataHadiah(final HadiahInterface hadiahInterface){
        StringRequest stringRequest = new StringRequest(Http.url + "hadiah", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<HadiahPojo> hadiahPojos = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        HadiahPojo hadiahPojo = new HadiahPojo();
                        hadiahPojo.setIdhadiah(object.getString("id_hadiah"));
                        hadiahPojo.setKeterangan(object.getString("keterangan"));
                        hadiahPojo.setTipe(object.getString("tipe"));
                        hadiahPojo.setNama_hadiah(object.getString("nama_hadiah"));
                        hadiahPojo.setPoint(object.getString("point"));
                        hadiahPojos.add(hadiahPojo);
                    }
                    hadiahInterface.setelahAdaData(hadiahPojos);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(this.requstTimeOut());
        this.requestQueue.add(stringRequest);
    }
}
