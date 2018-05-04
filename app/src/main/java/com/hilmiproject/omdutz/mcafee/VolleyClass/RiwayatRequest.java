package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hilmiproject.omdutz.mcafee.ClientPackage.AppDetail;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.TabunganInterface;
import com.hilmiproject.omdutz.mcafee.Pojo.TabunganEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 05/12/17.
 */

public class RiwayatRequest extends MyVolley {

    public RiwayatRequest(Context context){
        super(context);
    }

    public void getTabungan(final TabunganInterface tabunganInterface){
        String email = new AppDetail(context).getEmail();
        StringRequest stringRequest = new StringRequest(Http.url+"tabungan/"+email,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("data");
                            List<TabunganEntity> tabunganEntities = new ArrayList<>();
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject obj = jsonArray.getJSONObject(i);
                                TabunganEntity tabunganEntity = new TabunganEntity();
                                tabunganEntity.setKode_scan(obj.getString("kode_scan"));
                                tabunganEntity.setNama_produk(obj.getString("nama_produk"));
                                tabunganEntity
                                        .setSerial_number(obj.getString("serial_number"));
                                tabunganEntity.setPoint(obj.getString("point"));
                                tabunganEntity.setTnggal_scan(obj.getString("tgl_scan"));
                                tabunganEntities.add(tabunganEntity);
                            }
                            tabunganInterface.setTabunga(tabunganEntities);

                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(this.requstTimeOut());
        requestQueue.add(stringRequest);
    }

}
