package au.edu.federation.itech3107.studentattendance30395629.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtils {
    //读取数据  Read data
    public static Cursor getSchoolData(SQLiteDatabase database) {
        return database.query("user",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    //插入数据  Insert data
    public static void insertToSchoolData(SQLiteDatabase database, ContentValues values) {
        database.insert("user", null, values);
    }

    //根据账号删除方法封装  Encapsulated by account deletion method
    public static void deleteSchoolData(SQLiteDatabase database, String name) {
        database.delete("user", "account=?", new String[]{name});
    }

    //修改数据  Modify data
    public static void updateSchoolData(SQLiteDatabase database, ContentValues values, String name) {
        database.update("user", values, "account=?", new String[]{name});
    }


    //读取数据  Read data
    public static Cursor getStudentData(SQLiteDatabase database) {
        return database.query("student",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    //插入数据  Insert data
    public static void insertToStudentData(SQLiteDatabase database, ContentValues values) {
        database.insert("student", null, values);
    }

    //根据账号删除方法封装  Encapsulated by account deletion method
    public static void deleteStudentData(SQLiteDatabase database, String name) {
        database.delete("student", "account=?", new String[]{name});
    }

    //修改数据  Modify data
    public static void updateStudentData(SQLiteDatabase database, ContentValues values, String name) {
        database.update("student", values, "account=?", new String[]{name});
    }


}
