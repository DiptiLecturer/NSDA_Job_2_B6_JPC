package org.freedu.nsda_job_2jetpack.data

import android.app.Application
import kotlin.getValue

class ProfileApp : Application() {
    val database: ProfileDatabase by lazy { ProfileDatabase.getDatabase(this) }
    val repository: ProfileRepository by lazy { ProfileRepository(database.profileDao()) }
}

