package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hilmiproject.omdutz.mcafee.ClientPackage.AppDetail;
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.RewardHistoryInterface;
import com.hilmiproject.omdutz.mcafee.Pojo.Reward;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 04/12/17.
 */

public class HistoryRewardRequest extends MyVolley{

    public HistoryRewardRequest(Context context){
        super(context);
    }

    public void getData(final RewardHistoryInterface historyInterface){
        String email = new AppDetail(context).getEmail();
        StringRequest stringRequest = new StringRequest(Http.url + "klaim/" + email
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Reward> rewards = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray data = object.getJSONArray("data");
                    for(int i=0;i<data.length();i++){
                        JSONObject obj = data.getJSONObject(i);
                        Reward reward = new Reward();
                        reward.setKeterangan(obj.getString("keterangan"));
                        reward.setNominal(obj.getString("nama_hadiah"));
                        reward.setPoint(obj.getString("point"));
                        reward.setStatus(obj.getString("status"));
                        reward.setType(obj.getString("tipe"));
                        reward.setTgl_kirim(obj.getString("tgl_klaim"));
                        rewards.add(reward);
                    }
                    historyInterface.getRewardHistory(rewards);
                }catch (Exception e){
                    Log.d("e",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof VolleyError){
                    Toast.makeText(context, "Periksa Jarainga" +
                            " Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });
        stringRequest.setRetryPolicy(this.requstTimeOut());
        requestQueue.add(stringRequest);
    }
}
