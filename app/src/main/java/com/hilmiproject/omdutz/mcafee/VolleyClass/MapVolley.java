package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.AfterMapDatLoaded;
import com.hilmiproject.omdutz.mcafee.Pojo.LocationPojo;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 25/11/17.
 */

public class MapVolley extends MyVolley {

    public List<LatLng>         latLngs;
    public List<LocationPojo>   locationPojos;

    public MapVolley(Context context){
        super(context);
        latLngs         = new ArrayList<>();
        locationPojos   = new ArrayList<>();
    }

    public void getMapLocation(AfterMapDatLoaded afterMapDatLoaded){
        StringRequest stringRequest = new StringRequest(Http.url + "toko",
               responseListener(afterMapDatLoaded),errorListener() );
        stringRequest.setRetryPolicy(this.requstTimeOut());
        this.requestQueue.add(stringRequest);
    }
    public List<LatLng> getLatLngs(){
        return this.latLngs;
    }
    public List<LocationPojo> getlocationPojos(){
        return this.locationPojos;
    }
    private Response.Listener responseListener(final AfterMapDatLoaded afterMapDatLoaded){
        return  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0;i < jsonArray.length();i++){
                            JSONObject data = jsonArray.getJSONObject(i);
                            LatLng latLng   =
                                    new LatLng(Double.parseDouble(data.getString("lat")),
                                               Double.parseDouble(data.getString("lng")));
                            LocationPojo locationPojo = new LocationPojo();
                            locationPojo.setAlamat(data.getString("alamat"));
                            locationPojo.setNama  (data.getString("nama_toko"));
                            locationPojos.add(locationPojo);
                            latLngs.add(latLng);
                        }
                        afterMapDatLoaded.makeMap(getLatLngs(),getlocationPojos());
                    }catch (Exception e){
                        Log.d("error : ",e.getMessage());
                    }
            }
        };
    }
    private Response.ErrorListener errorListener(){
       return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError){
                    Toast.makeText(context, "Periksa Jaringan Anda "
                            , Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
