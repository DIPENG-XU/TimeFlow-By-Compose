# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ---------------------------- Hilt 专用规则 ----------------------------
# 保留 Hilt 生成的组件
-keepclassmembers class dagger.hilt.internal.GeneratedComponent {
    void <init>();
}

# 保留所有带 @InstallIn 注解的模块
-keepclassmembers @dagger.hilt.InstallIn class * {
    void <init>();
}

# 保留所有 EntryPoint 接口
-keep @dagger.hilt.EntryPoint interface * {
    void <init>();
}

# 保留 Hilt 的 GeneratedEntryPoint 注解类
-keep @dagger.hilt.android.internal.GeneratedEntryPoint class * {
    void <init>();
}

# 保留 Hilt 的组件构建器
-keepclassmembers class * extends dagger.hilt.android.internal.builders.ActivityComponentBuilder {
    void <init>();
}

# 保留 @Inject 注解的字段和构造函数
-keepclassmembers class * {
    @javax.inject.Inject <fields>;
    @javax.inject.Inject <init>(...);
}

# 保留 Hilt 的 GeneratedComponentManager
-keep class dagger.hilt.internal.GeneratedComponentManager {
    *;
}

-keepclassmembers class **.*$Companion {
    @javax.inject.Inject <fields>;
}

-keepclassmembers @dagger.hilt.android.internal.testing.TestInstallIn class * {
    void <init>();
}