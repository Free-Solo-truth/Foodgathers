package com.example.foodrecipes.ui.Activity1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.foodrecipes.DB.Operate_User_info_db
import com.example.foodrecipes.R
import com.example.foodrecipes.Utils.isEmail
import com.example.foodrecipes.Utils.isPhone
import com.example.foodrecipes.model.UserData
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import kotlinx.android.synthetic.main.activity_enroll.*

class EnrollActivity : BaseActivity() {
    val getresult = getData_fromSQLViewmodel()
    lateinit var editor:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getPreferences(Context.MODE_PRIVATE)
        editor = prefs.edit()
        val Operator = Operate_User_info_db.operUser_info
        setContentView(R.layout.activity_enroll)

        val intent = Intent(this, LoginActivity::class.java)

        changeStatusBarTextColor(true)
        EnrollFinsh.setOnClickListener {
            if (!editTextTextEmailAddress.text.toString().isEmail()||editTextTextEmailAddress.text.toString()==null) {
                Toast.makeText(this, "请输入正确的邮箱", Toast.LENGTH_LONG).show()
            }else if(!editTextPhone.text.toString().isPhone()||editTextPhone.text.toString()==null){
                Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_LONG).show()
            } else {
                getresult.User_Enroll(UserData(editTextname.text.toString(),
                    editTextTextEmailAddress.text.toString(),
                    editTextPhone.text.toString(),
                    editTextPassword.text.toString()
                    ))
                getresult.User_EnrollState.observe(this,object :Observer<String?>{
                    override fun onChanged(t: String?) {
                        when(t){
                            "邮箱已存在" ->{
                                Toast.makeText(this@EnrollActivity,t,Toast.LENGTH_SHORT).show()
                                AlertDialog.Builder(this@EnrollActivity)
                                        .setTitle("Email already registered")
                                        .setMessage("Login Directly?")
                                        .setPositiveButton("OK") { dialog, which ->
                                            startActivity(intent)
                                        }
                                        .setNegativeButton("cencel") { dialog, which -> }
                                        .show()
                        }
                            "注册成功" ->{
                                startActivity(intent)
                            }
                            else ->{
                                Toast.makeText(this@EnrollActivity,t,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                })

            }
        }


    }

}