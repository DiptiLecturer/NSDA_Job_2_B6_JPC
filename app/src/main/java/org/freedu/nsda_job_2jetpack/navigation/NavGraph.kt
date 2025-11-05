package org.freedu.nsda_job_2jetpack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import org.freedu.nsda_job_2jetpack.screens.AddProfileScreen
import org.freedu.nsda_job_2jetpack.screens.ProfileListScreen
import org.freedu.nsda_job_2jetpack.screens.SingleProfileScreen
import org.freedu.nsda_job_2jetpack.screens.WelcomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("list") { ProfileListScreen(navController) }
        composable("add") { AddProfileScreen(navController) }
        composable("single/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            SingleProfileScreen(navController, id)
        }
    }
}