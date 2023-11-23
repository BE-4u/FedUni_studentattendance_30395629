package au.edu.federation.itech3107.studentattendance30395629.ui.teacher;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395629.R;
import au.edu.federation.itech3107.studentattendance30395629.adapter.MyTeacherManageAdapter;
import au.edu.federation.itech3107.studentattendance30395629.base.BaseActivity;
import au.edu.federation.itech3107.studentattendance30395629.bean.UserBean;
import au.edu.federation.itech3107.studentattendance30395629.db.DatabaseUtils;
import au.edu.federation.itech3107.studentattendance30395629.utils.DateTimePickerUtils;

public class TeacherManageActivity extends BaseActivity {

    private RecyclerView rvShowManage;
    private List<UserBean> userBeanList = new ArrayList<>();
    private List<String> teacheCourstList = new ArrayList<>();
    private MyTeacherManageAdapter myAdapter;
    private EditText etCourse;
    private AppCompatButton btStartTime;
    private AppCompatButton btEndTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_teacher_manage;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {

        String account = getIntent().getStringExtra("account");
        String fromWhere = getIntent().getStringExtra("FromWhere");

        TextView tvTitle = findViewById(R.id.tvTitle);
        etCourse = findViewById(R.id.etCourse);
        btStartTime = findViewById(R.id.btStartTime);
        btEndTime = findViewById(R.id.btEndTime);
        Button btAdd = findViewById(R.id.btAdd);
        rvShowManage = findViewById(R.id.rvShowManage);
        AppCompatButton btReset = findViewById(R.id.btReset);

        tvTitle.setText(account);//设置标题  Set title
        if (fromWhere.equals("AuthenManageActivity")){//管理员 Administrator
            btReset.setVisibility(View.INVISIBLE);
        }else {//教师 teacher
            btReset.setVisibility(View.VISIBLE);
        }

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(TeacherManageActivity.this);
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(TeacherManageActivity.this);
                inputDialog.setTitle("Please enter the latest password").setView(editText);
                inputDialog.setPositiveButton("Determine",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < userBeanList.size(); i++) {
                                    UserBean userBean = userBeanList.get(i);
                                    if (userBean.getAccount().equals(account) && userBean.getType().equals("2")){
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("type","2");
                                        contentValues.put("account",userBean.getAccount());
                                        contentValues.put("password",editText.getText().toString());
                                        contentValues.put("standby", userBean.getStandby());
                                        contentValues.put("school_num",userBean.getSchool_num());
                                        contentValues.put("other",userBean.getOther());
                                        DatabaseUtils.updateSchoolData(mSQLiteDatabase,contentValues,userBean.getAccount());
                                        Toast.makeText(mContext, "Successfully modified, log in again！", Toast.LENGTH_SHORT).show();
                                        TeacherManageActivity.this.finish();
                                    }
                                }
                            }
                        }).show();
            }
        });

        btStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerUtils.showDateTimePickerDialog(mContext, btStartTime);
            }
        });
        btEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerUtils.showDateTimePickerDialog(mContext, btEndTime);
            }
        });

        btAdd.setOnClickListener(view -> {
            if (btStartTime.getText().equals("StartTime") || btEndTime.getText().toString().equals("EndTime")){
                Toast.makeText(mContext, "Please select a time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(etCourse.getText().toString())){
                Toast.makeText(mContext, "Please enter the course", Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i < userBeanList.size(); i++) {
                UserBean userBean = userBeanList.get(i);
                if (userBean.getAccount().equals(account) && userBean.getType().equals("2")){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("type","2");
                    contentValues.put("account",userBean.getAccount());
                    contentValues.put("password",userBean.getPassword());
                    if (TextUtils.isEmpty(userBean.getStandby())){//"2023,数学,2024|2023,语文,2025"
                        String stringBuilder = btStartTime.getText().toString() + "," +
                                etCourse.getText().toString() + "," + btEndTime.getText().toString() + "|";
                        contentValues.put("standby", stringBuilder);
                    }else {
                        String stringBuilder = userBean.getStandby() + btStartTime.getText().toString() +
                                "," + etCourse.getText().toString() + "," + btEndTime.getText().toString() + "|";
                        contentValues.put("standby", stringBuilder);
                    }
                    contentValues.put("school_num",userBean.getSchool_num());
                    contentValues.put("other",userBean.getOther());
                    DatabaseUtils.updateSchoolData(mSQLiteDatabase,contentValues,userBean.getAccount());
                    Toast.makeText(mContext, "Added successfully", Toast.LENGTH_SHORT).show();
                    initRecycleView(account);
                    etCourse.setText("");
                    btStartTime.setText("StartTime");
                    btEndTime.setText("EndTime");
                }
            }

        });

        myAdapter = new MyTeacherManageAdapter(this, teacheCourstList);

        initRecycleView(account);
        // 布局管理器  Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        // RecyclerView
        rvShowManage.setLayoutManager(linearLayoutManager);
        rvShowManage.setAdapter(myAdapter);
        // 设置默认分割线  Set the default divider
        rvShowManage.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        myAdapter.setOnItemClickBlock((view, stringValue) -> {
            if (fromWhere.equals("AuthenManageActivity")){
                Toast.makeText(mContext, "Please log in to the teacher account for operation！", Toast.LENGTH_SHORT).show();
            }else {
                String[] split = stringValue.split(",");
                //进入到考勤记录界面  The attendance record page is displayed
                Intent intent = new Intent(TeacherManageActivity.this,RecordAttendanceActivity.class);
                intent.putExtra("account",account);
                intent.putExtra("course",stringValue);
                intent.putExtra("startTime",split[0]);
                intent.putExtra("endTime",split[2]);
                startActivity(intent);
            }

        });
        
    }

    private void initRecycleView(String accountValue) {
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


        teacheCourstList.clear();
        for (int i = 0; i < userBeanList.size(); i++) {
            UserBean userBean = userBeanList.get(i);
            if (userBean.getAccount().equals(accountValue) && userBean.getType().equals("2")){
                String standby = userBean.getStandby();
                if (!TextUtils.isEmpty(standby)){
                    try {
                        String[] split = standby.split("\\|");
                        teacheCourstList.addAll(Arrays.asList(split));
                    }catch (Exception e){
                        e.getStackTrace();
                    }
                }

            }
        }
        myAdapter.notifyDataSetChanged();
    }
}