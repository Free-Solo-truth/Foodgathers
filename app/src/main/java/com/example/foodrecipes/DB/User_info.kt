package com.example.foodrecipes.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val user_info = "create table User_info ( " +
        "id integer primary key autoincrement,"+
        "name text,"+
        "image none,"+
        "email text,"+
        "tel text,"+
        "password text)"
const val ConPerosn_info = "Create table ConPerson_info1 ("+
        "id integer primary key autoincrement,"+
        "name text,"+
        "telNumber text)"
//这里的Version使用在数据库的跟新中，只要是比调用的时候输入的大，就表示要对数据库进行跟新，执行onUpgrade
class User_info_db(val context:Context,name:String,version:Int):SQLiteOpenHelper(context,name,null,version){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(user_info)
        db?.execSQL(ConPerosn_info)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       if(oldVersion <= 5){
           db?.execSQL(ConPerosn_info)
       }
    }

}
