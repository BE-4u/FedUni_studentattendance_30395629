package au.edu.federation.itech3107.studentattendance30395629.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper openhelper;
    //用户表  User table
    private String create = "create table user" + "(_id  integer primary key " + "autoincrement," +
            "type" + " text," +//类型 type
            "account" + " text," +//账号 account
            "password" + " text," +//密码 password
            "school_num" + " text," +//学校里的号 school number
            "other" + " text," +//其他 other
            "standby" + " text" + ")";//备用 standby

    //考勤记录表  Attendance sheet
    private String record = "create table student" + "(_id  integer primary key " + "autoincrement," +
            "account" + " text," +//账号  account
            "school_num" + " text," +//学校里的号  school number
            "start" + " text," +//状态   status
            "standby" + " text" + ")";//备用  standby
    private DbHelper(Context context) {
        super(context, "school.db", null, 2);
    }

    //单例模式获取数据库帮助类   The singleton schema gets the database help class
    public static DbHelper getInstance(Context context) {
        if (openhelper == null) {
            openhelper = new DbHelper(context);
        }
        return openhelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表  create table
        db.execSQL(create);
        db.execSQL(record);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
