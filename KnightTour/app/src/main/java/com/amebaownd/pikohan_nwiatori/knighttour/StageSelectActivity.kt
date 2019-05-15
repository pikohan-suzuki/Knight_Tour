package com.amebaownd.pikohan_nwiatori.knighttour

import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import com.amebaownd.pikohan_nwiatori.knighttour.Data.Record
import java.sql.Time

class StageSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stage_select)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
        val gridLayout = findViewById<GridLayout>(R.id.stage_select_gridLayout)
        db.recordDao().getAllOrderByStageId().observe(this, Observer<List<Record>> {
            if (it != null) {
                var count=0
                for (i in 0 until it.size) {
                    val item = layoutInflater.inflate(R.layout.stage_select_grid_item, null)
                    item.background=getDrawable(R.drawable.board_cell_white)
                    item.findViewById<TextView>(R.id.stage_id_stage_select_grid_item).text = (i + 1).toString()
                    item.findViewById<TextView>(R.id.rank_stage_select_grid_item).text=it[i].rank
                    item.setOnClickListener(gridItemClickListener(i+1,it[i].rank,Time(it[i].time.toLong()*1000)))
                    if(it[i].rank!="")  count+=1
                    val params = GridLayout.LayoutParams()
                    params.rowSpec = GridLayout.spec(i / 6,GridLayout.FILL,1f)
                    params.columnSpec = GridLayout.spec(i % 6,GridLayout.FILL,1f)
                    item.layoutParams = params
                    gridLayout.addView(item)
                }
                findViewById<TextView>(R.id.stage_select_cleared_textView).text=getString(R.string.clear_percent,count,it.size)
            }
        })

        val backButton = findViewById<Button>(R.id.back_stage_select_button)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun gridItemClickListener(stageId: Int, rank: String, time: Time): View.OnClickListener {
        return View.OnClickListener {
            val dialogManager = DialogManager()
            dialogManager.startDialog(supportFragmentManager, 202, stageId, rank, time)
        }
    }
}