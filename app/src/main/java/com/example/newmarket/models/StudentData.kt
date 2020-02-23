package com.example.newmarket.models

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