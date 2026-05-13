package com.techinnovation.learning.feature.home.impl

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.techinnovation.learning.feature.home.api.HomeFeatureApi

class HomeFeatureEntry : HomeFeatureApi {
    override val route: String = "home"

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(route) { HomeScreen() }
    }
}

@Composable
private fun HomeScreen() = Text("Home Feature")
