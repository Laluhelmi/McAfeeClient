package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.LoadScanInfo;
import com.hilmiproject.omdutz.mcafee.Interface.ProfilInterface;
import com.hilmiproject.omdutz.mcafee.Pojo.ProfilPojo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by L on 30/11/17.
 */

public class ProfilVolley {
    RequestQueue requestQueue;
    Context context;
    public ProfilVolley(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }
    public void ambilProfil(final ProfilInterface profilInterface,final String email){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Http.url + "profil"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    JSONObject data   = object.getJSONObject("data");
                    ProfilPojo profil = new ProfilPojo();
                    profil.setAtasnama(data.getString("atas_nama"));
                    profil.setNama_toko(data.getString("nama_toko"));
                    profil.setAlamattoko(data.getString("alamat"));
                    profil.setJk(data.getString("j_kelamin"));
                    profil.setNorek(data.getString("no_rek"));
                    profil.setStatus(data.getString("status"));
                    profil.setTelp(data.getString("telp"));
                    profil.setJenis_bank(data.getString("jenis_bank"));
                    profil.setCabang(data.getString("cabang"));
                    profilInterface.displayProfil(profil);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context,
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("email",email);
                return data;
            }
        };
        this.requestQueue.add(stringRequest);
    }
    public void ubahProfil(final Map<String,String> data, final LoadScanInfo loadScanInfo){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Http.url + "edit_profil", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadScanInfo.setelahScan();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return data;
            }
        };
    this.requestQueue.add(stringRequest);
    }
}
