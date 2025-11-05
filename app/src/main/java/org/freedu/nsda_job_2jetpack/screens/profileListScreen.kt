package org.freedu.nsda_job_2jetpack.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.freedu.nsda_job_2jetpack.R
import org.freedu.nsda_job_2jetpack.viewModel.ProfileViewModel

@Composable
fun ProfileListScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    // Collect profiles as state
    val profiles by viewModel.allProfiles.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White, // Scaffold background
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add") },
                modifier = Modifier.size(80.dp), // slightly bigger FAB
                containerColor = Color.White,    // FAB background
                contentColor = Color.Black,      // icon color
                shape = CircleShape,             // round FAB
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_ic),
                    contentDescription = "Add Profile",
                    modifier = Modifier.size(50.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Total Profiles header
            Text(
                text = "Total Profiles: ${profiles.size}",
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 22.dp),
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (profiles.isEmpty()) {
                // Empty state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No profiles found.\nClick the '+' icon to create a new profile.",
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            } else {
                // Profiles list
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
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
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = profile.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 28.sp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        text = profile.email,
                                        fontSize = 24.sp,
                                        color = Color.DarkGray
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        text = profile.phone,
                                        fontSize = 16.sp,
                                        color = Color.DarkGray
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                }

                                IconButton(
                                    onClick = { viewModel.delete(profile) },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color.DarkGray, shape = CircleShape)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.delete),
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


