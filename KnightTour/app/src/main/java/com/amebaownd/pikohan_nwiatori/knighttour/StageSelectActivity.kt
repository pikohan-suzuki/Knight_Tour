package com.amebaownd.pikohan_nwiatori.knighttour

import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.amebaownd.pikohan_nwiatori.knighttour.Data.Record
import java.sql.Time
import java.util.*

class StageSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stage_select)
        val rand = Random()
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
        val gridLayout = findViewById<GridLayout>(R.id.stage_select_gridLayout)
        db.recordDao().getAllOrderByStageId().observe(this, Observer<List<Record>> {
            if (it != null) {
                var count=0
                for (i in 0 until it.size) {
                    val item = layoutInflater.inflate(R.layout.stage_select_grid_item, null)
                    val r = rand.nextInt(5)
                    when(r%4){
                        0->
                            item.background=getDrawable(R.drawable.blue_paper)
                        1->
                            item.background=getDrawable(R.drawable.red_paper)
                        2->
                            item.background=getDrawable(R.drawable.green_paper)
                        3->
                            item.background=getDrawable(R.drawable.orange_paper)
                    }
                    item.rotation=(r* Math.pow((-1).toDouble(),(r%2).toDouble())).toFloat()
                    item.findViewById<TextView>(R.id.stage_id_stage_select_grid_item).text = (i + 1).toString()
                    when(it[i].rank){
                        "S"->
                            item.findViewById<ImageView>(R.id.stage_select_item_rank).setImageResource(R.drawable.rank_s)
                        "A"->
                            item.findViewById<ImageView>(R.id.stage_select_item_rank).setImageResource(R.drawable.rank_a)
                        "B"->
                            item.findViewById<ImageView>(R.id.stage_select_item_rank).setImageResource(R.drawable.rank_b)
                        "C"->
                            item.findViewById<ImageView>(R.id.stage_select_item_rank).setImageResource(R.drawable.rank_c)
                        "D"->
                            item.findViewById<ImageView>(R.id.stage_select_item_rank).setImageResource(R.drawable.rank_d)
                        else->
                            item.findViewById<ImageView>(R.id.stage_select_item_rank).setImageResource(0)
                    }

                    item.setOnClickListener(gridItemClickListener(i+1,it[i].rank,it[i].time))
                    if(it[i].rank!="")  count+=1
                    val params = GridLayout.LayoutParams()
                    params.rowSpec = GridLayout.spec(i / 6,GridLayout.FILL,1f)
                    params.columnSpec = GridLayout.spec(i % 6,GridLayout.FILL,1f)
                    item.layoutParams = params
                    gridLayout.addView(item)
                }
                findViewById<TextView>(R.id.stage_select_cleared_textView).text=getString(R.string.clear_percent,count,it.size,(count*100/it.size))
            }
        })

        val backButton = findViewById<ImageButton>(R.id.back_stage_select_imageButton)
        backButton.setOnTouchListener{view, motionEvent ->
            if(motionEvent.action==MotionEvent.ACTION_DOWN){
                (view as ImageButton).setImageResource(R.drawable.back_button_background_clicked)
            }else if(motionEvent.action==MotionEvent.ACTION_UP){
                (view as ImageButton).setImageResource(R.drawable.back_button_background)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            return@setOnTouchListener true
        }

    }

    private fun gridItemClickListener(stageId: Int, rank: String, time: Int): View.OnClickListener {
        return View.OnClickListener {
            val dialogManager = DialogManager()
            if(time==99999) {
                dialogManager.startDialog(supportFragmentManager, 202, stageId, rank, null)
            }else{
                dialogManager.startDialog(supportFragmentManager, 202, stageId, rank, Time(time.toLong()*1000))
            }
        }
    }
}