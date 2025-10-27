package com.kongxiaoan.composedreamfactory.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * BasicSyntaxScreen - Compose基础语法示例
 * 
 * 展示Compose的核心概念和基础语法
 * 学习要点：
 * 1. @Composable注解 - 标记可组合函数
 * 2. Modifier - 修饰符链式调用
 * 3. State - 状态管理
 * 4. Layout - 布局组件（Column, Row, Box）
 */
@Composable
fun BasicSyntaxScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 标题
        Text(
            text = "Compose 基础语法",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Divider()
        
        // 1. 文本组件
        SectionTitle("1. Text - 文本组件")
        TextExamples()
        
        Divider()
        
        // 2. 按钮组件
        SectionTitle("2. Button - 按钮组件")
        ButtonExamples()
        
        Divider()
        
        // 3. 布局组件
        SectionTitle("3. Layout - 布局组件")
        LayoutExamples()
        
        Divider()
        
        // 4. 状态管理
        SectionTitle("4. State - 状态管理")
        StateExamples()
        
        Divider()
        
        // 5. Modifier使用
        SectionTitle("5. Modifier - 修饰符")
        ModifierExamples()
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
private fun TextExamples() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("普通文本")
            Text(
                text = "带样式的文本",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "彩色文本",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun ButtonExamples() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // 普通按钮
            Button(onClick = { /* 点击事件 */ }) {
                Text("普通按钮")
            }
            
            // 文本按钮
            TextButton(onClick = { /* 点击事件 */ }) {
                Text("文本按钮")
            }
            
            // 轮廓按钮
            OutlinedButton(onClick = { /* 点击事件 */ }) {
                Text("轮廓按钮")
            }
        }
    }
}

@Composable
private fun LayoutExamples() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Column - 垂直布局", fontWeight = FontWeight.Bold)
            Column {
                Text("项目 1")
                Text("项目 2")
                Text("项目 3")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("Row - 水平布局", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("项目 1")
                Text("项目 2")
                Text("项目 3")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("Box - 堆叠布局", fontWeight = FontWeight.Bold)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text("居中文本", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun StateExamples() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // remember - 记住状态
            var count by remember { mutableStateOf(0) }
            
            Text("点击计数: $count")
            Button(onClick = { count++ }) {
                Text("点击增加")
            }
            
            // TextField示例
            var text by remember { mutableStateOf("") }
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("输入文本") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ModifierExamples() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Modifier链式调用示例:")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Blue.copy(alpha = 0.3f))
                    .padding(8.dp)
            ) {
                Text("fillMaxWidth + height + background + padding")
            }
        }
    }
}
