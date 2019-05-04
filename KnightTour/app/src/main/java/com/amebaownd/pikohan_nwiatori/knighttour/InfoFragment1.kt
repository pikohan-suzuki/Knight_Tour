package com.amebaownd.pikohan_nwiatori.knighttour

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class InfoFragment1():Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info1,container,false)
        return view
    }
}

fun newInfoFragment1Instance():InfoFragment1{
    val fragment = InfoFragment1()
    return fragment
}