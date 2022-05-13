package com.example.foodrecipes.ui.Activity1

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.foodrecipes.R
import com.example.foodrecipes.Utils.checkPremission
import com.example.foodrecipes.Utils.getData
import com.example.foodrecipes.model.Carrier_DynamicMessage
import com.example.foodrecipes.model.User_info
import com.example.foodrecipes.ui.adapter.dynamiAdapter
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dynmic.*
import kotlinx.android.synthetic.main.activity_set_dynamic.*
import java.io.File


class DynamicActivity : BaseActivity() {
    val getInfo_SQL = getData_fromSQLViewmodel()
    lateinit var outputImage:File
    lateinit var imageUri: Uri
    lateinit var email:String
    lateinit var popupView:View
    lateinit var popuwindow:PopupWindow
    var dynamic:Carrier_DynamicMessage? = null
     var show = false
    lateinit var Adapter:dynamiAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynmic)

        email = intent.getStringExtra("email").toString()
        recycleView_dynamic.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
            reverseLayout = true
            stackFromEnd = true


        }

        recycleView_dynamic.setItemViewCacheSize(200)
        Adapter = dynamiAdapter(dynamic)
        recycleView_dynamic.adapter = Adapter

        InitUi()
         popupView = layoutInflater.inflate(R.layout.popupwindow_dynamic, null)
         popuwindow = PopupWindow().apply {
            height = LinearLayout.LayoutParams.WRAP_CONTENT
            width = LinearLayout.LayoutParams.MATCH_PARENT
            contentView = popupView
            isFocusable = true
            elevation= 300f
            isTouchable = true
            isOutsideTouchable = true
            animationStyle = R.style.anim_popupWindow
        }
//        top_dynamic1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.gradient_alpha).apply {
//            fillAfter = true
//        })
        /*刷新朋友圈界面*/
         Refresh_Dynamic()
        /*发布动态*/
        setDynamic.setOnClickListener {
            checkPremission(this)
            setDynamic()
        }
        /*初始化动态界面*/
        Init_Dynamic_recycleView()
        /*Back*/
        back()
        /*更换背景*/
        set_background.setOnClickListener {
           setDynamic_background()
        }

    }

    private fun InitUi(){
        dynamic_username.setText(User_info.User?.name)
        Picasso.with(dynamic_userimage.context).load(User_info.User?.photo).into(dynamic_userimage)
    }


    /*打开相册或是相机*/
    fun get_permission(permissions: String, value: String? = null, intent: Intent? = null){
        when(permissions){
            "camera" -> {
                outputImage = File(externalCacheDir, getData.getData1() + ".jpg")
                Log.v("ppp1", "Filename:${outputImage}")
                //file对应不同的版本转换为不同的uri
                imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //版本不低于7.0  这里涉及fileContext的注册标注authority
                    FileProvider.getUriForFile(this, "com.example.foodrecipes.fileprvoider", outputImage)
                } else {
                    Uri.fromFile(outputImage)
                }
                //开启相机
                startActivityForResult(Intent("android.media.action.IMAGE_CAPTURE").putExtra(MediaStore.EXTRA_OUTPUT, imageUri), 4)
            }
            "album" -> {
                startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    this.addCategory(Intent.CATEGORY_OPENABLE)
                    this.type = "image/*"
                }, 5)
            }
        }
    }

    /*发布动态*/
    fun setDynamic(){
        this.window.attributes = this.window.attributes.apply {
            alpha = 0.7f
        }
        popuwindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0)
        /*cancel*/
        popuwindow.setOnDismissListener {
            this.window.attributes = this.window.attributes.apply {
                alpha = 1f
            }
        }
        popupView.findViewById<TextView>(R.id.camera).setOnClickListener {
            popuwindow.dismiss()
            startActivity(Intent(this, setDynamicActivity::class.java).apply {
                putExtra("mode", "camera")
            })
            overridePendingTransition(R.anim.translate_in, R.anim.translate_out)
        }
        popupView.findViewById<TextView>(R.id.lnumb).setOnClickListener {
            popuwindow.dismiss()
            startActivity(Intent(this, setDynamicActivity::class.java).apply {
                putExtra("mode", "album")
            })
            overridePendingTransition(R.anim.translate_in, R.anim.translate_out)
        }
        popupView.findViewById<TextView>(R.id.cancel).setOnClickListener {
            popuwindow.dismiss()
        }
    }
    /*设置背景图片*/
    fun setDynamic_background(){
        this.window.attributes = this.window.attributes.apply {
            alpha = 0.7f
        }
        popuwindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0)
        /*cancel*/
        popuwindow.setOnDismissListener {
            this.window.attributes = this.window.attributes.apply {
                alpha = 1f
            }
        }
        popupView.findViewById<TextView>(R.id.camera).setOnClickListener {
            popuwindow.dismiss()
            get_permission("camera")
        }
        popupView.findViewById<TextView>(R.id.lnumb).setOnClickListener {
            popuwindow.dismiss()
            get_permission("album")
        }
        popupView.findViewById<TextView>(R.id.cancel).setOnClickListener {
            popuwindow.dismiss()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            4 -> {
                if (resultCode == Activity.RESULT_OK) {
                    set_background.setImageBitmap(BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri)))
                    Log.v("ppp", "${imageUri}")
                }
            }
            5 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data.let { uri ->
                        set_background.setImageBitmap(BitmapFactory.decodeStream(contentResolver.openInputStream(uri!!)))
                    }
                }
            }
        }
    }



    /*刷新朋友圈界面*/
    fun Refresh_Dynamic(){
        swipRefreshView.setBackgroundResource(android.R.color.white)
        swipRefreshView.setColorSchemeResources(R.color.black, R.color.teal_200, R.color.red)

        swipRefreshView.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                Log.v("result5", "start")
                getInfo_SQL.getDynamicInfo(email)
                Log.v("result5", "成功")
            }
        })
    }
    /*初始化朋友圈界面*/
    fun Init_Dynamic_recycleView(){
        getInfo_SQL.getDynamicInfo(email)
        getInfo_SQL.DynamicInfoState.observe(this, object : Observer<Carrier_DynamicMessage?> {
            override fun onChanged(t: Carrier_DynamicMessage?) {
                when (t) {
                    null -> {
                        Toast.makeText(this@DynamicActivity, "正在加载", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        swipRefreshView.isRefreshing = false
                        Toast.makeText(this@DynamicActivity, "加载完成", Toast.LENGTH_SHORT).show()
                        /*???*/
                        Adapter.notifydynamic(t)
                    }
                }
            }

        })
    }
    /*back*/
    fun back(){
        back.setOnClickListener{
            finish()
            overridePendingTransition(R.anim.destory_translate_in, R.anim.destory_translate_out)
        }
    }

}