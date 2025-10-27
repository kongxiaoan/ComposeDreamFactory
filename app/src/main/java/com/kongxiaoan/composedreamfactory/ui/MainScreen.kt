package com.kongxiaoan.composedreamfactory.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kongxiaoan.composedreamfactory.R
import com.kongxiaoan.composedreamfactory.ui.screens.*

/**
 * MainScreen - 主屏幕
 * 
 * 展示Compose导航和底部导航栏的使用
 * 学习要点：
 * 1. Navigation Compose - 声明式导航
 * 2. Scaffold - Material Design脚手架
 * 3. Bottom Navigation - 底部导航栏
 * 4. State管理
 */

// 导航目的地密封类
sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object BasicSyntax : Screen("basic_syntax", "基础语法", Icons.Filled.Code)
    object ApiExamples : Screen("api_examples", "API示例", Icons.Filled.Apps)
    object CommonPatterns : Screen("common_patterns", "常用模式", Icons.Filled.Widgets)
    object Architecture : Screen("architecture", "架构", Icons.Filled.AccountTree)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Scaffold提供基本的Material Design布局结构
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    Screen.BasicSyntax,
                    Screen.ApiExamples,
                    Screen.CommonPatterns,
                    Screen.Architecture
                )
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                // 避免多次点击创建多个实例
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        // NavHost管理导航图
        NavigationGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.BasicSyntax.route,
        modifier = modifier
    ) {
        composable(Screen.BasicSyntax.route) {
            BasicSyntaxScreen()
        }
        composable(Screen.ApiExamples.route) {
            ApiExamplesScreen()
        }
        composable(Screen.CommonPatterns.route) {
            CommonPatternsScreen()
        }
        composable(Screen.Architecture.route) {
            ArchitectureScreen()
        }
    }
}
