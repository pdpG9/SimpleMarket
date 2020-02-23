package com.example.lesson32.dialogs

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import com.example.newmarket.R
import kotlinx.android.synthetic.main.add_group_dialog.view.*


class AddGroupDialog(context: Context){
    private var listener:setGroup? = null

    fun setGroupListener(f:setGroup){
        listener = f
    }
    private var dialog = AlertDialog.Builder(context).create()

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.add_group_dialog,null,false)
        dialog.setView(view)
        view.apply {
            btCancelGr.setOnClickListener { close() }
            btOkGr.setOnClickListener {
                val title = inputTextTitle.text.toString()
                Log.d("TTT","setTitte $title")
                listener?.invoke(title)
            }
        }

    }
    fun show(){
        dialog.show()
    }
    fun close(){
        dialog.dismiss()
    }
}
typealias setGroup = (String)->Unit