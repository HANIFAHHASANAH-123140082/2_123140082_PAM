package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.ProfileData

data class ProfileUiState(
    val profile: ProfileData = ProfileData(),
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false,
    val editName: String = "",
    val editBio: String = "",
    val editEmail: String = "",
    val editPhone: String = "",
    val editLocation: String = ""
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun startEditing() {
        _uiState.update {
            it.copy(
                isEditing = true,
                editName = it.profile.name,
                editBio = it.profile.bio,
                editEmail = it.profile.email,
                editPhone = it.profile.phone,
                editLocation = it.profile.location
            )
        }
    }

    fun cancelEditing() {
        _uiState.update { it.copy(isEditing = false) }
    }

    fun onNameChange(value: String) = _uiState.update { it.copy(editName = value) }
    fun onBioChange(value: String) = _uiState.update { it.copy(editBio = value) }
    fun onEmailChange(value: String) = _uiState.update { it.copy(editEmail = value) }
    fun onPhoneChange(value: String) = _uiState.update { it.copy(editPhone = value) }
    fun onLocationChange(value: String) = _uiState.update { it.copy(editLocation = value) }

    fun saveProfile() {
        _uiState.update {
            it.copy(
                profile = it.profile.copy(
                    name = it.editName,
                    bio = it.editBio,
                    email = it.editEmail,
                    phone = it.editPhone,
                    location = it.editLocation
                ),
                isEditing = false
            )
        }
    }
}