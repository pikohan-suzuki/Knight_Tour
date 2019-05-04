package com.amebaownd.pikohan_nwiatori.knighttour

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import java.sql.Time

class GameActivity :AppCompatActivity(){
    var stage_id=1
    var bestRank=""
    var bestTime:Time?=null
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_game)

        createGameScreen(stage_id)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if(requestCode==202&&resultCode== Activity.RESULT_OK){
            stage_id+=1
            createGameScreen(stage_id)
        }
    }

    private fun createGameScreen(stageId:Int){

    }
    private fun clear(rank:String,time:Time){
        val dialogManager=DialogManager()
        dialogManager.startDialog(supportFragmentManager,203,stage_id,rank,time)
        if(time<bestTime)
            dialogManager.startDialog(supportFragmentManager,204,stage_id,rank,time)
    }

    override fun onBackPressed(){}
}