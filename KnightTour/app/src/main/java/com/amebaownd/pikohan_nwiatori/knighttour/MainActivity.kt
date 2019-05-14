package com.amebaownd.pikohan_nwiatori.knighttour

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.amebaownd.pikohan_nwiatori.knighttour.Data.Stage
import com.amebaownd.pikohan_nwiatori.knighttour.Data.StageInfo
import java.io.InputStreamReader
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private var nextStage = 0
    lateinit var stageTextVew: TextView
    lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextStage = readFile(this, "nextStage")?.toInt() ?: 1
        stageTextVew = findViewById(R.id.stage_id_front_textView)
        stageTextVew.text = getString(R.string.stage_id_front, nextStage)

        val privacyPolicyTextView = findViewById<TextView>(R.id.privacy_policy_textView)
        privacyPolicyTextView.text =
            Html.fromHtml("<a href=\"https://pikohan-niwatori.amebaownd.com/posts/6171216\">PrivacyPolicy</a>")
        privacyPolicyTextView.linksClickable = true
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
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "database").build()
        val data1 = readStageCsv()
        thread {
            try {
                db.stageDao().insertAll(*data1.toTypedArray())
            } catch (e: android.database.sqlite.SQLiteConstraintException) {
                Log.d("aaaaa", e.toString())
            }
        }
        val data2 = readStageInfoCsv()
        thread {
            try {
                db.stageInfoDao().insertAll(*data2.toTypedArray())
            } catch (e: android.database.sqlite.SQLiteConstraintException) {
                Log.d("aaaaa", e.toString())
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (intent != null && requestCode == 101 && resultCode == Activity.RESULT_OK) {
            nextStage = intent.getIntExtra("stage_id", 1)
            stageTextVew.text = getString(R.string.stage_id_front, nextStage)
        }
    }

    private fun readStageCsv(): List<Stage> {
        val inputStream = resources.assets.open("stage.csv")
        val inputStreamReader = InputStreamReader(inputStream)
        val results = mutableListOf<Stage>()
        inputStreamReader.use {
            it.readLines().forEach {
                val str = it!!.split(",")
                val result = Stage().apply {
                    this.stageId = str[0].toInt()
                    this.row = str[1].toInt()
                    this.column = str[2].toInt()
                    this.sTime = str[3].toInt()
                    this.aTime = str[4].toInt()
                    this.sTime = str[5].toInt()
                    this.aTime = str[6].toInt()
                }
                results.add(result)
            }
        }
        return results.toList()
    }
    private fun readStageInfoCsv(): List<StageInfo> {
        val inputStream = resources.assets.open("stage_info.csv")
        val inputStreamReader = InputStreamReader(inputStream)
        val results = mutableListOf<StageInfo>()
        inputStreamReader.use {
            it.readLines().forEach {
                val str = it!!.split(",")
                val result = StageInfo().apply {
                    this.stageId = str[0].toInt()
                    this.row = str[1].toInt()
                    this.column = str[2].toInt()
                }
                results.add(result)
            }
        }
        return results.toList()
    }
}
