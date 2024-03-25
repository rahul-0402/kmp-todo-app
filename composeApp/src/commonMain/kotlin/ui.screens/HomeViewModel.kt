package ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Database
import data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import utils.Action
import utils.RequestState

typealias MutableTasks = MutableState<RequestState<List<Task>>>
typealias Tasks = MutableState<RequestState<List<Task>>>

class HomeViewModel(private val database: Database) : ScreenModel {
    private var _activeTasks: MutableTasks = mutableStateOf(RequestState.Idle)
    val activeTasks: Tasks = _activeTasks

    private var _completedTasks: MutableTasks = mutableStateOf(RequestState.Idle)
    val completedTasks: Tasks = _completedTasks

    init {
        _activeTasks.value = RequestState.Loading
        _completedTasks.value = RequestState.Loading
        screenModelScope.launch(Dispatchers.Main) {
            delay(500)
            database.readActiveTasks().collectLatest {
                _activeTasks.value = it
            }
        }
        screenModelScope.launch(Dispatchers.Main) {
            delay(500)
            database.readCompletedTasks().collectLatest {
                _completedTasks.value = it
            }
        }
    }

    fun setAction(action: Action) {
        when (action) {
            is Action.Delete -> {
                deleteTask(action.task)
            }

            is Action.SetCompleted -> {
                setCompleted(action.task, action.completed)
            }

            is Action.SetFavorite -> {
                setFavorite(action.task, action.isFavorite)
            }

            else -> {}
        }
    }

    private fun setCompleted(task: Task, completed: Boolean) {
        screenModelScope.launch(Dispatchers.IO) {
            database.setCompleted(task, completed)
        }
    }

    private fun setFavorite(task: Task, isFavorite: Boolean) {
        screenModelScope.launch(Dispatchers.IO) {
            database.setFavorite(task, isFavorite)
        }
    }

    private fun deleteTask(task: Task) {
        screenModelScope.launch(Dispatchers.IO) {
            database.deleteTask(task)
        }
    }
}