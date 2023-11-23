package au.edu.federation.itech3107.studentattendance30395629.ui.student;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395629.R;
import au.edu.federation.itech3107.studentattendance30395629.base.BaseActivity;
import au.edu.federation.itech3107.studentattendance30395629.bean.UserBean;
import au.edu.federation.itech3107.studentattendance30395629.db.DatabaseUtils;

public class StudentActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_student;
    }

    @Override
    public void initView() {

        EditText btAuthentication = findViewById(R.id.btAuthentication);
        EditText btTeacher = findViewById(R.id.btTeacher);
        AppCompatButton btStudent = findViewById(R.id.btStudent);



        btStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = btAuthentication.getText().toString();
                String password = btTeacher.getText().toString();
                Cursor schoolData = DatabaseUtils.getSchoolData(mSQLiteDatabase);
                if (schoolData.getCount() == 0){
                    Toast.makeText(mContext, "The administrator has not assigned an account！", Toast.LENGTH_SHORT).show();
                }else {
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
                            if (account.equals(userBeanList.get(i).getAccount()) &&
                                    password.equals(userBeanList.get(i).getPassword())){
                                Intent intent = new Intent(StudentActivity.this, CheckActivity.class);
                                intent.putExtra("account",userBeanList.get(i).getAccount());
                                intent.putExtra("school_num",userBeanList.get(i).getSchool_num());
                                intent.putExtra("studentOther",userBeanList.get(i).getOther());
                                startActivity(intent);
                                return;
                            }
                        }
                    }
                    Toast.makeText(mContext, "Account or password error！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}