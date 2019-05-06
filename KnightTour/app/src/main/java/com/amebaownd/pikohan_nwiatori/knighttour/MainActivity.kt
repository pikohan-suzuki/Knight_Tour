package com.amebaownd.pikohan_nwiatori.knighttour

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var nextStage = 0
    lateinit var stageTextVew: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextStage = readFile(this,"nextStage")?.toInt() ?: 1
        stageTextVew = findViewById(R.id.stage_id_front_textView)
        stageTextVew.text = getString(R.string.stage_id_front, nextStage)

        val privacyPolicyTextView = findViewById<TextView>(R.id.privacy_policy_textView)
        privacyPolicyTextView.text= Html.fromHtml("<a href=\"https://pikohan-niwatori.amebaownd.com/posts/6171216\">PrivacyPolicy</a>")
        privacyPolicyTextView.linksClickable=true
        val startBeginButton = findViewById<Button>(R.id.from_beginning_button)
        startBeginButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("stage_id", 1)
            startActivityForResult(intent, 101)
        }
        val selectStageButton = findViewById<Button>(R.id.select_stage_front_button)
        selectStageButton.setOnClickListener {
            val intent = Intent(this, StageSelectActivity::class.java)
            startActivity(intent)
        }
        val howToPlayButton = findViewById<Button>(R.id.how_to_play_button)
        howToPlayButton.setOnClickListener {
            val intent = Intent(this, HowToPlayActivity::class.java)
            startActivity(intent)
        }
        val startContinuationButton = findViewById<Button>(R.id.from_continuation_button)
        startContinuationButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("stage_id", nextStage)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (intent!=null && requestCode == 101 && resultCode == Activity.RESULT_OK) {
            nextStage = intent.getIntExtra("stage_id", 1)
            stageTextVew.text = getString(R.string.stage_id_front, nextStage)
        }
    }


}
