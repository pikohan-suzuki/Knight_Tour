package com.amebaownd.pikohan_nwiatori.knighttour

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class InfoFragment3():Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info3,container,false)
        return view
    }
}

fun newInfoFragment3Instance():InfoFragment3{
    val fragment = InfoFragment3()
    return fragment
}