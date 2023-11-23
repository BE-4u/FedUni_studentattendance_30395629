package au.edu.federation.itech3107.studentattendance30395629.ui.authen;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395629.R;
import au.edu.federation.itech3107.studentattendance30395629.adapter.MyAdapter;
import au.edu.federation.itech3107.studentattendance30395629.base.BaseActivity;
import au.edu.federation.itech3107.studentattendance30395629.bean.UserBean;
import au.edu.federation.itech3107.studentattendance30395629.db.DatabaseUtils;
import au.edu.federation.itech3107.studentattendance30395629.ui.teacher.TeacherManageActivity;

public class AuthenManageActivity extends BaseActivity {

    private RecyclerView rvShowManage;
    private List<UserBean> userBeanList = new ArrayList<>();
    private MyAdapter myAdapter;
    private RadioButton rbAuthen;
    private RadioButton rbTeacher;
    private RadioButton rbStudent;
    private EditText etAddAccount;
    private EditText etAddPwd;
    private EditText etStudentNumber;

    @Override
    public int getLayoutId() {
        return R.layout.activity_authen_manage;
    }

    @Override
    public void initView() {
        RadioGroup rgManage = findViewById(R.id.rgManage);
        rbAuthen = findViewById(R.id.rbAuthen);
        rbTeacher = findViewById(R.id.rbTeacher);
        rbStudent = findViewById(R.id.rbStudent);
        etAddAccount = findViewById(R.id.etAddAccount);
        etAddPwd = findViewById(R.id.etAddPwd);
        etStudentNumber = findViewById(R.id.etStudentNumber);
        AppCompatButton btAdd = findViewById(R.id.btAdd);
        rvShowManage = findViewById(R.id.rvShowManage);

        rgManage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
                switch (checkid){
                    case R.id.rbAuthen:
                        etStudentNumber.setVisibility(View.GONE);
                        rbAuthen.setChecked(true);
                        rbTeacher.setChecked(false);
                        rbStudent.setChecked(false);
                        break;
                    case R.id.rbTeacher:
                        etStudentNumber.setVisibility(View.GONE);
                        rbAuthen.setChecked(false);
                        rbTeacher.setChecked(true);
                        rbStudent.setChecked(false);
                        break;
                    case R.id.rbStudent:
                        etStudentNumber.setVisibility(View.VISIBLE);
                        rbAuthen.setChecked(false);
                        rbTeacher.setChecked(false);
                        rbStudent.setChecked(true);
                        break;
                }
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rbAuthen.isChecked() && !rbTeacher.isChecked() && !rbStudent.isChecked()){
                    Toast.makeText(mContext, "Please select a role！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etAddAccount.getText().toString())){
                    Toast.makeText(mContext, "Please enter an account！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etAddPwd.getText().toString())){
                    Toast.makeText(mContext, "Please enter the password！", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues contentValues = new ContentValues();
                if (rbAuthen.isChecked()){
                    contentValues.put("type","1");
                }
                if (rbTeacher.isChecked()){
                    contentValues.put("type","2");
                }
                if (rbStudent.isChecked()){
                    if (TextUtils.isEmpty(etStudentNumber.getText().toString())){
                        Toast.makeText(mContext, "Please enter the student ID！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    contentValues.put("type","3");
                    contentValues.put("school_num", etStudentNumber.getText().toString());
                }
                contentValues.put("account", etAddAccount.getText().toString());
                contentValues.put("password", etAddPwd.getText().toString());

                DatabaseUtils.insertToSchoolData(mSQLiteDatabase,contentValues);
                initRecycleView();
            }
        });



        myAdapter = new MyAdapter(this, userBeanList);

        initRecycleView();
        // 布局管理器  Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        // RecyclerView
        rvShowManage.setLayoutManager(linearLayoutManager);
        rvShowManage.setAdapter(myAdapter);
        // 设置默认分割线  Set the default divider
        rvShowManage.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        myAdapter.setOnItemClickBlock(new MyAdapter.OnItemClickBlock() {
            @Override
            public void onItemClick(View view, int position) {
                UserBean userBean = userBeanList.get(position);
                if (userBean.getType().equals("2")){
                    Intent intent = new Intent(AuthenManageActivity.this, TeacherManageActivity.class);
                    intent.putExtra("account",userBean.getAccount());
                    intent.putExtra("FromWhere","AuthenManageActivity");
                    startActivity(intent);
                }else {
                    Toast.makeText(mContext, "Not modifiable", Toast.LENGTH_SHORT).show();
                }

            }
        });
        
    }

    private void initRecycleView() {
        rbAuthen.setChecked(false);
        rbTeacher.setChecked(false);
        rbStudent.setChecked(false);
        etAddAccount.setText("");
        etAddPwd.setText("");
        etStudentNumber.setText("");
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
        myAdapter.notifyDataSetChanged();
    }
}