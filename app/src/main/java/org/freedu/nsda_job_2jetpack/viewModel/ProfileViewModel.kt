package org.freedu.nsda_job_2jetpack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.freedu.nsda_job_2jetpack.data.Profile
import org.freedu.nsda_job_2jetpack.data.ProfileDatabase
import org.freedu.nsda_job_2jetpack.data.ProfileRepository

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ProfileDatabase.getDatabase(application).profileDao()
    private val repository = ProfileRepository(dao)

    // Compose-friendly StateFlow
    val allProfiles: StateFlow<List<Profile>> = repository.allProfiles
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insert(profile: Profile) = viewModelScope.launch {
        repository.insert(profile)
    }

    fun delete(profile: Profile) = viewModelScope.launch {
        repository.delete(profile)
    }
}


