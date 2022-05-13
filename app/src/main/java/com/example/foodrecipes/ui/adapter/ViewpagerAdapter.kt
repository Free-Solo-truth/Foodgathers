package com.example.foodrecipes.ui.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.foodrecipes.model.SendList
import com.example.foodrecipes.ui.fragment.RecipesFragment
import com.example.foodrecipes.ui.fragment.favoritesFragment
import com.example.foodrecipes.ui.fragment.jokeFragment

class ViewpagerAdapter(val FavInfo:SendList?,
                       manager: FragmentManager,
                       it:Int,
                       email:String): FragmentPagerAdapter(manager,it) {
    var fragmentlist: Array<Fragment> = arrayOf(RecipesFragment(FavInfo,email),favoritesFragment(email),jokeFragment())

    override fun getItem(position: Int): Fragment {
        Log.v("viewpaher:","${position}")
        return fragmentlist.get(position)
    }

    override fun getCount(): Int {
        return fragmentlist.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}