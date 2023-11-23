package au.edu.federation.itech3107.studentattendance30395629.ui.student;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import au.edu.federation.itech3107.studentattendance30395629.R;
import au.edu.federation.itech3107.studentattendance30395629.adapter.CheckStudentAdapter;
import au.edu.federation.itech3107.studentattendance30395629.adapter.MyTeacherManageAdapter;
import au.edu.federation.itech3107.studentattendance30395629.base.BaseActivity;
import au.edu.federation.itech3107.studentattendance30395629.bean.UserBean;
import au.edu.federation.itech3107.studentattendance30395629.db.DatabaseUtils;
import au.edu.federation.itech3107.studentattendance30395629.ui.teacher.RecordAttendanceActivity;
import au.edu.federation.itech3107.studentattendance30395629.ui.teacher.TeacherManageActivity;
import au.edu.federation.itech3107.studentattendance30395629.utils.DateTimeUtils;

public class CheckActivity extends BaseActivity {

    private RecyclerView rvShowManage;
    private List<UserBean> userBeanList = new ArrayList<>();
    private List<String> studentCourstList = new ArrayList<>();
    private CheckStudentAdapter myAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_student_check;
    }

    @Override
    public void initView() {
        String account = getIntent().getStringExtra("account");
        String school_num = getIntent().getStringExtra("school_num");
        String studentOther = getIntent().getStringExtra("studentOther");
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(account);//设置标题  Set title


        rvShowManage = findViewById(R.id.rvShowManage);
        AppCompatButton btReset = findViewById(R.id.btReset);

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(CheckActivity.this);
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(CheckActivity.this);
                inputDialog.setTitle("Please enter the latest password").setView(editText);
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < userBeanList.size(); i++) {
                                    UserBean userBean = userBeanList.get(i);
                                    if (userBean.getAccount().equals(account) && userBean.getType().equals("3")){
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("type","3");
                                        contentValues.put("account",userBean.getAccount());
                                        contentValues.put("password",editText.getText().toString());
                                        contentValues.put("standby", userBean.getStandby());
                                        contentValues.put("school_num",userBean.getSchool_num());
                                        contentValues.put("other",userBean.getOther());
                                        DatabaseUtils.updateSchoolData(mSQLiteDatabase,contentValues,userBean.getAccount());
                                        Toast.makeText(mContext, "Successfully modified, log in again！", Toast.LENGTH_SHORT).show();
                                        CheckActivity.this.finish();
                                    }
                                }
                            }
                        }).show();
            }
        });


        myAdapter = new CheckStudentAdapter(this, studentCourstList,mSpUtils,school_num);

        // 布局管理器  Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        // RecyclerView
        rvShowManage.setLayoutManager(linearLayoutManager);
        rvShowManage.setAdapter(myAdapter);
        // 设置默认分割线  Set the default divider
        rvShowManage.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));


        String[] split = studentOther.split("\\|");
        //"2023,数学,2024,30395629,2023/11/01|2023,数学,2024,30395629,2023/11/01"
        List<String> timesAll = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            // 定义下拉框选项数据  Define drop-down option data
            String[] split1 = split[i].split(",");
            List<String> times = DateTimeUtils.getTimes(split1[0], split1[2]);
            timesAll.addAll(times);
        }
        Set<String> set = new LinkedHashSet<>(timesAll); // 去重  Avoid repetition
        timesAll.clear();
        timesAll.addAll(set);

        // 找到 Spinner 控件  Find the Spinner control
        Spinner mSpinner = findViewById(R.id.spinner);

        // 创建适配器，并设置选项数据  Create the adapter and set the option data
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_tiem_data, timesAll);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = (String) parent.getItemAtPosition(position);
                initRecycleView(account,selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void initRecycleView(String account, String accountValue) {
        userBeanList.clear();
        Cursor schoolData = DatabaseUtils.getSchoolData(mSQLiteDatabase);
        if (schoolData.getCount() != 0){
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

        }


        studentCourstList.clear();
        for (int i = 0; i < userBeanList.size(); i++) {
            UserBean userBean = userBeanList.get(i);
            if (userBean.getAccount().equals(account) && userBean.getType().equals("3")){
                String other = userBean.getOther();
                if (!TextUtils.isEmpty(other)){
                    try {
                        //"2023,数学,2024,30395629,2023/11/01|2023,数学,2024,30395629,2023/11/01"
                        String[] split = other.split("\\|");
                        for (int j = 0; j < split.length; j++) {
                            String[] split1 = split[j].split(",");
                            if (split1[4].equals(accountValue)){
                                studentCourstList.add(split[j]);
                            }
                        }
                    }catch (Exception e){
                        e.getStackTrace();
                    }
                }

            }
        }
        myAdapter.notifyDataSetChanged();
    }

}
