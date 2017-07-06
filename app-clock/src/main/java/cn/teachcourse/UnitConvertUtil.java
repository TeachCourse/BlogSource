package cn.teachcourse;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by http://teachcourse.cn on 2017/7/5.
 */

public class UnitConvertUtil {
    public static float getDpVal(Context context,float dp) {
        Resources res = context.getResources();
        if (res != null)
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
        return dp;
    }

    public static float getSpVal(Context context,float sp) {
        Resources res = context.getResources();
        if (res != null)
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, res.getDisplayMetrics());
        return sp;
    }
}
