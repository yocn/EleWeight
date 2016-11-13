package eleweigh.woxian.com.eleweight.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eleweigh.woxian.com.eleweight.R;
import eleweigh.woxian.com.eleweight.bean.DetailBean;

/**
 * Created by Hui on 2016/11/12.
 */

public class DetailAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<DetailBean> mDetailBeanList = new ArrayList<>();

    public DetailAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<DetailBean> mDetailBeanList) {
        this.mDetailBeanList = mDetailBeanList;
        System.out.println(mDetailBeanList.toString());
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
        DetailBean detailBean = mDetailBeanList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_detail, null);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
            holder.tv_real = (TextView) convertView.findViewById(R.id.tv_real);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_num.setText(detailBean.getNum());
        holder.tv_name.setText(detailBean.getName());
        holder.tv_weight.setText(detailBean.getWeight());
        holder.tv_real.setText(detailBean.getRead());
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_num;
        private TextView tv_name;
        private TextView tv_weight;
        private TextView tv_real;
    }
}
