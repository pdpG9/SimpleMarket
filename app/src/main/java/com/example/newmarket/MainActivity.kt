package com.example.newmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.lesson32.Database.Database
import com.example.lesson32.dialogs.AddGroupDialog
import com.example.lesson32.dialogs.DeleteGroupDialog
import com.example.lesson32.dialogs.EditDialog
import com.example.newmarket.adapters.GroupAdapter
import com.example.newmarket.fragments.GroupFragment
import com.example.newmarket.models.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val data = ArrayList<GroupData>()
    private val fragments = ArrayList<GroupFragment>()
    private val adapter by lazy { GroupAdapter(supportFragmentManager, fragments) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout.setupWithViewPager(pager)
        loadDate()

        data.addAll(Database.getDatabase().getAllGroups())

        pager.adapter = adapter
        adapter.setOnEditClickListener {
            val itemIndex = it.first
            val page = it.second
            val dialog = EditDialog(this)
            val s = data[page].students[itemIndex]
//            dialog.setName("${s.name}+?")
//            dialog.setAge(s.age)

            dialog.setDialogListener { name, age ->
                val student = StudentData(0,name,age,data[page].id)
                student.update()
                data[page].students[itemIndex] = student
                val insertPosition = itemIndex
                fragments[page].notifyStudentUpdate(insertPosition)
                "Student updating".show(this)
                dialog.close()
            }
            dialog.show()
        }
        adapter.setOnDeleteClickListener { pair ->
            val itemIndex = pair.first
            val page = pair.second
            data[page].students[itemIndex].delete()
            data[page].students.removeAt(itemIndex)
        }
    }
    private fun loadDate(){
        for(i in 1..10){
            val group = GroupData(0,"Group $i", ArrayList())
            data.add(group)
            for (j in 1..20){
                group.students.add(StudentData(0,"Student name ${(i-1)*20+j}",20,group.id))

            }

        }

        for (page in 0 until data.size){
            val fragment = GroupFragment()
            fragment.data = data[page].students
            fragment.title = data[page].title
            fragment.swipe = when(page){
                0->ItemTouchHelper.RIGHT
                data.size - 1->ItemTouchHelper.LEFT
                else -> ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            }
            fragment.setOnAddClickListener {
                val dialog = EditDialog(this)
                dialog.setDialogListener { name, age ->

                    val student = StudentData(0,name,age,data[page].id)
                    student.add()
                    data[page].students.add(student)
                    val insertPosition = data[page].students.size-1
                    fragments[page].notifyStudentInsert(insertPosition)
                    "Add Student".show(this)
                    dialog.close()
                }
                dialog.show()
            }
            fragment.setOnItemClickListener{position,action->
                when(action){
                    Action.LEFT->{
                        val student = data[page].students.removeAt(position)
                        fragments[page].notifyStudentRemove(position)
                        data[page-1].students.add(student)
                        val insertPosition = data[page-1].students.size-1
                        fragments[page-1].notifyStudentInsert(insertPosition)
                    }
                    Action.RIGHT->{
                        val student = data[page].students.removeAt(position)
                        fragments[page].notifyStudentRemove(position)
                        data[page+1].students.add(student)
                        val insertPosition = data[page+1].students.size-1
                        fragments[page+1].notifyStudentInsert(insertPosition)
                    }

                }
            }
            fragments.add(fragment)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.AddGroup) {
            val dialog = AddGroupDialog(this)
            dialog.setGroupListener {
                val group = GroupData(0,it, ArrayList())
                group.add()
                data.add(group)
                val page = data.size-1
                loadFragment(page)
                adapter.notifyDataSetChanged()
                "Add Group".show(this)
                dialog.close()
            }
            dialog.show()
        }
        if(item?.itemId == R.id.DeleteGroup){
            val dialog = DeleteGroupDialog(this)
            dialog.show()
            adapter.notifyDataSetChanged()
        }
        return true
    }

    fun loadFragmentTodata(data:ArrayList<GroupData>){
        for (page in 0 until data.size) {
            val fragment = GroupFragment()
            fragment.data = data[page].students
            fragment.title = data[page].title
            fragment.swipe = when (page) {
                0 -> ItemTouchHelper.RIGHT
                data.size - 1 -> ItemTouchHelper.LEFT
                else -> ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            }
            fragment.setOnAddClickListener {
                val dialog = EditDialog(this)
                dialog.setDialogListener { name, age ->

                    val student = StudentData(0,name,age,data[page].id)
                    student.add()
                    data[page].students.add(student)
                    val insertPosition = data[page].students.size-1
                    fragments[page].notifyStudentInsert(insertPosition)
                    "Add Student".show(this)
                    dialog.close()
                }
                dialog.show()
            }
            fragment.setOnItemClickListener { position, action ->
                when (action) {
                    Action.LEFT -> {
                        val student = data[page].students.removeAt(position)
                        fragments[page].notifyStudentRemove(position)
                        data[page - 1].students.add(student)
                        val insertPosition = data[page - 1].students.size - 1
                        fragments[page - 1].notifyStudentInsert(insertPosition)
                    }
                    Action.RIGHT -> {
                        val student = data[page].students.removeAt(position)
                        fragments[page].notifyStudentRemove(position)
                        data[page + 1].students.add(student)
                        val insertPosition = data[page + 1].students.size - 1
                        fragments[page + 1].notifyStudentInsert(insertPosition)
                    }
                }
            }
            fragments.add(fragment)

        }}
    fun loadFragment(page:Int){
        val fragment = GroupFragment()
        fragment.data = data[page].students
        fragment.title = data[page].title
        fragment.swipe = when(page){
            0->ItemTouchHelper.RIGHT
            data.size - 1->ItemTouchHelper.LEFT
            else->ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        }
        fragment.setOnAddClickListener {
            val dialog = EditDialog(this)
            dialog.setDialogListener { name, age ->

                val student = StudentData(0,name,age,data[page].id)
                student.add()
                data[page].students.add(student)
                val insertPosition = data[page].students.size-1
                fragments[page].notifyStudentInsert(insertPosition)
                "Add Student".show(this)
                dialog.close()
            }
            dialog.show()
        }
        fragment.setOnItemClickListener{position,action->
            when(action){
                Action.LEFT->{
                    val student = data[page].students.removeAt(position)
                    fragments[page].notifyStudentRemove(position)
                    data[page-1].students.add(student)
                    val insertPosition = data[page-1].students.size-1
                    fragments[page-1].notifyStudentInsert(insertPosition)
                }
                Action.RIGHT->{
                    val student = data[page].students.removeAt(position)
                    fragments[page].notifyStudentRemove(position)
                    data[page+1].students.add(student)
                    val insertPosition = data[page+1].students.size-1
                    fragments[page+1].notifyStudentInsert(insertPosition)
                }

            }
        }
        fragments.add(fragment)
    }
}
