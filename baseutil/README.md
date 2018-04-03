## 2、缓存工具`ACache`，支持缓存*字符串*、*字节数组*、*Bitmap*等 ##
具体用法：
- 调用静态方法`get(Context)`实例化ACache对象，默认缓存目录getCacheDir()：/data/user/0/package_name/cache
- 调用ACache的实例方法保存数据，比如`put(String,String)`，参数一指定文件名称，参数二指定保存的内容

**feature：**
- 一个缓存创建一个独立的文件，删除缓存即删除对应的文件
- 缓存以明文的形式保存，保存之前需要独立加密、解密
- 参数一指定的文件名称，最终获取文件名称字符串的`hashCode()`值，保证唯一性

[源码](src/main/java/cn/teahcourse/baseutil/ACache.java)

## 1、获取某年某月某日往后连续几天日期：`CalendarUtil` ##
比如，获取当前系统日期，往后连续5天日期：
```
String[] dates=CalendarUtil.getDate();
```
获取当前月份的天数：
```
int days=CalendarUtil.getDays();
```

[源码](src/main/java/cn/teahcourse/baseutil/CalendarUtil.java)

## `baseutil-library`库说明 ##
这是一个整理日常开发用到的工具库，比如：SystemUtil，ToastUtil，LogUtil，SharedPreferencesUtil，ACache，ActivityManager，CompressImg等等