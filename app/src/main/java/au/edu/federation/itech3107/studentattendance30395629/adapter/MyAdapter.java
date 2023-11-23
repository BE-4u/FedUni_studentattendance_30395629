package au.edu.federation.itech3107.studentattendance30395629.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395629.R;
import au.edu.federation.itech3107.studentattendance30395629.bean.UserBean;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<UserBean> userBeanList;

    // Item点击事件回调  Item click event callback
    private OnItemClickBlock mOnItemClickBlock;

    // 构造方法  Construction method
    public MyAdapter(Context mContext, List<UserBean> mList) {
        this.mContext = mContext;
        this.userBeanList = mList;
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
        UserBean userBean = userBeanList.get(position);
        if (userBean.getType().equals("1")){
            holder.itemValue.setText("Administrators   " + "Account："+userBean.getAccount());
        }else if (userBean.getType().equals("2")){
            holder.itemValue.setText("Teacher   " + "Account："+userBean.getAccount());
        }else {
            holder.itemValue.setText("Student   " + "Account："+userBean.getAccount());
        }
        holder.itemValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                mOnItemClickBlock.onItemClick(view, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userBeanList.size();
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
        void onItemClick(View view, int position);
    }

    public void setOnItemClickBlock(OnItemClickBlock onItemClickListener) {
        this.mOnItemClickBlock = onItemClickListener;
    }

}


