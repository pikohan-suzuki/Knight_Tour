package com.amebaownd.pikohan_nwiatori.knighttour

data class Range(val row:Int,val column:Int)

class RangeStack(){
    var mutableList = mutableListOf<Range>()

    fun push(range:Range){
        mutableList.add(range)
    }

    fun pop():Range{
        val result =mutableList[mutableList.lastIndex]
        mutableList.removeAt(mutableList.lastIndex)
        return result
    }
    fun isEmpty() = mutableList.isEmpty()

    fun isUnique(range:Range)=!(mutableList.contains(range))

    fun size() = mutableList.size

    fun clear(){
        while(mutableList.size!=0)
            this.pop()
    }
}