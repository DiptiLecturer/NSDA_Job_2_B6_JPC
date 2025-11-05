package org.freedu.nsda_job_2jetpack.data

import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val dao: ProfileDao) {
    val allProfiles: Flow<List<Profile>> = dao.getAllProfiles()

    suspend fun insert(profile: Profile) = dao.insert(profile)
    suspend fun delete(profile: Profile) = dao.delete(profile)
}

