# MVVM 架构指南

## 什么是 MVVM？

MVVM (Model-View-ViewModel) 是一种软件架构模式，特别适合用于构建用户界面。在 Android 开发中，结合 Jetpack Compose 使用 MVVM 可以实现清晰的关注点分离。

## 架构层次

```
┌─────────────────────────────────────┐
│         View (UI Layer)             │
│    Jetpack Compose Screens          │
└──────────────┬──────────────────────┘
               │ 观察状态
               │ 发送事件
┌──────────────▼──────────────────────┐
│       ViewModel (Presentation)      │
│    管理 UI 状态和业务逻辑            │
└──────────────┬──────────────────────┘
               │ 调用方法
               │ 获取数据
┌──────────────▼──────────────────────┐
│      Model (Data Layer)             │
│   Repository + DataSource           │
└─────────────────────────────────────┘
```

## 各层职责

### 1. View 层 (UI Layer)

**职责**:
- 展示 UI
- 观察 ViewModel 的状态
- 将用户事件发送给 ViewModel
- 不包含业务逻辑

**示例**:
```kotlin
@Composable
fun UserListScreen(
    viewModel: UserViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // UI 根据状态渲染
    when {
        uiState.isLoading -> LoadingView()
        uiState.error != null -> ErrorView(uiState.error)
        else -> UserList(uiState.users)
    }
    
    // 用户事件发送给 ViewModel
    Button(onClick = { viewModel.loadUsers() }) {
        Text("刷新")
    }
}
```

### 2. ViewModel 层 (Presentation Layer)

**职责**:
- 管理 UI 状态
- 处理业务逻辑
- 调用 Repository 获取数据
- 生命周期感知（比 Activity/Fragment 生命周期长）

**示例**:
```kotlin
class UserViewModel : ViewModel() {
    private val repository = UserRepository()
    
    // 私有可变状态
    private val _uiState = MutableStateFlow(UserUiState())
    // 公开只读状态
    val uiState: StateFlow<UserUiState> = _uiState
    
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val users = repository.getUsers()
                _uiState.value = UserUiState(users = users)
            } catch (e: Exception) {
                _uiState.value = UserUiState(error = e.message)
            }
        }
    }
}

data class UserUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
```

### 3. Model 层 (Data Layer)

**职责**:
- 封装数据访问逻辑
- 提供统一的数据接口
- 处理数据缓存
- 协调多个数据源

**示例**:
```kotlin
// Repository
class UserRepository(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) {
    suspend fun getUsers(): List<User> {
        // 先尝试从本地获取
        val cachedUsers = localDataSource.getUsers()
        if (cachedUsers.isNotEmpty()) {
            return cachedUsers
        }
        
        // 从远程获取
        val users = remoteDataSource.getUsers()
        // 缓存到本地
        localDataSource.saveUsers(users)
        return users
    }
}

// Data Source Interface
interface UserRemoteDataSource {
    suspend fun getUsers(): List<User>
}

interface UserLocalDataSource {
    suspend fun getUsers(): List<User>
    suspend fun saveUsers(users: List<User>)
}
```

## 数据流向

### 单向数据流 (Unidirectional Data Flow)

```
UI Event → ViewModel → Repository → DataSource
                ↓
            State Update
                ↓
            UI Update
```

1. **用户交互** → View 发送事件到 ViewModel
2. **处理事件** → ViewModel 调用 Repository
3. **获取数据** → Repository 从 DataSource 获取数据
4. **更新状态** → ViewModel 更新 UI 状态
5. **UI 响应** → View 观察到状态变化，重新渲染

## 状态管理

### StateFlow vs LiveData

在 Compose 中推荐使用 StateFlow:

```kotlin
// StateFlow (推荐)
private val _uiState = MutableStateFlow(UiState())
val uiState: StateFlow<UiState> = _uiState

// 在 Compose 中使用
@Composable
fun MyScreen(viewModel: MyViewModel) {
    val state by viewModel.uiState.collectAsState()
}
```

### State vs Events

- **State (状态)**: UI 的当前状态，可以被观察
- **Events (事件)**: 一次性的动作，如导航、显示 Toast

```kotlin
// State - 持久的 UI 状态
data class UiState(
    val isLoading: Boolean = false,
    val data: List<Item> = emptyList()
)

// Event - 一次性事件
sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    object NavigateBack : UiEvent()
}
```

## 依赖注入

使用依赖注入可以提高代码的可测试性：

```kotlin
// 使用构造函数注入
class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {
    // ...
}

// 使用 Hilt (推荐)
@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    // ...
}
```

## 测试策略

### ViewModel 测试
```kotlin
@Test
fun `load users successfully`() = runTest {
    // Given
    val fakeRepository = FakeUserRepository()
    val viewModel = UserViewModel(fakeRepository)
    
    // When
    viewModel.loadUsers()
    
    // Then
    val state = viewModel.uiState.value
    assertTrue(state.users.isNotEmpty())
    assertFalse(state.isLoading)
}
```

### Repository 测试
```kotlin
@Test
fun `get users from cache when available`() = runTest {
    // Given
    val localDataSource = FakeLocalDataSource()
    val remoteDataSource = FakeRemoteDataSource()
    val repository = UserRepository(remoteDataSource, localDataSource)
    
    // When
    val users = repository.getUsers()
    
    // Then
    assertEquals(localDataSource.users, users)
    verify(remoteDataSource, never()).getUsers()
}
```

## 最佳实践

1. **ViewModel 不应持有 View 引用**: 防止内存泄漏
2. **使用 StateFlow 而非 LiveData**: 更适合 Compose
3. **保持 ViewModel 纯粹**: 不应包含 Android 框架依赖（除 ViewModel 基类）
4. **Repository 作为单一数据源**: 统一数据访问接口
5. **使用依赖注入**: 提高可测试性
6. **状态应该是不可变的**: 使用 data class 的 copy 方法
7. **处理配置更改**: ViewModel 会保留状态

## 常见问题

### Q: ViewModel 何时被销毁？
A: 当关联的 Activity 被销毁且不会重建时（如点击返回键）。

### Q: 如何在 ViewModel 之间共享数据？
A: 使用共享的 Repository 或 Application-scoped ViewModel。

### Q: ViewModel 可以访问 Context 吗？
A: 尽量避免。如需使用，使用 AndroidViewModel 并传入 Application context。

### Q: 如何处理一次性事件（如导航）？
A: 使用 Channel 或 SharedFlow，或者使用事件包装器。

## 进阶话题

### Clean Architecture

在 MVVM 基础上可以引入 Clean Architecture 的概念：

```
Presentation Layer (UI + ViewModel)
        ↓
Domain Layer (Use Cases)
        ↓
Data Layer (Repository + DataSource)
```

### Use Cases

```kotlin
class GetUsersUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<List<User>> {
        return try {
            Result.success(repository.getUsers())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

## 参考资源

- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Guide to app architecture](https://developer.android.com/jetpack/guide)
- [ViewModel Overview](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
