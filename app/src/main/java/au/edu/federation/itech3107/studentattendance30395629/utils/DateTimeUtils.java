package au.edu.federation.itech3107.studentattendance30395629.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtils {


    public static List<String> getTimes(String startDateString,String endDateString){

        List<String> timeDateList = new ArrayList<>();

        // 将字符串日期转换为 Date 对象  Converts a string Date to a date object
        Date startDate = parseDate(startDateString);
        Date endDate = parseDate(endDateString);

        // 创建 Calendar 实例，并将其设置为起始日期    Create a Calendar instance and set it as the start date
        Calendar calendar = Calendar.getInstance();
        assert startDate != null;
        calendar.setTime(startDate);

        // 循环处理日期区间内的每七天的日期  Cycle the dates of each seven days within the processing date interval
        while (calendar.getTime().before(endDate)) {
            // 输出当前日期  Output current date
            timeDateList.add(formatDate(calendar.getTime()));
            // 将日期增加七天  Add seven days to the date
            calendar.add(Calendar.DATE, 7);
        }
        return timeDateList;
    }

    // 将字符串日期解析为 Date 对象  Parses a string Date into a date object
    private static Date parseDate(String dateString) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            return format.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将 Date 对象格式化为字符串日期  Format the Date object as a string date
    private static String formatDate(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }


}
