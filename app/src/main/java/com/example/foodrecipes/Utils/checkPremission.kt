package com.example.foodrecipes.Utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.foodrecipes.model.setDynamic

class checkPremission(val activity: Activity){

    var permissions = arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    var permissionsHigh = arrayOf<String>(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
    var mPermissionList: MutableList<String> = ArrayList()
    private val PERMISSION_REQUEST = 3
    private val ANDROID11_PERMISSION_REQUEST =4
    init {
        checkAndroid11FilePermission(activity)
    }
    // 检查低版本权限
     fun checkPermission() {
        mPermissionList.clear()
        //判断哪些权限未授予
        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i])
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {
            /*权限全部授予，可以发布动态*/

        } else { //请求权限方法
            val permissions = mPermissionList.toTypedArray() //将List转为数组
            ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST)
        }
    }
    /**
     * 响应授权
     * 这里不管用户是否拒绝，都进入首页，不再重复申请权限
     */


    /** 检查Android 11或更高版本的文件权限 */
     fun checkAndroid11FilePermission(activity: Activity) {
        // Android 11 (Api 30)或更高版本的写文件权限需要特殊申请，需要动态申请管理所有文件的权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Log.i("ABCD", "此手机是Android 11或更高的版本，且已获得访问所有文件权限，可发布动态")
            } else {
                Log.i("ABCD", "此手机是Android 11或更高的版本，且没有访问所有文件权限")
                showDialog(activity, """本应用需要获取"访问所有文件"权限，请给予此权限，否则无法使用本应用""") {
                    ActivityCompat.requestPermissions(activity,permissionsHigh,ANDROID11_PERMISSION_REQUEST)
                }
            }
        } else {
            Log.i("ABCD", "此手机版本小于Android 11，版本为：API ${Build.VERSION.SDK_INT}，不需要申请文件管理权限")
            checkPermission()
        }
    }

     fun showDialog(activity: Activity, message: String, okClick: () -> Unit) {
        AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定") { _, _ -> okClick() }
                .setCancelable(false)
                .show()
    }

}