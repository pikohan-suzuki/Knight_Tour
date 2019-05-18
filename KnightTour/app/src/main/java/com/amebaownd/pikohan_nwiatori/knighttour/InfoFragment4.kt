package com.amebaownd.pikohan_nwiatori.knighttour

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class InfoFragment4():Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info4,container,false)
        return view
    }
}

fun newInfoFragment4Instance():InfoFragment4{
    val fragment = InfoFragment4()
    return fragment
}