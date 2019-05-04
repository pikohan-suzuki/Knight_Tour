package com.amebaownd.pikohan_nwiatori.knighttour

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.security.AccessControlContext
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.logging.SimpleFormatter

class PauseDialog():DialogFragment() {
    var stageId:Int=1
    var rank:String =""
    var time: Time?=null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog : View = activity!!.layoutInflater.inflate(R.layout.dialog_pause,null)
        val backToGameButton = alertDialog.findViewById<Button>(R.id.back_to_game_button)
        backToGameButton.setOnClickListener{
            this.dismiss()
        }
        val goToStageSelectButton = alertDialog.findViewById<Button>(R.id.goto_select_pause_button)
        goToStageSelectButton.setOnClickListener{
            this.dismiss()
            val intent = Intent(this.context,StageSelectActivity::class.java)
            startActivity(intent)
        }

        val stageIdTextView = alertDialog.findViewById<TextView>(R.id.stage_id_pause_textView)
        stageIdTextView.text = getString(R.string.stage_id_pause,stageId)
        val rankTextView =alertDialog.findViewById<TextView>(R.id.best_rank_pause_textView)
        rankTextView.text=getString(R.string.rank,rank)
        val timeTextView = alertDialog.findViewById<TextView>(R.id.time_pause_textVIew)
        timeTextView.text=SimpleDateFormat("mm:ss").format(time)

        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setView(alertDialog)
        this.isCancelable=false
        return dialogBuilder.create()
    }
}