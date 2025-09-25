package com.jiang;

import com.jiang.utils.SnowflakeIdConfig;

import java.lang.reflect.InvocationTargetException;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {

    public static void main(String[] args)  {
        //TIP 当文本光标位于高亮显示的文本处时按 <shortcut actionId="ShowIntentionActions"/>
        // 查看 IntelliJ IDEA 建议如何修正。
        System.out.println("Hello and welcome!");

        // 创建一个雪花算法生成器，传入机器ID和数据中心ID
        SnowflakeIdConfig idGenerator = new SnowflakeIdConfig(0,0);
        // 生成10个唯一ID并打印
        // 这里使用多线程来模拟并发生成ID的场景
        // 每个线程生成一个唯一ID并打印
        // 通过 SnowflakeIdGenerator 类的 generateUniqueId 方法生成唯一ID
        // 该方法使用雪花算法生成唯一ID，确保在分布式环境中不会产生重复ID
        // 生成的唯一ID是一个长整型数值
        // 该数值由时间戳、机器ID、数据中心ID和序列号组成
        // 时间戳部分占用41位，机器ID占用10位，数据中心ID占用10位，序列号占用12位
        // 通过位运算将这些部分组合成一个唯一的长整型数值
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                long uniqueId = idGenerator.generateUniqueId(); // 生成唯一ID
                System.out.println("Generated Unique ID: " + uniqueId); // 打印生成的唯一ID
            }).start();
        }
        Main main = new Main();
        System.err.println("Running JDK 8 new features demonstration...");
        main.exampleMethod();
//        System.err.println("Running JDK 9-24 new features demonstration...");
//        main.jdk24NewFeature();
    }

    //TIP 要<b>运行</b>此方法，请将光标放在方法名上并按 <shortcut actionId="RunContextConfiguration"/>
    // 或在方法名上单击鼠标右键并选择 <b>Run 'exampleMethod()'</b>。
    /**
     * 演示 JDK 8 的新特性
     * 包括 Lambda 表达式、Stream API、新的日期时间 API、Optional 类等
     */
    //TIP 要<b>调试</b>此方法，请将光标放在方法名上并按 <shortcut actionId="DebugContextConfiguration"/>
    // 或在方法名上单击鼠标右键并选择 <b>Debug 'exampleMethod()'</b>
    //TIP 要<b>运行</b>此方法，请将光标放在方法名上并按 <shortcut actionId="RunContextConfiguration"/>
    // 或在方法名上单击鼠标右键并选择 <b>Run 'exampleMethod()'</b>。
    public  void exampleMethod()  {
        /*
         jdk8的新特性演示
         JDK 8 相对于 JDK 7 的主要新特性演示
         Lambda 表达式
        */
        java.util.List<String> names = java.util.Arrays.asList("Alice", "Bob", "Charlie");
        names.forEach(System.out::print);
        // Stream API
        java.util.List<Integer> numbers = java.util.Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
                         .filter(n -> n % 2 == 0)// 过滤偶数
                            .mapToInt(Integer::intValue)// 转换为整数
                            .sum();// 求和
        System.out.println("Sum of even numbers: " + sum);
        // 新的日期时间 API
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate birthday = java.time.LocalDate.of(1990, 1, 1);
        java.time.Period age = java.time.Period.between(birthday, today);
        System.out.println("Age: " + age.getYears() + " years, " + age.getMonths() + " months, " + age.getDays() + " days");
        // Optional 类
        java.util.Optional<String> optionalName = java.util.Optional.empty();
        optionalName.ifPresentOrElse(
            name -> System.out.println("Name: " + name),
            () -> System.out.println("Name is not provided")
        );
        // 方法引用
        java.util.List<String> upperCaseNames = names.stream()
                                                        .map(String::toUpperCase)
                                                        .toList();
        System.out.println("Uppercase names: " + upperCaseNames);
        // 默认方法和静态方法
        java.util.Comparator<String> comparator = String::compareToIgnoreCase;
        names.sort(comparator);
        System.out.println("Sorted names: " + names);
        // 重复注解
        @java.lang.SuppressWarnings("unchecked")
        class Example {}
        // 类型注解
        java.util.List<String> deprecatedNames = java.util.Arrays.asList("OldName1", "OldName2");
        for (String deprecatedName : deprecatedNames) {
            System.out.println("Deprecated name: " + deprecatedName);
        }
        deprecatedNames.forEach(System.out::println);

        // CompletableFuture
        java.util.concurrent.CompletableFuture.supplyAsync(() -> "Hello")
            .thenAccept(greeting -> System.out.println(greeting + " from CompletableFuture!"));
        // 新的类型推断
        java.util.function.Function<String, Integer> stringToLength = String::length;
        int length = stringToLength.apply("Hello, World!");
        System.out.println("Length of 'Hello, World!': " + length);
        // 新的接口和类
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        String encoded = encoder.encodeToString("Hello, World!".getBytes());
        System.out.println("Base64 Encoded: " + encoded);
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        String decoded = new String(decoder.decode(encoded));
        System.out.println("Base64 Decoded: " + decoded);
        // 新的注解
        @java.lang.FunctionalInterface
        interface MyFunctionalInterface {
            void myMethod();
        }
        MyFunctionalInterface func = () -> System.out.println("Functional Interface Method");
        try {
            func.getClass().getDeclaredMethods()[0].invoke(func);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        // 新的集合工厂方法
        java.util.List<String> list = java.util.List.of("A", "B", "C");
        System.out.println("Immutable List: " + list);
        java.util.Set<String> set = java.util.Set.of("X", "Y", "Z");
        System.out.println("Immutable Set: " + set);
        java.util.Map<String, Integer> map = java.util.Map.of("One", 1, "Two", 2, "Three", 3);
        System.out.println("Immutable Map: " + map);
        // 新的 JDK 工具
        // jjs 命令行工具用于执行 JavaScript 脚本
        // jdeps 命令行工具用于分析 Java 模块依赖关系
        // jlink 命令行工具用于创建自定义的运行时映像
        // jlink --module-path $JAVA_HOME/jmods --add-modules java.base,java.logging --output myapp
        // 这将创建一个包含 java.base 和 java.logging 模块的自定义运行
        // 时映像，输出目录为 myapp
        // jcmd 命令行工具用于诊断和管理 Java 应用程序
        // jcmd <pid> VM.system_properties
        // 这将显示指定进程 ID 的 Java 虚拟机系统属性
        // Nashorn JavaScript 引擎
        try {
            javax.script.ScriptEngineManager manager = new javax.script.ScriptEngineManager();
            javax.script.ScriptEngine engine = manager.getEngineByName("nashorn");
            System.out.println("Executing JavaScript with Nashorn:"+engine);
            //engice==null解决一下
            if (engine == null) {
                throw new RuntimeException("Nashorn engine not found. Ensure you're using a compatible JDK version.");
            }
            // 执行简单的 JavaScript 代码
            engine.eval("print('Hello from Nashorn!');");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }






    //TIP 要<b>运行</b>此方法，请将光标放在方法名上并按 <shortcut actionId="RunContextConfiguration"/>
    // 或在方法名上单击鼠标右键并选择 <b>Run 'jdk24NewFeature()'</b>。
    /**
     * 演示 JDK 9-24 的新特性
     * 包括模块系统、局部变量类型推断、字符串新方法、Switch 表达式等
     */
    public  void jdk24NewFeature() {
        // JDK 9-24 相对于 JDK 8 的主要新特性演示
        // JDK 9: 模块系统 (Project Jigsaw)
        // module-info.java 文件定义模块依赖关系
        // 示例模块定义
        // module com.example.myapp {
        //     requires java.base; // 依赖于 java.base 模块
        //     exports com.example.myapp; // 导出 com.example.myapp 包
        // }
        // JDK 9: 私有接口方法
        interface MyInterface {
            void publicMethod();

            private void privateMethod() {
                System.out.println("这是一个私有接口方法");
            }

            default void defaultMethod() {
                privateMethod(); // 调用私有方法
                System.out.println("这是一个默认方法");
            }

        }
        class MyClass implements MyInterface {
            @Override
            public void publicMethod() {
                System.out.println("这是一个公共方法");
            }
        }

        MyClass myClass = new MyClass();
        myClass.publicMethod(); // 调用公共方法
        myClass.defaultMethod(); // 调用默认方法
        // JDK 10: 局部变量类型推断 (var)
        var list = java.util.List.of("Java", "Kotlin", "Scala");
        var map = java.util.Map.of("key1", "value1", "key2", "value2");
        System.out.println("List: " + list);
        System.out.println("Map: " + map);
        // JDK 11: 字符串新方法
        String text = "  Hello World  ";
        System.out.println(text.strip()); // 去除首尾空白
        System.out.println(text.isBlank()); // 检查是否为空白
        System.out.println("Java".repeat(3)); // 重复字符串
        // JDK 12: Switch 表达式 (预览)
        int day = 3;
        String dayName = switch (day) {
            case 1 -> "星期一";
            case 2 -> "星期二";
            case 3 -> "星期三";
            case 4 -> "星期四";
            case 5 -> "星期五";
            case 6 -> "星期六";
            case 7 -> "星期日";
            default -> throw new IllegalArgumentException("无效的天数: " + day);
        };
        System.out.println("今天是: " + dayName);
        // JDK 13: 文本块 (Text Blocks) (预览)
        String html = """
            <html>
                <body>
                    <h1>Hello, World!</h1>
                </body>
            </html>
            """;
        System.out.println("HTML 内容:\n" + html);
        // JDK 14: NullPointerException 改进
        String nullableString = null;
        try {
            System.out.println(nullableString.length());
        } catch (NullPointerException e) {
            System.err.println("发生了 NullPointerException: " + e.getMessage());
        }
        // JDK 14: 新的 switch 表达式 (预览)
        // 下面的代码演示了 JDK 14 的 switch 表达式
        // 注意：JDK 14 的 switch 表达式是预览特性，
        // 在 JDK 15 中成为正式特性

        // JDK 14: Switch 表达式
        String dayType = switch (java.time.LocalDate.now().getDayOfWeek()) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "工作日";
            case SATURDAY, SUNDAY -> "周末";
        };
        System.out.println("今天是: " + dayType);
        // JDK 15: 文本块 (Text Blocks)
        String jsonString = """
            {
                "name": "John",
                "age": 30,
                "city": "New York"
            }
            """;
        System.out.println("JSON 数据:\n" + jsonString);
        String json = """
        {
            "name": "张三",
            "age": 30,
            "city": "北京"
        }
        """;
        System.out.println("JSON 数据:\n" + json);
        // JDK 16: instanceof 模式匹配
        Object obj = "Hello";
        if (obj instanceof String str) {
            System.out.println("字符串长度: " + str.length());
        } else {
            System.out.println("不是字符串");
        }
        if (obj instanceof String s) {
            System.out.println("字符串长度: " + s.length());
        }
        // JDK 16: 记录类 (Record Classes)
        record Person(String name, int age) {}
        Person person = new Person("Alice", 25);
        System.out.println("姓名: " + person.name() + ", 年龄: " + person.age());
        // JDK 17: 新的 switch 表达式
        String fruit = "Apple";
        String color = switch (fruit) {
            case "Apple" -> "红色";
            case "Banana" -> "黄色";
            case "Grapes" -> "紫色";
            default -> throw new IllegalArgumentException("未知水果: " + fruit);
        };
        System.out.println("水果颜色: " + color);
        // JDK 17: sealed 类
        // public sealed class Shape permits Circle, Rectangle {}

        // public final class Circle extends Shape {}
        // public final class Rectangle extends Shape {}
        // JDK 18: Vector API (预览)
        // 使用 Vector API 进行向量计算
        var vector1 = java.util.stream.IntStream.range(0, 4).toArray();
        var vector2 = java.util.stream.IntStream.range(4, 8).toArray();
        var dotProduct = java.util.stream.IntStream.range(0, vector1.length)
            .map(i -> vector1[i] * vector2[i])
            .sum();
        System.out.println("向量点积: " + dotProduct);
        // JDK 19: 外部函数和内存 API (预览)
        // 使用外部函数和内存 API 进行本地内存操作
        // 注意：此示例需要 JDK 19 的外部函数和内存
        // API 支持，可能无法在所有环境中运行
        // var memorySegment = java.lang.foreign.MemorySegment.allocateNative(8);
        // memorySegment.set(ValueLayout.JAVA_INT, 0, 42);
        // System.out.println("内存段值: " + memorySegment.get(ValueLayout.JAVA_INT, 0));
        // memorySegment.close();
        // JDK 20: Record 模式匹配 (预览)
        // 使用 Record 模式匹配进行类型检查和解构
        record Rectangle(int width, int height) {}
        Object shape = new Rectangle(10, 20);
        if (shape instanceof Rectangle(int w, int h)) {
            System.out.println("矩形宽度: " + w + ", 高度: " + h);
        } else {
            System.out.println("不是矩形");
        }
        // JDK 20: Switch 表达式增强
        String dayOfWeek = "MONDAY";
        String message = switch (dayOfWeek) {
            case "MONDAY" -> "今天是星期一";
            case "TUESDAY" -> "今天是星期二";
            case "WEDNESDAY" -> "今天是星期三";
            case "THURSDAY" -> "今天是星期四";
            case "FRIDAY" -> "今天是星期五";
            case "SATURDAY" -> "今天是星期六";
            case "SUNDAY" -> "今天是星期日";
            default -> throw new IllegalArgumentException("未知的星期: " + dayOfWeek);
        };
        System.out.println(message);
        // JDK 21: 虚拟线程 (Virtual Threads)
        try (var executor = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                System.out.println("虚拟线程运行中: " + Thread.currentThread());
            });
        }
        // JDK 21: 新的字符串方法
        String multiline = "Hello\nWorld\nJava";
        String[] lines = multiline.lines().toArray(String[]::new);
        System.out.println("多行字符串:");
        for (String line : lines) {
            System.out.println(line);
        }
        // JDK 21: 新的集合方法
        java.util.List<String> fruits = java.util.List.of("Apple", "Banana",
            "Cherry", "Date");
        java.util.List<String> filteredFruits = fruits.stream()
            .filter(fruit1 -> fruit1.startsWith("A") || fruit1.startsWith("B"))
            .toList();
        System.out.println("过滤后的水果: " + filteredFruits);
        // JDK 21: 新的 switch 表达式
        String month = "January";
        String season = switch (month) {
            case "December", "January", "February" -> "冬季";
            case "March", "April", "May" -> "春季";
            case "June", "July", "August" -> "夏季";
            case "September", "October", "November" -> "秋季";
            default -> throw new IllegalArgumentException("未知的月份: " + month);
        };
        System.out.println("月份: " + month + ", 季节: " + season);
        // JDK 21: 新的文本块语法
        String json1 = """
            {
                "name": "Alice",
                "age": 30,
                "city": "New York"
            }
            """;
        System.out.println("JSON 数据:\n" + json1);
        // JDK 21: 新的集合工厂方法
        java.util.Set<String> colors = java.util.Set.of("Red", "Green", "Blue");
        System.out.println("颜色集合: " + colors);
        // JDK 21: 新的日期时间 API 方法
        java.time.LocalDate date = java.time.LocalDate.of(2023, 10, 1);
        java.time.LocalDate nextWeek = date.plusWeeks(1);
        System.out.println("当前日期: " + date);
        System.out.println("下周同一天: " + nextWeek);
        // JDK 21: 新的异常处理语法
        try {
            int result = 10 / 0; // 故意引发异常
        } catch (ArithmeticException e) {
            System.err.println("发生了算术异常: " + e.getMessage());
        } finally {
            System.out.println("异常处理完成");
        }
        // JDK 21: 新的流 API 方法
        java.util.List<Integer> numbers = java.util.List.of(1, 2, 3, 4, 5);
        int sum = numbers.stream()
            .filter(n -> n % 2 == 0) // 过滤偶数
            .mapToInt(Integer::intValue) // 转换为整数
            .sum(); // 求和
        System.out.println("偶数的总和: " + sum);
        // JDK 21: 新的集合方法
        java.util.List<String> names = java.util.List.of("Alice", "Bob", "Charlie");
        java.util.List<String> upperCaseNames = names.stream()
            .map(String::toUpperCase) // 转换为大写
            .toList(); // 收集结果
        System.out.println("大写名字: " + upperCaseNames);
        // JDK 21: 新的字符串方法
        String text1 = "  Hello World  ";
        System.out.println("去除首尾空白: '" + text1.strip() + "'"); // 去除首尾空白
        System.out.println("是否为空白: " + text1.isBlank()); // 检查
        // JDK 21: Record 模式匹配
        record Point(int x, int y) {}
        Object point = new Point(1, 2);
        if (point instanceof Point(var x, var y)) {
            System.out.println("坐标: (" + x + ", " + y + ")");
        }

        /*// JDK 22: 未命名变量和模式
        var numbers1 = java.util.List.of(1, 2, 3, 4, 5);
        for (var _ : numbers1) {
            System.out.println("处理数字");
        }
        System.out.println("未命名变量示例完成");
        // JDK 23: 原始字符串字面量 (预览)
        // String path = `C:\Users\Name\Documents\file.txt`;

        // JDK 24: 新的垃圾收集器优化和性能改进
        System.out.println("JDK 24 包含了许多性能优化和新的API改进");
        // JDK 24: 新的垃圾收集器优化和性能改进
        // JDK 24 引入了许多性能优化和新的 API 改进
        // 例如，新的垃圾收集器优化可以提高应用程序的性能和响应
        // 性能。以下是一些示例：
        // 1. 使用新的垃圾收集器优化
        System.out.println("JDK 24 引入了新的垃圾收集器优化，可以提高应用程序的性能和响应速度。");
        // 2. 使用新的 API 改
        // JDK 24 引入了许多新的 API 改进，例如新的集合方法、字符串方法等。
        // 以下是一些示例：
        // 1. 使用新的集合方法
        java.util.List<String> items = java.util.List.of("Apple", "Banana", "Cherry");
        items.stream()
            .filter(item -> item.startsWith("A"))
            .forEach(System.out::println); // 过滤并打印以 "A" 开
        // 2. 使用新的字符串方法
        String multilineText = """
            Hello,
            World!
            """;
        System.out.println("多行字符串:\n" + multilineText);
        // 3. 使用新的日期时间 API
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate nextMonth = today.plusMonths(1);
        System.out.println("今天是: " + today);
        System.out.println("下个月同一天是: " + nextMonth);
        // 4. 使用新的流 API 方法
        java.util.List<Integer> numbersList = java.util.List.of(1, 2, 3, 4, 5);
        int sumOfEvens = numbersList.stream()
            .filter(n -> n % 2 == 0)
            .mapToInt(Integer::intValue)
            .sum();
        System.out.println("偶数的总和: " + sumOfEvens);
        // 5. 使用新的集合方法
        java.util.List<String> namesList = java.util.List.of("Alice", "Bob", "Charlie");
        java.util.List<String> upperCaseNamesList = namesList.stream()
            .map(String::toUpperCase)
            .toList();
        System.out.println("大写名字: " + upperCaseNamesList);
        // 6. 使用新的字符串方法
        String textWithSpaces = "  Hello World  ";
        System.out.println("去除首尾空白: '" + textWithSpaces.strip() + "'"); // 去除首尾空白
        System.out.println("是否为空白: " + textWithSpaces.isBlank()); //
        // 7. 使用新的 Record 模式匹配
        record Point2(int x, int y) {}
        Object pointObj = new Point2(1, 2);
        if (pointObj instanceof Point2(var x, var y)) {
            System.out.println("坐标: (" + x + ", " + y + ")");
        }
        // 8. 使用新的虚拟线程
        try (var executor = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                System.out.println("虚拟线程运行中: " + Thread.currentThread());
            });
        }
        System.out.println("JDK 24 新特性演示完成");*/
    }
}
