package com.hilmiproject.omdutz.mcafee.VolleyClass

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hilmiproject.omdutz.mcafee.Pojo.HadiahPojo
import com.hilmiproject.omdutz.mcafee.R

/**
 * Created by L on 20/12/17.
 */
class GetNotification : RecyclerView.Adapter<GetNotification.Adapter> {
    private var data : MutableList<HadiahPojo>

    constructor(data : MutableList<HadiahPojo>){
        this.data = data
    }

    class Adapter : RecyclerView.ViewHolder{
        public lateinit var jenis        : ImageView
        public lateinit var point        : TextView
        public lateinit var nominal      : TextView
        public lateinit var pointcount   : TextView
        public lateinit var idhadiah     : TextView

        constructor(v : View) : super(v){
            jenis        = v.findViewById(R.id.jenishadiah)
            point        = v.findViewById(R.id.point)
            //nominal      = v.findViewById(R.id.nominal)
            pointcount   = v.findViewById(R.id.pointcount)
            idhadiah     = v.findViewById(R.id.idhadiah)
        }

    }

    override fun onBindViewHolder(holder: Adapter?, position: Int) {
        var d : HadiahPojo = data.get(position)
        holder?.pointcount?.setText(d.point)
        //holder?.nominal   ?.setText(d.nama_hadiah)
        holder?.point     ?.setText("Point : "+d.point)
        holder?.idhadiah  ?.setText(d.idhadiah)
        if(d.tipe.equals("Pulsa")){
            holder?.jenis?.setImageResource(R.drawable.pulsa)
        }else holder?.jenis?.setImageResource(R.drawable.uang2)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Adapter {
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_item_hadiah,
                parent,false)
        var adapter = Adapter(view)

        return adapter
    }

    override fun getItemCount(): Int {
       return this.data.size
    }


}