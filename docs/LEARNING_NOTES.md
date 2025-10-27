# Compose 学习笔记

## 基础概念

### 1. 什么是 Jetpack Compose？

Jetpack Compose 是 Android 的现代声明式 UI 工具包。使用 Compose，您可以使用更少的代码、强大的工具和直观的 Kotlin API 来构建 UI。

### 2. 声明式 vs 命令式

**命令式 UI (传统 XML)**:
```kotlin
// 需要手动查找和更新视图
val textView = findViewById<TextView>(R.id.textView)
textView.text = "Hello World"
```

**声明式 UI (Compose)**:
```kotlin
// UI 随状态自动更新
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name")
}
```

## 核心组件

### Text - 文本组件
```kotlin
Text(
    text = "Hello Compose",
    style = MaterialTheme.typography.headlineMedium,
    color = Color.Blue
)
```

### Button - 按钮组件
```kotlin
Button(onClick = { /* 处理点击 */ }) {
    Text("Click Me")
}
```

### Column / Row / Box - 布局组件
```kotlin
// 垂直布局
Column {
    Text("Item 1")
    Text("Item 2")
}

// 水平布局
Row {
    Text("Item 1")
    Text("Item 2")
}

// 堆叠布局
Box {
    Text("Background")
    Text("Foreground")
}
```

## 状态管理

### remember - 记住状态
```kotlin
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    
    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}
```

### 状态提升 (State Hoisting)
```kotlin
@Composable
fun Parent() {
    var text by remember { mutableStateOf("") }
    Child(
        text = text,
        onTextChange = { text = it }
    )
}

@Composable
fun Child(
    text: String,
    onTextChange: (String) -> Unit
) {
    TextField(value = text, onValueChange = onTextChange)
}
```

## Modifier - 修饰符

Modifier 用于修饰 Composable 的外观和行为：

```kotlin
Box(
    modifier = Modifier
        .fillMaxWidth()        // 填充宽度
        .height(100.dp)        // 设置高度
        .background(Color.Blue) // 背景色
        .padding(16.dp)        // 内边距
        .clickable { }         // 点击事件
)
```

## 列表

### LazyColumn - 垂直滚动列表
```kotlin
LazyColumn {
    items(users) { user ->
        UserCard(user)
    }
}
```

### LazyRow - 水平滚动列表
```kotlin
LazyRow {
    items(images) { image ->
        ImageCard(image)
    }
}
```

## 导航

### Navigation Compose
```kotlin
val navController = rememberNavController()

NavHost(navController, startDestination = "home") {
    composable("home") { HomeScreen() }
    composable("detail") { DetailScreen() }
}

// 导航到其他屏幕
navController.navigate("detail")
```

## 主题

### MaterialTheme
```kotlin
MaterialTheme(
    colorScheme = lightColorScheme,
    typography = Typography,
    content = content
)
```

### 自定义颜色
```kotlin
val MyColors = lightColorScheme(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC5)
)
```

## 副作用 (Side Effects)

### LaunchedEffect
```kotlin
LaunchedEffect(key1) {
    // 当 key1 改变时重新执行
    delay(1000)
    doSomething()
}
```

### DisposableEffect
```kotlin
DisposableEffect(Unit) {
    val listener = MyListener()
    registerListener(listener)
    
    onDispose {
        unregisterListener(listener)
    }
}
```

## 动画

### animate*AsState
```kotlin
val size by animateDpAsState(
    targetValue = if (expanded) 100.dp else 50.dp
)

Box(modifier = Modifier.size(size))
```

### AnimatedVisibility
```kotlin
AnimatedVisibility(visible = isVisible) {
    Text("Hello!")
}
```

## 最佳实践

1. **保持 Composable 纯函数**: 不应有副作用
2. **状态提升**: 让组件无状态，方便重用
3. **使用 remember**: 避免重组时重新创建对象
4. **合理使用 LaunchedEffect**: 处理异步操作
5. **Modifier 顺序很重要**: 从左到右应用

## 性能优化

1. **避免不必要的重组**: 使用 `remember` 和 `derivedStateOf`
2. **使用 `key` 参数**: 帮助 Compose 识别列表项
3. **避免在 Composable 中创建新对象**: 使用 `remember`
4. **使用 LazyColumn/LazyRow**: 而不是 ScrollableColumn

## 常见问题

### Q: Composable 何时重组？
A: 当其依赖的状态改变时。

### Q: 如何在 Composable 中使用协程？
A: 使用 `LaunchedEffect` 或 `rememberCoroutineScope`。

### Q: 如何访问 Context？
A: 使用 `LocalContext.current`。

### Q: 如何优化列表性能？
A: 使用 `LazyColumn` 并提供稳定的 `key`。
