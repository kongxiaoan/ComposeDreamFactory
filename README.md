# ComposeDreamFactory
Compose 学习工厂 - 一个全面的 Jetpack Compose 学习项目

## 项目介绍

ComposeDreamFactory 是一个专注于 Jetpack Compose 学习的示例项目，涵盖了从基础语法到架构设计的完整内容。

## 学习内容

### 1. 基础语法 (Basic Syntax)
- **@Composable 注解**: 可组合函数的基础
- **Text 组件**: 文本显示和样式
- **Button 组件**: 按钮类型和使用
- **Layout 组件**: Column、Row、Box 布局
- **State 管理**: remember、mutableStateOf
- **Modifier**: 修饰符链式调用

### 2. API 示例 (API Examples)
- **Icon**: 图标组件使用
- **Card**: 卡片组件（Filled、Outlined、Elevated）
- **Checkbox & Switch**: 选择组件
- **LazyColumn**: 懒加载列表
- **TextField**: 文本输入

### 3. 常用模式 (Common Patterns)
- **State Hoisting**: 状态提升模式
- **Animation**: 动画系统
  - Size 动画
  - Visibility 动画
- **Side Effects**: 副作用处理
  - LaunchedEffect
  - DisposableEffect
- **可重用组件**: 组件设计最佳实践

### 4. 架构设计 (Architecture)
- **MVVM 架构**: Model-View-ViewModel
- **ViewModel**: 视图模型和状态管理
- **Repository**: 数据仓库模式
- **单向数据流**: UI事件和状态更新
- **StateFlow**: 响应式数据流

## 技术栈

- **Kotlin**: 1.9.0
- **Compose BOM**: 2023.10.01
- **Material3**: 最新版本
- **Navigation Compose**: 导航组件
- **Lifecycle**: 生命周期感知组件

## 项目结构

```
app/
├── src/main/java/com/kongxiaoan/composedreamfactory/
│   ├── MainActivity.kt              # 主入口
│   ├── ui/
│   │   ├── MainScreen.kt           # 主屏幕和导航
│   │   ├── theme/                  # 主题系统
│   │   │   ├── Color.kt           # 颜色定义
│   │   │   ├── Type.kt            # 文字排版
│   │   │   └── Theme.kt           # 主题配置
│   │   └── screens/               # 各个学习模块
│   │       ├── BasicSyntaxScreen.kt      # 基础语法
│   │       ├── ApiExamplesScreen.kt      # API示例
│   │       ├── CommonPatternsScreen.kt   # 常用模式
│   │       └── ArchitectureScreen.kt     # 架构设计
│   ├── data/                       # 数据层（Repository）
│   ├── domain/                     # 领域层（Use Cases）
│   └── presentation/               # 表现层（ViewModel）
```

## 架构设计

本项目采用 MVVM (Model-View-ViewModel) 架构：

### View 层
- 使用 Composable 函数构建 UI
- 观察 ViewModel 的状态
- 发送用户事件到 ViewModel

### ViewModel 层
- 管理 UI 状态（使用 StateFlow）
- 处理业务逻辑
- 与 Repository 交互

### Model 层
- Repository: 数据仓库，统一数据访问接口
- DataSource: 数据源（本地/网络）
- Entity: 数据实体

## 核心概念

### 1. 声明式 UI
```kotlin
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
```

### 2. 状态管理
```kotlin
var count by remember { mutableStateOf(0) }
Button(onClick = { count++ }) {
    Text("Count: $count")
}
```

### 3. 状态提升
```kotlin
@Composable
fun Parent() {
    var state by remember { mutableStateOf("") }
    Child(state = state, onStateChange = { state = it })
}
```

### 4. 副作用处理
```kotlin
LaunchedEffect(key1) {
    // 执行协程任务
}
```

## 如何使用

1. **克隆项目**
```bash
git clone https://github.com/kongxiaoan/ComposeDreamFactory.git
```

2. **打开项目**
   - 使用 Android Studio 打开项目
   - 等待 Gradle 同步完成

3. **运行项目**
   - 连接 Android 设备或启动模拟器
   - 点击运行按钮

4. **浏览学习内容**
   - 基础语法：学习 Compose 基础组件
   - API 示例：了解常用 API 使用
   - 常用模式：掌握开发模式
   - 架构设计：理解 MVVM 架构

## 学习路径建议

1. **第一步**: 从"基础语法"开始，理解 Composable 函数和基本组件
2. **第二步**: 学习"API 示例"，掌握常用组件的使用方法
3. **第三步**: 研究"常用模式"，了解状态管理和动画
4. **第四步**: 深入"架构设计"，学习 MVVM 架构实践

## 相关资源

- [Jetpack Compose 官方文档](https://developer.android.com/jetpack/compose)
- [Compose Pathway](https://developer.android.com/courses/pathways/compose)
- [Material Design 3](https://m3.material.io/)
- [架构参考仓库](https://github.com/kongxiaoan/Architecture)

## 贡献

欢迎提交 Issue 和 Pull Request 来完善这个学习项目！

## License

MIT License

