package cn.teahcourse.baseutil;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by http://teachcourse.cn on 2017/10/31.
 */

public class CalendarUtil {
    private static int MONTHS_OF_ONE_YEAR = 12;
    private static int LATEST_DATE = 5;//存储最近5天日期（包含当天）

    /**
     * 获取当前日期之后的5天
     *
     * @return
     */
    public static String[] getDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;//系统返回月份从0开始计算，设计月份从1开始
        int day = calendar.get(Calendar.DAY_OF_MONTH)+1;//日期从明天开始计算

        String[] items = getDate(year, month, day);
        return items;
    }

    /**
     * 获取指定年月日，往后连续的几天日期
     * @param year
     * @param month
     * @param day
     * @return
     */
    @NonNull
    public static String[] getDate(int year, int month, int day) {
        int days = getDays(year, month);

        int months = MONTHS_OF_ONE_YEAR;

        String[] items = new String[LATEST_DATE];
        for (int index = 0; index < items.length; index++) {
            //日期没有超出当月天数
            if (day <= days) {
                items[index] = year + "-" + month + "-" + day;
            } else {
                //日期超出当月天数，月份往后推，日期从1号开始计算
                day = 1;
                month++;
                //同理，判断月份是否超出12个月
                if (month <= months) {
                    items[index] = year + "-" + month + "-" + day;
                } else {
                    //超出12个月，年份往后推，月份从1月开始计算
                    month = 1;
                    year++;
                    items[index] = year + "-" + month + "-" + day;
                }
            }
            day++;
        }
        return items;
    }

    public static int getDays(int year, int month) {
        //计算当月天数
        int days = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
            days = 31;
        if (month == 4 || month == 6 || month == 9 || month == 11)
            days = 30;
        if (month == 2) {
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                days = 29;
            } else
                days = 28;
        }
        return days;
    }
}
