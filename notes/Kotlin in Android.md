## Kotlin in Android
### 1.Kotlin Android Extensions P188
这个是另一个Kotlin团队研发的的插件。目前仅包括了view的绑定。这个插件自动创建了很多的属性来让我们直接访问XML中的view，所以也就不需要我们写一堆findViewById的代码了。

**这些属性的名字就是来自对应的view的id**，所以我们给view取id的时候要注意，**不使用下划线了，建议使用驼峰命名**。这些属性的类型也是来自XML中的，所以我们不需要去进行额外的类型转换。

Kotlin Android Extensions的一个优点是它不需要在我们的代码中依赖其它额外的库。它仅仅由插件组层，需要时用于生成工作所需的代码，只需要依赖于Kotlin的标准库。

#### 如何使用Kotlin Android Extensions
- 项目根目录的`build.gradle`：

```gradle
buildscript {
    ext.kotlin_version = '1.1.51'
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.0'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
}
```
- module层的`build.gradle`：

```gradle
apply plugin: 'kotlin-android-extensions'
```
然后我们就可以在类中“手工”import来使用这个功能：

我们需要使用的import语句以`kotlin.android.synthetic`开头，然后加上我们要绑定到Activity的布局XML的名字：
```kotlin
import kotlinx.android.synthetic.activity_main.*
```
此后，我们就可以在`setContentView`调用后直接通过id访问这些view了。

### 2.Application单例化 P222

### 3.使用anko的ManagedSQLiteOpenHelper创建数据库 P225


