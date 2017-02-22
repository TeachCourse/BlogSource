package cn.teachcourse.assets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import cn.teachcourse.R;
import cn.teachcourse.assets.bean.NewsInfoBean;
import cn.teachcourse.assets.util.NewsService;
import cn.teachcourse.common.BaseActivity;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {
    TextView mdiplayInfoTV;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x110:
                    List list = (List) msg.obj;
                    if (list==null){
                        Toast.makeText(MainActivity.this, "读取assets文件夹xml数据失败", Toast.LENGTH_LONG).show();
                    }
                    Iterator iter = list.iterator();
                    StringBuffer sb = new StringBuffer();
                    NewsInfoBean bean;
                    while (iter.hasNext()) {
                        bean = (NewsInfoBean) iter.next();
                        sb.append(bean.getId() + "\n");
                        sb.append(bean.getContent() + "\n");
                        sb.append(bean.getFull_title() + "\n");
                        sb.append(bean.getImg() + "\n");
                        sb.append(bean.getImg_length() + "\n");
                        sb.append(bean.getImg_width() + "\n");
                        sb.append(bean.getPdate() + "\n");
                        sb.append(bean.getPdate_src() + "\n");
                        sb.append(bean.getSrc() + "\n");
                        sb.append(bean.getTitle() + "\n");
                        sb.append(bean.getUrl() + "\n");
                    }
                    mdiplayInfoTV.setText(sb.toString());
                    break;
                case 0x111:
                    mdiplayInfoTV.setText("msg:"+msg.obj);
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_main);
        initView();
        sendMessage();
        mdiplayInfoTV.setText(getFromAssets("newsbean.xml"));
    }

    private void initView() {
        initCommon(getWindow().getDecorView());
        mdiplayInfoTV = (TextView) findViewById(R.id.display_tv);
    }

    /**
     * 开启线程，读取assets文件夹xml文件
     */
    private void sendMessage() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                  //绝对路径file:///android_assets/newsbean.xml，相对路径newsbean.xml
                  getFromAssetsPath("file:///android_asset/newsbean.xml");
            }
        }).start();
    }

    /**
     * 读取assets指定的xml文件，返回List
     * @param fileName 文件名称
     * @return List
     */
   public List getFromAssetsPath(String fileName){
       InputStream is = null;
       AssetManager manager = getAssets();
       try {
           is = manager.open(fileName);
           List list = NewsService.getNewsBean(is);
           Message msg = new Message();
           msg.obj = list;
           msg.what = 0x110;
           handler.sendMessage(msg);
           return list;
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           try {
               if(is!=null)
                   is.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

       return null;
   }
    /**读取文件，以字符串返回
     * @param fileName 路径文件名称
     * @return
     */
    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            String result = "";
            while ((line = bufReader.readLine()) != null){
                result += line;
            }
            Message msg = new Message();
            msg.obj = result;
            msg.what = 0x111;
            handler.sendMessage(msg);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param fileName
     * @return
     */
    public String getFromAssetsFile(String fileName) {
        try {
            InputStream is=getResources().getAssets().open(fileName);
            InputStreamReader inputReader = new InputStreamReader(is);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            String result = "";
            while ((line = bufReader.readLine()) != null){
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }
}
