## 2018-04-01
因为每次升级buildTool或compileSdk后，创建module时默认使用最新的，造成在当前项目中出现多个buildTool版本和多个compileSdk版本

这样，下载该项目的朋友，编译时同时需要下载多个buildTool版本和多个compileSdk版本，非常不友好且浪费硬盘空间

因此，在项目根目录`build.gradle`[文件](/build.gradle)添加module的全局变量：`compileSdk`、`buildTools`、`appCompat`、`recyclerView`等，方便统一管理

在module中引用 [module引用例子](app/build.gradle)

```
    compileSdkVersion rootProject.ext.compileSdk
    buildToolsVersion rootProject.ext.buildTools
```

## 2018-01-12
重新整理`AllDemos`项目源码，命名为`BlogSource`

[参考文档](/screenshot/README.md)
init submit
