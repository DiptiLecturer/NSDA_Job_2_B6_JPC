package org.freedu.nsda_job_2jetpack.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.freedu.nsda_job_2jetpack.R
import org.freedu.nsda_job_2jetpack.data.ProfileApp
import org.freedu.nsda_job_2jetpack.viewModel.ProfileViewModel
import org.freedu.nsda_job_2jetpack.viewModel.ProfileViewModelFactory

@Composable
fun ProfileListScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(
            (context.applicationContext as ProfileApp).repository
        )
    )

    val profiles by viewModel.allProfiles.collectAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add") },
                containerColor = Color.Black, // Black background
                contentColor = Color.White     // Icon color
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_ic),
                    contentDescription = "Add Profile",
                    tint = Color.White, // Ensure icon is white
                    modifier = Modifier.size(28.dp) // adjust size if needed
                )
            }
        }

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(
                "Total Profiles: ${profiles.size}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (profiles.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No profiles found. Click '+' to add.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(profiles) { profile ->
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate("single/${profile.id}") }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Profile info on the left
                                Column(
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        "Name: ${profile.name}",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text("Email: ${profile.email}",
                                        fontSize = 18.sp,
                                        color = Color(0xFF1E88E5)
                                    )
                                    Text("Phone: ${profile.phone}",
                                        fontSize = 16.sp,
                                        color = Color(0xFF00897B)
                                    )
                                    Text(
                                        "DOB: ${profile.dob}",
                                        fontSize = 16.sp,
                                        color = Color.DarkGray
                                    )
                                    Text(
                                        "District: ${profile.district}",
                                        fontSize = 16.sp,
                                        color = Color(0xFF039BE5)
                                    )
                                }

                                // Delete button on the right
                                IconButton(
                                    onClick = { viewModel.delete(profile) },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color.Red, shape = CircleShape)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.delete),
                                        contentDescription = "Delete",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}



