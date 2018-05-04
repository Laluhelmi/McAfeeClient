package com.hilmiproject.omdutz.mcafee.VolleyClass

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http
import com.hilmiproject.omdutz.mcafee.Interface.GetNotifListInterface
import com.hilmiproject.omdutz.mcafee.Interface.NotifikasiInterface
import com.hilmiproject.omdutz.mcafee.Pojo.Reward
import org.json.JSONObject

/**
 * Created by L on 21/12/17.
 */
class Notifikas : MyVolley{

    constructor(context: Context) : super(context){

    }
    public fun hitungBanyakNotifikasi(email : String,listener : NotifikasiInterface){
        var response = Response.Listener<String> { data ->
            try {
                var json  = JSONObject(data)
                var count = json.getJSONObject("data").get("hadiah sukses")
                        as String
                listener.getData(count)
            }catch (e:Exception){
                Log.d("error",e.message+data)
            }
        }
        var error   = Response.ErrorListener { error ->
                        if(error is VolleyError){
                            Toast.makeText(context,"Periksa Jaringan Internet Anda",
                                    Toast.LENGTH_LONG)
                        }
                        }

        var request = StringRequest(Http.url+"notifikasi/$email",response,error)
        request.setRetryPolicy(this.requstTimeOut())
        requestQueue.add(request)
    }
    public fun getNotifList(email: String,listener: GetNotifListInterface){
        var response = Response.Listener<String> { data ->
            var dataNotif :MutableList<Reward> = mutableListOf()
            try {
                    var json = JSONObject(data)
                    var data = json.getJSONArray("data")
                    for(i in 0..data.length() - 1){
                        var reward = Reward()
                        var json = data.getJSONObject(i)
                        reward.type = json.getString("tipe")
                        reward.nominal = json.getString("nama_hadiah")
                        reward.point = json.getString("point")
                        reward.tgl_kirim = json.getString("tgl_klaim")
                        reward.status    = json.getString("status")
                        dataNotif.add(reward)
                    }
                listener.getData(dataNotif)

            }catch (e:Exception){
                Log.d("error",e.message+data)
            }
        }
        var error   = Response.ErrorListener { error ->
            if(error is VolleyError){
                Toast.makeText(context,"Periksa Jaringan Internet Anda",
                        Toast.LENGTH_LONG)
            }
        }

        var request = StringRequest(Http.url+"notif_user/$email",response,error)
        request.setRetryPolicy(this.requstTimeOut())
        requestQueue.add(request)
    }



}