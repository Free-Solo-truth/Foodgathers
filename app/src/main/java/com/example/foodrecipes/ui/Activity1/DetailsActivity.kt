package com.example.foodrecipes.ui.Activity1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodrecipes.R
import com.example.foodrecipes.ui.MainActivity
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context,name:String,telNumber:String,email:String) {
            context.startActivity(Intent(context,DetailsActivity::class.java).apply {
                putExtra("name",name)
                putExtra("tel",telNumber)
                putExtra("email",email)
            })


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        telPhone.setOnClickListener {
            MainActivity().get_permission("tel",intent.getStringExtra("tel"),this)
        }
        Chat.setOnClickListener {
            startActivity(Intent(this,ChatActivity::class.java).apply {
                putExtra("tel","${intent.getStringExtra("tel")}")
                putExtra("name","${intent.getStringExtra("name")}")
                putExtra("email","${intent.getStringExtra("email")}")
            })
        }
    }
}