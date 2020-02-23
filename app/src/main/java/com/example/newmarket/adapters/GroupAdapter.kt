package com.example.newmarket.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.newmarket.fragments.GroupFragment

class GroupAdapter(fm: FragmentManager, val data: ArrayList<GroupFragment>) :
    FragmentPagerAdapter(fm) {
    private var listenerEdit: ItemClick<Pair<Int, Int>>? = null
    private var listenerDelete: ItemClick<Pair<Int, Int>>? = null
    fun setOnEditClickListener(f: ItemClick<Pair<Int, Int>>?) {
        listenerEdit = f
    }

    fun setOnDeleteClickListener(f: ItemClick<Pair<Int, Int>>?) {
        listenerDelete = f
    }

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("POSITION", position)
        data[position].arguments = bundle
        data[position].setOnDeleteClickListener(listenerDelete)
        data[position].setOnEditClickListener(listenerEdit)
        return data[position]
    }

    override fun getPageTitle(position: Int) = data[position].title

    override fun getCount() = data.size

}