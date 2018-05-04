package com.hilmiproject.omdutz.mcafee

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_new_register.*

class NewRegister : AppCompatActivity() {
    public companion object {
        lateinit var frameStatic:ViewPager
        lateinit var activity:NewRegister
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_register)

        activity = this@NewRegister
        var adapter = MyAdapter(supportFragmentManager)
        frame.adapter = adapter
        frameStatic = frame
        tab.setupWithViewPager(frame)

    }
    class MyAdapter : FragmentStatePagerAdapter{

        constructor(fragmentManager: FragmentManager) : super(fragmentManager){
        }

        override fun getItem(position: Int): Fragment {
           if(position == 0 ){
               return Register()
           }else{
               return Register2()
           }

        }

        override fun getPageTitle(position: Int): CharSequence {
            if(position == 0){
                return "Register 1"
            }else{
                return "Register 2"
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }
}
