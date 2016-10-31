package cn.lightsky.infiniteindicator.recycle;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.lightsky.infiniteindicator.ImageLoader;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import cn.lightsky.infiniteindicator.R;

public class RecyleAdapter extends RecyclingPagerAdapter {

    private int resId;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private OnPageClickListener mOnPageClickListener;
    private List<Page> pages = new ArrayList<>();
    private boolean isLoop = true;

    public RecyleAdapter(Context context ,@IdRes int resId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.resId = resId;
    }

    public RecyleAdapter(Context context,OnPageClickListener onPageClickListener) {
        mContext = context;
        mOnPageClickListener = onPageClickListener;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    public int getRealPosition(int position) {
        return isLoop ? position % getRealCount()  : position;
    }

    @Override
    public int getCount() {
        return isLoop ? getRealCount() * 100 : getRealCount();
    }

    public int getRealCount() {
        return pages.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {

        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext)
                    .inflate(resId != 0 ? resId : R.layout.simple_slider_view , null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        final Page page = pages.get(getRealPosition(position));

        if(page.onPageClickListener != null){
            holder.target.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    page.onPageClickListener.onPageClick(getRealPosition(position), page);
                }
            });
        }

        mImageLoader.load(mContext,holder.target,page.res);
        return convertView;
    }

    private static class ViewHolder {
        final ImageView target;

        public ViewHolder(View view) {
            target = (ImageView) view.findViewById(R.id.slider_image);
        }
    }

    public void setImageLoader(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
        notifyDataSetChanged();
    }

    /**
     * @return the is Loop
     */
    public boolean isLoop() {
        return isLoop;
    }

    /**
     * @param isLoop the is InfiniteLoop to set
     */
    public void setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

}
