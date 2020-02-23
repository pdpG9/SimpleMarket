package com.example.lesson32.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.newmarket.dataBase.DBHelper
import com.example.newmarket.models.GroupData
import com.example.newmarket.models.StudentData

class Database private constructor(context: Context) : DBHelper(context, "data.db") {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var database: Database

        fun init(context: Context) {
            database = Database(context)
        }

        fun getDatabase() = database
    }
    //-------Get--------//

    fun getStudentByGroup(group: Int): ArrayList<StudentData> {
        val ls = ArrayList<StudentData>()
        val cursor = database().rawQuery("select * from students where students.team = ?", arrayOf(group.toString()))
        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast) {
                val id = cursor.getLong(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val age = cursor.getInt(cursor.getColumnIndex("age"))
                val team = cursor.getInt(cursor.getColumnIndex("team"))
                ls.add(StudentData(id, name, age, team.toLong()))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return ls
    }

    fun getAllGroups(): ArrayList<GroupData> {
        Log.d("TTT","into getAllGroup")
        val ls = ArrayList<GroupData>()
        val cursor = database().rawQuery("select * from groups", null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getLong(cursor.getColumnIndex("id"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                Log.d("TTT","getGR $title")
                ls.add(GroupData(id, title, getStudentByGroup(id.toInt())))
                cursor.moveToNext()

            }
        }
        cursor.close()
        return ls
    }

    //------------Add-------------//

    fun addGroup(g: GroupData) {
        val cv = ContentValues()
        cv.put("title", g.title)
        val id = database().insert("groups", null, cv)
    }

    fun addStudents(d: StudentData) {
        val cv = ContentValues()
        cv.put("name", d.name)
        cv.put("age", d.age)
        cv.put("team", d.team)
        val id = database().insert("students", null, cv)
        d.id = id
    }
    //----------delete---------------//

    fun deleteGroup(g: GroupData) {
        database().delete("groups", "id = ?", arrayOf(g.id.toString()))
    }

    fun deleteStudent(d: StudentData) {
        database().delete("students", "id = ?", arrayOf(d.id.toString()))
    }

    //-----------update---------//


    fun updateStudent(s: StudentData) {
        val cv = ContentValues()
        cv.put("id", s.id)
        cv.put("name", s.name)
        cv.put("team", s.team)
        cv.put("age", s.age)
        database().update("Students", cv, "id=?", arrayOf(s.id.toString()))
    }


}