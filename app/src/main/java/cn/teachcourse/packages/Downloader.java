package cn.teachcourse.packages;

import android.os.Message;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *@author postmaster@teachcourse.cn
 *@date 创建于：2016-3-7
 */
public class Downloader {
    private PackageInfoActivity context;
    private Message msg;
    protected int totalSize=0;//读取的进度数
    protected int totalCount = 38682601;//文件大小

    public Downloader(){
    }
    public Downloader(PackageInfoActivity context){
        this.context=context;
        msg=new Message();
    }
    public void setContext(PackageInfoActivity context){
        this.context=context;
        msg=new Message();
    }

    /**
     * 想要获取流的字节数据
     * @param url
     * @return
     */
    public static long loadFile(URL url) {
        try {
            InputStream mInputStream = getInputStream(url).getInputStream();

            return readStream(mInputStream);
        } catch (IOException e) {

            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 字节输入流
     * @param url 输入流地址
     * @return 输入流
     *
     */
    public static HttpURLConnection getInputStream(URL url){
        try{
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) url
                    .openConnection();
            mHttpURLConnection.setConnectTimeout(1000);
            mHttpURLConnection.setRequestMethod("POST");
            mHttpURLConnection.setDoInput(true);
            mHttpURLConnection.setDoOutput(true);
            mHttpURLConnection.setUseCaches(false);
            mHttpURLConnection.setUseCaches(false);
            mHttpURLConnection.setInstanceFollowRedirects(true);
            mHttpURLConnection.getContentLength();
            return mHttpURLConnection;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据输入流
     * @param mInputStream
     * @return
     * @throws IOException
     */
    public static long readStream(InputStream mInputStream) throws IOException {
        DataInputStream mDataInputStream = new DataInputStream(mInputStream);
        String info = mDataInputStream.readUTF();
        return info.length();
    }

    /**
     * 下载文件
     * @param path 下载地址
     * @return
     */
    public boolean loadFile(String path) {
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
            fis = new FileInputStream(path);
            dis = new DataInputStream(fis);

            byte b[] = new byte[1024];// 每次读取10字节
            int readed;
            while ((readed = dis.read(b)) != -1) {
                msg.arg1=readed;
                msg.what=PackageInfoActivity.REFRESH_UI;
                context.mHandler.sendMessage(msg);
            }
            msg.what=PackageInfoActivity.NOTIFY_UI;
            context.mHandler.sendMessage(msg);
           return true;
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
                fis.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return false;

    }

    /**
     * 将服务器的apk文件下载本地
     * @param is 输入流
     * @param savePath 本地路径
     * @return
     */
    public long loadFile(InputStream is, String savePath) {
        DataInputStream dis = null;
        FileOutputStream fos = null;
        ByteArrayInputStream bis=null;
        try {
            dis = new DataInputStream(is);
            fos = new FileOutputStream(savePath);

            byte b[] = new byte[1024];// 每次读取1024字节
            int readed = 0;
            while ((readed = dis.read(b)) != -1) {
                fos.write(b, 0, readed);
                totalSize+=readed;
                context.mHandler.sendEmptyMessage(PackageInfoActivity.REFRESH_UI);
            }
            context.mHandler.sendEmptyMessage(PackageInfoActivity.NOTIFY_UI);
            return readed;
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                dis.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return 0;
    }


}
