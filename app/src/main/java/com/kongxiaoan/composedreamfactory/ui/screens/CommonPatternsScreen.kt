package com.kongxiaoan.composedreamfactory.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * CommonPatternsScreen - Compose常用开发模式
 * 
 * 展示常用的Compose开发模式和最佳实践
 * 学习要点：
 * 1. Side Effects - 副作用
 * 2. Animation - 动画
 * 3. 状态提升 - State Hoisting
 * 4. 可重用组件设计
 */
@Composable
fun CommonPatternsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Compose 常用模式",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Divider()
        
        // 1. 状态提升模式
        SectionTitle("1. State Hoisting - 状态提升")
        StateHoistingExample()
        
        Divider()
        
        // 2. 动画模式
        SectionTitle("2. Animation - 动画")
        AnimationExamples()
        
        Divider()
        
        // 3. Side Effects
        SectionTitle("3. Side Effects - 副作用")
        SideEffectExamples()
        
        Divider()
        
        // 4. 可展开列表
        SectionTitle("4. Expandable List - 可展开列表")
        ExpandableListExample()
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
}

/**
 * 状态提升示例
 * 将状态从子组件提升到父组件，使组件更可重用
 */
@Composable
private fun StateHoistingExample() {
    var text by remember { mutableStateOf("") }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("当前输入: $text")
            
            // 状态提升的TextField
            StatelessTextField(
                text = text,
                onTextChange = { text = it }
            )
        }
    }
}

@Composable
private fun StatelessTextField(
    text: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text("无状态组件") },
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * 动画示例
 */
@Composable
private fun AnimationExamples() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // 1. 简单动画
            var expanded by remember { mutableStateOf(false) }
            val size by animateDpAsState(
                targetValue = if (expanded) 100.dp else 50.dp,
                label = "size"
            )
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("大小动画")
                Box(
                    modifier = Modifier
                        .size(size)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .clickable { expanded = !expanded }
                )
            }
            
            Divider()
            
            // 2. 可见性动画
            var visible by remember { mutableStateOf(true) }
            
            Column {
                Button(onClick = { visible = !visible }) {
                    Text(if (visible) "隐藏" else "显示")
                }
                
                AnimatedVisibility(visible = visible) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Text(
                            "带动画的可见性",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Side Effects示例
 */
@Composable
private fun SideEffectExamples() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("LaunchedEffect 示例:", fontWeight = FontWeight.Bold)
            
            var count by remember { mutableStateOf(0) }
            
            // LaunchedEffect在组件进入时执行
            LaunchedEffect(Unit) {
                // 这里可以执行异步操作
                kotlinx.coroutines.delay(1000)
                count = 1
            }
            
            Text("计数器: $count (1秒后自动变为1)")
            
            Divider()
            
            Text("DisposableEffect 示例:", fontWeight = FontWeight.Bold)
            Text("用于需要清理的副作用，如监听器注册")
        }
    }
}

/**
 * 可展开列表示例
 */
@Composable
private fun ExpandableListExample() {
    val items = listOf(
        "Android 开发" to "使用 Kotlin 和 Jetpack Compose",
        "iOS 开发" to "使用 Swift 和 SwiftUI",
        "Web 开发" to "使用 React 或 Vue.js"
    )
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { (title, description) ->
            ExpandableCard(title = title, description = description)
        }
    }
}

@Composable
private fun ExpandableCard(title: String, description: String) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontWeight = FontWeight.Bold)
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "收起" else "展开"
                )
            }
            
            AnimatedVisibility(visible = expanded) {
                Text(
                    text = description,
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
