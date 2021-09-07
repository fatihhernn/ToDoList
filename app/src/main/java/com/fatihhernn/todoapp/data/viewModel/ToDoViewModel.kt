package com.fatihhernn.todoapp.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fatihhernn.todoapp.data.ToDoDatabase
import com.fatihhernn.todoapp.data.models.ToDoData
import com.fatihhernn.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ToDoViewModel(application: Application):AndroidViewModel(application) {

    private val toDoDao= ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository = ToDoRepository(toDoDao)

    private val getAllData:LiveData<List<ToDoData>> = repository.getAllData

    fun insertData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

}