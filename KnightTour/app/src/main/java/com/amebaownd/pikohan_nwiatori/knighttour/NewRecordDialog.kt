package com.amebaownd.pikohan_nwiatori.knighttour

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.sql.Time
import java.text.SimpleDateFormat

class NewRecordDialog(): DialogFragment() {
    var stageId:Int=1
    var rank:String =""
    var time: Time?=null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog : View = activity!!.layoutInflater.inflate(R.layout.dialog_new_record,null)
        val okButton = alertDialog.findViewById<Button>(R.id.ok_new_record_button)
        okButton.setOnClickListener{
            this.dismiss()
        }
        val rankTextView =alertDialog.findViewById<TextView>(R.id.rank_new_record_textView)
        rankTextView.text=getString(R.string.rank,rank)
        val timeTextView = alertDialog.findViewById<TextView>(R.id.clear_time_new_record_textView)
        timeTextView.text=SimpleDateFormat("mm:ss").format(time)

        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setView(alertDialog)
        return dialogBuilder.create()
    }
}