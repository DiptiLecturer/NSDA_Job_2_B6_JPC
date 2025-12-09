package org.freedu.nsda_job_2jetpack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import org.freedu.nsda_job_2jetpack.data.Profile
import org.freedu.nsda_job_2jetpack.data.ProfileRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    val allProfiles: Flow<List<Profile>> = repository.allProfiles

    fun insert(profile: Profile) = viewModelScope.launch {
        repository.insert(profile)
    }

    fun delete(profile: Profile) = viewModelScope.launch {
        repository.delete(profile)
    }
}


