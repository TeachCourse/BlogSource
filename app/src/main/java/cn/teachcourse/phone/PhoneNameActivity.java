package cn.teachcourse.phone;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.teachcourse.R;

/**
 * Created by postmaster@teachcourse.cn on 2016/5/5.
 */
public class PhoneNameActivity extends ActionBarActivity {
    private static final String TAG = PhoneNameActivity.class.getSimpleName();
    private EditText et;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, PhoneNameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_name);
        et = (EditText) this.findViewById(R.id.mobile);
    }

    /**
     * 基于回调机制的事件处理方式有：
     * 1、匿名类
     * 2、内部类
     * 3、外部类
     * 4、xml定义（当前使用）
     * @param view 当前的View对象
     */
    public void getTelClick(View view) {
        String name = et.getText().toString().trim();
        number(name);
    }

    /**
     * 通过输入获取电话号码
     */
    public void number(String name) {
        //使用ContentResolver查找联系人数据
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //遍历查询结果，找到所需号码
        while (cursor.moveToNext()) {
            //获取联系人ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人的名字
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            if (name.equals(contactName)) {
                //使用ContentResolver查找联系人的电话号码
                Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                if (phone.moveToNext()) {
                    String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Toast.makeText(this, phoneNumber+"\nTeachCourse——blog。。。", Toast.LENGTH_SHORT).show();
                    break;
                }

            }
        }
    }

}
