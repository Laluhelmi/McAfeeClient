package com.hilmiproject.omdutz.mcafee

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_first_page.*

class FirstPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)
        pagerview.adapter = MyAdapter(supportFragmentManager)
        tab.setupWithViewPager(pagerview)
    }
    class MyAdapter : FragmentStatePagerAdapter {

        constructor(fragmentManager: FragmentManager) : super(fragmentManager){
        }

        override fun getItem(position: Int): Fragment {
            if(position == 0 ){
                return Login()
            }else{
                return Register()
            }

        }

        override fun getPageTitle(position: Int): CharSequence {
            if(position == 0){
                return "Login"
            }else{
                return "Register"
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }
}
