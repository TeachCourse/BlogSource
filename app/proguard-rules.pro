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
# -------------------------------------------1.1 混淆第三方类库------------------------------------------

# 添加okhttp混淆规则
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

#添加nineoldandroids混淆规则
#-keepnames com.nineoldandroids.**{*;}

# -------------------------------------------1.2 混淆注解相关代码------------------------------------------

-keepattributes *Annotation*

# -------------------------------------------1.3 混淆序列化对象------------------------------------------

# 混淆实现Serializable的类
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#混淆实现Parcelable的类
-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

# -------------------------------------------1.4 混淆实体类------------------------------------------

# 注意将cn.teachcourse.bean更改为您项目存放实体类的路径
-keep class cn.teachcourse.bean.** {
    void set*(***);
    void set*(int, ***);

    boolean is*();
    boolean is*(int);

    *** get*();
    *** get*(int);
}

# -------------------------------------------1.5 混淆自定义View------------------------------------------

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

# -------------------------------------------1.6 混淆枚举------------------------------------------

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# -------------------------------------------1.7 混淆native方法------------------------------------------

-keepclasseswithmembernames class * {
    native <methods>;
}

# -------------------------------------------1.8 混淆callback方法------------------------------------------

-keep class android.widget.Button {
    public void setTextSize(...);
}

# -------------------------------------------1.8 混淆JS交互方法------------------------------------------

-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
# 将cn.teachcourse.LoginInterface替换成您定义的接口
-keepclassmembers class cn.teachcourse.LoginInterface {
    public *;
}


