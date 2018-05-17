package cn.teachcourse.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 钊林IT on 2018/5/5.
 */

public class CompatBasePopupWindActivity extends AppCompatActivity {
    private int width;
    private int height;
    private Bundle bundle;
    private Class<?> mBaseFragment;

    private static final String DATA_OF_BUNDLE = "data";

    public static void start(Context context, Window window) {
        Intent intent = new Intent(context, BasePopupWindActivity.class);

        intent.putExtra(DATA_OF_BUNDLE, window);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popuwind);
        Intent intent = getIntent();
        CompatBasePopupWindActivity.Window window = intent != null ? (CompatBasePopupWindActivity.Window) intent.getParcelableExtra(DATA_OF_BUNDLE) : null;
        if (window == null) {
            throw new RuntimeException("you must invoke method of start(Context,Window) to start the BasePopupWindActivity.");
        }
        width = window.width;
        height = window.height;
        bundle = window.bundle;
        mBaseFragment = window.fragment;

        getWindow().setLayout(width, height);

        initView();
    }

    private void initView() {
        Fragment baseFragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (baseFragment == null) {
            baseFragment = getFragmentInstance();
            if (baseFragment.getArguments() == null)
                baseFragment.setArguments(bundle);
            addFragmentToActivity(getSupportFragmentManager(), baseFragment, R.id.contentFrame);
        }
    }

    private void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    private void checkNotNull(FragmentManager fragmentManager) {
        if (fragmentManager == null)
            throw new NullPointerException("fragmentManager can not null");
    }

    private void checkNotNull(Fragment fragment) {
        if (fragment == null)
            throw new NullPointerException("fragment can not null");
    }

    private Fragment getFragmentInstance() {

        try {
            return (Fragment) mBaseFragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Builder {
        private int width;
        private int height;
        private Bundle bundle;
        private Class<?> fragment;


        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setBundle(Bundle bundle) {
            this.bundle = bundle;
            return this;
        }

        public Builder setFragment(Class<?> fragment) {
            this.fragment = fragment;
            return this;
        }

        public Window create() {
            return new Window(width, height, bundle, fragment);
        }
    }

    public static class Window implements Parcelable {
        private int width;
        private int height;
        private Bundle bundle;
        private Class<?> fragment;

        public Window(int width, int height, Bundle bundle, Class<?> fragment) {
            this.width = width;
            this.height = height;
            this.bundle = bundle;
            this.fragment = fragment;
        }

        protected Window(Parcel in) {
            width = in.readInt();
            height = in.readInt();
            bundle = in.readBundle();
            fragment = (Class<?>) in.readSerializable();
        }

        public static final Creator<Window> CREATOR = new Creator<Window>() {
            @Override
            public Window createFromParcel(Parcel in) {
                return new Window(in);
            }

            @Override
            public Window[] newArray(int size) {
                return new Window[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(width);
            dest.writeInt(height);
            dest.writeBundle(bundle);
            dest.writeSerializable(fragment);
        }
    }
}
