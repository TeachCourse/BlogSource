package cn.teachcourse.main;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import cn.teachcourse.R;
import cn.teachcourse.service.music.MusicPlayerActivity;
import cn.teachcourse.view.MyTextViewActivity;
import cn.teachcourse.view.progress.ProgressActivity;

/**
 * Created by postmaster@teachcourse.cn on 2016/5/4.
 */
public class MainListActivity extends ListActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter mAdapter=ArrayAdapter.createFromResource(this, R.array.items,android.R.layout.simple_spinner_item);
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position+1){
            case Constant.RECORDER_VIDEO:
                 cn.teachcourse.activity.MainActivity.start(this);
                break;
            case Constant.RECORDER_AUDIO:
                 cn.teachcourse.activity.AudioMainActivity.start(this);
                break;
            case Constant.ENUM_DEMO:
                 cn.teachcourse.enums.MainActivity.start(this);
                break;
            case Constant.DEVICE_ID:
                 cn.teachcourse.deviceId.MainActivity.start(this);
                break;
            case Constant.PHONE_NUM:
                 cn.teachcourse.phone.PhoneNameActivity.start(this);
                break;
            case Constant.MUSIC_SIMPLE_DEMO:
                 MusicPlayerActivity.start(this);
                break;
            case Constant.KENBURNSVIEW_DEMO:
                 cn.teachcourse.imageview.overlook.OverlookActivity.start(this);
                break;
            case Constant.HANDLER_RECYCLE_SEND_MESSAGE_DEMO:
                 cn.teachcourse.handler.RefreshUIActivity.start(this);
                break;
            case Constant.ASSET_XML_FILE:
                 cn.teachcourse.assets.MainActivity.start(this);
                break;
            case Constant.JSON_RESOLVE:
                 cn.teachcourse.json.ResolveJSONActivity.start(this);
                break;
            case Constant.INTERFACE_DEMO:
                 cn.teachcourse.interfaces.MsgBeanActivity.start(this);
                break;
            case Constant.SWIPEREFRESHLAYOUT_RECYCLERVIEW:
                 cn.teachcourse.view.SwitchListViewActivity.start(this);
                break;
            case Constant.RECYCLER_ADAPTER:
                 cn.teachcourse.view.recyclerview.RecyclerViewActivity.start(this);
                break;
            case Constant.BASEDAPTER_LISTVIEW:
                 cn.teachcourse.view.listview.ListViewActivity.start(this);
                break;
            case Constant.GRIDVIEW_SIMPLE_DEMO:
                 cn.teachcourse.view.gridview.GridViewActivity.start(this);
                break;
            case Constant.RECYCLERVIEW_NETEASE_DEMO:
                 cn.teachcourse.view.recyclerview.netease.TabsActivity.start(this);
                break;
            case Constant.ANDROID_LOCATION:
                 cn.teachcourse.location.AddressActivity.start(this);
                break;
            case Constant.FRAGMENT_DEMO:
                 cn.teachcourse.fragment.FragmentActivity.start(this);
                break;
            case Constant.TABHOST_DEMO:
                 cn.teachcourse.tabhost.OneTabActivity.start(this);
                break;
            case Constant.VIEWPAGER_DEMO:
                 cn.teachcourse.view.viewpager.TabIndicatorActivity.start(this);
                break;
            case Constant.DOWNLOAD_BY_NETWORK_DEMO:
                 cn.teachcourse.packages.PackageInfoActivity.start(this);
                break;
            case Constant.FILE_LENGTH_BY_NETWORK_DEMO:
                 cn.teachcourse.urlconnection.URLConneActivity.start(this);
                break;
            case Constant.TRANSITION_DRAWABLE_DEMO:
                 cn.teachcourse.transition.TransitionActivity.start(this);
                break;
            case Constant.WELCOME_GUIDE_VIEWPAGER_DEMO:
                 cn.teachcourse.view.viewpager.WelcomeGuideActivity.start(this);
                break;
            case Constant.H5_FULLCREEN_WEBVIEW:
                 cn.teachcourse.view.webview.H5WebViewActivity.start(this);
                break;
            case Constant.ANDROID_WEBVIEW_JS_INTERATIVE:
                 cn.teachcourse.view.webview.JSWebViewActivity.start(this);
                break;
            case Constant.ANDROID_WEBVIEW_JS_ZXING:
                 cn.teachcourse.view.webview.ZXingJSWebViewActivity.start(this);
                break;
            case Constant.PULLABLE_SCROLL_VIEW:
                 cn.teachcourse.chenjing.PullableScrollViewActivity.start(this);
                break;
            case Constant.RADIO_BUTTON_EXAMPLE:
                 cn.teachcourse.view.RadioButtonActivity.start(this);
                break;
            case Constant.UNDERSTAND_INTERFACE:
                MyTextViewActivity.start(this);
                break;
            case Constant.DEFINE_PROGRESS_STYLE:
                ProgressActivity.start(this);
                break;
        }
    }
}
