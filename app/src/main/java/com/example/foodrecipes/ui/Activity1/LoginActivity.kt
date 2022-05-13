package com.example.foodrecipes.ui.Activity1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.foodrecipes.DB.Operate_User_info_db
import com.example.foodrecipes.Lifecycle.getSocketConnect_LoginActivity
import com.example.foodrecipes.R
import com.example.foodrecipes.Service.AppListener
import com.example.foodrecipes.Utils.isEmail
import com.example.foodrecipes.Utils.loginLoadingButton
import com.example.foodrecipes.ui.MainActivity
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: BaseActivity() {
    private val getData_fromSQL = getData_fromSQLViewmodel()
    /*问题1 company object*/
      val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            Log.v("Error","handlerresiver:成功")
            Toast.makeText(this@LoginActivity, msg.data.getString("result"), Toast.LENGTH_SHORT).show()
    }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val editor = prefs.edit()
        /*建立socket连接  根据页面的变化决定Socket的注册*/
        lifecycle.addObserver(getSocketConnect_LoginActivity())

        setContentView(R.layout.activity_login)

        changeStatusBarTextColor(true)

        val intent_At = Intent(this, MainActivity::class.java)


        if (prefs.getBoolean("isremember",false)){
            checkbox.isChecked = true
           editTextTextEmailAddress.setText(prefs.getString("Email",null))
            editTextTextPassword.setText(prefs.getString("password",null))
        }
        checkbox.setOnClickListener {
            Log.v("ppp1","${checkbox.isChecked}")
            if(!checkbox.isChecked) {
                editor.putBoolean("isremember", false)
                editor.apply()
            }
        }

        LoginButton.mCallBack = {state->
            if (!editTextTextEmailAddress.text.toString().isEmail()) {
                Toast.makeText(this, "Input Error. Please Reinput ", Toast.LENGTH_SHORT).show()
            } else {
//                通过email查找用户
                getData_fromSQL.VerifyPasword(editTextTextEmailAddress.text.toString(), editTextTextPassword.text.toString())
                getData_fromSQL.VerifyPasswordState.observe(this, object : Observer<String?> {
                    override fun onChanged(t: String?) {
                        when (t) {
                            "登录成功" -> {
                                Toast.makeText(this@LoginActivity,t,Toast.LENGTH_SHORT).show()
                                Log.v("LogSuccess","登录成功")
                                if (checkbox.isChecked) {
                                    editor.apply {
                                        /*本地村存储实现记住密码的功能*/
                                        putBoolean("isremember", true)
                                        putString("Email", editTextTextEmailAddress.text.toString())
                                        putString("password", editTextTextPassword.text.toString())
                                    }
                                }
                                editor.apply()
                                if(state == loginLoadingButton.OFF){
                                    startActivity(intent_At.apply {
                                        putExtra("email", editTextTextEmailAddress.text.toString())
                                    })
                                }
                                finish()
                            }

                            else ->{

                                Toast.makeText(this@LoginActivity,t,Toast.LENGTH_SHORT).show()

                            }


                        }
                    }

                })
            }
        }


        enroll.setOnClickListener {
            startActivityForResult(Intent(this, EnrollActivity::class.java),1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode ){
            1 -> if (resultCode == Activity.RESULT_OK){
                editTextTextEmailAddress.setText(data?.getStringExtra("Email"))
                editTextTextPassword.setText(data?.getStringExtra("password"))
            }
        }
    }

}


