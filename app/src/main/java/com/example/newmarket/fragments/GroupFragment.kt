package com.example.newmarket.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newmarket.R
import com.example.newmarket.adapters.ItemClick
import com.example.newmarket.adapters.StudentAdapter
import com.example.newmarket.models.Action
import com.example.newmarket.models.StudentData
import kotlinx.android.synthetic.main.fragment_group.*

class GroupFragment :Fragment(){

    lateinit var data:ArrayList<StudentData>
    lateinit var title:String
    var swipe = 0
    private val adapter by lazy { StudentAdapter(data) }

    private var listener:((Int, Action)->Unit)? = null
    private var addListener:(()->Unit)? = null

   fun setOnAddClickListener(f:(()->Unit)?){
       addListener = f
   }
   fun setOnItemClickListener(f:((Int,Action)->Unit)?){
       listener = f
   }

    private var listenerEdit:ItemClick<Pair<Int,Int>>? = null
    private var listenerDelete:ItemClick<Pair<Int,Int>>? = null
    fun setOnEditClickListener(f:ItemClick<Pair<Int,Int>>?){
        listenerEdit = f
    }
    fun setOnDeleteClickListener(f:ItemClick<Pair<Int,Int>>?){
        listenerDelete = f
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter
        val bundle = arguments

        addStudents.setOnClickListener {
            addListener?.invoke()
        }

        adapter.setOnEditClickListener {
            listenerEdit?.invoke(Pair(it,bundle!!.getInt("POSITION")))
        }
        adapter.setOnDeleteClickListener {
            listenerDelete?.invoke(Pair(it,bundle!!.getInt("POSITION")))
        }

        val helper = ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,swipe){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val action = if(direction == ItemTouchHelper.RIGHT)Action.RIGHT else Action.LEFT
                listener?.invoke(viewHolder.adapterPosition,action)
            }
        })
        helper.attachToRecyclerView(list)
    }
    fun notifyStudentInsert(position:Int){
        adapter.notifyItemInserted(position)
    }
    fun notifyStudentRemove(position:Int){
        adapter.notifyItemRemoved(position)
    }
    fun notifyStudentUpdate(position:Int){
        adapter.notifyItemChanged(position)
    }
}