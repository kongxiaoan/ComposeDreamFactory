package com.kongxiaoan.composedreamfactory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ArchitectureScreen - 架构示例
 * 
 * 展示MVVM架构在Compose中的应用
 * 学习要点：
 * 1. ViewModel - 视图模型
 * 2. State管理
 * 3. 单向数据流
 * 4. Repository模式
 */
@Composable
fun ArchitectureScreen(
    viewModel: UserViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "MVVM 架构示例",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Divider()
        
        // 架构说明
        ArchitectureDescription()
        
        Divider()
        
        // 用户列表示例
        SectionTitle("实际应用示例")
        UserListContent(
            uiState = uiState,
            onRefresh = { viewModel.loadUsers() }
        )
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
private fun ArchitectureDescription() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("MVVM架构层次:", fontWeight = FontWeight.Bold)
            
            Text("• View (UI层) - Composable函数")
            Text("• ViewModel (视图模型) - 管理UI状态")
            Text("• Model (数据层) - Repository + DataSource")
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("关键概念:", fontWeight = FontWeight.Bold)
            Text("• 单向数据流 - UI事件 → ViewModel → UI状态")
            Text("• 状态管理 - StateFlow/State")
            Text("• 生命周期感知")
        }
    }
}

@Composable
private fun UserListContent(
    uiState: UserUiState,
    onRefresh: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("用户列表", fontWeight = FontWeight.Bold)
                IconButton(onClick = onRefresh) {
                    Icon(Icons.Filled.Refresh, contentDescription = "刷新")
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null -> {
                    Text(
                        text = "错误: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        uiState.users.forEach { user ->
                            UserItem(user)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserItem(user: User) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = user.role,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

// ====== ViewModel 层 ======

/**
 * UserViewModel - 用户视图模型
 * 
 * 负责管理UI状态和业务逻辑
 */
class UserViewModel : ViewModel() {
    private val repository = UserRepository()
    
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState
    
    init {
        loadUsers()
    }
    
    fun loadUsers() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        
        // 模拟网络请求
        viewModelScope.launch {
            try {
                kotlinx.coroutines.delay(1000)
                val users = repository.getUsers()
                _uiState.value = UserUiState(users = users, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = UserUiState(error = e.message, isLoading = false)
            }
        }
    }
}

/**
 * UserUiState - UI状态
 */
data class UserUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// ====== Model 层 ======

/**
 * User - 数据模型
 */
data class User(
    val id: Int,
    val name: String,
    val role: String
)

/**
 * UserRepository - 数据仓库
 * 
 * 负责数据获取和缓存
 */
class UserRepository {
    fun getUsers(): List<User> {
        // 模拟数据
        return listOf(
            User(1, "张三", "Android 工程师"),
            User(2, "李四", "iOS 工程师"),
            User(3, "王五", "Web 工程师"),
            User(4, "赵六", "后端工程师"),
            User(5, "钱七", "测试工程师")
        )
    }
}
