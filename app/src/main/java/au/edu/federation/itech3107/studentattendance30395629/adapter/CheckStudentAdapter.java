package au.edu.federation.itech3107.studentattendance30395629.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395629.R;
import au.edu.federation.itech3107.studentattendance30395629.utils.SpUtils;

public class CheckStudentAdapter extends RecyclerView.Adapter<CheckStudentAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> stringList;
    private SpUtils mSpUtils;
    private String school_num;

    // Item点击事件回调  Item click event callback
    private OnItemClickBlock mOnItemClickBlock;

    // 构造方法  Construction method
    public CheckStudentAdapter(Context mContext, List<String> mList, SpUtils mSpUtils, String school_num) {
        this.mContext = mContext;
        this.stringList = mList;
        this.mSpUtils = mSpUtils;
        this.school_num = school_num;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_manager, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String userBean = stringList.get(position);//2023,语文,2025"
        String[] split = userBean.split(",");
        if (mSpUtils.getBoolean(userBean)){
            holder.itemValue.setText("Content:"+split[1]+"\nTime:"+split[4]+"\nAttendance:Participated");
        }else {
            holder.itemValue.setText("Content:"+split[1]+"\nTime:"+split[4]+"\nAttendance:uncommitted");
        }

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    // ViewHolder重用组件  The ViewHolder reuses the component
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemValue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemValue = itemView.findViewById(R.id.itemValue);
        }
    }

    // Item点击事件回调  Item click event callback
    public interface OnItemClickBlock {
        void onItemClick(View view, String value);
    }

    public void setOnItemClickBlock(OnItemClickBlock onItemClickListener) {
        this.mOnItemClickBlock = onItemClickListener;
    }

}


