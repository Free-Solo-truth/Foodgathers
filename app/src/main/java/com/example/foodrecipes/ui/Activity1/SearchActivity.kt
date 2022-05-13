package com.example.foodrecipes.ui.Activity1

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import com.example.foodrecipes.R
import com.example.foodrecipes.model.Save_temp
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val adapter1 = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Save_temp.favfoodTitle)
        val adapter2 = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Save_temp.netfoodTitle)
        favorityfoodresult.adapter =adapter1
        Netfoodresult.adapter = adapter2
        favorityfoodresult.setTextFilterEnabled(true);
        Netfoodresult.setTextFilterEnabled(true);
        changeStatusBarTextColor(true)
        back.setOnClickListener {
            finish()
        }
        SearchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    adapter1.filter.filter(newText)
                    adapter2.filter.filter(newText)
                }else{
                    favorityfoodresult.clearTextFilter()
                    Netfoodresult.clearTextFilter()
                }
                return false
            }

            }
        )
        favorityfoodresult.setOnItemClickListener { parent, view, position, id ->
            Log.v("pppq",Save_temp.title_position.get(Save_temp.favfoodTitle.get(position))!!)
            startActivity(Intent(this,show_foodActivity::class.java).putExtra("position",Save_temp.title_position.get(Save_temp.favfoodTitle.get(position))))
        }
        Netfoodresult.setOnItemClickListener { parent, view, position, id ->
            Log.v("pppq",Save_temp.title_position.get(Save_temp.favfoodTitle.get(position))!!)
            startActivity(Intent(this,show_foodActivity::class.java).putExtra("position",Save_temp.title_position.get(Save_temp.favfoodTitle.get(position))))
        }
    }
}


