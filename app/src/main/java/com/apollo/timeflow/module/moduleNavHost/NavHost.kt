package com.apollo.timeflow.module.moduleNavHost

import android.os.Bundle
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apollo.timeflow.module.homefeed.ui.card.CardHomeFeed
import com.apollo.timeflow.module.launch.ui.LaunchPage
import com.apollo.timeflow.module.settings.ui.ConfirmDialog
import com.apollo.timeflow.module.settings.ui.DateFormatListDialog
import com.apollo.timeflow.module.settings.ui.LanguageConfigurationListDialog
import com.apollo.timeflow.module.settings.ui.TimeFlowSettings

@Composable
fun TimeFlowNavHost(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    NavHost(
        navController = navController,
        startDestination = NavHostRouteConfig.NAV_HOST_LAUNCH_PAGE,
        modifier = Modifier.fillMaxSize(),
        enterTransition = {
            fadeIn(tween(0))
        }, exitTransition = {
            fadeOut(tween(0))
        }
    ) {
        composable(route = NavHostRouteConfig.NAV_HOST_LAUNCH_PAGE) {
            LaunchPage(
                viewModelStoreOwner,
                navController,
            )
        }

        composable(route = NavHostRouteConfig.NAV_HOST_ROUTE_FOR_HOMEFEED) {
            CardHomeFeed(
                viewModelStoreOwner,
                navController,
            )
        }

        composable(route = NavHostRouteConfig.NAV_HOST_ROUTE_FOR_SETTINGS) {
            TimeFlowSettings(navController, viewModelStoreOwner)
        }

        composable(route = NavHostRouteConfig.DATE_FORMAT_DIALOG_ROUTE) {
            ConfirmDialog(
                navController = navController,
                route = NavHostRouteConfig.DATE_FORMAT_DIALOG_ROUTE,
                viewModelStoreOwner = viewModelStoreOwner,
            )
        }

        composable(route = NavHostRouteConfig.TIME_FORMAT_DIALOG_ROUTE) {
            ConfirmDialog(
                navController = navController,
                route = NavHostRouteConfig.TIME_FORMAT_DIALOG_ROUTE,
                viewModelStoreOwner = viewModelStoreOwner,
            )
        }

        composable(route = NavHostRouteConfig.THEME_FORMAT_DIALOG_ROUTE) {
            ConfirmDialog(
                navController = navController,
                route = NavHostRouteConfig.THEME_FORMAT_DIALOG_ROUTE,
                viewModelStoreOwner = viewModelStoreOwner,
            )
        }

        composable(
            route = "${NavHostRouteConfig.LANGUAGE_CONFIGURATION_CONFIRM_DIALOG_ROUTE}/{${NavHostLanguageConfigurationConfirmDialogArgument.SELECTED_AREA}}",
            arguments = listOf(navArgument(NavHostLanguageConfigurationConfirmDialogArgument.SELECTED_AREA) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            ConfirmDialog(
                navController = navController,
                route = NavHostRouteConfig.LANGUAGE_CONFIGURATION_CONFIRM_DIALOG_ROUTE,
                viewModelStoreOwner = viewModelStoreOwner,
                bundle = Bundle().also {
                    val selectedArea = backStackEntry.arguments?.getString(
                        NavHostLanguageConfigurationConfirmDialogArgument.SELECTED_AREA
                    )
                    it.putString(
                        NavHostLanguageConfigurationConfirmDialogArgument.SELECTED_AREA,
                        selectedArea
                    )
                }
            )
        }

        composable(route = NavHostRouteConfig.LANGUAGE_CONFIGURATION_DIALOG_ROUTE) {
            LanguageConfigurationListDialog(
                navController = navController,
                viewModelStoreOwner = viewModelStoreOwner,
            )
        }

        composable(
            route = "${NavHostRouteConfig.DATE_FORMAT_SELECTOR_CONFIRM_DIALOG_ROUTE}/{${NavHostDateFormatSelectorConfigurationConfirmDialogArgument.SELECTED_DATE_FORMAT}}",
            arguments = listOf(navArgument(NavHostDateFormatSelectorConfigurationConfirmDialogArgument.SELECTED_DATE_FORMAT) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            ConfirmDialog(
                navController = navController,
                route = NavHostRouteConfig.DATE_FORMAT_SELECTOR_CONFIRM_DIALOG_ROUTE,
                viewModelStoreOwner = viewModelStoreOwner,
                bundle = Bundle().also {
                    val selectedDateFormat = backStackEntry.arguments?.getString(
                        NavHostDateFormatSelectorConfigurationConfirmDialogArgument.SELECTED_DATE_FORMAT
                    )
                    it.putString(
                        NavHostDateFormatSelectorConfigurationConfirmDialogArgument.SELECTED_DATE_FORMAT,
                        selectedDateFormat
                    )
                }
            )
        }

        composable(
            route = NavHostRouteConfig.POWER_BY_DIALOG_ROUTE,
        ) {
            ConfirmDialog(
                navController = navController,
                route = NavHostRouteConfig.POWER_BY_DIALOG_ROUTE,
                viewModelStoreOwner = viewModelStoreOwner,
            )
        }

        composable(route = NavHostRouteConfig.DATE_FORMAT_SELECTOR_DIALOG_ROUTE) {
            DateFormatListDialog(
                navController = navController,
                viewModelStoreOwner = viewModelStoreOwner,
            )
        }
    }
}