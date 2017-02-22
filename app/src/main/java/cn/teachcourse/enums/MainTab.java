package cn.teachcourse.enums;

import android.support.v4.app.Fragment;

import cn.teachcourse.R;
import cn.teachcourse.enums.fragment.ChatRoomListFragment;
import cn.teachcourse.enums.fragment.SessionListFragment;
import cn.teachcourse.enums.fragment.ContactListFragment;

/**
 * Created by Administrator on 2016/5/4.
 */
public enum MainTab {
    RECENT_CONTACTS(0, ReminderId.SESSION, SessionListFragment.class, R.string.main_tab_session, R.layout.activity_session_list),
    CONTACT(1, ReminderId.CONTACT, ContactListFragment.class, R.string.main_tab_contact, R.layout.activity_contacts_list),
    CHAT_ROOM(2, ReminderId.INVALID, ChatRoomListFragment.class, R.string.chat_room, R.layout.activity_chat_room_tab);
    public final int tabIndex;

    public final int reminderId;

    public final Class<? extends Fragment> clazz;

    public final int resId;

    public final int fragmentId;

    public final int layoutId;

    MainTab(int index, int reminderId, Class<? extends Fragment> clazz,
            int resId, int layoutId) {
        this.tabIndex = index;
        this.reminderId = reminderId;
        this.clazz = clazz;
        this.resId = resId;
        this.layoutId = layoutId;
        this.fragmentId = index;
    }

    public static final MainTab fromReminderId(int reminderId) {
        for (MainTab value : MainTab.values()) {
            if (value.reminderId == reminderId) {
                return value;
            }
        }

        return null;
    }

    public static final MainTab fromTabIndex(int tabIndex) {
        for (MainTab value : MainTab.values()) {
            if (value.tabIndex == tabIndex) {
                return value;
            }
        }

        return null;
    }
}

