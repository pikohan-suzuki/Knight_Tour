package com.amebaownd.pikohan_nwiatori.knighttour.Dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.amebaownd.pikohan_nwiatori.knighttour.R
import com.amebaownd.pikohan_nwiatori.knighttour.StageSelectActivity
import java.sql.Time
import java.text.SimpleDateFormat

class ClearDialog(): DialogFragment() {
    var stageId:Int=1
    var rank:String =""
    var time: Time?=null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog : View = activity!!.layoutInflater.inflate(R.layout.dialog_clear,null)
        val nextStageButton = alertDialog.findViewById<Button>(R.id.next_stage_button)
        nextStageButton.setOnClickListener{
            val intent= Intent()
            val pendingIntent =activity!!.createPendingResult(targetRequestCode,intent, PendingIntent.FLAG_ONE_SHOT)
            try{
                pendingIntent.send(Activity.RESULT_OK)
            }catch (ex: PendingIntent.CanceledException){
                ex.printStackTrace()
            }
            this.dismiss()
        }
        val backStageSelectButton = alertDialog.findViewById<Button>(R.id.goto_select_clear_button)
        backStageSelectButton.setOnClickListener{
            this.dismiss()
            val intent = Intent(this.context, StageSelectActivity::class.java)
            startActivity(intent)
        }
        val rankTextView =alertDialog.findViewById<TextView>(R.id.rank_clear_textView)
        rankTextView.text=getString(R.string.rank,rank)
        val timeTextView = alertDialog.findViewById<TextView>(R.id.clear_time_clear_textView)
        timeTextView.text="Time: "+SimpleDateFormat("mm:ss").format(time)

        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setView(alertDialog)
        this.isCancelable=false
        return dialogBuilder.create()
    }
}