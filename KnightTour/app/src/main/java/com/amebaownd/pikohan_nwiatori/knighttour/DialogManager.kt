package com.amebaownd.pikohan_nwiatori.knighttour

import android.support.v4.app.FragmentManager
import com.amebaownd.pikohan_nwiatori.knighttour.Dialog.*
import java.sql.Time

class DialogManager(){
    @JvmOverloads
    fun startDialog(fragmentManager: FragmentManager, id:Int, stageId:Int=1, rank:String="", time:Time? = null){
        when(id){
            //pause
            201->{
                val dialog = PauseDialog()
                dialog.stageId=stageId
                dialog.rank=rank
                dialog.time=time
                dialog.setTargetFragment(null,id)
                dialog.show(fragmentManager,"pause")
            }
            //confirm_start
            202->{
                val dialog= ConfirmStartDialog()
                dialog.rank=rank
                dialog.stageId=stageId
                dialog.time=time
                dialog.setTargetFragment(null,id)
                dialog.show(fragmentManager,"confirm_start")
            }
            //clear
            203->{
                val dialog= ClearDialog()
                dialog.rank=rank
                dialog.time=time
                dialog.stageId=stageId
                dialog.setTargetFragment(null,id)
                dialog.show(fragmentManager,"clear")
            }
            //new_record
            204->{
                val dialog= NewRecordDialog()
                dialog.rank=rank
                dialog.time=time
                dialog.stageId=stageId
                dialog.setTargetFragment(null,id)
                dialog.show(fragmentManager,"new_record")
            }
            //congratulation
            205->{
                val dialog= CongratulationDialog()
                dialog.setTargetFragment(null,id)
                dialog.show(fragmentManager,"congratulation")
            }

        }
    }
}