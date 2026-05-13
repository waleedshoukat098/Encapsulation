package com.techinnovation.learning.feature.trends.impl

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.techinnovation.learning.feature.trends.api.TrendsFeatureApi

class TrendsFeatureEntry : TrendsFeatureApi {
    override val route: String = "trends"

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(route) { TrendsScreen() }
    }
}

@Composable
private fun TrendsScreen() = Text("Trends Feature")
