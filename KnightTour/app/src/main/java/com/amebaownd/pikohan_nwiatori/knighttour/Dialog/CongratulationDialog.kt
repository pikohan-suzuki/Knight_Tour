package com.amebaownd.pikohan_nwiatori.knighttour.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.Button
import com.amebaownd.pikohan_nwiatori.knighttour.MainActivity
import com.amebaownd.pikohan_nwiatori.knighttour.R

class CongratulationDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog : View = activity!!.layoutInflater.inflate(R.layout.dialog_congratulation,null)
        val okButton = alertDialog.findViewById<Button>(R.id.ok_congratulation_button)
        okButton.setOnClickListener{
            this.dismiss()
            val intent = Intent(this.context, MainActivity::class.java)
            startActivity(intent)
        }

        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setView(alertDialog)
        this.isCancelable=false
        return dialogBuilder.create()
    }
}