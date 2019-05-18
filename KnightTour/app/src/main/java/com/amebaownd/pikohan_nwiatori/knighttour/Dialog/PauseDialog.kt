package com.amebaownd.pikohan_nwiatori.knighttour.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.amebaownd.pikohan_nwiatori.knighttour.R
import com.amebaownd.pikohan_nwiatori.knighttour.StageSelectActivity
import com.amebaownd.pikohan_nwiatori.knighttour.writeFile
import java.sql.Time
import java.text.SimpleDateFormat

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
            writeFile(this.context!!,"nextStage",stageId.toString())
            this.dismiss()
            val intent = Intent(this.context, StageSelectActivity::class.java)
            startActivity(intent)
        }

        val stageIdTextView = alertDialog.findViewById<TextView>(R.id.stage_id_pause_textView)
        stageIdTextView.text = getString(R.string.stage_id_pause,stageId)
        val rankTextView =alertDialog.findViewById<TextView>(R.id.best_rank_pause_textView)
        if(rank!="")
            rankTextView.text=getString(R.string.rank,rank)
        else
            rankTextView.text=getString(R.string.rank,"-")
        val timeTextView = alertDialog.findViewById<TextView>(R.id.time_pause_textVIew)
        if(time!=Time(99999*1000)) {
            timeTextView.text = "Time: " + SimpleDateFormat("mm:ss").format(time)
        }else{
            timeTextView.text = "Time: --:--"
        }

        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setView(alertDialog)
        this.isCancelable=false
        return dialogBuilder.create()
    }
}