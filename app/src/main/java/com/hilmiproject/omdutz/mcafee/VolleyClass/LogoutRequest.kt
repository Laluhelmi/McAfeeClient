package com.hilmiproject.omdutz.mcafee.VolleyClass

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.hilmiproject.omdutz.mcafee.ClientPackage.Http
import com.hilmiproject.omdutz.mcafee.Interface.LogoutInterface
import org.json.JSONObject

/**
 * Created by L on 21/12/17.
 */
class LogoutRequest : MyVolley{

    constructor(c : Context) : super(c)

    public fun keluar(token : String,keluar: LogoutInterface){
        var request = object  : StringRequest(Request.Method.POST,Http.url+"logout"
                ,Response.Listener {
                response ->
                try {
                    var hasil = JSONObject(response)
                    if(hasil.get("status").toString().equals("success")){
                        keluar.lakukanSesuatu()
                    }

                }catch (e: Exception){
                    Log.d("error terjadi",e.message)
                }

        },Response.ErrorListener {  }){
            override fun getParams(): MutableMap<String, String> {

                var data : MutableMap<String,String> = mutableMapOf()
                data.put("token",token)

                return data
            }
        }
        request.setRetryPolicy(requstTimeOut())
        requestQueue.add(request)
    }

}