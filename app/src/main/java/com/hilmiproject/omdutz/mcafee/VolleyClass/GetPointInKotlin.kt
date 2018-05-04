package com.hilmiproject.omdutz.mcafee.VolleyClass

import android.content.Context
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

/*
 * Created by L on 20/12/17.
 */
class GetPointInKotlin : MyVolley {

    constructor(c : Context) : super(c){
        this.requestQueue = Volley.newRequestQueue(context)
    }

    constructor(c: Context,s : String) : super(c){

    }

    public fun getPoint(url : String){
        //val request = StringRequest
        val reqeust = object :StringRequest("",
                        object : Response.Listener<String>{
                            override fun onResponse(response: String?) {

                            }
                        },
                        object : Response.ErrorListener{
                            override fun onErrorResponse(error: VolleyError?) {

                            }
                        }){
            override fun getParams(): MutableMap<String, String> {
               // var intent = Intent(thi,Login::class.java)
                return super.getParams()
            }
        }
    }
}