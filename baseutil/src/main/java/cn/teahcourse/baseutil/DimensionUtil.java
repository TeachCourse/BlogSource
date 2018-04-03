package cn.teahcourse.baseutil;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by http://teachcourse.cn on 2018/1/15.
 */

public class DimensionUtil {

    /**
     * 根据手机屏幕尺寸，获取value参数的dp值
     *
     * @param context
     * @param value
     * @return
     */
    public static float getUnitDip(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机屏幕尺寸，获取value参数的px值
     *
     * @param context
     * @param value
     * @return
     */
    public static float getUnitPx(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机屏幕尺寸，获取value参数的sp值
     *
     * @param context
     * @param value
     * @return
     */
    public static float getUnitSp(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机屏幕尺寸，获取value参数的屏幕点数（即Point）值
     *
     * @param context
     * @param value
     * @return
     */
    public static float getUnitPoint(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, value, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机屏幕尺寸，获取value参数的英寸值
     *
     * @param context
     * @param value
     * @return
     */
    public static float getUnitInch(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, value, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机屏幕尺寸，获取value参数的毫米值
     *
     * @param context
     * @param value
     * @return
     */
    public static float getUnitMM(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, value, context.getResources().getDisplayMetrics());
    }
}
