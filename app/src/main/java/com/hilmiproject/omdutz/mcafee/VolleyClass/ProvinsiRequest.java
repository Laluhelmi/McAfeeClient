package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.GetProvinsiInterface;
import com.hilmiproject.omdutz.mcafee.Pojo.Provinsi;
import com.hilmiproject.omdutz.mcafee.Pojo.Wilayah;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 06/01/18.
 */

public class ProvinsiRequest extends MyVolley {

    public ProvinsiRequest(Context context){
        super(context);
    }

    public void getProvinsi(final GetProvinsiInterface anInterface){
        StringRequest stringRequest = new StringRequest(Http.url + "provinsi",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Provinsi>  provinsis = new ArrayList<>();
                            JSONArray array = new JSONArray(response);
                            for(int i =0;i<array.length();i++){
                                Provinsi provinsi = new Provinsi();
                                provinsi.id = array.getJSONObject(i).getString("id");
                                provinsi.nama = array.getJSONObject(i).getString("nama");
                                provinsis.add(provinsi);
                            }
                          anInterface.giveData(provinsis);
                          provinsi = provinsis;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, errorListeners());

        stringRequest.setRetryPolicy(requstTimeOut());
        requestQueue.add(stringRequest);
    }
    public void getKota(final GetProvinsiInterface anInterface,String id){
        StringRequest stringRequest = new StringRequest(Http.url + "provinsi/wilayah/"+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Wilayah>  wilayahList= new ArrayList<>();
                            JSONArray array = new JSONArray(response);
                            for(int i =0;i<array.length();i++){
                                Wilayah wilayah = new Wilayah();
                                wilayah.id = array.getJSONObject(i).getString("id_wilayah");
                                wilayah.namawilaya = array.getJSONObject(i).getString("nama_wilayah");
                                wilayahList.add(wilayah);
                            }
                            anInterface.giveData(wilayahList);

                        }catch (Exception e){
                            Log.d("erro ambil kota",e.getMessage());
                        }
                    }
                }, errorListeners());

        stringRequest.setRetryPolicy(requstTimeOut());
        requestQueue.add(stringRequest);
    }
    private List<Provinsi> provinsi;

}
