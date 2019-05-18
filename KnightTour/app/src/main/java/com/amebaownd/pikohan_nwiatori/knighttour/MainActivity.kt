package com.amebaownd.pikohan_nwiatori.knighttour

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import com.amebaownd.pikohan_nwiatori.knighttour.Data.Record
import com.amebaownd.pikohan_nwiatori.knighttour.Data.Stage
import com.amebaownd.pikohan_nwiatori.knighttour.Data.StageInfo
import java.io.InputStreamReader
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private var nextStage = 0
    lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextStage = readFile(this, "nextStage")?.toInt() ?: 1

        val privacyPolicyButton = findViewById<ImageButton>(R.id.privacy_policy_imageButton)
        privacyPolicyButton.setOnTouchListener(setTouchListener(privacyPolicyButton))
        val startBeginButton = findViewById<ImageButton>(R.id.from_beginning_imageButton)
        startBeginButton.setOnTouchListener(setTouchListener(startBeginButton))
//        startBeginButton.setOnClickListener{
//            val intent = Intent(this, GameActivity::class.java)
//            intent.putExtra("stage_id", 1)
//            startActivityForResult(intent, 101)
//        }
        val selectStageButton = findViewById<ImageButton>(R.id.select_stage_front_ImageButton)
        selectStageButton.setOnTouchListener(setTouchListener(selectStageButton))
//        selectStageButton.setOnClickListener {
//            val intent = Intent(this, StageSelectActivity::class.java)
//            startActivity(intent)
//        }
        val howToPlayButton = findViewById<ImageButton>(R.id.how_to_play_ImageButton)
        howToPlayButton.setOnTouchListener(setTouchListener(howToPlayButton))
//        howToPlayButton.setOnClickListener {
//            val intent = Intent(this, HowToPlayActivity::class.java)
//            startActivity(intent)
//        }
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "database").build()
        val data1 = readStageCsv()
        thread {
            try {
                db.stageDao().insertAll(*data1.toTypedArray())
            } catch (e: android.database.sqlite.SQLiteConstraintException) {
                Log.d("stage insert exception", e.toString())
            }
        }
        val data2 = readStageInfoCsv()
        thread {
            try {
                db.stageInfoDao().insertAll(*data2.toTypedArray())
            } catch (e: android.database.sqlite.SQLiteConstraintException) {
                Log.d("stage info insert exception", e.toString())
            }
        }
        val data3 = readRecordCsv()
        thread{
            try {
                db.recordDao().insertAll(*data3.toTypedArray())
            } catch (e: android.database.sqlite.SQLiteConstraintException) {
                Log.d("record insert exception", e.toString())
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (intent != null && requestCode == 101 && resultCode == Activity.RESULT_OK) {
            nextStage = intent.getIntExtra("stage_id", 1)
        }
    }


    //ステージ情報の読み込み
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
                    this.bTime = str[5].toInt()
                    this.cTime = str[6].toInt()
                }
                results.add(result)
            }
        }
        return results.toList()
    }

    //ステージの盤面情報の読み込み
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
    //クリア記録の読み込み
    private fun readRecordCsv(): List<Record> {
        val inputStream = resources.assets.open("record.csv")
        val inputStreamReader = InputStreamReader(inputStream)
        val results = mutableListOf<Record>()
        inputStreamReader.use {
            it.readLines().forEach {
                val str = it!!.split(",")
                val result = Record().apply {
                    this.stageId = str[0].toInt()
                    this.time = str[1].toInt()
                    this.rank = str[2]
                }
                results.add(result)
            }
        }
        return results.toList()
    }

    private fun setTouchListener(view:View):View.OnTouchListener?{
        when(view.id){
            R.id.from_beginning_imageButton->{
                return View.OnTouchListener { view, event ->
                    if(event.action==MotionEvent.ACTION_DOWN){
                        (view as ImageButton).setImageResource(R.drawable.start_clicked)
                    }else if(event.action==MotionEvent.ACTION_UP){
                        (view as ImageButton).setImageResource(R.drawable.start_button)
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra("stage_id", 1)
                        startActivityForResult(intent, 101)
                    }
                    return@OnTouchListener true
                }
            }
            R.id.select_stage_front_ImageButton->{
                return View.OnTouchListener { view, event ->
                    if(event.action==MotionEvent.ACTION_DOWN){
                        (view as ImageButton).setImageResource(R.drawable.stage_select_clicked)
                    }else if(event.action==MotionEvent.ACTION_UP){
                        val intent = Intent(this, StageSelectActivity::class.java)
                        startActivity(intent)
                        (view as ImageButton).setImageResource(R.drawable.stage_select_button)
                    }
                    return@OnTouchListener true
                }
            }
            R.id.how_to_play_ImageButton->{
                return View.OnTouchListener { view, event ->
                    if(event.action==MotionEvent.ACTION_DOWN){
                        (view as ImageButton).setImageResource(R.drawable.how_to_play_clicked)
                    }else if(event.action==MotionEvent.ACTION_UP){
                        (view as ImageButton).setImageResource(R.drawable.how_to_play_button)
                        val intent = Intent(this, HowToPlayActivity::class.java)
                        startActivity(intent)
                    }
                    return@OnTouchListener true
                }
            }
            R.id.privacy_policy_imageButton->{
                return View.OnTouchListener { view, event ->
                    if(event.action==MotionEvent.ACTION_DOWN){
                        (view as ImageButton).setImageResource(R.drawable.privacy_policy_clicked)
                    }else if(event.action==MotionEvent.ACTION_UP){
                        (view as ImageButton).setImageResource(R.drawable.privacy_policy_button)
                        val uri = Uri.parse("https://pikohan-niwatori.amebaownd.com/posts/6171216")
                        val intent =Intent(Intent.ACTION_VIEW,uri)
                        startActivity(intent)
                    }
                    return@OnTouchListener true
                }
            }
        }
        return null
    }
//    class ImageButtonOnClickListener() : View.OnTouchListener{
//        override fun onTouch(view: View?, event: MotionEvent?): Boolean {
//            if(view!=null && event!=null){
//                when(event.action){
//                    MotionEvent.ACTION_BUTTON_PRESS->{
//                        when(view.id){
//                            R.id.from_beginning_imageButton->{
////                                (view as ImageButton).setImageResource()
//                            }
//                            R.id.select_stage_front_ImageButton->{
////                                (view as ImageButton).setImageResource()
//                            }
//                            R.id.how_to_play_ImageButton->{
////                                (view as ImageButton).setImageResource()
//                            }
//                            R.id.privacy_policy_imageButton->{
////                                (view as ImageButton).setImageResource()
//                            }
//                        }
//                    }
//                    MotionEvent.ACTION_BUTTON_RELEASE->{
//                        when(view.id){
//                            R.id.from_beginning_imageButton->{
////                                (view as ImageButton).setImageResource()
//                            }
//                            R.id.select_stage_front_ImageButton->{
////                                (view as ImageButton).setImageResource()
//                            }
//                            R.id.how_to_play_ImageButton->{
////                                (view as ImageButton).setImageResource()
//                            }
//                            R.id.privacy_policy_imageButton->{
////                                (view as ImageButton).setImageResource()
//                            }
//                        }
//                    }
//                }
//            }
//            return false
//        }
//    }
}
