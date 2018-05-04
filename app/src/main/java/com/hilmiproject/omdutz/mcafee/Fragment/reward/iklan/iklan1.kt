package com.hilmiproject.omdutz.mcafee.Fragment.reward.iklan

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ImageView
import com.hilmiproject.omdutz.mcafee.R
import com.hilmiproject.omdutz.mcafee.VolleyClass.IklanRequest

class iklan1 : Fragment() {
    private lateinit var gambar:String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater?.inflate(R.layout.activity_iklan1,container,false)

        gambar = arguments.getString("iklanpath")

        var bitmap = IklanRequest().loadIklan(gambar,context)
        var imageView =  view?.findViewById<ImageView>(R.id.iklan)
        imageView?.setImageBitmap(bitmap)
        view?.findViewById<ImageView>(R.id.iklan)?.setOnClickListener(View.OnClickListener {
            tampilIklna()
        })

        return view
    }
    public fun tampilIklna(){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.iklan_layout)
        val bitmap = IklanRequest().loadIklan(gambar, context)
        (dialog.findViewById<View>(R.id.gambariklan) as ImageView).setImageBitmap(bitmap)
        //val frameLayout = activity.findViewById<View>(R.id.ini_Fragment) as FrameLayout
        //val appBarLayout = activity.findViewById<View>(R.id.myappbar) as AppBarLayout
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.attributes.gravity = Gravity.CENTER
        dialog.window!!.setBackgroundDrawable(null)

        dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        dialog.show()
    }
}
