package com.techinnovation.learning.feature.scan.impl

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.techinnovation.learning.feature.scan.api.ScanFeatureApi

class ScanFeatureEntry : ScanFeatureApi {
    override val route: String = "scan"

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(route) { ScanScreen() }
    }
}

@Composable
private fun ScanScreen() = Text("Scan Feature")
