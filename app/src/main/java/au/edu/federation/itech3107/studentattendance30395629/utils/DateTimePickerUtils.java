package au.edu.federation.itech3107.studentattendance30395629.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import androidx.appcompat.widget.AppCompatButton;

import java.util.Calendar;

public class DateTimePickerUtils {

    public static void showDateTimePickerDialog(Context context, AppCompatButton textView) {
        // 获取当前系统时间  Example Obtain the current system time
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 弹出日期选择对话框  The date selection dialog box is displayed
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                String month;
                String day;
                if ((selectedMonth + 1) < 10){
                    month = "0"+(selectedMonth + 1);
                }else {
                    month = (selectedMonth + 1)+"";
                }
                if (selectedDay < 10){
                    day = "0"+selectedDay;
                }else {
                    day = selectedDay+"";
                }
                textView.setText(selectedYear + "/" + month + "/" + day);

            }
        }, year, month, day);
        datePickerDialog.show();
    }

}
