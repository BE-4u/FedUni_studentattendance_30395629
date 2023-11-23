package au.edu.federation.itech3107.studentattendance30395629.ui.teacher;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395629.R;
import au.edu.federation.itech3107.studentattendance30395629.adapter.TeacherManageStudentAdapter;
import au.edu.federation.itech3107.studentattendance30395629.base.BaseActivity;
import au.edu.federation.itech3107.studentattendance30395629.bean.UserBean;
import au.edu.federation.itech3107.studentattendance30395629.db.DatabaseUtils;
import au.edu.federation.itech3107.studentattendance30395629.utils.DateTimeUtils;

public class RecordAttendanceActivity extends BaseActivity {

    private List<UserBean> studentBeanList = new ArrayList<>();
    private List<UserBean> studentList = new ArrayList<>();
    private String accountIntent;
    private String courseIntent;
    private TeacherManageStudentAdapter teacherManageStudentAdapter;
    private String selectedValue = "2023";//选择每周课的时间  Choose a class time each week

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_attendance;
    }

    @Override
    public void initView() {

        accountIntent = getIntent().getStringExtra("account");
        courseIntent = getIntent().getStringExtra("course");
        String startTime = getIntent().getStringExtra("startTime");
        String endTime = getIntent().getStringExtra("endTime");

        Button btAddStudent = findViewById(R.id.btAddStudent);
        RecyclerView rvShowManage = findViewById(R.id.rvShowManage);
        Button btAdd = findViewById(R.id.btAdd);
        TextView kq = findViewById(R.id.kq);

        try {
            String[] split = courseIntent.split(",");
            kq.setText(split[1]);
        }catch (Exception e){
            e.getStackTrace();
            kq.setText("Course attendance");
        }

        btAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceStudentDialog();
            }
        });

        teacherManageStudentAdapter = new TeacherManageStudentAdapter(mContext,studentList,courseIntent,mSpUtils);

        // 布局管理器  Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        // RecyclerView
        rvShowManage.setLayoutManager(linearLayoutManager);
        rvShowManage.setAdapter(teacherManageStudentAdapter);
        // 设置默认分割线  Set the default divider
        rvShowManage.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<UserBean> dataUserList = teacherManageStudentAdapter.getData();

                for (int i = 0; i < dataUserList.size(); i++) {
                    UserBean userBean = dataUserList.get(i);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("type",userBean.getType());
                    contentValues.put("account",userBean.getAccount());
                    contentValues.put("password",userBean.getPassword());
                    if (TextUtils.isEmpty(userBean.getStandby())){
                        contentValues.put("standby",courseIntent + "," + "false");
                    }else {
                        contentValues.put("standby", userBean.getStandby());
                    }
                    contentValues.put("other",userBean.getOther());
                    contentValues.put("school_num",userBean.getSchool_num());
                    DatabaseUtils.updateSchoolData(mSQLiteDatabase,contentValues,userBean.getAccount());
                }
                if (dataUserList.size() == 0){
                    Toast.makeText(mContext, "Let's add students first！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext, "Attendance input successful！", Toast.LENGTH_SHORT).show();
                }

            }
        });


        // 定义下拉框选项数据  Define drop-down option data
        List<String> times = DateTimeUtils.getTimes(startTime, endTime);

        // 找到 Spinner 控件  Find the Spinner control
        Spinner mSpinner = findViewById(R.id.spinner);

        // 创建适配器，并设置选项数据  Create the adapter and set the option data
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_tiem_data, times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedValue = (String) parent.getItemAtPosition(position);
                teacherManageStudentAdapter.setSelectedValue(selectedValue);
                initData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void initData() {

        studentBeanList.clear();
        studentList.clear();

        Cursor schoolData = DatabaseUtils.getSchoolData(mSQLiteDatabase);
        if (schoolData.getCount() != 0){
            List<UserBean> userBeanList = new ArrayList<>();
            schoolData.move(-1);
            while(schoolData.moveToNext()){
                UserBean userBean = new UserBean();
                userBean.setType(schoolData.getString(schoolData.getColumnIndex("type")));
                userBean.setAccount(schoolData.getString(schoolData.getColumnIndex("account")));
                userBean.setPassword(schoolData.getString(schoolData.getColumnIndex("password")));
                userBean.setSchool_num(schoolData.getString(schoolData.getColumnIndex("school_num")));
                userBean.setOther(schoolData.getString(schoolData.getColumnIndex("other")));
                userBean.setStandby(schoolData.getString(schoolData.getColumnIndex("standby")));
                userBeanList.add(userBean);
            }

            for (int i = 0; i < userBeanList.size(); i++) {
                if ("3".equals(userBeanList.get(i).getType())){
                    studentBeanList.add(userBeanList.get(i));
                    if (!TextUtils.isEmpty(userBeanList.get(i).getOther())){
                        String other = userBeanList.get(i).getOther();
                        if (other.contains(courseIntent)){
                            String[] split = other.split("\\|");
                            for (int j = 0; j < split.length; j++) {
                                String[] split1 = split[j].split(",");
                                if (split1[4].equals(selectedValue)){
                                    studentList.add(userBeanList.get(i));
                                }
                            }

                        }
                    }

                }
            }
        }

        teacherManageStudentAdapter.notifyDataSetChanged();

    }


    private int choice;
    private void showChoiceStudentDialog(){
        final String[] items = new String[studentBeanList.size()];
        for (int i = 0; i < studentBeanList.size(); i++) {
            UserBean userStudentBean = studentBeanList.get(i);
            items[i] = userStudentBean.getAccount()+":"+userStudentBean.getSchool_num();
        }
        choice = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(RecordAttendanceActivity.this);
        singleChoiceDialog.setTitle("Please select a student！");
        // 第二个参数是默认选项，此处设置为0  The second parameter is the default option, which is set to 0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("Determine",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choice != -1) {
                            UserBean userBean = studentBeanList.get(choice);
                            for (int i = 0; i < studentList.size(); i++) {
                                if (studentList.get(i).getAccount().equals(userBean.getAccount())){
                                    Toast.makeText(mContext, "Do not add again！", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("type","3");
                            contentValues.put("account",userBean.getAccount());
                            contentValues.put("password",userBean.getPassword());
                            contentValues.put("school_num",userBean.getSchool_num());
                            contentValues.put("standby",userBean.getStandby());
                            if (TextUtils.isEmpty(userBean.getOther())){//"2023,数学,2024,30395629|2023,数学,2024,30395629"
                                String stringBuilder = courseIntent + "," + userBean.getSchool_num() + "," + selectedValue;
                                contentValues.put("other", stringBuilder);
                            }else {
                                String stringBuilder = userBean.getOther() + "|" + courseIntent + "," + userBean.getSchool_num() + "," + selectedValue;
                                contentValues.put("other", stringBuilder);
                            }
                            DatabaseUtils.updateSchoolData(mSQLiteDatabase,contentValues,userBean.getAccount());
                            Toast.makeText(mContext, "Added successfully", Toast.LENGTH_SHORT).show();
                            initData();
                        }else {
                            UserBean userBean = studentBeanList.get(0);
                            for (int i = 0; i < studentList.size(); i++) {
                                if (studentList.get(i).getAccount().equals(userBean.getAccount())){
                                    Toast.makeText(mContext, "Do not add again！", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("type","3");
                            contentValues.put("account",userBean.getAccount());
                            contentValues.put("password",userBean.getPassword());
                            contentValues.put("school_num",userBean.getSchool_num());
                            contentValues.put("standby",userBean.getStandby());
                            if (TextUtils.isEmpty(userBean.getOther())){//"2023,数学,2024,30395629,2023/11/01|2023,数学,2024,30395629,2023/11/01"
                                String stringBuilder = courseIntent + "," + userBean.getSchool_num() + "," + selectedValue;
                                contentValues.put("other", stringBuilder);
                            }else {
                                String stringBuilder = userBean.getOther() + "|" + courseIntent + "," + userBean.getSchool_num() + "," + selectedValue;
                                contentValues.put("other", stringBuilder);
                            }
                            DatabaseUtils.updateSchoolData(mSQLiteDatabase,contentValues,userBean.getAccount());
                            Toast.makeText(mContext, "Added successfully", Toast.LENGTH_SHORT).show();
                            initData();
                        }
                    }
                });
        singleChoiceDialog.show();
    }

}
