package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.Wilayahlistener;
import com.hilmiproject.omdutz.mcafee.Pojo.TokoEntity;
import com.hilmiproject.omdutz.mcafee.Pojo.Wilayah;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 01/12/17.
 */

public class WilayahVolley extends MyVolley {
    List<Wilayah> wilayahs;

    public WilayahVolley(Context context){
        super(context);
        this.requestQueue = Volley.newRequestQueue(context);
    }
    public void getWilayah(final Wilayahlistener wilayahlistener){

        StringRequest stringRequest = new StringRequest(Http.url + "wilayah", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Wilayah> wilayahs = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0;i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        Wilayah wilayah = new Wilayah();
                        wilayah.id = object.getString("id_wilayah");
                        wilayah.namawilaya = object.getString("nama_wilayah");
                        wilayahs.add(wilayah);
                    }
                    wilayahlistener.wilayah(wilayahs);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(this.requstTimeOut());
        this.requestQueue.add(stringRequest);
    }
    public void getToko(final Wilayahlistener wilayahlistener, final String wilayah){
        StringRequest stringRequest = new StringRequest(Http.url + "toko/" + wilayah,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<TokoEntity> tokoEntities =
                                new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i =0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                TokoEntity tokoEntity = new TokoEntity();
                                tokoEntity.alamat = object.getString("alamat");
                                tokoEntity.nama_toko = object.getString("nama_toko");
                                tokoEntity.id_toko = object.getString("id_toko");
                                tokoEntities.add(tokoEntity);
                            }
                            wilayahlistener.getToko(tokoEntities);
                        }catch (Exception e){
                            Log.d("error",e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setRetryPolicy(this.requstTimeOut());
        this.requestQueue.add(stringRequest);
    }
}
