package com.amebaownd.pikohan_nwiatori.knighttour

import android.app.Application
import android.content.Context
import java.io.BufferedReader
import java.io.File

fun readFile(context: Context, file:String): String?{
    val readFile = File(context.filesDir,file)
    if(readFile.exists()){
        return readFile.bufferedReader().use(BufferedReader::readText)
    }
    return null
}
fun writeFile(context: Context,fileName:String,data:String){
    File(context.filesDir,fileName).writer().use {
        it.write(data)
    }
}