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

# --------------------------------《Android开发之混淆基础教程》：混淆规则----------------------------------------
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
#-keep public class * extends android.view.View {
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#    public void set*(...);
#}
# 保留类方法，比如：City.from()
#-keepclassmembers class * {
#    public static <methods>;
#}

# --------------------------------《Android开发之混淆高级教程（一）》：混淆规则----------------------------------------
# 关闭压缩过程
#-dontshrink
# 指定未引用类信息输出文件
#-printusage .\usage.txt
# 打印被保留类和内部成员的详细说明
#-whyareyoukeeping class **.Subject
# 打印被保留类和内部成员的详细说明（包含字段、方法签名）
#-whyareyoukeeping class **.Subject -verbose
# 有选择地优化符合指定规则的字节码
#-optimizations class/marking/final
# 指定多个优化选项
#-optimizations "code/simplification/variable,code/simplification/arithmetic"
# 指定多个优化选项（添加'!'和'*'）
#-optimizations "!method/propagation/*"
# 指定多个优化选项（添加'!'和'*'）
#-optimizations "!code/simplification/advanced,code/simplification/*"
# -keep和-keepnames之间的区别（保留类名、方法名）
#-keep,allowshrinking class **.Subject
#-keepnames class **.Subject
# -keepclassmembers和-keepclassmembernames之间的区别（仅保留方法名）
-keepclassmembers,allowshrinking class **.Subject{
public <init>(...);
*** get*();
}
-keepclassmembernames class **.Subject{
public <init>(...);
*** get*();
}
# -keepclasseswithmembers和-keepclasseswithmembernames之间的区别（保留类名和方法名）
-keepclasseswithmembers,allowshrinking class **.Subject{
public void set*(...);
}
-keepclasseswithmembernames class **.Subject{
public void set*(...);
}