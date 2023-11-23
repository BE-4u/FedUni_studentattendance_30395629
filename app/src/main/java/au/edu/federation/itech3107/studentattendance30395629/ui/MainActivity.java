package au.edu.federation.itech3107.studentattendance30395629.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import au.edu.federation.itech3107.studentattendance30395629.R;
import au.edu.federation.itech3107.studentattendance30395629.base.BaseActivity;
import au.edu.federation.itech3107.studentattendance30395629.ui.authen.AuthenticationActivity;
import au.edu.federation.itech3107.studentattendance30395629.ui.student.StudentActivity;
import au.edu.federation.itech3107.studentattendance30395629.ui.teacher.TeacherActivity;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        findViewById(R.id.btAuthentication).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
        });

        findViewById(R.id.btAuthentication).setOnLongClickListener(v -> {

            // 创建对话框构建器  Create a dialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 设置对话框的属性  Set the properties of the dialog box
            builder.setTitle("Kind reminder!") // 设置对话框标题  Set the dialog title
                    .setMessage("Are you sure you want to delete all data？") // 设置对话框消息内容  Set dialog message content
                    .setPositiveButton("Determine", new DialogInterface.OnClickListener() { // 设置确认按钮  Set confirmation button
                        public void onClick(DialogInterface dialog, int which) {
                            // 获取PackageManager和ActivityManager的实例  Get instances of PackageManager and ActivityManager
                            PackageManager packageManager = getPackageManager();
                            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                            // 获取你的应用包名  Get your application package name
                            String packageName = getPackageName();
                            // 清除应用数据  Clear application data
                            packageManager.clearPackagePreferredActivities(packageName);
                            activityManager.clearApplicationUserData();
                            Toast.makeText(mContext, "All data cleared", Toast.LENGTH_SHORT).show();
                        }
                    });

            // 创建并显示对话框  Create and display dialog boxes
            AlertDialog dialog = builder.create();
            dialog.show();

            return false;
        });
        findViewById(R.id.btTeacher).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TeacherActivity.class));
        });
        findViewById(R.id.btStudent).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StudentActivity.class));
        });

    }
}