package com.techinnovation.learning.feature.logmeal.impl

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.techinnovation.learning.feature.logmeal.api.LogmealFeatureApi

class LogmealFeatureEntry : LogmealFeatureApi {
    override val route: String = "logmeal"

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(route) { LogmealScreen() }
    }
}

@Composable
private fun LogmealScreen() = Text("Logmeal Feature")
