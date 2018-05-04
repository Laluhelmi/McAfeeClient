package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.Wilayahlistener;
import com.hilmiproject.omdutz.mcafee.Pojo.TokoEntity;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 01/12/17.
 */

public class TokoRequest extends MyVolley{

    public TokoRequest(Context context){
        super(context);
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void getToko(String id, final Wilayahlistener listener){
        StringRequest stringRequest = new StringRequest(Http.url + "toko/" + id
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<TokoEntity> tokoEntities = new ArrayList<>();
                    for (int i = 0;i < jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        TokoEntity tokoEntity = new TokoEntity();
                        tokoEntity.id_toko = object.getString("id_toko");
                        tokoEntity.nama_toko = object.getString("nama_toko");
                        tokoEntity.no_telp = object.getString("no_telp");
                        tokoEntity.alamat = object.getString("alamat");
                       // tokoEntity.nama_wilayah = object.getString("nama_wilayah");
                        tokoEntity.latLng = new LatLng(Double.parseDouble(object.getString("lat")),
                                Double.parseDouble(object.getString("lng")));
                        tokoEntities.add(tokoEntity);
                    }
                listener.getToko(tokoEntities);
                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(this.requstTimeOut());
        this.requestQueue.add(stringRequest);


    }
}
