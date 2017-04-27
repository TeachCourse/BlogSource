package cn.teachcourse.json;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teachcourse.json.bean.DistrictBean;

public class ResolveJSONActivity extends BaseActivity {
    private ListView mListView;//地区选择列表
    private List<DistrictBean> mList;//存放地区数据的List
    private DistrictBaseAdapter mAdapter;//适配器
    private boolean isTwoDirection=false;//判断是否二级栏目
    private List<DistrictBean> mTwoList;//存放上一级地区数据的List
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent();
        intent.setClass(context,ResolveJSONActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(extras!=null){
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_json);
        initView();
        getValue();
    }
    /**
     * 初始化布局控件
     */
    private void initView(){
        initCommon(getWindow().getDecorView());
        mListView=(ListView)findViewById(R.id.district_select_lv);
        mListView.setOnItemSelectedListener(mOnItemSelected);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0&&isTwoDirection){
                    isTwoDirection=false;
                    getValue();
                    return;
                }
                String childrenNode=mList.get(position).getChildrenNode();
                if (childrenNode==null){
                    return;
                }
                isTwoDirection=true;
                resolveJson(childrenNode);
            }
        });
    }
    /**
     * 添加列表选择监听器
     */
    private AdapterView.OnItemSelectedListener mOnItemSelected=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    /**
     * 读取地区数据：json字符串
     */
    private void getValue(){
        String json=getResources().getString(R.string.jsonDistrict);
        resolveJson(json);
    }
    /**
     * 解析json字符串
     * @param json 字符串
     */
    private void resolveJson(String json){
        try{
            mList=new ArrayList<DistrictBean>();
            if(!isTwoDirection){
                mList.add(new DistrictBean("0","全部",json));
            }else{
                mList.add(new DistrictBean("0","...",json));
            }
            JSONArray array=new JSONArray(json);
            int length=array.length();
            int index=0;
            while(index<length){
                JSONObject obj=array.getJSONObject(index);
                String id=obj.getString("id");
                String name=obj.getString("name");
                String parentId=obj.getString("parentId");
                boolean isNode=obj.has("childrenNodes");
                DistrictBean bean=new DistrictBean();
                if(isNode){
                    String childrenNodes=obj.getString("childrenNodes");
                    bean.setChildrenNode(childrenNodes);
                }
                bean.setId(id);
                bean.setName(name);
                bean.setParentId(parentId);
                mList.add(bean);
                index++;
            }
           mHandler.sendEmptyMessage(REFRESH_UI);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 刷新UI界面Handler
     */
    private static final int REFRESH_UI=0X110;//刷新UI界面
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REFRESH_UI:
                    mAdapter=new DistrictBaseAdapter(mList,ResolveJSONActivity.this);
                    mListView.setAdapter(mAdapter);
                    break;
            }
        }
    };

    @Override
    public String getUrl() {
        return null;
    }
}
