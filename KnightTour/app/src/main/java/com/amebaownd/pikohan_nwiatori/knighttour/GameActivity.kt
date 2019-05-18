package com.amebaownd.pikohan_nwiatori.knighttour

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.amebaownd.pikohan_nwiatori.knighttour.Data.Record
import com.amebaownd.pikohan_nwiatori.knighttour.Data.Stage
import com.amebaownd.pikohan_nwiatori.knighttour.Data.StageInfo
import java.lang.Exception
import java.lang.Math.abs
import java.sql.Time
import kotlin.concurrent.thread

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

    lateinit var gridLayout: GridLayout
    lateinit var chronometer: Chronometer
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        stage_id = intent.getIntExtra("stage_id", 1)

        db = Room.databaseBuilder(this, AppDatabase::class.java, "database").build()

        gridLayout = findViewById<GridLayout>(R.id.game_gridLayout)
        chronometer = findViewById<Chronometer>(R.id.timer)

        createGameScreen()

//        findViewById<ImageButton>(R.id.pause_game).setOnClickListener(pauseButtonClickListener)
//        findViewById<ImageButton>(R.id.back_to_previous_range_imageButton).setOnClickListener {
//            currentRange = backToPreviousRange()
//        }


        findViewById<ImageButton>(R.id.pause_game).setOnTouchListener(setTouchListener(findViewById<ImageButton>(R.id.pause_game)))
        findViewById<ImageButton>(R.id.back_to_previous_range_imageButton).setOnTouchListener(setTouchListener(findViewById<ImageButton>(R.id.back_to_previous_range_imageButton)))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == 203 && resultCode == Activity.RESULT_OK) {
            if (stage_id < NUMBER_OF_STAGES - 1) {
                stage_id += 1
                createGameScreen()
            }
        }
    }

    private fun createGameScreen() {
        resetBoards()
        //ステージ情報を取得
        db.stageDao().getByStageId(stage_id).observe(this, Observer<Stage> {
            if (it != null) {
                gridLayout.rowCount = it.row
                gridLayout.columnCount = it.column
                sTime = it.sTime
                aTime = it.aTime
                bTime = it.bTime
                cTime = it.cTime
            }
            //セルを正方形に
            if (gridLayout.width != 0 && gridLayout.height != 0) {
                val params = gridLayout.layoutParams
                if (gridLayout.width > gridLayout.height) {
                    params.width = gridLayout.height
                } else {
                    params.height = gridLayout.width
                }
                gridLayout.layoutParams = params
            }
            //セル情報の取得・inflate・bind
            var row = 0
            var column = 0
            db.stageInfoDao().getByStageId(stage_id).observe(this, Observer<List<StageInfo>> { cells ->
                if (cells != null) {
                    numberOfRange = cells.size
                    cells.forEach { cell ->
                        while (row != cell.row || column != cell.column) {
                            val params = GridLayout.LayoutParams()
                            params.rowSpec = GridLayout.spec(row, GridLayout.FILL, 1f)
                            params.columnSpec = GridLayout.spec(column, GridLayout.FILL, 1f)
                            val view = layoutInflater.inflate(R.layout.game_grid_item, null)
                            view.layoutParams = params
                            view.background = getDrawable(R.drawable.blank_cell2)
                            gridLayout.addView(view)
                            row += (column + 1) / gridLayout.columnCount
                            column = (column + 1) % gridLayout.columnCount
                        }
                        val params = GridLayout.LayoutParams()
                        params.rowSpec = GridLayout.spec(cell.row, GridLayout.FILL, 1f)
                        params.columnSpec = GridLayout.spec(cell.column, GridLayout.FILL, 1f)
                        val view = layoutInflater.inflate(R.layout.game_grid_item, null)
                        view.setOnClickListener(gridItemClickOnListener(cell.row, cell.column))
                        view.layoutParams = params
                        view.background =
                            if ((cell.row + cell.column) % 2 == 0) getDrawable(R.drawable.board_cell_white) else getDrawable(
                                R.drawable.board_cell_black
                            )
                        gridLayout.addView(view)
                        row += (column + 1) / gridLayout.columnCount
                        column = (column + 1) % gridLayout.columnCount
                    }
                }
                while (row != gridLayout.rowCount) {
                    val params = GridLayout.LayoutParams()
                    params.rowSpec = GridLayout.spec(row, GridLayout.FILL, 1f)
                    params.columnSpec = GridLayout.spec(column, GridLayout.FILL, 1f)
                    val view = layoutInflater.inflate(R.layout.game_grid_item, null)
                    view.layoutParams = params
                    view.background = getDrawable(R.drawable.blank_cell2)
                    gridLayout.addView(view)
                    row += (column + 1) / gridLayout.columnCount
                    column = (column + 1) % gridLayout.columnCount
                }
            })
            //ステージレコードを取得
            db.recordDao().getByStageId(stage_id).observe(this, Observer<Record> { record ->
                if (record != null) {
                    bestRank = record.rank
                    bestTime = Time(record.time.toLong() * 1000)
                }
            })
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
        })
    }

    private fun clear(rank: String, time: Time) {
        if (stage_id < NUMBER_OF_STAGES)
            writeFile(this, "nextStage", (stage_id + 1).toString())
        else
            writeFile(this, "nextStage", 1.toString())
        val dialogManager = DialogManager()
        if(stage_id==NUMBER_OF_STAGES)
            dialogManager.startDialog(supportFragmentManager, 205, stage_id, rank, time)
        else
            dialogManager.startDialog(supportFragmentManager, 203, stage_id, rank, time)
        if (time < bestTime) {
            val newRecord = Record()
            newRecord.stageId = this.stage_id
            newRecord.rank = rank
            newRecord.time = time.seconds

            thread {
                try {
                    db.recordDao().update(newRecord)
                } catch (e: Exception) {
                    Log.d("update database exception", e.toString())
                }
            }
            dialogManager.startDialog(supportFragmentManager, 204, stage_id, rank, time)
        }

    }

    private fun isMovable(currentRange: Range, destinationRange: Range) =
        (abs(currentRange.row - destinationRange.row) + abs(currentRange.column - destinationRange.column) == 3 &&
                (abs(currentRange.row - destinationRange.row) == 2 || abs(currentRange.column - destinationRange.column) == 2))

    private fun isClear() = (numberOfRange == rangeStack.size() + 1)

    private fun backToPreviousRange(): Range {
        if (rangeStack.isEmpty() && currentRange != Range(-1, -1)) {
            moveKnight(gridLayout, null, currentRange, true)
            return Range(-1, -1)
        } else if (!(rangeStack.isEmpty())) {
            val previousRange = rangeStack.pop()
            moveKnight(gridLayout, previousRange, currentRange, true)
            return previousRange
        }
        return Range(-1, -1)
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

    private fun setTouchListener(view:View):View.OnTouchListener?{
        when(view.id){
            R.id.pause_game->{
                return View.OnTouchListener { view, event ->
                    if(event.action==MotionEvent.ACTION_DOWN){
                        (view as ImageView).setImageResource(R.drawable.pause_button_clicked)
                    }else if(event.action==MotionEvent.ACTION_UP){
                        val dialogManager = DialogManager()
                        dialogManager.startDialog(supportFragmentManager, 201, stage_id, bestRank, bestTime)
                        (view as ImageView).setImageResource(R.drawable.pause_button)
                    }
                    return@OnTouchListener true
                }
            }
            R.id.back_to_previous_range_imageButton->{
                return View.OnTouchListener { view, event ->
                    if(event.action==MotionEvent.ACTION_DOWN){
                        (view as ImageView).setImageResource(R.drawable.back_button_clicked)
                    }else if(event.action==MotionEvent.ACTION_UP){
                        currentRange = backToPreviousRange()
                        (view as ImageView).setImageResource(R.drawable.back_button)
                    }
                    return@OnTouchListener true
                }
            }
        }
        return null
    }

    private fun gridItemClickOnListener(row: Int, column: Int): View.OnClickListener {
        return View.OnClickListener {
            val destinationRange = Range(row, column)
            if (rangeStack.isUnique(destinationRange)) {
                if (currentRange == Range(-1, -1)) {
                    currentRange = destinationRange
                    moveKnight(gridLayout, destinationRange, null, false)
                } else if (isMovable(currentRange, destinationRange)) {
                    rangeStack.push(currentRange)
                    moveKnight(gridLayout, destinationRange, currentRange, false)
                    currentRange = destinationRange
                    setCheckedImageResource(it)
                    if (isClear()) {
                        chronometer.stop()
                        clear(
                            getRank(Time(SystemClock.elapsedRealtime() - chronometer.base)),
                            Time(SystemClock.elapsedRealtime() - chronometer.base)
                        )

                    }
                }
            }
        }
    }

    private fun moveKnight(gridLayout: GridLayout, nextRange: Range?, previousRange: Range?, isBack: Boolean) {
        if (nextRange != null) {
            if (isBack) {
                gridLayout.getChildAt(nextRange.row * gridLayout.columnCount + nextRange.column).background =
                    if ((nextRange.row + nextRange.column) % 2 == 0) getDrawable(R.drawable.board_cell_white) else getDrawable(
                        R.drawable.board_cell_black
                    )
            }
            gridLayout.getChildAt(nextRange.row * gridLayout.columnCount + nextRange.column)
                .findViewById<ImageView>(R.id.pawn).setImageResource(R.drawable.knight)
            gridLayout.getChildAt(nextRange.row * gridLayout.columnCount + nextRange.column)
                .background=getDrawable(R.drawable.board_cell_clicked2)
        }
        if (previousRange != null) {
            gridLayout.getChildAt(previousRange.row * gridLayout.columnCount + previousRange.column)
                .findViewById<ImageView>(R.id.pawn).setImageResource(0)
            if (isBack) {
                gridLayout.getChildAt(previousRange.row * gridLayout.columnCount + previousRange.column)
                    .background =
                    if ((previousRange.row + previousRange.column) % 2 == 0) getDrawable(R.drawable.board_cell_white) else getDrawable(
                        R.drawable.board_cell_black
                    )
            } else {
//                gridLayout.getChildAt(previousRange.row * gridLayout.columnCount + previousRange.column)
//                    .background =
//                    getDrawable(R.drawable.board_cell_clicked2)
            }
        }
    }

    private fun resetBoards() {
        gridLayout.removeAllViews()
        rangeStack.clear()
        currentRange = Range(-1, -1)
        findViewById<TextView>(R.id.game_stage_id_textView).text = getString(R.string.stage_id_pause,stage_id)
    }

    private fun setCheckedImageResource(view: View) {
//        view.setBackgroundResource(R.drawable.checked_game_grid_background)
    }

    private fun setUnCheckedImageResource(view: View) {
//        view.setBackgroundResource(R.drawable.checked_game_grid_background)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        writeFile(this, "nextStage", stage_id.toString())
    }
}