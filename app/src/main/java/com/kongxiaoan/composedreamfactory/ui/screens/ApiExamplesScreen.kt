package com.kongxiaoan.composedreamfactory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * ApiExamplesScreen - Compose常用API示例
 * 
 * 展示Compose的常用API和组件
 * 学习要点：
 * 1. LazyColumn/LazyRow - 懒加载列表
 * 2. Icon - 图标组件
 * 3. Card - 卡片组件
 * 4. Checkbox/Switch - 选择组件
 */
@Composable
fun ApiExamplesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Compose API 示例",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Divider()
        
        // 1. Icon示例
        SectionTitle("1. Icon - 图标")
        IconExamples()
        
        Divider()
        
        // 2. Card示例
        SectionTitle("2. Card - 卡片")
        CardExamples()
        
        Divider()
        
        // 3. Checkbox和Switch
        SectionTitle("3. Checkbox & Switch - 选择组件")
        CheckboxSwitchExamples()
        
        Divider()
        
        // 4. LazyColumn示例
        SectionTitle("4. LazyColumn - 懒加载列表")
        Text("(完整示例请查看下方)")
        LazyColumnExample()
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

@Composable
private fun IconExamples() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(Icons.Filled.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.primary)
            Icon(Icons.Filled.Favorite, contentDescription = "Favorite", tint = MaterialTheme.colorScheme.error)
            Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.secondary)
            Icon(Icons.Filled.Person, contentDescription = "Person", tint = MaterialTheme.colorScheme.tertiary)
            Icon(Icons.Filled.Star, contentDescription = "Star", tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun CardExamples() {
    // Filled Card
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Filled Card", fontWeight = FontWeight.Bold)
            Text("这是一个填充卡片")
        }
    }
    
    Spacer(modifier = Modifier.height(8.dp))
    
    // Outlined Card
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Outlined Card", fontWeight = FontWeight.Bold)
            Text("这是一个轮廓卡片")
        }
    }
    
    Spacer(modifier = Modifier.height(8.dp))
    
    // Elevated Card
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Elevated Card", fontWeight = FontWeight.Bold)
            Text("这是一个带阴影的卡片")
        }
    }
}

@Composable
private fun CheckboxSwitchExamples() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            var checked1 by remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked = checked1,
                    onCheckedChange = { checked1 = it }
                )
                Text("Checkbox 示例")
            }
            
            var checked2 by remember { mutableStateOf(true) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Switch(
                    checked = checked2,
                    onCheckedChange = { checked2 = it }
                )
                Text("Switch 示例")
            }
        }
    }
}

@Composable
private fun LazyColumnExample() {
    // 示例数据
    val items = remember {
        List(20) { index -> "列表项 ${index + 1}" }
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.List, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(item)
                    }
                }
            }
        }
    }
}
