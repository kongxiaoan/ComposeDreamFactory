# 项目结构可视化

## 📁 目录结构

```
ComposeDreamFactory/
│
├── 📄 README.md                          # 项目介绍和使用指南
├── 📄 .gitignore                         # Git 忽略文件配置
├── 📄 settings.gradle.kts                # Gradle 设置
├── 📄 build.gradle.kts                   # 项目级 Gradle 配置
├── 📄 gradle.properties                  # Gradle 属性配置
│
├── 📂 docs/                              # 文档目录
│   ├── 📄 LEARNING_NOTES.md             # Compose 学习笔记
│   ├── 📄 ARCHITECTURE.md               # MVVM 架构详细指南
│   └── 📄 PROJECT_SUMMARY.md            # 项目总结
│
├── 📂 gradle/wrapper/                    # Gradle Wrapper
│   └── 📄 gradle-wrapper.properties     
│
└── 📂 app/                               # 应用模块
    ├── 📄 build.gradle.kts              # 应用级 Gradle 配置
    ├── 📄 proguard-rules.pro            # ProGuard 规则
    │
    └── 📂 src/main/
        ├── 📄 AndroidManifest.xml       # Android 清单文件
        │
        ├── 📂 res/                       # 资源文件
        │   ├── 📂 values/
        │   │   ├── strings.xml          # 字符串资源
        │   │   ├── themes.xml           # 主题配置
        │   │   └── ic_launcher_background.xml
        │   ├── 📂 drawable/
        │   │   └── ic_launcher_foreground.xml
        │   └── 📂 mipmap-anydpi-v26/
        │       ├── ic_launcher.xml
        │       └── ic_launcher_round.xml
        │
        └── 📂 java/com/kongxiaoan/composedreamfactory/
            │
            ├── 📄 MainActivity.kt        # 应用入口
            │
            ├── 📂 ui/                    # UI 层
            │   ├── 📄 MainScreen.kt     # 主屏幕和导航
            │   │
            │   ├── 📂 theme/            # 主题系统
            │   │   ├── 📄 Color.kt      # 颜色定义
            │   │   ├── 📄 Type.kt       # 文字排版
            │   │   └── 📄 Theme.kt      # 主题配置
            │   │
            │   ├── 📂 screens/          # 各个学习模块屏幕
            │   │   ├── 📄 BasicSyntaxScreen.kt      # 基础语法模块
            │   │   ├── 📄 ApiExamplesScreen.kt      # API 示例模块
            │   │   ├── 📄 CommonPatternsScreen.kt   # 常用模式模块
            │   │   └── 📄 ArchitectureScreen.kt     # 架构设计模块
            │   │
            │   └── 📂 components/        # 可重用组件（待扩展）
            │
            ├── 📂 data/                  # 数据层（待扩展）
            ├── 📂 domain/                # 领域层（待扩展）
            └── 📂 presentation/          # 表现层（待扩展）
```

## 🏗️ 架构图

```
┌─────────────────────────────────────────────────────────┐
│                      UI Layer                           │
│                                                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐ │
│  │ Basic Syntax │  │ API Examples │  │ Common       │ │
│  │   Screen     │  │    Screen    │  │  Patterns    │ │
│  └──────────────┘  └──────────────┘  └──────────────┘ │
│                                                         │
│  ┌──────────────┐         ┌──────────────────────┐    │
│  │Architecture  │         │   Navigation &       │    │
│  │   Screen     │         │   Theme System       │    │
│  └──────────────┘         └──────────────────────┘    │
└────────────────┬────────────────────────────────────────┘
                 │
                 │ StateFlow / Events
                 │
┌────────────────▼────────────────────────────────────────┐
│                  ViewModel Layer                        │
│                                                         │
│  ┌──────────────────────────────────────────────────┐  │
│  │            UserViewModel                         │  │
│  │  • UI State Management (StateFlow)              │  │
│  │  • Business Logic                               │  │
│  │  • Lifecycle Awareness                          │  │
│  └──────────────────────────────────────────────────┘  │
└────────────────┬────────────────────────────────────────┘
                 │
                 │ Method Calls
                 │
┌────────────────▼────────────────────────────────────────┐
│                   Model Layer                           │
│                                                         │
│  ┌──────────────────────────────────────────────────┐  │
│  │          UserRepository                          │  │
│  │  • Data Access Abstraction                      │  │
│  │  • Caching Logic                                │  │
│  │  • Multiple Data Sources Coordination           │  │
│  └──────────────────────────────────────────────────┘  │
│                                                         │
│  ┌───────────────┐           ┌───────────────┐        │
│  │  Remote Data  │           │  Local Data   │        │
│  │    Source     │           │    Source     │        │
│  └───────────────┘           └───────────────┘        │
└─────────────────────────────────────────────────────────┘
```

## 🔄 数据流

```
┌──────────┐
│  User    │ 用户交互
│ Action   │
└────┬─────┘
     │
     │ onClick/onValueChange
     ▼
┌──────────────────┐
│   UI (Compose)   │ 发送事件
└────┬─────────────┘
     │
     │ viewModel.action()
     ▼
┌──────────────────┐
│   ViewModel      │ 处理逻辑
└────┬─────────────┘
     │
     │ repository.getData()
     ▼
┌──────────────────┐
│   Repository     │ 获取数据
└────┬─────────────┘
     │
     │ 返回数据
     ▼
┌──────────────────┐
│   ViewModel      │ 更新状态
│  _uiState.value  │
└────┬─────────────┘
     │
     │ StateFlow.collectAsState()
     ▼
┌──────────────────┐
│   UI (Compose)   │ 重新渲染
│   Recomposition  │
└──────────────────┘
```

## 📱 屏幕结构

```
┌─────────────────────────────────────┐
│      Top App Bar                    │
│   "Compose Dream Factory"           │
├─────────────────────────────────────┤
│                                     │
│                                     │
│          Content Area               │
│     (当前选中的 Screen)              │
│                                     │
│                                     │
│                                     │
├─────────────────────────────────────┤
│    Bottom Navigation Bar            │
│  ┌────┬────┬────┬────┐             │
│  │基础│API │模式│架构│             │
│  │语法│示例│    │    │             │
│  └────┴────┴────┴────┘             │
└─────────────────────────────────────┘
```

## 🎨 主题系统

```
ComposeDreamFactoryTheme
    │
    ├── ColorScheme
    │   ├── Light Colors
    │   │   ├── primary: Purple40
    │   │   ├── secondary: PurpleGrey40
    │   │   └── tertiary: Pink40
    │   │
    │   └── Dark Colors
    │       ├── primary: Purple80
    │       ├── secondary: PurpleGrey80
    │       └── tertiary: Pink80
    │
    └── Typography
        ├── displayLarge (57sp, Bold)
        ├── titleLarge (22sp, SemiBold)
        ├── bodyLarge (16sp, Normal)
        └── labelMedium (12sp, Medium)
```

## 🧩 组件层次

```
MainActivity
    └── ComposeDreamFactoryTheme
            └── Surface
                    └── MainScreen
                            ├── Scaffold
                            │   ├── TopAppBar
                            │   ├── BottomNavigationBar
                            │   └── NavHost
                            │       ├── BasicSyntaxScreen
                            │       ├── ApiExamplesScreen
                            │       ├── CommonPatternsScreen
                            │       └── ArchitectureScreen
                            │           └── UserViewModel
                            │               └── UserRepository
```

## 📚 学习路径

```
Step 1: 基础语法
    └── 学习 Composable、State、Modifier
            │
            ▼
Step 2: API 示例
    └── 掌握常用组件使用
            │
            ▼
Step 3: 常用模式
    └── 理解状态管理、动画、副作用
            │
            ▼
Step 4: 架构设计
    └── 实践 MVVM 架构
```

## 🎯 核心概念映射

| 传统 Android View | Jetpack Compose |
|------------------|-----------------|
| XML Layout       | @Composable 函数 |
| findViewById     | remember + State |
| RecyclerView     | LazyColumn/Row  |
| Fragment         | Composable Screen |
| ViewModel        | ViewModel (相同) |
| LiveData         | StateFlow       |
| onClick listener | onClick lambda  |
| Styles/Themes    | MaterialTheme   |

## 🔧 扩展点

项目预留了以下扩展点：

1. **ui/components/** - 添加自定义可重用组件
2. **data/** - 实现数据层（Room、Retrofit）
3. **domain/** - 添加 Use Cases（Clean Architecture）
4. **presentation/** - 添加更多 ViewModels

## 📖 使用建议

1. **初学者**：按顺序浏览四个模块
2. **有经验者**：直接查看架构模块
3. **实践者**：在各模块基础上添加新功能
4. **教学者**：使用文档和代码注释作为教材
