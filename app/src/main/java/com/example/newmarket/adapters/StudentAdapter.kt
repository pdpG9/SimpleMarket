package com.example.newmarket.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.newmarket.R
import com.example.newmarket.models.StudentData
import kotlinx.android.synthetic.main.item_student.view.*

class StudentAdapter(val data: ArrayList<StudentData>) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    private var listener: ItemClick<Int>? = null

    fun setOnItemClickListener(f: ItemClick<Int>?) {
        listener = f
    }

    private var listenerEdit: ItemClick<Int>? = null
    private var listenerDelete: ItemClick<Int>? = null
    fun setOnEditClickListener(f: ItemClick<Int>?) {
        listenerEdit = f
    }

    fun setOnDeleteClickListener(f: ItemClick<Int>?) {
        listenerDelete = f
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            setOnItemClickListener { listener?.invoke(adapterPosition) }
            itemView.btMore.setOnClickListener {
                val menu = PopupMenu(view.context, it)
                menu.inflate(R.menu.bt_more)
                menu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.delete -> listenerDelete?.invoke(adapterPosition)
                        R.id.edit -> listenerEdit?.invoke(adapterPosition)
                    }
                    true
                }
                menu.show()
            }
        }

        fun bind() {
            val d = data[adapterPosition]
            itemView.apply {
                textName.text = d.name
                textAge.text = d.age.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflater(R.layout.item_student))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

}

fun ViewGroup.inflater(@LayoutRes resId: Int) =
    LayoutInflater.from(context).inflate(resId, this, false)

typealias ItemClick<T> = (T) -> Unit