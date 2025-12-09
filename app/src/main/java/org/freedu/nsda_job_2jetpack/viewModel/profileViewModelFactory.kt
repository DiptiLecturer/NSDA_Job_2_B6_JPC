package org.freedu.nsda_job_2jetpack.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.freedu.nsda_job_2jetpack.data.ProfileRepository


class ProfileViewModelFactory(
    private val repository: ProfileRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
