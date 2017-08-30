# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\sinolvc\adt-bundle-windows-x86_64-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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
# 保留指定包名
#-keeppackagenames cn.teachcourse.bean
# 保留指定类名
#-keepnames class cn.teachcourse.obfuscateapplication.common.City
# 保留指定方法名
#-keepclassmembernames  class cn.teachcourse.obfuscateapplication.common.City{
#    public double get*(...);
#}
# 保留cn.teachcourse目录下所有包名
#-keeppackagenames cn.teachcourse.**
# 混淆`cn.teachcourse`包名下的第二级及其以下的目录
#-keeppackagenames cn.teachcourse.*
# 保留City和City2或User和User2一组类
#-keep class cn.teachcourse.bean.City
#-keep class cn.teachcourse.**.City?
# 保留实现Serializable接口的类
#-keep class * implements java.io.Serializable
# 保留多个继承类，比如：继承View
#-keep public class * extends android.view.View
# 保留静态嵌套类，比如：Student.Builder
#-keep class **.Student$*
# 保留继承自View子类的构造方法、set方法
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
# 保留类方法，比如：City.from()
-keepclassmembers class * {
    public static <methods>;
}
