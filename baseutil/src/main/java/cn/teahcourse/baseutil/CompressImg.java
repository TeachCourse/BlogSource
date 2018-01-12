package cn.teahcourse.baseutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 图片压缩类
 *
 * Created by http://teachcourse.cn on 2017/10/16.
 */
public class CompressImg {
    private static final String TAG = "CompressImg";

    /**
     * 保存，压缩后图片的目录和原图目录一样
     *
     * @param imgPath 原图路径
     * @return 返回压缩后的图片Bitmap对象
     */
    public static Bitmap compress(String imgPath) {
        File file = new File(imgPath).getParentFile();
        file = new File(file, Config.getSimpleName());
        String savePath = file.getAbsolutePath();
        return compress(imgPath, savePath);
    }

    /**
     * 按原图尺寸压缩图片，大小1M
     *
     * @param imgPath
     * @param savePath
     * @return
     */
    public static Bitmap compress(String imgPath, String savePath) {

        return compress(imgPath, savePath, 1024);
    }

    /**
     * 按原图尺寸压缩图片
     *
     * @param imgPath 原图路径
     * @param bytes   压缩后图片的字节数
     * @return 返回压缩后的图片Bitmap对象
     */
    public static Bitmap compress(String imgPath, String savePath, int bytes) {

        return compress(imgPath, bytes, savePath, 0, 0);
    }

    /**
     * 每次压缩图片的质量减少10
     *
     * @param imgPath
     * @param bytes
     * @param savePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap compress(String imgPath, int bytes, String savePath, int reqWidth, int reqHeight) {

        return compress(imgPath, bytes, 10, savePath, reqWidth, reqHeight);
    }

    /**
     * 图片压缩（质量压缩和尺寸压缩）
     *
     * @param imgPath       原图路径
     * @param bytes         压缩图片后的字节大小
     * @param reduceQuality 每次压缩图片的质量减少值
     * @param savePath      保存压缩图片后的目录
     * @param reqWidth      目标图片的宽度
     * @param reqHeight     目标图片的高度
     * @return 返回压缩后的图片Bitmap对象
     */
    public static Bitmap compress(String imgPath, int bytes, int reduceQuality, String savePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);//这一步容易被忽略，否则options.outHeight和options.outWidth返回0
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);// 一、尺寸压缩：设置缩放比例
        options.inJustDecodeBounds = false;// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bmp = BitmapFactory.decodeFile(imgPath, options);//读取源图片的数据

        String fileName = Quality.compressQuality(bmp, savePath, bytes, reduceQuality);//二、质量压缩：将原图压缩成bytes字节大小
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        return bitmap;
    }

    public static class Size{
        /**
         * 按尺寸压缩原图
         * @param bmp
         * @param reqWidth
         * @param reqHeight
         * @return
         */

        public static Bitmap compressSize(Bitmap bmp, int reqWidth, int reqHeight) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            return compressSize(bis, reqWidth, reqHeight);
        }

        public static Bitmap compressSize(String imgPath, int reqWidth, int reqHeight) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            BitmapFactory.decodeFile(imgPath, options);//这一步容易被忽略，否则options.outHeight和options.outWidth返回0
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);// 设置缩放比例
            options.inJustDecodeBounds = false;// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            return BitmapFactory.decodeFile(imgPath, options);
        }

        public static Bitmap compressSize(InputStream is, int reqWidth, int reqHeight) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            BitmapFactory.decodeStream(is, null, options);//这一步容易被忽略，否则options.outHeight和options.outWidth返回0
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);// 设置缩放比例
            options.inJustDecodeBounds = false;// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            return bitmap;
        }
    }

    public static class Quality{
        /**
         * 按质量压缩原图
         * @param bmp
         * @param bytes
         * @param options
         * @return
         */
        public static Bitmap compressQuality(Bitmap bmp, int bytes, BitmapFactory.Options options) {

            return compressQuality(bmp, bytes, 10, options);// 每次都减少10
        }

        /**
         * 压缩图片质量、大小
         *
         * @param bmp           需要压缩的Bitmap对象
         * @param bytes         压缩后图片的大小
         * @param reduceQuality 每次压缩质量参数，默认100
         * @return
         */
        public static Bitmap compressQuality(Bitmap bmp, int bytes, int reduceQuality, BitmapFactory.Options options) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到bos中
            int defaultQuality = 100;
            while (bos.toByteArray().length / 1024 > bytes) { // 循环判断如果压缩后图片是否大于size,大于继续压缩
                bos.reset();// 重置bos即清空bos
                defaultQuality -= reduceQuality;// 每次都减少quality
                bmp.compress(Bitmap.CompressFormat.JPEG, defaultQuality, bos);// 这里压缩options，把压缩后的数据存放到bos中
            }
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());// 把压缩后的数据bos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(bis, null, options);// 把ByteArrayInputStream数据生成图片
            return bitmap;
        }

        public static Bitmap compressQuality(Bitmap bmp, int bytes, int reduceQuality) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            int defaultQuality = 100;
            while (bos.toByteArray().length / 1024 > bytes) {
                bos.reset();
                defaultQuality -= reduceQuality;
                bmp.compress(Bitmap.CompressFormat.JPEG, defaultQuality, bos);
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.toByteArray().length);
            return bitmap;
        }

        public static Bitmap compressQuality(String imgPath, int bytes, int reduceQuality, BitmapFactory.Options options) {
            Bitmap bmp = BitmapFactory.decodeFile(imgPath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            int defaultQuality = 100;
            while (bos.toByteArray().length / 1024 > bytes) {
                bos.reset();
                defaultQuality -= reduceQuality;
                bmp.compress(Bitmap.CompressFormat.JPEG, defaultQuality, bos);
            }
            options.inPreferredConfig = Bitmap.Config.RGB_565;//改变图片每个像素占用的字节数
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            Bitmap bitmap = BitmapFactory.decodeStream(bis, null, options);
            return bitmap;
        }


        public static String compressQuality(String imgPath, int bytes, int reduceQuality) {
            Bitmap bmp = BitmapFactory.decodeFile(imgPath);
            File file = new File(imgPath).getParentFile();
            file = new File(file, Config.getSimpleName());
            String savePath = file.getAbsolutePath();

            return compressQuality(bmp, savePath, bytes, reduceQuality);
        }

        public static String compressQuality(String imgPath, String savePath, int bytes, int reduceQuality) {
            Bitmap bmp = BitmapFactory.decodeFile(imgPath);

            return compressQuality(bmp, savePath, bytes, reduceQuality);
        }

        /**
         * 图片质量压缩
         *
         * @param bmp
         * @param bytes
         * @param reduceQuality
         * @param savePath
         * @return
         */
        public static String compressQuality(Bitmap bmp, String savePath, int bytes, int reduceQuality) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            int defaultQuality = 100;
            while (bos.toByteArray().length / 1024 > bytes) {
                bos.reset();
                defaultQuality -= reduceQuality;
                bmp.compress(Bitmap.CompressFormat.JPEG, defaultQuality, bos);
            }
            String fileName = saveBitmap(savePath, bos.toByteArray());
            return fileName;
        }
    }




    @NonNull
    private static String saveBitmap(String savePath, byte[] bytes) {
        File file = new File(savePath);
        File fileDir = file.getParentFile();
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }else if(file.exists()){
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(savePath);
            fos.write(bytes);
            fos.flush();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.closeSilently(fos);
        }
        return savePath;
    }

    /**
     * 动态计算图片宽高比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(TAG, "origin, w= " + width + " h=" + height);
        Log.d(TAG, "request, w= " + reqWidth + " h=" + reqHeight);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        Log.d(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }

    /**
     * 动态计算图片宽高比例的第二种方式，固定比例缩放，效果比第一种方式好一些
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int inSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可，be=1表示不缩放
        if (width > height && width > reqWidth) {// 如果宽度大的话根据宽度固定大小缩放
            inSampleSize = options.outWidth / reqWidth;
        } else if (width < height && height > reqHeight) {// 如果高度高的话根据宽度固定大小缩放
            inSampleSize = options.outHeight / reqHeight;
        }
        if (inSampleSize <= 0)
            inSampleSize = 1;
        return inSampleSize;
    }

    public static class Config {
        /**
         * 图片的根目录
         */
        public final static File CAMERA_DIR = new File(
                Environment.getExternalStorageDirectory() + "/DCIM/Camera");

        /**
         * 默认相册图片保存路径
         *
         * @return
         */
        public static String getExternalPath() {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File dir = CAMERA_DIR;
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                return dir.getAbsolutePath();
            }
            return null;
        }

        /**
         * 指定文件保存sdcard路径
         *
         * @param directory
         * @return
         */
        public static String getExternalPath(String directory) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/" + directory);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                return dir.getAbsolutePath();
            }
            return null;
        }

        /**
         * 得到图片名称
         *
         * @return
         */
        public static String getSimpleName() {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "'IMG'_yyyyMMdd_HHMMSS");
            return dateFormat.format(date) + ".jpg";
        }
    }
}
