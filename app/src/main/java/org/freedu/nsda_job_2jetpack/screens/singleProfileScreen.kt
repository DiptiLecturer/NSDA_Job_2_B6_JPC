package org.freedu.nsda_job_2jetpack.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.freedu.nsda_job_2jetpack.viewModel.ProfileViewModel

@Composable
fun SingleProfileScreen(
    navController: NavController,
    profileId: Int,
    viewModel: ProfileViewModel = viewModel()
) {
    // Use collectAsState() because ViewModel exposes StateFlow
    val profiles by viewModel.allProfiles.collectAsState()
    val profile = profiles.find { it.id == profileId }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (profile != null) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Name: ${profile.name}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Email: ${profile.email}")
                Text("Phone: ${profile.phone}")
            }
        } else {
            Text("Profile not found", color = Color.Gray)
        }
    }
}
