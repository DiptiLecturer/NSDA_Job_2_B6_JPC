package org.freedu.nsda_job_2jetpack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val dob: String,        // 👈 added
    val district: String    // 👈 added
)

