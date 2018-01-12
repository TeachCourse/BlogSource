package cn.teachcourse;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.teachcourse.common.BaseActivity;

public class MainActivity extends BaseActivity {
    private ListView mListView;
    public static final String ACTION="cn.teachcourse.action.MAIN";
    public static final String CATEGORY="cn.teachcourse.category.SAMPLE_CODE";
    private String name = "cn.teachcourse.demos.Path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initView();
        initData();
        addEvent();
    }

    private void addEvent() {
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    private void initData() {
        Intent intent = getIntent();

        String path = intent.getStringExtra(name);
        /*首次进入，path为空字符串*/
        if (path == null) {
            path = "";
        }
        mListView.setAdapter(new SimpleAdapter(this, getData(path),
                android.R.layout.simple_list_item_1, new String[]{"title"},
                new int[]{android.R.id.text1}));
        mListView.setTextFilterEnabled(true);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.dire_list_view);
    }

    protected List<Map<String, Object>> getData(String path) {
        List<Map<String, Object>> dataSet = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(ACTION, null);
        mainIntent.addCategory(CATEGORY);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return dataSet;

        String[] prefixPath;
        String prefixWithSlash = path;

        if (path.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = path.split("/");
            prefixWithSlash = path + "/";
        }

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            /*获取每一个activity的label值（app/RecordAudioActivity），如果为空，取activity的name值（cn.teachcourse.app.RecordAudioActivity）*/
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;

            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {

                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(dataSet, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    /*首次进入，entries集合为空*/
                    if (entries.get(nextLabel) == null) {
                        addItem(dataSet, nextLabel, browseIntent(path.equals("") ? nextLabel : path + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        Collections.sort(dataSet, sDisplayNameComparator);

        return dataSet;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                private final Collator collator = Collator.getInstance();

                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    return collator.compare(map1.get("title"), map2.get("title"));
                }
            };

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, MainActivity.class);
        result.putExtra(name, path);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);

            Intent intent = (Intent) map.get("intent");
            startActivity(intent);
        }
    };

    @Override
    public String getUrl() {
        return null;
    }
}
