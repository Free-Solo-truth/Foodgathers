package com.example.foodrecipes.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentFavoritesBinding
import com.example.foodrecipes.model.OneFavorityMessage
import com.example.foodrecipes.model.SendList
import com.example.foodrecipes.model.Save_temp
import com.example.foodrecipes.ui.adapter.FavorityAdapter
import com.example.foodrecipes.viewmodel.FavorityFragmentViewModel
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.random.Random

class favoritesFragment(var email:String):Fragment() {
    var test = true
    lateinit var timer: Timer
    var popupView:View? = null
    var popupWindow:PopupWindow? = null
    private val getUserInfo = FavorityFragmentViewModel()
    lateinit var binding: FragmentFavoritesBinding
    lateinit var RandFood:MutableList<String>
    public interface listener{
        public fun  sendValue( value:List<OneFavorityMessage>);
    }
    private  var changeData:listener? = null
    private var favFoodlist:SendList? = null
    private lateinit var Adapter:FavorityAdapter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.favorityRecycleView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        }

//        binding.favorityRecycleView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
//          override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//              super.onScrollStateChanged(recyclerView, newState)
//              (binding.favorityRecycleView.layoutManager as StaggeredGridLayoutManager).invalidateSpanAssignments()
//          }
//      })


        popupView = layoutInflater.inflate(R.layout.popupwindowlayout, null)
        popupWindow = PopupWindow().apply {
            height = LinearLayout.LayoutParams.WRAP_CONTENT
            width = LinearLayout.LayoutParams.WRAP_CONTENT
            contentView = popupView
            isFocusable = true
            elevation= 300f
            isTouchable = true
            isOutsideTouchable = true
            animationStyle = R.style.anim_popupWindow
        }


        Adapter = FavorityAdapter(favFoodlist)
        binding.favorityRecycleView.adapter =Adapter

        binding.swipRefreshView.setBackgroundResource(android.R.color.white)
        binding.swipRefreshView.setColorSchemeResources(R.color.black, R.color.teal_200, R.color.red)
        getUserInfo.getFavorityInfo(email)
        getUserInfo.FavorityInfoState.observe(viewLifecycleOwner, object : Observer<SendList?> {
            override fun onChanged(t: SendList?) {
                when (t) {
                    null -> {
                        Log.v("MainActivity:favority", "??????")
                    }
                    else -> {
                        binding.swipRefreshView.isRefreshing = false
                        changeData?.sendValue(t.favMeg)
                        /*????????????????????????????????????notifyDatasetChange()
                        * ??????recycleView?????????????????????????????????????????????????????????????????????????????????
                        * */
                        Adapter.notifyAdapter(t)
                        Log.v("MainActivity:favority", t.favMeg.size.toString())
                        Save_temp.favfood = t.favMeg
                    }
                }
            }

        })
        /*??????RandFood???????????????*/
        getRandFood()
        /*??????RecycleView???????????????*/
        binding.swipRefreshView.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                Log.v("result5", "start")
                getUserInfo.getFavorityInfo(email)
                Log.v("result5", "??????")
            }
        })
        return binding.root
    }


    fun getRandFood() {
        var result = 0

        var handler = object :Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when(msg.what){
                    1->{
                        result = msg.data.getInt("ha")
                        binding.t1.text = Save_temp.favfood.get(result).favoritytitle

                    }
                    3->{

                    }
                    else ->{

                    }
                }
            }

        }
        binding.materialCardView.setOnClickListener {
            if(test){
                timer = Timer()
                binding.t2.text = ""
                test = false
                timer.schedule(object :TimerTask(){
                    override fun run() {

                        var i = Random.nextInt(Save_temp.favfood.size)
                        handler.sendMessage(Message.obtain().apply {
                            what = 1
                            data = Bundle().apply {
                                putInt("ha",i)
                            }
                        })
                    }
                }, 0, 100);
                test = false
            }else
            {
                timer.cancel()
                binding.t2.text = "Click here!"
                /*handler?????? ??? ????????????msg.callback??????????????????callback?????????*/
                handler.postDelayed(Runnable{
                    Picasso.with( popupView?.findViewById<ImageView>(R.id.randFood_imageview)?.context).load(Save_temp.favfood.get(result).favorityimage).into(popupView?.findViewById<ImageView>(R.id.randFood_imageview))
                    popupView?.findViewById<TextView>(R.id.Title)?.setText(Save_temp.favfood.get(result).favoritytitle)
                    popupView?.findViewById<TextView>(R.id.subTilte)?.setText( "\t"+Save_temp.favfood.get(result).favoritysubit.replace(Regex("\\<b>|\\</b>|\\<a>|\\</a>"),""))

                    popupWindow?.showAtLocation(popupView,Gravity.CENTER,0,0)
                },2000)
                test = true
            }
        }
    }


////                while (test){
//                    var i = Random.nextInt(isExistFavObinput.tt2.size-1)
//                    binding.t2.text = isExistFavObinput.tt2.get(i).favoritytitle
//                    it.setOnClickListener {
//                        test = false
//                    }
//                }


//            startActivity(Intent(activity, RandFoodActivity::class.java))


//        {
//            getUserInfo.getFavorityInfo(email)
//            Log.v("reuslt5","????????????")
//        }
//        return  binding.root
//    }

//    //    /*??????ChangeFavorityData????????????*/
//    fun ListenerChangeData(parent: ViewGroup){
//        ChangeInfo.ChangeFavorityState.observe(lifecycleOwner, object :Observer<String>{
//            override fun onChanged(t: String?) {
//                when(t){
//                    "" ->{
//
//                    }
//                    "????????????" ->{
//                        Toast.makeText(parent.context,t,Toast.LENGTH_SHORT).show()
//                    }
//                    "????????????" ->{
//                        Toast.makeText(parent.context,t,Toast.LENGTH_SHORT).show()
//                    }
//                    "????????????" ->{
//                        favorityFragment.binding.favorityRecycleView.adapter.notifyItemRemoved()
//                        Toast.makeText(parent.context,t,Toast.LENGTH_SHORT).show()
//                    }
//                    "????????????" ->{
//                        Toast.makeText(parent.context,t,Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//
//        })
//    }

        /*???????????????????????????????????????????????????????????????????????????
    * ?????????????????????????????????????????????????????????????????????
    * ???????????????????????????????????????????????????????????????
    * ????????????????????????????????????????????????????????????
    * */
        fun getMutable(): MutableLiveData<List<OneFavorityMessage>?> {
            var t3: MutableLiveData<List<OneFavorityMessage>?> = MutableLiveData(null)
            t3.value = Save_temp.favfood
            return t3
        }


    }




