package com.example.lesson32.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.newmarket.R
import kotlinx.android.synthetic.main.edit_dialog.view.*

class EditDialog(context: Context) {
    private var listener: setDialog? = null
    fun setDialogListener(f: setDialog) {
        listener = f
    }

    private var dialog = AlertDialog.Builder(context).create()

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.edit_dialog, null, false)
        dialog.setView(view)
        view.apply {
            btOk.setOnClickListener {
                val name = inputTextName.text.toString()
                val age = inputTextAge.text.toString().toInt()

                listener?.invoke(name, age)
                close()
            }
            btCancel.setOnClickListener {
                close()
            }

        }
    }

    fun show() {
        dialog.show()
    }

    fun close() {
        dialog.dismiss()
    }
//    fun setName(name:String){
//        dialog.inputTextName.setText(name)
//    }
//    fun setAge(age:Int){
//        dialog.inputTextAge.setText(age.toString())
//    }
}

typealias setDialog = (String, Int) -> Unit