package cn.lightsky.infiniteindicator.recycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.lightsky.infiniteindicator.ImageLoader;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import cn.lightsky.infiniteindicator.R;

/**
 * Created by lightsky on 2016/11/18.
 */

public class BaseViewBinder implements ViewBinder {

    @Override
    public View bindView(Context context,
                         final int position,
                         final Page page,
                         ImageLoader imageLoader,
                         final OnPageClickListener mOnPageClickListener,
                         View convertView,
                         ViewGroup container) {

        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.simple_slider_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        if (holder.target != null) {
            if (mOnPageClickListener != null) {
                holder.target.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnPageClickListener.onPageClick(position, page);
                    }
                });
            }

            if (imageLoader != null) {
                imageLoader.load(context, holder.target, page.res);
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        final ImageView target;

        public ViewHolder(View view) {
            target = (ImageView) view.findViewById(R.id.slider_image);
        }
    }
}
