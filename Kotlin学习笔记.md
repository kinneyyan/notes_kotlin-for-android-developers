## 一、类和函数
### 怎么定义一个类
```kotlin
class Person(name: String, surname: String)
```
构造函数体
```kotlin
class Person(name: String, surname: String) {
    init {
        ...
    }
}
```
### 类继承
默认任何类都是基础继承自**Any**（与java中的Object类似），所有的类默认不可继承（final），只能继承那些明确声明 **open** 或 **abstract** 的类：
```kotlin
open class Animal(name: String)
class Person(name: String, surname: String): Animal(name)
```
### 函数
不指定返回值（会返回Unit，与Java的void类似）
```kotlin
fun onCreate(savedInstanceState: Bundle?) {
}
```
指定返回值
```kotlin
fun add(x: Int, y: Int): Int {
    return x + y
}
```
如果返回的结果可以使用一个表达式计算出来，你可以不使用括号而是使用等号
```kotlin
fun add(x: Int, y: Int): Int = x + y
```
### 构造方法和函数参数
我们可以给参数指定一个默认值使得它们变得可选，作用相当于重载函数：
```kotlin
fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}
```
*String模版（类似shell中的 $+变量名）*
```kotlin
var name = "kinney"
"Your name is $name"
//如果表达式有一点复杂，可以使用一对大括号括起来
"Your name is ${user.name}"
```
*对象实例化的区别*

Java | Kotlin
------- | -------
`Person person = new Person("kinney", "yan")` | var person = Person("kinney", "yan")

---

## 二、变量和属性
在Kotlin中，一切都是对象。没有像Java中那样的原始基本类型。
### 基本类型
- 数字类型不会自动转型，必须要做明确的类型转换：

```kotlin
val i:Int = 7
val d:Double = i.toDouble()
```
- Char不能直接作为一个数字来处理：

```kotlin
val c:Char = 'c'
val i:Int = c.toInt()
```
- 位运算的区别：

```kotlin
//Java
int bitwiseOr = FLAG1 | FLAG2;
int bitwiseAnd = FLAG1 | FLAG2;
//Kotlin
val bitwiseOr = FLAG1 or FLAG2
val bitwiseAnd = FLAG1 and FLAG2
```
- 类型不用明确指明，可以让编译器自己去推断具体的类型：

```kotlin
val i = 12 //int
val iHex = 0x0f //一个十六进制的Int类型
val l = 3L //long
val d = 3.5 //double
val f = 3.5F //float
```
- 一个String可以像数组那样访问，并且被迭代

```kotlin
val s = "example"
val c = s[2] //获取字符'a'
//迭代String
for (c in s) {
    print(c)
}
```
### 变量
变量可以很简单地定义成可变（var）和不可变（val）的变量。这个与Java中使用的final类似。**一个重要的概念是：尽可能地使用val**。
### 属性
```kotlin
public class Person {
    var name:String = ""
}
val person = Person()
person.name = "kinney"
val name = person.name
```
若没有任何指定，属性会默认使用getter和setter。当然也可以修改为自定义的代码，并不修改已存在的代码：
```kotlin
public class Person {
    var name: String = ""
        //下面的field为预留字段，它会被编译器找到正在使用的并自动创建，只能在属性访问器内访问
        get() = field.toLowerCase()
        set(value) {
            field = "Name:$value"
        }
}
```
### Anko库
### 扩展函数
扩展函数数是指在一个类上增加一种新的行为，甚至我们没有这个类代码的访问权限。这是一个在缺少有用函数的类上扩展的方法。

以下这个函数可以被任何Context或它的子类调用：
```kotlin
fun Context:toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
```
然后这个函数就可以在Activity中直接调用：
```kotlin
toast("Hello world!")
toast("Hello world!", Toast.LENGTH_LONG)
```
扩展函数**也可以是一个属性**，下面的例子展示了使用TextView自己的getter/setter生成一个属性的方式。（其实Kotlin已经提供了这个属性）
```kotlin
public var TextView.text: CharSequence
    get() = getText()
    set(v) = setText(v)
```
扩展函数**并不是真正地修改了原来的类**，它是以**静态导入**的方式来实现的。扩展函数可以被声明在任何文件中，因此有个通用的实践是把一系列有关的函数放在一个新建的文件里。

---

## 数据类
数据类可以让我们非常方便地定义一个Java中常见的POJO类：
```kotlin
data class Forecast(val date: Date, val temperature: Float, val details: String)
```
### 复制一个数据类
使用Kotlin来复制一个不可变的对象也十分简介：
```kotlin
val f1 = Forecast(Date(), 27.5f, "Shiny day")
val f2 = f1.copy(temperature = 30f)
```
如上，我们拷贝了第一个对象，然后只修改了temperature的属性而没有修改这个对象的其他属性。
### 映射对象到变量中
映射对象的每一个属性到一个变量中去，这个过程其实就是声明多个变量。
```kotlin
val f1 = Forecast(Date(), 27.5f, "Shiny day")
val(date, temperature, details) = f1
```
上面的代码会被编译成下面的代码：
```kotlins
val date = f1.component1()
val temperature = f1.component2()
val details = f1.component3()
```
这个特性在很多情况下帮助我们简化代码。比如Map类含有一些扩展函数的实现，允许它在迭代时使用key和value：
```kotlin
for ((key, value) in map) {
    Log.d("map", "key:$key, value:$value")
}
```
*Companion objects*
> Kotlin允许我们去定义一些行为与静态对象一样的对象。尽管这些对象可以用众所周知的模式来实现，比如容易实现的单例模式。
我们需要一个类里面有一些静态的属性、常量或者函数，我们可以使用companion objecvt。这个对象被这个类的所有对象所共享，就像Java中的静态属性或者方法。

*with函数*
> with是一个非常有用的函数，它包含在Kotlin的标准库中。它接收一个对象和一个扩展函数作为它的参数，然后使这个对象扩展这个函数。这表示所有我们在括号中编写的代码都是作为对象（第一个参数）的一个扩展函数，我们可以就像作为this一样使用所有它的public方法和属性。当我们针对同一个对象做很多操作的时候这个非常有利于简化代码。
```kotlin
with(list[position]) {
    holder.textView.text = "$date - $description - $high/$low"
    //date、description都是list取出的对象的属性
}
```

