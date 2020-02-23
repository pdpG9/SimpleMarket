package com.example.lesson32.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.newmarket.R
import kotlinx.android.synthetic.main.item_config.*
import kotlinx.android.synthetic.main.item_config.view.*

class ConfigurationDialog(context: Context) {

    private var dialog = AlertDialog.Builder(context).create()
    private lateinit var view: View
    private var listener:(()->Unit)? = null

    fun setClickListener(f:(()->Unit)?){
        listener = f
    }

    init {
        view = LayoutInflater.from(context).inflate(R.layout.item_config, null, false)
        dialog.setView(view)

        dialog.dialogCancel.setOnClickListener {
            close()
        }
        dialog.dialogOk.setOnClickListener {
             listener?.invoke()
        }
    }

    fun show() {
        dialog.show()
    }

    fun close() {
        dialog.dismiss()
    }

    fun setTitle(title: String) {
        view.textConfigTitle.text = title
    }
    fun setText(text: String) {
        view.textConfigText.text = text
    }
}
