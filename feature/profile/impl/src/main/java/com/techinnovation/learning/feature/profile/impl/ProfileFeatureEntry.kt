package com.techinnovation.learning.feature.profile.impl

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.techinnovation.learning.feature.profile.api.ProfileFeatureApi

class ProfileFeatureEntry : ProfileFeatureApi {
    override val route: String = "profile"

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(route) { ProfileScreen() }
    }
}

@Composable
private fun ProfileScreen() = Text("Profile Feature")
