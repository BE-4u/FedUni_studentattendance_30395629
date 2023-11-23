package au.edu.federation.itech3107.studentattendance30395629.ui.authen;

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
import au.edu.federation.itech3107.studentattendance30395629.db.DbHelper;

public class AuthenticationActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    public void initView() {
        EditText etAccount = findViewById(R.id.etAccount);
        EditText etPsd = findViewById(R.id.etPsd);
        AppCompatButton btLogin = findViewById(R.id.btLogin);



        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = etAccount.getText().toString();
                String password = etPsd.getText().toString();
                if ("admin".equals(account) && "123456".equals(password)){
                    startActivity(new Intent(AuthenticationActivity.this,AuthenManageActivity.class));
                }else {
                    Cursor schoolData = DatabaseUtils.getSchoolData(mSQLiteDatabase);
                    if (schoolData.getCount() == 0){
                        Toast.makeText(mContext, "Please use admin login for initial login！", Toast.LENGTH_SHORT).show();
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
                            if ("1".equals(userBeanList.get(i).getType())){
                                if (account.equals(userBeanList.get(i).getAccount()) &&
                                        password.equals(userBeanList.get(i).getPassword())){
                                    startActivity(new Intent(AuthenticationActivity.this,AuthenManageActivity.class));
                                    return;
                                }
                            }
                        }
                        Toast.makeText(mContext, "Account or password error！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}