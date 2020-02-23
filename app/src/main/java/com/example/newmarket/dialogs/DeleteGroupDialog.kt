package com.example.lesson32.dialogs

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newmarket.R
import com.example.newmarket.adapters.DeleteDialogAdapter
import kotlinx.android.synthetic.main.delete_dialog.view.*

class DeleteGroupDialog(context: Context){

private var dialog = AlertDialog.Builder(context).create()

init {
    val view = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null, false)
    val data = Database.getDatabase().getAllGroups()
    val adapter = DeleteDialogAdapter(data)
    dialog.setView(view)
    view.apply {
        listGrDelete.layoutManager = LinearLayoutManager(context)
        listGrDelete.adapter = adapter
        adapter.setOnItemClickListener {
            Log.d("TTT", "dialog1")
            val dialog1 = ConfigurationDialog(context)
            dialog1.setTitle("Delete")
            dialog1.setText("Siz ${data[it].title} guruhini o`chirmoqchimisiz?")
            dialog1.setClickListener {
                data[it].delete()
                data.removeAt(it)
                adapter.notifyItemRemoved(it)
            }
        }
    CanceBt.setOnClickListener {
        close()
    }
}

}
fun show(){
    dialog.show()
}
fun close(){
    dialog.dismiss()
}}