package com.cricketapp.matches.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cricketapp.matches.current.MatchesScreen
import com.cricketapp.matches.details.MatchDetailsScreen

object Routes {
    const val MATCHES_SCREEN = "matches_screen"
    const val MATCH_DETAIL_SCREEN = "match_detail_screen"
}

@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController,
    onToolbarTitleChanged: (String) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.MATCHES_SCREEN
    ) {
        composable(Routes.MATCHES_SCREEN, ) {
            onToolbarTitleChanged("Cricket App")
            MatchesScreen { matchId, matchName ->
                navController.navigate("${Routes.MATCH_DETAIL_SCREEN}/${matchId}/${matchName}") {
                    launchSingleTop = true
                }
            }
        }
        composable("${Routes.MATCH_DETAIL_SCREEN}/{matchId}/{matchName}") { backStackEntry ->
            onToolbarTitleChanged(backStackEntry.arguments?.getString("matchName")?: "")
            MatchDetailsScreen()
        }
    }
}