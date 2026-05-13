package com.techinnovation.learning.feature.onboarding.impl

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.techinnovation.learning.feature.onboarding.api.OnboardingFeatureApi

class OnboardingFeatureEntry : OnboardingFeatureApi {
    override val route: String = "onboarding"

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable(route) { OnboardingScreen() }
    }
}

@Composable
private fun OnboardingScreen() = Text("Onboarding Feature")
