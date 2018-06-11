## 2018-06-11
将`baseutil`、`fileprovider24`、`flinglistview`、`popupwindow`、`upversion`上传JCenter，并将当前项目中的依赖库删除，改为依赖JCenter上的库，详情浏览[app/build.gradle](app/build.gradle)

## 2018-06-08
自定义Gradle插件内容移动到独立的仓库：`CustomGradle`，需要的同学可以自行前往现在，并参考TeachCourse文章《*Android Studio集成greenDAO 3.0基础教程*》

## 2018-06-07
删除非封装库项目：`app-clock`、`app-greendao`、`app-obfuscated`、`app-reboot`、`app-scancode`、`app-uploadpic`、`red-packet-plug`、`screenadapter`，并新建分支`thin-master`管理，推荐下载该分支，后期也主要维护`thin-master`分支

## 2018-04-01
因为每次升级buildTool或compileSdk后，创建module时默认使用最新的，造成在当前项目中出现多个buildTool版本和多个compileSdk版本

这样，下载该项目的朋友，编译时同时需要下载多个buildTool版本和多个compileSdk版本，非常不友好且浪费硬盘空间

因此，在项目根目录[build.gradle](/build.gradle)文件添加module的全局变量：`compileSdk`、`buildTools`、`appCompat`、`recyclerView`等，方便统一管理

在module中引用 [module引用例子](app/build.gradle)

```
    compileSdkVersion rootProject.ext.compileSdk
    buildToolsVersion rootProject.ext.buildTools
```

## 2018-01-12
重新整理`AllDemos`项目源码，命名为`BlogSource`

[参考文档](/screenshot/README.md)
init submit
