package com.example.quanlydiemsinhvien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quanlydiemsinhvien.R;
import com.example.quanlydiemsinhvien.data_models.Accounts;

import java.util.ArrayList;

public class PhongDaoTaoAdapter extends ArrayAdapter {
    TextView tvIDPhongDaoTao, tvPassword;
    ArrayList<Accounts> dataAccounts;
    Context context;
    int resource;
    public PhongDaoTaoAdapter(Context context, int resource, ArrayList<Accounts> dataAccounts) {
        super(context, resource, dataAccounts);
        this.context = context;
        this.resource = resource;
        this.dataAccounts = dataAccounts;
    }
    @Override
    public int getCount() {
        return dataAccounts.size();
    }
    @Override
    public View getView(int position, View recycleView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(recycleView==null){
            recycleView = LayoutInflater.from(context).inflate(R.layout.rclist_item_phongdaotao,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvIDPhongDaoDao = (TextView)recycleView.findViewById(R.id.tvIDPhongDaoTao);
            viewHolder.tvPassword = (TextView)recycleView.findViewById(R.id.tvPassword);

            recycleView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) recycleView.getTag();
        }
        //Set text:
        Accounts accounts = dataAccounts.get(position);
        tvIDPhongDaoTao.setText(accounts.getId());
        tvPassword.setText(accounts.getPassword());
        return recycleView;
    }
    public class ViewHolder{
        private TextView tvIDPhongDaoDao;
        private TextView tvPassword;
    }
}