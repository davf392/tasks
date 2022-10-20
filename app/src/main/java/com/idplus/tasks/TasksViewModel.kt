package com.idplus.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idplus.tasks.data.Task
import com.idplus.tasks.data.TaskDao
import kotlinx.coroutines.launch

class TasksViewModel(val dao: TaskDao) : ViewModel() {

    var newTaskName = ""

    val tasks = dao.getAll()

    fun addTask() {
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }
}