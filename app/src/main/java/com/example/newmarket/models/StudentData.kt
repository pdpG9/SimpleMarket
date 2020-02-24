package com.example.newmarket.models

import android.content.Context
import android.widget.Toast
import com.example.lesson32.Database.Database

data class StudentData(
    var id:Long,
    val name:String,
    val age:Int,
    val team:Long
)

data class GroupData(
    var id:Long,
    val title:String,
    val students:ArrayList<StudentData>
)

enum class Action{
    LEFT,RIGHT
}


fun GroupData.add() = Database.getDatabase().addGroup(this)
fun GroupData.delete() = Database.getDatabase().deleteGroup(this)
fun StudentData.add() = Database.getDatabase().addStudents(this)
fun StudentData.delete()= Database.getDatabase().deleteStudent(this)
fun StudentData.update()= Database.getDatabase().updateStudent(this)

fun String.show(context: Context) = Toast.makeText(context,this, Toast.LENGTH_SHORT).show()