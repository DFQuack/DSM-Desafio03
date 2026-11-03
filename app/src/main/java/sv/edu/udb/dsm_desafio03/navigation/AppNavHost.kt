package sv.edu.udb.dsm_desafio03.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import sv.edu.udb.dsm_desafio03.R
import sv.edu.udb.dsm_desafio03.feature.form.ResourceFormScreen
import sv.edu.udb.dsm_desafio03.feature.resources.ResourceScreen

object Routes {
    const val RESOURCES = "resources"
    const val FORM = "form?resourceId={resourceId}"
    // If the resource id is passed, the form is in Edit mode. Otherwise, it's in Create mode.
    fun form(id: String? = null) = if (id == null) "form" else "form?resourceId=$id"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute == Routes.RESOURCES

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Routes.RESOURCES,
                        onClick = { navController.navigateToTab(Routes.RESOURCES) },
                        icon = { Icon(Icons.Default.Home, null) },
                        label = { Text(stringResource(R.string.nav_resources)) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.RESOURCES,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.RESOURCES) {
                ResourceScreen(
                    onAddClick = { navController.navigate(Routes.form()) },
                    onEditClick = { id -> navController.navigate(Routes.form(id)) }
                )
            }
            composable(
                route = Routes.FORM,
                arguments = listOf(navArgument("resourceId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })
            ) {
                ResourceFormScreen(onNavigateBack = { navController.popBackStack() })
            }
        }
    }
}

private fun NavController.navigateToTab(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}