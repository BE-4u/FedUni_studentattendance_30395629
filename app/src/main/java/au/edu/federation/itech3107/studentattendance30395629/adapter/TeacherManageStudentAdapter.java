package au.edu.federation.itech3107.studentattendance30395629.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395629.R;
import au.edu.federation.itech3107.studentattendance30395629.bean.UserBean;
import au.edu.federation.itech3107.studentattendance30395629.utils.SpUtils;

public class TeacherManageStudentAdapter extends RecyclerView.Adapter<TeacherManageStudentAdapter.MyViewHolder> {

    private Context mContext;
    private List<UserBean> stringList;
    private String course;
    private String selectedValue;
    private SpUtils mSpUtils;

    // Item点击事件回调  Item click event callback
    private OnItemClickBlock mOnItemClickBlock;

    // 构造方法  Construction method
    public TeacherManageStudentAdapter(Context mContext, List<UserBean> mList, String course, SpUtils mSpUtils) {
        this.mContext = mContext;
        this.stringList = mList;
        this.course = course;
        this.mSpUtils = mSpUtils;
    }

    public void setSelectedValue(String selectedValue){
        this.selectedValue = selectedValue;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_teacher_record, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserBean userBean = stringList.get(position);
        holder.itemValue.setText("Account:"+userBean.getAccount() + "\nStudentID:" +userBean.getSchool_num());
        holder.cbCheckBox.setChecked(mSpUtils.getBoolean(course + "," + userBean.getSchool_num() + "," + selectedValue));

        holder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mSpUtils.putBoolean(course + "," + userBean.getSchool_num() + "," + selectedValue,b);
            }
        });
    }

    public List<UserBean> getData(){
        return stringList;
    }


    @Override
    public int getItemCount() {
        return stringList.size();
    }

    // ViewHolder重用组件  The ViewHolder reuses the component
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemValue;
        private final CheckBox cbCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemValue = itemView.findViewById(R.id.itemValue);
            cbCheckBox = itemView.findViewById(R.id.cbCheckBox);
        }
    }

    // Item点击事件回调  Item click event callback
    public interface OnItemClickBlock {
        void onItemClick(View view, UserBean value);
    }

    public void setOnItemClickBlock(OnItemClickBlock onItemClickListener) {
        this.mOnItemClickBlock = onItemClickListener;
    }

}


