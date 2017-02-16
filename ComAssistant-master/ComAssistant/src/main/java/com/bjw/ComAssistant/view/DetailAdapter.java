package com.bjw.ComAssistant.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjw.ComAssistant.R;
import com.bjw.ComAssistant.bean.product.ProductBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hui on 2016/11/12.
 */

public class DetailAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ProductBean> mDetailBeanList = new ArrayList<>();
    private int mCheckPosition = -1;

    public DetailAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<ProductBean> mDetailBeanList) {
        this.mDetailBeanList = mDetailBeanList;
        this.notifyDataSetChanged();
    }

    public void setCheckPosition(int position) {
        mCheckPosition = position;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDetailBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ProductBean detailBean = mDetailBeanList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_detail, null);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
            holder.tv_real = (TextView) convertView.findViewById(R.id.tv_real);
            holder.ll_all = (LinearLayout) convertView.findViewById(R.id.ll_all);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == mCheckPosition) {
            holder.ll_all.setBackgroundResource(R.color.gray_et_787878);
        } else {
            holder.ll_all.setBackgroundResource(R.color.white);
        }
        holder.tv_num.setText(detailBean.getLine_num());
        holder.tv_name.setText(detailBean.getGoods_name());
        holder.tv_weight.setText(detailBean.getQuantity() + detailBean.getQuantity_unit());
        if (detailBean.getQuantity_real() != null && !"".equals(detailBean.getQuantity_real())) {
            holder.tv_real.setText(detailBean.getQuantity_real() + detailBean.getQuantity_unit());
        } else {
            holder.tv_real.setText(detailBean.getQuantity_real());
        }
        return convertView;
    }

    private class ViewHolder {
        private LinearLayout ll_all;
        private TextView tv_num;
        private TextView tv_name;
        private TextView tv_weight;
        private TextView tv_real;
    }
}
