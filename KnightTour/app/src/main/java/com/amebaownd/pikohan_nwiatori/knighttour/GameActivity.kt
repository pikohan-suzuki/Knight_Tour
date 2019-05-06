package com.amebaownd.pikohan_nwiatori.knighttour

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.GridLayout
import java.lang.Math.abs
import java.sql.Time

class GameActivity : AppCompatActivity() {
    val NUMBER_OF_STAGES = 30
    var stage_id = 1
    var bestRank = ""
    var bestTime: Time? = null
    var numberOfRange = 9999
    var currentRange = Range(-1, -1)
    var rangeStack = RangeStack()
    var sTime = 0
    var aTime = 0
    var bTime = 0
    var cTime = 0

    lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_game)

        db = Room.databaseBuilder(this, AppDatabase::class.java, "database").build()
        createGameScreen()

        findViewById<Button>(R.id.pause_game).setOnClickListener(pauseButtonClickListener)
        findViewById<Button>(R.id.back_to_previous_range_button).setOnClickListener {
            currentRange = backToPreviousRange()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == 202 && resultCode == Activity.RESULT_OK) {
            if (stage_id < NUMBER_OF_STAGES - 1) {
                stage_id += 1
                createGameScreen()
            }
        }
    }

    private fun createGameScreen() {
        val gridLayout = findViewById<GridLayout>(R.id.game_gridLayout)
        gridLayout.removeAllViews()
        db.stageDao().getByStageId(stage_id).observe(this, Observer<Stage> {
            if (it != null) {
                gridLayout.rowCount = it.row
                gridLayout.columnCount = it.column
                sTime = it.sTime
                aTime = it.aTime
                bTime = it.bTime
                cTime = it.cTime
            }
        })
        db.recordDao().getByStageId(stage_id).observe(this, Observer<Record> {
            if (it != null) {
                bestRank = it.rank
                bestTime = Time(it.time.toLong())
            }
        })
        db.stageInfoDao().getByStageId(stage_id).observe(this, Observer<List<StageInfo>> {
            if (it != null) {
                numberOfRange = it.size
                it.forEach {
                    val params = GridLayout.LayoutParams()
                    params.rowSpec = GridLayout.spec(it.row)
                    params.rowSpec = GridLayout.spec(it.column)
                    val view = layoutInflater.inflate(R.layout.game_grid_item, null)
                    view.setOnClickListener(gridItemClickOnListener(it.row, it.column))
                    view.layoutParams = params
                    gridLayout.addView(view)
                }
            }
        })

    }

    private fun clear(rank: String, time: Time) {
        val dialogManager = DialogManager()
        dialogManager.startDialog(supportFragmentManager, 203, stage_id, rank, time)
        if (time < bestTime)
            dialogManager.startDialog(supportFragmentManager, 204, stage_id, rank, time)
    }

    private fun isMovable(currentRange: Range, destinationRange: Range) =
        (abs(currentRange.row - destinationRange.row) + abs(currentRange.column - destinationRange.column) == 3 &&
                (abs(currentRange.row - destinationRange.row) == 2 || abs(currentRange.column - destinationRange.column) == 2))

    private fun isClear() = (numberOfRange == rangeStack.size() + 1)

    private fun backToPreviousRange(): Range {
        if (rangeStack.isEmpty() && currentRange!=Range(-1,-1)) {
            setUnCheckedImageResource()
            return Range(-1, -1)
        }else {
            return rangeStack.pop()
        }
    }

    private fun getRank(time: Time): String {
        if (time.seconds < sTime)
            return "S"
        else if (time.seconds < aTime)
            return "A"
        else if (time.seconds < bTime)
            return "B"
        else if (time.seconds < cTime)
            return "C"
        else
            return "D"
    }

    override fun onBackPressed() {}

    private val pauseButtonClickListener = View.OnClickListener {
        val dialogManager = DialogManager()
        dialogManager.startDialog(supportFragmentManager, 201, stage_id, bestRank, bestTime)
    }

    private fun gridItemClickOnListener(row: Int, column: Int): View.OnClickListener {
        return View.OnClickListener {
            val destinationRange = Range(row, column)
            if (rangeStack.isUnique(destinationRange)) {
                if (currentRange == Range(-1, -1)) {
                    currentRange = destinationRange
                } else if (isMovable(currentRange, destinationRange)) {
                    rangeStack.push(currentRange)
                    currentRange = destinationRange
                    setCheckedImageResource(it)
                    if (isClear())
                        clear(
                            getRank(Time(findViewById<Chronometer>(R.id.timer).base)),
                            Time(findViewById<Chronometer>(R.id.timer).base)
                        )
                }
            }
        }
    }

    private fun setCheckedImageResource(view:View){
//        view.setBackgroundResource(R.drawable.checked_game_grid_background)
    }
    private fun setUnCheckedImageResource(view:View){
//        view.setBackgroundResource(R.drawable.checked_game_grid_background)
    }
}