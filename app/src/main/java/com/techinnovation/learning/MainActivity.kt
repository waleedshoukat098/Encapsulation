package com.techinnovation.learning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.techinnovation.learning.core.navigation.FeatureApi
import com.techinnovation.learning.feature.home.impl.HomeFeatureEntry
import com.techinnovation.learning.feature.logmeal.impl.LogmealFeatureEntry
import com.techinnovation.learning.feature.onboarding.impl.OnboardingFeatureEntry
import com.techinnovation.learning.feature.profile.impl.ProfileFeatureEntry
import com.techinnovation.learning.feature.scan.impl.ScanFeatureEntry
import com.techinnovation.learning.feature.trends.impl.TrendsFeatureEntry
import com.techinnovation.learning.ui.theme.RishtonKeMasailTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RishtonKeMasailTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val features: List<FeatureApi> = listOf(
                        OnboardingFeatureEntry(), HomeFeatureEntry(), LogmealFeatureEntry(),
                        ScanFeatureEntry(), TrendsFeatureEntry(), ProfileFeatureEntry()
                    )
                    NavHost(navController = navController, startDestination = "onboarding") {
                        features.forEach { it.registerGraph(this, navController) }
                    }
                }
            }
        }
    }
}
