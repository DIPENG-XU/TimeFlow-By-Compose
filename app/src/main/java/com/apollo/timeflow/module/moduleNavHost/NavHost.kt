package com.apollo.timeflow.module.moduleNavHost

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun TimeFlowNavHost(viewModelStoreOwner: ViewModelStoreOwner) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavHostRouteConfig.Launch.ROUTE,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { fadeIn(tween(0)) },
        exitTransition = { fadeOut(tween(0)) }
    ) {
        with(ModuleGraph) {
            launchGraph(navController, viewModelStoreOwner)
            homeGraph(navController, viewModelStoreOwner)
            settingsGraph(navController, viewModelStoreOwner)
        }
    }
}
