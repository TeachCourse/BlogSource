## 针对API 24写入或读取Uri权限申请的封装

- 拍摄照片Uri权限的申请与授权过程

```
    /**
     * 调用系统相机
     */
    private void goToTakePhoto() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(mFileRoot, System.currentTimeMillis() + ".jpg");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri contentUri = FileProvider24.getUriForFile(this, file);
            FileProvider24.grantUriWritePermission(this, intent, contentUri);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            startActivityForResult(intent, RESULT_CAPTURE_IMAGE);
        } else {
            Toast.makeText(this, "sdcard不存在", Toast.LENGTH_SHORT).show();
        }
    }
```

- 安装apk文件Uri权限的申请与授权过程

```
    /**
     * 安装应用程序
     *
     * @param file apk文件存放的路径
     */
    private void installApk(File file) {
        file = new File(file, "load/");
        file = new File(file, "92Recycle-release.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri contentUri = FileProvider24.getUriForFile(this, file);
        FileProvider24.grantUriReadPermission(this, intent, contentUri);

        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        startActivity(intent);
    }
```

详情参考demo：[WriteToReadActivity](app/src/main/java/cn/teachcourse/nougat)

Android开发之深入理解Android 7.0系统权限更改相关文档

http://teachcourse.cn/2379.html