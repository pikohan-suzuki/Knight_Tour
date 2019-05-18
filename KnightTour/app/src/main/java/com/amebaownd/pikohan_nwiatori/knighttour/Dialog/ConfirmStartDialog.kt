package com.amebaownd.pikohan_nwiatori.knighttour.Dialog

import android.app.ActionBar
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.amebaownd.pikohan_nwiatori.knighttour.GameActivity
import com.amebaownd.pikohan_nwiatori.knighttour.R
import java.sql.Time
import java.text.SimpleDateFormat

class ConfirmStartDialog(): DialogFragment() {
    var stageId:Int=1
    var rank:String =""
    var time: Time?=null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog : View = activity!!.layoutInflater.inflate(R.layout.dialog_confirm_stage,null)
        val cancelButton = alertDialog.findViewById<Button>(R.id.back_confirm_start_button)
        cancelButton.setOnClickListener{
            this.dismiss()
        }
        val startGameButton = alertDialog.findViewById<Button>(R.id.start_confirm_start_button)
        startGameButton.setOnClickListener{
            this.dismiss()
            val intent = Intent(this.context, GameActivity::class.java)
            intent.putExtra("stage_id",stageId)
            startActivity(intent)
        }

        val stageIdTextView = alertDialog.findViewById<TextView>(R.id.stage_id_confirm_start_textView)
        stageIdTextView.text = getString(R.string.stage_id_confirm_start,stageId)
        val rankTextView =alertDialog.findViewById<TextView>(R.id.rank_confirm_start_textView)
        if(rank!="")
            rankTextView.text=getString(R.string.rank,rank)
        else
            rankTextView.text=getString(R.string.rank,"-")

        val timeTextView = alertDialog.findViewById<TextView>(R.id.time_confirm_start_textView)
        if(time!=null) {
            timeTextView.text = "Time: " + SimpleDateFormat("mm:ss").format(time)
        }else{
            timeTextView.text="Time: --:--"
        }
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setView(alertDialog)
        return dialogBuilder.create()
    }
}