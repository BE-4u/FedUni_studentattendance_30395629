package au.edu.federation.itech3107.studentattendance30395629.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import au.edu.federation.itech3107.studentattendance30395629.db.DbHelper;
import au.edu.federation.itech3107.studentattendance30395629.utils.SpUtils;


public abstract class BaseActivity extends AppCompatActivity {

    //常用的一些变量 Some commonly used variables
    protected Context mContext;
    //数据库对象  Database object
    protected SQLiteDatabase mSQLiteDatabase;
    protected SpUtils mSpUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        mContext = this;
        mSQLiteDatabase = DbHelper.getInstance(mContext).getWritableDatabase();
        mSpUtils = new SpUtils(mContext);
        initView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置布局  Set layout
     */
    public abstract int getLayoutId();

    /**
     * 初始化视图  Initialize view
     */
    public abstract void initView();

}
