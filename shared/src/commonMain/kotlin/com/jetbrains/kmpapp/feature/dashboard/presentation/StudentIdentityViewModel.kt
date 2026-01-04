package com.jetbrains.kmpapp.feature.dashboard.presentation

import com.jetbrains.kmpapp.feature.student.domain.usecase.SetActiveStudentUseCase
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudentIdentityViewModel(
    private val setActiveStudent: SetActiveStudentUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(StudentIdentityState())

    @NativeCoroutinesState
    val state: StateFlow<StudentIdentityState> = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        StudentIdentityState()
    )

    fun updateId(value: String) {
        _state.update { it.copy(studentId = value, error = null) }
    }

    fun updateName(value: String) {
        _state.update { it.copy(displayName = value, error = null) }
    }

    fun save(onSaved: () -> Unit) {
        val current = _state.value
        if (current.studentId.isBlank()) {
            _state.update { it.copy(error = "Student ID is required") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null) }
            setActiveStudent(current.studentId, current.displayName.ifBlank { null })
            _state.update { it.copy(isSaving = false) }
            onSaved()
        }
    }
}
