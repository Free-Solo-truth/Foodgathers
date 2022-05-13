 package com.example.foodrecipes.ui


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.viewpager.widget.ViewPager
import com.example.foodrecipes.DB.Operate_User_info_db
import com.example.foodrecipes.DB.user_info
import com.example.foodrecipes.Lifecycle.getUserInfo_MainActivity
import com.example.foodrecipes.R
import com.example.foodrecipes.Utils.*
import com.example.foodrecipes.ui.Activity1.SearchActivity
import com.example.foodrecipes.databinding.ActivityMainBinding
import com.example.foodrecipes.model.*
import com.example.foodrecipes.model.postImage.Image
import com.example.foodrecipes.model.postImage.ImageList
import com.example.foodrecipes.ui.Activity1.ActivityCollector
import com.example.foodrecipes.ui.Activity1.BaseActivity
import com.example.foodrecipes.ui.Activity1.Conperson_infoActivity
import com.example.foodrecipes.ui.adapter.Dynamic_image_adapter
import com.example.foodrecipes.ui.adapter.ViewpagerAdapter
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_set_dynamic.*
import kotlinx.android.synthetic.main.fragment_user_info_header.*
import java.io.File
import java.lang.reflect.Type
import java.net.URL


 class MainActivity : BaseActivity()  {
     val uri:String="http://你的服务器:8080/post_war3/ImageServlet";
     private val getUserInfo = getData_fromSQLViewmodel()
    private var menuItem: MenuItem? = null
     private var user_image:String? = null
     lateinit var navController:NavController
     lateinit var jsonstr: String
     var title:String? = null
     val SQL_ViewModel = getData_fromSQLViewmodel()
     //相机权限
     lateinit var outputImage:File
     lateinit var imageUri: Uri
      val imageUri1:Uri by lazy {
          File(externalCacheDir,"output_image1.jpg")
          Uri.parse("content://com.example.foodrecipes.fileprvoider/my_images/Android/data/com.example.foodrecipes/cache/output_image1.jpg")
      }

     var favinfo :SendList?  = null

     /*初始化用户信息*/
     companion object {
         lateinit var UserData: UserData
         var handler_UserInfo: Handler = object : Handler() {
             override fun handleMessage(msg: Message) {
                 UserData = msg.obj as UserData
             }
         }
     }

    //    var test:getDataByRetrofit? = null
     var binding :ActivityMainBinding? = null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextCompat.checkSelfPermission(this,"")
        /*发送强制下线的广播*/
        sendBroadCast()
        lifecycle.addObserver(getUserInfo_MainActivity(intent.getStringExtra("email")))
//        toolbar.setNavigationIcon((resources.getDrawable(R.drawable.test)))
        //直接生成activity_main.xml文件的绑定的类的对象binding：其中的layoutInflater是MainActivity自动包含的一个解析器
         binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)


   /* 设置Viewpager-Bottom_menu*/
        var viewAdapter  = ViewpagerAdapter(favinfo,supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,intent.getStringExtra("email").toString())
        viewpager.adapter = viewAdapter
   /*DrawLayout-set*/
        setDrawLayoutAllWindows()
   /*底部和fragment绑定*/
        bindfragmentwithBottom()
   /*设置整个Toolbar*/
        set_Toolbar()
    /*设置整个User_info_menu的点击事件*/
        user_info_menu_click()
        /*设置用户的信息*/
        Set_UserInfo()
        /*头像更新的监听*/
        listener_post()




    }

     /*发送强制下线的广播*/
     fun sendBroadCast(){
         Intent("com.example.brodcastbestpractice.FORCE_OFFLINE")
         sendBroadcast(intent)
     }
     /*开启搜索功能*/
     fun SearchInfo(){
         startActivity(Intent(this,
             SearchActivity::class.java))
     }
//     /*初始化用户的数据*/
     fun Set_UserInfo() {
//         getUserInfo.getFavorityInfo(intent.getStringExtra("email"))
            getUserInfo.getUserInfo(intent.getStringExtra("email"), intent.getStringExtra("email"))
            getUserInfo.UserinfoState.observe(this, object : Observer<UserData?> {
                override fun onChanged(t: UserData?) {
                    when (t) {
                        null -> {
                            Toast.makeText(this@MainActivity, "正在加载用户数据", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this@MainActivity, "用户数据加载成功", Toast.LENGTH_LONG).show()
                            User_info.User = t
                            Picasso.with(User_imageView.context).load(t.photo).into(User_imageView)
                            Picasso.with(CircleImageView2.context).load(t.photo).into(CircleImageView2)
                            User_name.setText(t.name)
                            User_Name.setText(t.name)
                            email.setText(t.email)
                        }
                    }
                }

            })
}

     /*设计user_info界面的点击事件*/
     private fun user_info_menu_click(){
         //初始化头像
//         val initUri =  "content://com.example.foodrecipes.fileprvoider/my_images/Android/data/com.example.foodrecipes/cache/output_image1.jpg"
//         if (kotlin.runCatching { contentResolver.openInputStream(Uri.parse(initUri))}.isSuccess){
//             User_imageView.setImageBitmap(BitmapFactory.decodeStream( contentResolver.openInputStream(Uri.parse(initUri))))
//             CircleImageView2.setImageBitmap(BitmapFactory.decodeStream( contentResolver.openInputStream(Uri.parse(initUri))))
//         }



         //设置头像
         CircleImageView2.setOnClickListener {
             checkPremission(this)
             AlertDialog.Builder(this).
                     setTitle("Please select one below!")
//                     .setMessage("pictures of you where it came from")
                     .setSingleChoiceItems(arrayOf("Camera","Album"),-1,object :DialogInterface.OnClickListener{
                         override fun onClick(dialog: DialogInterface?, which: Int) {
                             when(which){
                                 0 -> { get_permission("camera")
                                 dialog?.dismiss()
                                 }
                                 1 -> {get_permission("album")
                                 dialog?.dismiss()
                                 }

                             }

                         }
                     })
                     .setNegativeButton("cancel",null)
                     .show()
         }
         //menu的点击事件
         user_info_navigation.setNavigationItemSelectedListener {
             when(it.itemId){
//                 get_permission("tel")
                 R.id.ContactPerson -> get_permission("contactPerson")
                 R.id.Collection ->null
                 R.id.Member -> null
                 R.id.Location -> null
                 R.id.Mail -> null
                 R.id.Task -> null
             }
             true
         }
     }
     /*显示toolbar右边的item 用menu的方式*/
     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.toobal_right,menu)
         return true
     }
     /*设置toolbar中的menu的点击事件*/
     fun set_Toolbar(){
         setSupportActionBar(toolbar)
         toolbar.setOnMenuItemClickListener(androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
             when(it.itemId){
                 R.id.search_food -> SearchInfo()
                 R.id.right_add -> show_rightmenu(it.itemId)
             }
             true
         })
    /*设计头像的点击事件*/
         User_imageView.setOnClickListener {
             drawlayout.openDrawer(GravityCompat.START)
         }
     }
    /*显示right-menu*/
     fun show_rightmenu(id:Int){
//         add_Button.setOnClickListener {
             //创建Popupmenu
             val popupMenu = PopupMenu(this,findViewById(id))
             //加载菜单资源
             popupMenu.menuInflater.inflate(R.menu.right_menu,popupMenu.menu)
             setIconEnable(popupMenu.menu,true)
             popupMenu.show()
//         }
     }
     /*利用反射加载menu之中的图标*/
     private fun setIconEnable(menu: Menu, enable: Boolean) {
         try {
             val clazz =
                 Class.forName("com.android.internal.view.menu.MenuBuilder")
             val m = clazz.getDeclaredMethod(
                 "setOptionalIconsVisible",
                 Boolean::class.javaPrimitiveType
             )
             m.isAccessible = true
             //传入参数
             m.invoke(menu, enable)
         } catch (e: java.lang.Exception) {
             e.printStackTrace()
         }
     }
     /*底部和fragment绑定*/
     private fun bindfragmentwithBottom(){
         bottomNavigationView2.setOnNavigationItemSelectedListener {
             when (it.itemId) {
                 R.id.recipesFragment -> viewpager.currentItem = 0
                 R.id.favoritesFragment -> viewpager.currentItem = 1
                 R.id.jokeFragment -> viewpager.currentItem = 2
             }
             true
         }
         viewpager.addOnPageChangeListener(
                 object : ViewPager.OnPageChangeListener {
                     override fun onPageScrolled(
                             position: Int,
                             positionOffset: Float,
                             positionOffsetPixels: Int
                     ) {}
                     override fun onPageSelected(position: Int) {
                         if (menuItem != null) {
                             //不是刚开始的时候  原来的item就会被取消
                             menuItem?.setChecked(false)
                         } else {
                             //初始化  刚进去 肯定为第一个界面 当页面开始变换 并且别的页面开始被选中 所以第一个item不被选中
                             bottomNavigationView2.getMenu().getItem(0).setChecked(false)
                         }
                         menuItem = bottomNavigationView2.getMenu().getItem(position)
                         menuItem?.setChecked(true)
                     }

                     override fun onPageScrollStateChanged(state: Int) {}
                 })
     }

     /*DrawLayout-set*/
     @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
     fun setDrawLayoutAllWindows(){
         var metrics = DisplayMetrics()
         windowManager.defaultDisplay.getRealMetrics(metrics)
         var wight = metrics.widthPixels
         var height = metrics.heightPixels
         var params = right_layout.layoutParams
         params.height = height
         params.width = wight
         right_layout.layoutParams = params
         //设置
     }
     /*获取用户权限*/
     fun get_permission( permissions:String,value:String? = null,context: Context? = null){
         /*首先检查权限高级权限，这里可以写在一起，但是我分开写了*/
         when(permissions){
             "tel" ->{
                 /*这里的使用的是Acitivity的返回栈的栈顶Activity，方便别的Activity界面调用*/
                     if (ContextCompat.checkSelfPermission(ActivityCollector.Activity[ActivityCollector.Activity.size-1], android.Manifest.permission.CALL_PHONE) !=PackageManager.PERMISSION_GRANTED){
                         ActivityCompat.requestPermissions(ActivityCollector.Activity[ActivityCollector.Activity.size-1], arrayOf(android.Manifest.permission.CALL_PHONE),1)
                     }else{
                         try {
                            context?.startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+value)))
                         }catch (e:SecurityException){
                             e.printStackTrace()
                         }
                     }
                 }
             "camera" ->{
                 outputImage = File(externalCacheDir, getData.getData1() + ".jpg")
                 Log.v("ppp1","Filename:${outputImage}")
//                 //若是已近存在
//                 if (outputImage.exists()) outputImage.delete()
//                 //没有存在就构建file
//                 outputImage.createNewFile()
                 //file对应不同的版本转换为不同的uri
                 imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                     //版本不低于7.0  这里涉及fileContext的注册标注authority
                     FileProvider.getUriForFile(this,"com.example.foodrecipes.fileprvoider",outputImage)
                 }else{
                     Uri.fromFile(outputImage)
                 }
                 //开启相机
                 startActivityForResult(Intent("android.media.action.IMAGE_CAPTURE").putExtra(MediaStore.EXTRA_OUTPUT,imageUri),1)
             }
             "album" ->{
                 startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                     this.addCategory(Intent.CATEGORY_OPENABLE)
                     this.type = "image/*"
                 },2)
             }
             "contactPerson" ->{
                 if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                     /*没有获取权限*/
                     ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS),2)
                 }else{
                     readpersonData()
                 }
             }
         }
     }
     private fun readpersonData(){
         val Operator = Operate_User_info_db.operConPerson_info
         val ArrayConPersonInfo = ArrayList<ConPersonData>()
         contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
             ?.apply {
                 while (moveToNext()){
                     val ConPersonName = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                     val ConPerosnNumber = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                     ArrayConPersonInfo.add(ConPersonData(ConPersonName,ConPerosnNumber))
                 }
                 Operator.insertConPerson(ArrayConPersonInfo)
                 close()
             }
         startActivity(Intent(this, Conperson_infoActivity::class.java))
     }

     /*对授权的结果进行回调*/
     override fun onRequestPermissionsResult(
         requestCode: Int,
         permissions: Array<out String>,
         grantResults: IntArray
     ) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         when(requestCode){
             1 ->{
                 if (grantResults.isNotEmpty() &&
                         grantResults[0] == PackageManager.PERMISSION_GRANTED){
                         startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:")))
                 }else{
                     Toast.makeText(this,"you denied the permission",Toast.LENGTH_SHORT).show()
                 }
             }
             2 ->{
                 if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                     readpersonData()
                 }else
                     Toast.makeText(this,"You defined the Permission",Toast.LENGTH_SHORT).show()
             }
             3 -> {
                 if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                 }else
                     Toast.makeText(this,"You defined the Permission",Toast.LENGTH_SHORT).show()

             }
             4->{
                 if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                 }else
                     Toast.makeText(this,"You defined the Permission",Toast.LENGTH_SHORT).show()

             }
             else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)


         }
     }
     /*相机界面关闭之后开始返回数据
     * 点击返回之后相机界面调用setResult返回相应的resultCode和imageUri
     * 前面我们使用putExtra传递一个键值对到相机界面，通过我们的媒体数据库MediaStore设置我们的文件的输出位置为imageUri
     * */
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         when(requestCode){
             1 -> {
                 if (resultCode == Activity.RESULT_OK){
                    User_imageView.setImageBitmap(BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri)))
                     CircleImageView2.setImageBitmap(BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri)))
                              Log.v("ppp","${imageUri}")

                     /*开始更新用户的头像*/
                     postImage(outputImage.absolutePath)
                 }
             }
             2 ->{
                 /*将获取到的Uri转换为Bitmap*/
                 var test:(Uri) ->Bitmap  = {
                     contentResolver.openFileDescriptor(it,"r").use {
                         BitmapFactory.decodeFileDescriptor(it?.fileDescriptor)
                     }
                 }
                 if (resultCode ==Activity.RESULT_OK && data != null){
                     data.data.let {uri ->
                         Log.v("ppp1","${uri}")
                         CircleImageView2.setImageBitmap(test(uri!!))
                         User_imageView.setImageBitmap(test(uri!!))
                         postImage(Uri_to_path.getFilePathByUri(this, uri))
                     }

                     /*开始更新头像*/

                 }
             }
         }
     }

     /*上传头像*/
     fun postImage(imagePath:String){
             var List_image: ImageList = ImageList()
             val type: Type = object : TypeToken<ImageList?>() {}.getType()

             Log.v("uri", "${imagePath}")
             List_image.array_image.add(Image().apply {
                     image = Base64Util.bitmapToBase64(BitmapFactory.decodeFile(imagePath))
                     imagename = getFilename.getFileName(imagePath+ ".jpg")
                    user_image = "http://8.130.11.202:8080/pictures/"+imagename
             })
             jsonstr = Gson().toJson(List_image,type )
             SQL_ViewModel.post_Image(URL(uri), jsonstr)
     }
     fun listener_post() {
         SQL_ViewModel.PostImageState.observe(this, object : Observer<String> {
             override fun onChanged(t: String?) {
                 when (t?.toInt()) {
                     null -> {
                         Toast.makeText(this@MainActivity, "开始上传", Toast.LENGTH_SHORT).show()
                     }
                     1 -> {
                         Toast.makeText(this@MainActivity, "上传成功", Toast.LENGTH_SHORT).show()
                         SQL_ViewModel.update_userimage(UpdateUser(User_info.User?.name,User_info.User?.email,User_info.User?.phone,User_info.User?.password,user_image))
                     }
                     -1 -> {
                         Toast.makeText(this@MainActivity, "上传失败", Toast.LENGTH_SHORT).show()

                     }
                     else -> {

                     }
                 }
             }
         })
         SQL_ViewModel.updateImageState.observe(this, object : Observer<String> {
             override fun onChanged(t: String?) {
                 when (t) {
                     null -> {

                     }
                     "插入成功" -> {
                         Toast.makeText(this@MainActivity, t, Toast.LENGTH_SHORT).show()
                     }
                     "插入失败" -> {
                         Toast.makeText(this@MainActivity, t, Toast.LENGTH_SHORT).show()
                     }
                     else -> {

                     }
                 }
             }
         })
     }



/*
*    package com.example.myapplication;

   import androidx.annotation.RequiresApi;
   import androidx.appcompat.app.AppCompatActivity;
   import androidx.appcompat.view.menu.MenuPopupHelper;

   import android.annotation.SuppressLint;
   import android.os.Build;
   import android.os.Bundle;
   import android.view.ContextMenu;
   import android.view.Menu;
   import android.view.MenuInflater;
   import android.view.MenuItem;
   import android.view.SubMenu;
   import android.view.View;
   import android.widget.Button;
   import android.widget.PopupMenu;
   import android.widget.TextView;
   import android.widget.Toast;
   import android.widget.TextView;
   import android.widget.Toast;
   import java.lang.reflect.Field;
   import java.lang.reflect.Method;

    public class MainActivity extends AppCompatActivity {
    //定义按钮的全局变量
    private Button button;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 绑定按钮的资源（利用id找到xml文件中的按钮）
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        registerForContextMenu(textView);

        add_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //创建PopupMenu
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,button);
                //加载菜单资源
                popupMenu.getMenuInflater().inflate(R.menu.menu2, popupMenu.getMenu());
                //菜单事件监听
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.bu1:
                                Toast.makeText(MainActivity.this,"复制",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.bu2:
                                Toast.makeText(MainActivity.this,"粘贴",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.bu3:
                                Toast.makeText(MainActivity.this,"清除",Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                //使用反射，强制显示菜单图标
             	try {
                	Field field = popupMenu.getClass().getDeclaredField("mm");
               	 	field.setAccessible(true);
                	MenuPopupHelper menuPopupHelper = (MenuPopupHelper) field.get(popupMenu);
               	 	menuPopupHelper.setForceShowIcon(true);
               }catch (IllegalAccessException | NoSuchFieldException e){
                	e.printStackTrace();
            }
                popupMenu.show();
            }
        });

    }
 }
*/



    override fun onDestroy() {
        binding = null
        super.onDestroy()


    }


}
