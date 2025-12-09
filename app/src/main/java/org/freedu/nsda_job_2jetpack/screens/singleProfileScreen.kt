package org.freedu.nsda_job_2jetpack.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.freedu.nsda_job_2jetpack.data.Profile
import org.freedu.nsda_job_2jetpack.data.ProfileApp
import org.freedu.nsda_job_2jetpack.viewModel.ProfileViewModel
import org.freedu.nsda_job_2jetpack.viewModel.ProfileViewModelFactory

@Composable
fun SingleProfileScreen(
    navController: NavController,
    profileId: Int
) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(
            (context.applicationContext as ProfileApp).repository
        )
    )

    val profiles by viewModel.allProfiles.collectAsState(initial = emptyList())
    val profile = profiles.find { it.id == profileId }
    var showEditDialog by remember { mutableStateOf(false) }

    if (profile == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Profile not found", color = Color.Gray)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Profile Details", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            ProfileInfoRow("Name", profile.name)
            ProfileInfoRow("Email", profile.email)
            ProfileInfoRow("Phone", profile.phone)
            ProfileInfoRow("Date of Birth", profile.dob)
            ProfileInfoRow("District", profile.district)

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { showEditDialog = true }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text("Edit", color = Color.White)
                }

                Button(onClick = {
                    viewModel.delete(profile)
                    navController.popBackStack()
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                    Text("Delete", color = Color.White)
                }
            }
        }

        if (showEditDialog) {
            EditProfileDialog(
                profile = profile,
                onDismiss = { showEditDialog = false },
                onSave = { updatedProfile -> viewModel.insert(updatedProfile) }
            )
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, fontSize = 14.sp, color = Color.Gray)
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Divider(modifier = Modifier.padding(top = 4.dp))
    }
}
@Composable
fun EditProfileDialog(
    profile: Profile,
    onDismiss: () -> Unit,
    onSave: (Profile) -> Unit
) {
    var name by remember { mutableStateOf(profile.name) }
    var email by remember { mutableStateOf(profile.email) }
    var phone by remember { mutableStateOf(profile.phone) }
    var dob by remember { mutableStateOf(profile.dob) }
    var district by remember { mutableStateOf(profile.district) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone") })
                OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("Date of Birth") })
                OutlinedTextField(value = district, onValueChange = { district = it }, label = { Text("District") })
            }
        },
        confirmButton = {
            Button(onClick = { onSave(profile.copy(name = name, email = email, phone = phone, dob = dob, district = district)); onDismiss() }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

