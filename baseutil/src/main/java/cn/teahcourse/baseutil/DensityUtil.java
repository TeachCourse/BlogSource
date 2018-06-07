package cn.teahcourse.baseutil;

import android.content.Context;

public class DensityUtil {
    /** 
     * 根据当前手机的屏幕密度，将dp数值转成px值
     */  
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return  (dpValue * scale);
    }  
  
    /** 
     * 根据当前手机的屏幕密度，将 px数值转成dp值
     */  
    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (pxValue / scale);
    }

    /**
     * 自定义屏幕密度，将px数值转换成dp数值
     * @param context
     * @param pxValue
     * @param density
     * @return
     */
    public static float px2dip(Context context,float pxValue,float density ){
        final float scale = density/context.getResources().getDisplayMetrics().DENSITY_DEFAULT;
        return (pxValue / scale);
    }
}