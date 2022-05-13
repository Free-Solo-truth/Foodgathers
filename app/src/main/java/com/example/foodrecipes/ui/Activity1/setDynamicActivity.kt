package com.example.foodrecipes.ui.Activity1

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.Utils.Base64Util
import com.example.foodrecipes.Utils.Uri_to_path
import com.example.foodrecipes.Utils.getData
import com.example.foodrecipes.Utils.getFilename
import com.example.foodrecipes.model.Carrier_DynamicMessage
import com.example.foodrecipes.model.DynamicMessage
import com.example.foodrecipes.model.User_info
import com.example.foodrecipes.model.postImage.Image
import com.example.foodrecipes.model.postImage.ImageList
import com.example.foodrecipes.model.setDynamic
import com.example.foodrecipes.ui.adapter.Dynamic_image_adapter
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_set_dynamic.*
import java.io.File
import java.lang.reflect.Type
import java.net.URL


class setDynamicActivity : BaseActivity() {
    val uri:String="http://8.130.11.202:8080/post_war3/ImageServlet";
    lateinit var outputImage:File
    lateinit var imageUri: Uri
    lateinit var jsonstr: String
    lateinit var popupView: View
    lateinit var image_name_List:String
    lateinit var popuwindow:PopupWindow
    val SQL_ViewModel = getData_fromSQLViewmodel()
    var permissions = arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    var permissionsHigh = arrayOf<String>(Manifest.permission.MANAGE_EXTERNAL_STORAGE)

    var mPermissionList: MutableList<String> = ArrayList()
    // private ImageView welcomeImg = null;
    private val PERMISSION_REQUEST = 1
    private val ANDROID11_PERMISSION_REQUEST =2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_dynamic)
        changeStatusBarTextColor(true)
        popupView = layoutInflater.inflate(R.layout.popupwindow_dynamic, null)
        popuwindow = PopupWindow().apply {
            height = LinearLayout.LayoutParams.WRAP_CONTENT
            width = LinearLayout.LayoutParams.MATCH_PARENT
            contentView = popupView
            isFocusable = true
            elevation = 300f
            isTouchable = true
            isOutsideTouchable = true
            animationStyle = R.style.anim_popupWindow
        }
        /*开启相册或是相机*/
        get_permission(intent.getStringExtra("mode").toString())
        recycleView_image.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recycleView_image.isNestedScrollingEnabled = false
        if(recycleView_image.adapter!=null){
            (recycleView_image.adapter as Dynamic_image_adapter).getListener(object : Dynamic_image_adapter.callback {
                //设置点击的监听者，就是我们的回调的调用者
                override fun add_image() {
                    /*接口回调*/
                    add_image1()
                }

            })
        }

        /*发布Dynamic*/
        send_dynamic_button.setOnClickListener {
            displayImage(setDynamic.List_path)
        }
        /*监听是否发布成功*/
        listener_post()
    }

    /*添加imageView*/
    fun add_image1(){
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
            overridePendingTransition(R.anim.translate_in, R.anim.translate_out)
        }
        popupView.findViewById<TextView>(R.id.lnumb).setOnClickListener {
            popuwindow.dismiss()
            get_permission("album")
            overridePendingTransition(R.anim.translate_in, R.anim.translate_out)
        }
        popupView.findViewById<TextView>(R.id.cancel).setOnClickListener {
            popuwindow.dismiss()
        }
    }



//    fun checkStorageManagerPermissionandpostdynamic(context: Context) {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
////                !Environment.isExternalStorageManager()) {
////
////        }else{
////            displayImage(setDynamic.List_path)
////        }
//        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        context.startActivity(intent)
//    }
    /*post_image*/
    fun displayImage(Image_List: List<String>){
        var  bitmap:Bitmap?=null
        var picture:String?=null
        var List_image:ImageList= ImageList()
        var image_name:String? = null
        val type: Type = object : TypeToken<ImageList?>() {}.getType()
        for(i in 0..Image_List.size-1){
            bitmap= BitmapFactory.decodeFile(Image_List.get(i))
            picture= Base64Util.bitmapToBase64(bitmap)
            image_name = getFilename.getFileName(Image_List.get(i) + ".jpg")
//            image_name_List +=image_name+"|"
            Log.v("uri", "${Image_List.get(i)}")
            List_image.array_image.add(Image().apply {
                image = picture
                imagename = image_name
            })
        }
         jsonstr = Gson().toJson(List_image, type)
        SQL_ViewModel.post_Image(URL(uri), jsonstr)
    }

    fun listener_post(){
        SQL_ViewModel.PostImageState.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                when (t?.toInt()) {
                    null -> {
                        Toast.makeText(this@setDynamicActivity, "开始发布", Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        Toast.makeText(this@setDynamicActivity, "发布成功", Toast.LENGTH_SHORT).show()
                        val test: List<DynamicMessage> = ArrayList<DynamicMessage>().apply {
                            add(DynamicMessage().apply {
                                this.userIamge = User_info.User?.photo
                                this.userName = User_info.User?.name
                                this.dynamicText = dynamic_text.text.toString()
                                this.dynamicImage = jsonstr
                            })
                        }
                        SQL_ViewModel.save_dynamic(User_info.User?.email, Carrier_DynamicMessage(User_info.User?.email, test))
                    }
                    -1 -> {
                        Toast.makeText(this@setDynamicActivity, "发布失败", Toast.LENGTH_SHORT).show()

                    }
                    else -> {

                    }
                }
            }
        })
        SQL_ViewModel.SaveDynamicState.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                when (t) {
                    null -> {

                    }
                    "插入成功" -> {
                        Toast.makeText(this@setDynamicActivity, t, Toast.LENGTH_SHORT).show()
                        finish()
                        overridePendingTransition(R.anim.destory_translate_in, R.anim.destory_translate_out)
                    }
                    "插入失败" -> {
                        Toast.makeText(this@setDynamicActivity, t, Toast.LENGTH_SHORT).show()
                    }
                    else -> {

                    }
                }
            }
        })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            4 -> {
                if (resultCode == Activity.RESULT_OK) {
                    setDynamic.List_path.add(outputImage.absolutePath)
                    com.example.foodrecipes.model.setDynamic.List_Uri.add(imageUri)
                    startActivity(Intent(this, setDynamicActivity::class.java))
                    recycleView_image.adapter = Dynamic_image_adapter(setDynamic.List_Uri, this)
                    (recycleView_image.adapter as Dynamic_image_adapter).notifyDataSetChanged()
                     Log.v("ppp", "${outputImage.absolutePath}")
                }
            }
            5 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data.let { uri ->
                        var result = Uri_to_path.getFilePathByUri(this, uri)
                        setDynamic.List_path.add(result)
                        Log.v("ppp1", "${uri}")
                        Log.v("ppp1", "${result}")
                        setDynamic.List_Uri.add(uri)
                        recycleView_image.adapter = Dynamic_image_adapter(setDynamic.List_Uri, this)
                        (recycleView_image.adapter as Dynamic_image_adapter).notifyDataSetChanged()
                    }
                }
            }
//            content://com.android.providers.media.documents/document/image%3A77
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("this", "OnDestroy")
        setDynamic.List_Uri.clear()
    }
}