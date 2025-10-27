package com.kongxiaoan.composedreamfactory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kongxiaoan.composedreamfactory.ui.theme.ComposeDreamFactoryTheme
import com.kongxiaoan.composedreamfactory.ui.MainScreen

/**
 * MainActivity - 应用程序的主入口点
 * 
 * 这个Activity展示了如何使用Jetpack Compose创建UI
 * 核心概念：
 * 1. ComponentActivity - Compose的基础Activity
 * 2. setContent - 设置Compose内容的方法
 * 3. Theme - 应用主题系统
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 应用主题包装器
            ComposeDreamFactoryTheme {
                // Surface提供Material Design的背景容器
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 主屏幕内容
                    MainScreen()
                }
            }
        }
    }
}
