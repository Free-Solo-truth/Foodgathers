package com.example.foodrecipes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.databinding.FragmentRecipesBinding
import com.example.foodrecipes.model.*
import com.example.foodrecipes.Utils.unfoldAnimatior
import com.example.foodrecipes.Utils.closeAnimator

import com.example.foodrecipes.ui.adapter.RecipesAdapter
import com.example.foodrecipes.ui.adapter.chooseAdapter
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import com.example.foodrecipes.viewmodel.getFoodViewmodel
import com.example.foodrecipes.viewmodel.testViewModel


class RecipesFragment(var FavInfo:SendList?,var email:String) : Fragment() {
    var ChangeInfo = getData_fromSQLViewmodel()
    private val getUserInfo = testViewModel()
    private var typeViewAdapter:chooseAdapter? = null
    private var dietViewAdapter:chooseAdapter? = null
    private var cuisineViewAdapter:chooseAdapter? = null
    private val FoodViewmodel: getFoodViewmodel by viewModels()
    var choosetype:String = ""
    var choosediet:String = ""
    var choosecuisine:String =""
    var type:List<String> = mutableListOf(
        "soup",
        "main course",
        "side dish",
        "dessert",
        "appetizer",
        "salad",
        "bread",
        "breakfast",
        "main course",
        "beverage",
        "sauce",
        "marinade",
        "fingerfood",
        "snack",
        "drink",)
    var diet:List<String> = mutableListOf(
        "Vegan",
        "Diet Definitions",
        "Gluten Free",
        "Ketogenic",
        "Vegetarian",
        "Lacto-Vegetarian",
        "Ovo-Vegetarian",
        "Pescetarian",
        "Paleo",
        "Primal",
        "Low FODMAP",
        "Whole30"
    )
    var cuisines:List<String> = mutableListOf(
            "African",
            "American",
            "British",
            "Cajun",
            "Caribbean",
            "Chinese",
            "Eastern European",
            "European",
            "French",
            "German",
            "Greek",
            "Indian",
            "Irish",
            "Italian",
            "Japanese",
            "Jewish",
            "Korean",
            "Latin American",
            "Mediterranean",
            "Mexican",
            "Middle Eastern",
            "Nordic",
            "Southern",
            "Spanish",
            "Thai",
            "Vietnamese",
    )

    var favList:List<OneFavorityMessage> = ArrayList<OneFavorityMessage>()
    var i:String?=null
    set(value) {
        field = value
    }
    private var _binding :FragmentRecipesBinding? = null
    //当访问binding 的get()方法的时候直接访问_binding变量
    val binding get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        FoodViewmodel.getFoodNews("soup","vegan","")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        生成一种binding类，将xml文件进行解析，实现一一的对应关系
        _binding = FragmentRecipesBinding.inflate(inflater,container,false)
          initRecycleView()

        /*
        接口回调的使用
        */
        binding.recycleView1.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        binding.recycleView2.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        binding.recycleView3.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        typeViewAdapter = chooseAdapter(type)
        dietViewAdapter = chooseAdapter(diet)
        cuisineViewAdapter = chooseAdapter(cuisines)
        binding.recycleView1.adapter = typeViewAdapter
        binding.recycleView2.adapter = dietViewAdapter
        binding.recycleView3.adapter = cuisineViewAdapter
        typeViewAdapter!!.onItemListener = object : chooseAdapter.OnItemListener1 {
            override fun onClick(v: View?, pos: Int) {
                typeViewAdapter!!.setDefSelect(pos)
                choosetype = type.get(pos)
                FoodViewmodel.getFoodNews(choosetype,choosediet,choosecuisine)
                Log.v("ppp1","${choosetype}")
            }
        }
        dietViewAdapter!!.onItemListener =object : chooseAdapter.OnItemListener1 {
            override fun onClick(v: View?, pos: Int) {
                dietViewAdapter!!.setDefSelect(pos)
                choosediet = diet.get(pos)
                FoodViewmodel.getFoodNews(choosetype,choosediet,choosecuisine)
                Log.v("ppp","litenfei")
            }
        }
        cuisineViewAdapter!!.onItemListener =object : chooseAdapter.OnItemListener1 {
            override fun onClick(v: View?, pos: Int) {
                cuisineViewAdapter!!.setDefSelect(pos)
                choosecuisine = cuisines.get(pos)
                FoodViewmodel.getFoodNews(choosetype,choosediet,choosecuisine)
                Log.v("ppp","litenfei")
            }
        }
            binding.menuItem.setOnClickListener {
            Log.v("this","litenfei")
             unfoldAnimatior.newInstance(context,binding.recycleView1,binding.menuItem,300).toggle()
        }
        binding.menuItem1.setOnClickListener {
            Log.v("this","litenfei")
            unfoldAnimatior.newInstance(context,binding.recycleView2,binding.menuItem1,300).toggle()
        }
        binding.menuItem2.setOnClickListener {
            Log.v("this","litenfei")
            unfoldAnimatior.newInstance(context,binding.recycleView3,binding.menuItem2,300).toggle()
        }
        binding.menu.setOnClickListener{
            closeAnimator.newInstance(context,binding.recycleView1,binding.menu,300).toggle()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initRecycleView(){
        //binding.recycleView.showShimmer()  将我们的RecycleVIew设置成为线性布局
        binding.recycleView.layoutManager = LinearLayoutManager(context)
//        binding.recycleView.adapter = RecipesAdapter(NewsModel(),FavInfo)
        setDataUI()

        while (i!=null){
            Log.v("result2","${i}")
        }
    }
    fun setDataUI(){
        /*注意这里的LiveData的使用  LiveData是存在生命周期感知的  可以实时的监听数据的改变*/
        FoodViewmodel.foodState.observe(viewLifecycleOwner,object : Observer<NewsState> {
            override fun onChanged(t: NewsState?) {
                when(val result = FoodViewmodel.foodState.value) {
                    is NewsState.Success -> {
                        Log.v("ppp1","接收到数据")
                        //开始适配初始页面
                        binding.recycleView.adapter = RecipesAdapter(result.Response,email,viewLifecycleOwner,favList)
                        binding.progressBar.visibility = GONE
                        result.Response.results.forEach{
                            Log.v("ppp","${it.title}")
                        }

                        if(result.Response.results.size == 0){
                            Toast.makeText(requireContext(),"No food",Toast.LENGTH_LONG).show()
                        }
                        Save_temp.foodinfo = result.Response.results
                        getUserInfo.getChangeView()
                        setFavority(result.Response)
                    }
                    is NewsState.Failure -> {
                        Log.v("ppp","${result.Error}")
                        Toast.makeText(requireContext(),"Error choose",Toast.LENGTH_LONG).show()
                    }
                    is NewsState.Loading -> {

                    }
                }
            }
        })



    }

    fun setFavority(response:NewsModel){
        /*这样调用别的类LifeData，虽然不需要参数，但是需要不停的调用，才能够监听到别的类的lifeData的变化*/
        getUserInfo.rest1?.observe(viewLifecycleOwner,object : Observer<List<OneFavorityMessage>?> {
            override fun onChanged(t: List<OneFavorityMessage>?) {
                when (t){
                    null ->{
                        Log.v("Recpres:Fragment","null成功")
                    }
                    else ->{
                        favList = t
                        binding.recycleView.adapter = RecipesAdapter(response,email,viewLifecycleOwner,favList)
                        (binding.recycleView.adapter as RecipesAdapter).notifyDataSetChanged()
                        Log.v("Recipes:Fragment","${t}")
                    }
                }
            }

        })
    }
    fun get():String{
        return "litenfei"
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}