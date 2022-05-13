package com.example.foodrecipes.DB

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.foodrecipes.model.ConPersonData
import com.example.foodrecipes.model.Relationship
import com.example.foodrecipes.ui.Activity1.ActivityCollector

object Operate_User_info_db {
    val dbUserHelper by lazy {
         User_info_db(ActivityCollector.Activity[ActivityCollector.Activity.size-1],"User_info.db",6)
    }
    val dbConPersonHelper by lazy {
        User_info_db(ActivityCollector.Activity[ActivityCollector.Activity.size-1],"User_info.db",6)
    }
    val Userdb by lazy {
        dbUserHelper.writableDatabase
    }
    val ConPersondb  by lazy {
        dbConPersonHelper.writableDatabase
    }
    val operUser_info:Oper_user_info by lazy{
        Oper_user_info(Userdb,"User_info")
    }
    val operConPerson_info by lazy {
        Oper_user_info(ConPersondb,"ConPerson_info1")
    }

}

interface Operate_User_info_service{
    fun queryisemail(Email:String):Boolean{
        return false
    }
    fun querypassword(email: String,password: String):Boolean
    fun queryphone(email: String)
    fun query_all_info(email: String)
    fun delete(email: String)
    fun insert(name:String,email: String,tel:String,password:String)
    fun update(vlaues:ContentValues,where:String,whereVlaue:String)

    /*查询联系人*/
    fun queryConPerson():ArrayList<Relationship>
    /*插入联系人*/
    fun insertConPerson(ConPersonData: ArrayList<ConPersonData>)
    /*查询某个联系人*/
    fun queryOneConPerson(TelNumber: String):Boolean
}

class Oper_user_info(val db: SQLiteDatabase,val tablename:String):Operate_User_info_service{
    override fun queryisemail(Email: String):Boolean {
        var isExit = false
        val cursor = db.query(tablename,null,null,null,null,null,null)
        if (cursor.moveToFirst()){
            do {
                val email =  cursor.getString(cursor.getColumnIndex("email"))
                if (email == Email) {
                   isExit = true
                }
            }while (cursor.moveToNext())
        }
        cursor.close()
        Log.v("isExit","${isExit}")
        return isExit
    }

    override fun querypassword(email: String,password: String):Boolean {
        var isright = false
        val cursor = db.query(tablename, arrayOf("password"),"email = ?", arrayOf(email),null,null,null)
        if (cursor.moveToNext() && password == cursor.getString(cursor.getColumnIndex("password"))){
            isright = true
        }
        cursor.close()
        return isright
    }

    override fun queryphone(email: String) {
    }

    override fun query_all_info(email: String) {
        TODO("Not yet implemented")
    }

    override fun delete(email: String) {
        TODO("Not yet implemented")
    }

    override fun insert(name: String, email: String, tel: String, password: String) {
        db.insert(tablename,null,ContentValues().apply {
            put("name",name)
            put("email",email)
            put("tel",tel)
            put("password",password)
        })
    }

    override fun update(vlaues: ContentValues, where: String, whereVlaue: String) {
        TODO("Not yet implemented")
    }
/*ConPerSon的查询*/
    override fun queryConPerson():ArrayList<Relationship> {
    Log.v("DBVersion","${db.version}")
    var i =0
        var array = ArrayList<Relationship>()
        val Cursor = db.query(tablename,null,null,null,null,null,null)
    Log.v("NewsState","${db.version}")
    if (Cursor.moveToFirst()){
            do {
                array.add( Relationship("",Cursor.getString(Cursor.getColumnIndex("telNumber"))
                        ,Cursor.getString(Cursor.getColumnIndex("name"))))
            }while (Cursor.moveToNext())
        }
    Log.v("NewsState","${db.version}")
    Cursor.close()
    return array
    }
/*ConPerson的插入*/
    override fun insertConPerson(ConPersonData:ArrayList<ConPersonData>) {
    Log.v("DBVersion","${db.version}")
        ConPersonData.forEach {
            if (!queryOneConPerson(it.TelNumber)) {
                db.insert(tablename, null, ContentValues().apply {
                    put("name", it.name)
                    put("telNumber", it.TelNumber)
                })
            }
        }
    }
    /*查询某个ConPerson是否存在*/
    override fun queryOneConPerson(TelNumber:String): Boolean {
        val cursor = db.query(tablename,null,"telNumber = ?", arrayOf(TelNumber),null,null,null)
        return cursor.moveToFirst()
    }




}
