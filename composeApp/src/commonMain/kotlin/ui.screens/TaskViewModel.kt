package ui.screens

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Database
import data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import utils.Action

class TaskViewModel(
    private val database: Database
) : ScreenModel {

    fun setAction(action: Action) {
        when (action) {
            is Action.Add -> {
                addTask(action.task)
            }

            is Action.Update -> {
                updateTask(action.task)
            }

            else -> {}
        }
    }

    private fun addTask(task: Task) {
        screenModelScope.launch(Dispatchers.IO) {
            database.addTask(task)
        }
    }

    private fun updateTask(task: Task) {
        screenModelScope.launch(Dispatchers.IO) {
            database.updateTask(task)
        }
    }
}