package com.example.newmarket.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newmarket.R
import com.example.newmarket.models.GroupData
import kotlinx.android.synthetic.main.item_group.view.*


class DeleteDialogAdapter(val data: ArrayList<GroupData>) :
    RecyclerView.Adapter<DeleteDialogAdapter.ViewHolder>() {
    private var listener: ItemClick<Int>? = null

    fun setOnItemClickListener(f: ItemClick<Int>?) {
        Log.d("TTT", "listener = f")
        listener = f
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                setOnItemClickListener { listener?.invoke(adapterPosition) }
            }
        }

        fun bind() {
            val d = data[adapterPosition]
            itemView.apply {
                DeleteGroupTitle.text = d.title
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflater(R.layout.item_group))


    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
}
