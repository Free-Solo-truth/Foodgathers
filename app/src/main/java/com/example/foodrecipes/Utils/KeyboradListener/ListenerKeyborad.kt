package com.example.foodrecipes.Utils.KeyboradListener

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


object ListenerKeyborad {
    /**
     * 打开软键盘
     */
    fun openKeybord(mEditText: EditText, mContext: Context) {
        mEditText.isFocusable = true
        mEditText.isFocusableInTouchMode = true
        mEditText.requestFocus()
        val imm: InputMethodManager = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * 关闭软键盘
     */
    fun closeKeybord(mEditText: EditText, mContext: Context) {
        val imm: InputMethodManager = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    /**
     * 关闭软键盘
     */
    fun hideInput(activity: Activity) {
        if (activity.currentFocus != null) {
            val inputManager: InputMethodManager =
                activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    /**
     * 判断当前软键盘是否打开
     */
    fun isSoftInputShow(activity: Activity): Boolean {

        // 虚拟键盘隐藏 判断view是否为空
        val view: View? = activity.window.peekDecorView()
        if (view != null) {
            // 隐藏虚拟键盘
            val inputmanger: InputMethodManager = activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //       inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);
            return inputmanger.isActive() && activity.window.currentFocus != null
        }
        return false
    }
//    private fun setListenerToRootView() {
//        val rootView: View =
//            getWindow().getDecorView().findViewById(R.id.content)
//        rootView.viewTreeObserver.addOnGlobalLayoutListener {
//            val mKeyboardUp = isKeyboardShown(rootView)
//            if (mKeyboardUp) {
//                //键盘弹出
//                //Toast.makeText(getApplicationContext(), "键盘弹出", Toast.LENGTH_SHORT).show();
//            } else {
//                //键盘收起
//                //Toast.makeText(getApplicationContext(), "键盘收起", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//
//    private fun isKeyboardShown(rootView: View): Boolean {
//        val softKeyboardHeight = 100
//        val r = Rect()
//        rootView.getWindowVisibleDisplayFrame(r)
//        val dm = rootView.resources.displayMetrics
//        val heightDiff: Int = rootView.bottom - r.bottom
//        return heightDiff > softKeyboardHeight * dm.density
//    }
}