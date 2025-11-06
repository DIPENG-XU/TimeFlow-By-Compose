package com.apollo.timeflow.module.moduleNavHost

object NavHostLanguageConfigurationConfirmDialogArgument {
    const val SELECTED_AREA = "selected_area"
}

object NavHostDateFormatSelectorConfigurationConfirmDialogArgument {
    const val SELECTED_DATE_FORMAT = "selected_date_format"
}

object NavHostRouteConfig {

    object Launch {
        const val ROUTE = "launch"
    }

    object HomeFeed {
        const val ROUTE = "homefeed"
    }

    object Settings {
        const val ROUTE = "settings"
    }

    object Dialog {
        const val TIME_FORMAT = "dialog_time_format"
        const val DATE_FORMAT = "dialog_date_format"
        const val THEME_FORMAT = "dialog_theme_format"
        const val POWER_BY = "dialog_power_by"

        object LanguageConfig {
            const val ROUTE = "dialog_language_config"
            const val CONFIRM = "dialog_language_config_confirm"
            const val ARG_SELECTED_AREA = "selected_area"
        }

        object DateFormatSelector {
            const val ROUTE = "dialog_date_format_selector"
            const val CONFIRM = "dialog_date_format_selector_confirm"
            const val ARG_SELECTED_FORMAT = "selected_date_format"
        }
    }
}
