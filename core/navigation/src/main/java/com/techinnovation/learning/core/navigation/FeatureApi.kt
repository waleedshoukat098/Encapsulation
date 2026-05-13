package com.techinnovation.learning.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureApi {
    val route: String
    fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController)
}
