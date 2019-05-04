package com.amebaownd.pikohan_nwiatori.knighttour

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import java.sql.Time

class StageSelectActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stage_select)

        val gridLayout = findViewById<GridLayout>(R.id.stage_select_gridLayout)
        for(i in 0 until 30){
            val item = layoutInflater.inflate(R.layout.stage_select_grid_item,null)
            item.findViewById<TextView>(R.id.stage_id_stage_select_grid_item).text=(i+1).toString()
            val params = GridLayout.LayoutParams()
            params.rowSpec=GridLayout.spec(i/6)
            params.columnSpec=GridLayout.spec(i%6)
            item.layoutParams=params
            gridLayout.addView(item)
        }

        val backButton = findViewById<Button>(R.id.back_stage_select_button)
        backButton.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showCoinfirmDialog(stageId:Int,rank:String,time:Time){
        val dialogManager =DialogManager()
        dialogManager.startDialog(supportFragmentManager,202,stageId, rank,time)
    }

    private fun gridItemClickListener(stageId:Int,rank:String,time:Time):View.OnClickListener{
        return View.OnClickListener {
            val dialogManager = DialogManager()
            dialogManager.startDialog(supportFragmentManager,202,stageId,rank,time)
        }
    }
}