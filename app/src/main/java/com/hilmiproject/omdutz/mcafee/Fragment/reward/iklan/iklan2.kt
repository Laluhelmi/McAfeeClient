package com.hilmiproject.omdutz.mcafee.Fragment.reward.iklan

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import com.hilmiproject.omdutz.mcafee.R
import com.hilmiproject.omdutz.mcafee.VolleyClass.IklanRequest

class iklan2 : Fragment (){


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view      = inflater?.inflate(R.layout.activity_iklan2,container,false)

        var bitmap    = IklanRequest().loadIklan("iklan2",context)
        var imageView =
                view?.findViewById<ImageView>(R.id.iklan)
        if(bitmap != null)
        imageView?.setImageBitmap(bitmap)
        else{
          //
            //  IklanRequest().getIklan("iklan2", context,imageView)
        }

        view?.findViewById<ImageView>(R.id.iklan)?.setOnClickListener(View.OnClickListener {
            tampilIklan()
        })

        return view
    }
    public fun tampilIklan(){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.iklan_layout)
        val bitmap = IklanRequest().loadIklan("iklan2", context)
        (dialog.findViewById<View>(R.id.gambariklan) as ImageView).setImageBitmap(bitmap)
        val frameLayout = activity.findViewById<View>(R.id.ini_Fragment) as FrameLayout
        val appBarLayout = activity.findViewById<View>(R.id.myappbar) as AppBarLayout
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.attributes.gravity = Gravity.CENTER
        dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        dialog.show()
    }
}
