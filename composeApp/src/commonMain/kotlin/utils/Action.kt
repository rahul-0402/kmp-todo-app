package utils

import data.Task

sealed class Action {
    data class Add(val task: Task) : Action()
    data class Update(val task: Task) : Action()
    data class Delete(val task: Task) : Action()
    data class SetCompleted(val task: Task, val completed: Boolean) : Action()
    data class SetFavorite(val task: Task, val isFavorite: Boolean) : Action()
}