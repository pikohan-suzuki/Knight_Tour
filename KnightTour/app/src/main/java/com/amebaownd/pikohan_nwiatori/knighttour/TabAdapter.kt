package com.amebaownd.pikohan_nwiatori.knighttour

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.TextView

class TabAdapter(fm: FragmentManager, private val context: Context) : FragmentStatePagerAdapter(fm) {

    private val inflater = LayoutInflater.from(context)
    private val title = arrayOf<String>("1", "2","3","4")
    override fun getItem(position: Int): Fragment? {
        when(position){
            0->
                return InfoFragment1()
            1->
                return InfoFragment2()
            2->
                return InfoFragment3()
            3->
                return InfoFragment4()
        }
        return InfoFragment1()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

    override fun getCount() = title.size

    fun getTabView(tabLayout: TabLayout, position: Int): View {
        val view = inflater.inflate(R.layout.tab_item, tabLayout, false)
//        view.setBackgroundColor(Color.RED)
        val tabTextView = view.findViewById<TextView>(R.id.tab_item_textView)
        tabTextView.text = getPageTitle(position)
        tabTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        return view
    }

}