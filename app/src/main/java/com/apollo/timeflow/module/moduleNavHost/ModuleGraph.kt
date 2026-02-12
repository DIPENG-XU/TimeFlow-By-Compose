package com.apollo.timeflow.module.moduleNavHost

import android.os.Bundle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apollo.timeflow.module.homefeed.ui.card.CardHomeFeed
import com.apollo.timeflow.module.launch.ui.LaunchPage
import com.apollo.timeflow.module.settings.ui.ConfirmDialog
import com.apollo.timeflow.module.settings.ui.DateFormatListDialog
import com.apollo.timeflow.module.settings.ui.FontConfigurationListDialog
import com.apollo.timeflow.module.settings.ui.LanguageConfigurationListDialog
import com.apollo.timeflow.module.settings.ui.TimeFlowSettings

object ModuleGraph {

    // ------------------------
    // Graphs
    // ------------------------

    /**
     * Launch Screen Graph
     */
    fun NavGraphBuilder.launchGraph(
        navController: NavHostController
    ) {
        composable(NavHostRouteConfig.Launch.ROUTE) {
            LaunchPage {
                navController.popBackStack(NavHostRouteConfig.Launch.ROUTE, inclusive = true)
                navController.navigate(NavHostRouteConfig.HomeFeed.ROUTE)
            }
        }
    }

    /**
     * Home Screen Graph
     */
    fun NavGraphBuilder.homeGraph(
        navController: NavHostController,
    ) {
        composable(NavHostRouteConfig.HomeFeed.ROUTE) {
            CardHomeFeed {
                navController.navigate(NavHostRouteConfig.Settings.ROUTE)
            }
        }
    }

    /**
     * Settings and Dialogs Graph
     */
    fun NavGraphBuilder.settingsGraph(
        navController: NavHostController,
    ) {
        // Main Settings Screen
        composable(NavHostRouteConfig.Settings.ROUTE) {
            TimeFlowSettings { route ->
                navController.navigate(route)
            }
        }

        // Simple Confirm Dialogs
        listOf(
            NavHostRouteConfig.Dialog.TIME_FORMAT,
            NavHostRouteConfig.Dialog.DATE_FORMAT,
            NavHostRouteConfig.Dialog.THEME_FORMAT,
            NavHostRouteConfig.Dialog.POWER_BY
        ).forEach { route ->
            composable(route) {
                ConfirmDialog(route) { route, inclusive ->
                    navController.popBackStack(route, inclusive)
                }
            }
        }

        // Language Configuration List
        composable(NavHostRouteConfig.Dialog.LanguageConfig.ROUTE) {
            LanguageConfigurationListDialog(
                navigateClickable = { navController.navigate(it) },
                navigatePopBackStack = { navController.popBackStack(NavHostRouteConfig.Settings.ROUTE, false) }
            )
        }

        // Language Confirm Dialog with parameter
        confirmDialog(
            routePattern = "${NavHostRouteConfig.Dialog.LanguageConfig.CONFIRM}/{${NavHostLanguageConfigurationConfirmDialogArgument.SELECTED_AREA}}",
            argName = NavHostLanguageConfigurationConfirmDialogArgument.SELECTED_AREA,
            navController = navController
        )

        // Font Configuration List
        composable(NavHostRouteConfig.Dialog.FontConfig.ROUTE) {
            FontConfigurationListDialog(
                navigateClickable = { navController.navigate(it) },
                navigatePopBackStack = { navController.popBackStack(NavHostRouteConfig.Settings.ROUTE, false) }
            )
        }

        // Font Confirm Dialog With parameter
        confirmDialog(
            routePattern = "${NavHostRouteConfig.Dialog.FontConfig.CONFIRM}/{${NavHostFontConfigurationConfirmDialogArgs.SELECT_FONT}}",
            argName = NavHostFontConfigurationConfirmDialogArgs.SELECT_FONT,
            navController = navController
        )

        // Date Format Selector List
        composable(NavHostRouteConfig.Dialog.DateFormatSelector.ROUTE) {
            DateFormatListDialog(
                navigateClickable = { navController.navigate(it) },
                navigatePopBackStack = { navController.popBackStack(NavHostRouteConfig.Settings.ROUTE, false) }
            )
        }

        // Date Format Confirm Dialog with parameter
        confirmDialog(
            routePattern = "${NavHostRouteConfig.Dialog.DateFormatSelector.CONFIRM}/{${NavHostDateFormatSelectorConfigurationConfirmDialogArgument.SELECTED_DATE_FORMAT}}",
            argName = NavHostDateFormatSelectorConfigurationConfirmDialogArgument.SELECTED_DATE_FORMAT,
            navController = navController
        )
    }

    // ------------------------
    // Helper Functions
    // ------------------------


    /**
     * Generic helper for confirm dialogs with string parameters
     */
    private fun NavGraphBuilder.confirmDialog(
        routePattern: String,
        argName: String,
        navController: NavHostController
    ) {
        composable(
            route = routePattern,
            arguments = listOf(navArgument(argName) { type = NavType.StringType })
        ) { backStackEntry ->
            val value = backStackEntry.arguments?.getString(argName)
            ConfirmDialog(
                route = routePattern.substringBefore("/{"),
                bundle = Bundle().apply { putString(argName, value) }
            ) { route, inclusive ->
                navController.popBackStack(route, inclusive)
            }
        }
    }
}
