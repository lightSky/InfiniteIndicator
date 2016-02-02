package cn.lightsky.infiniteindicator.indicator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.lightsky.infiniteindicator.R;
import cn.lightsky.infiniteindicator.loader.ImageLoader;
import cn.lightsky.infiniteindicator.jakewharton.salvage.RecyclingPagerAdapter;
import cn.lightsky.infiniteindicator.page.OnPageClickListener;
import cn.lightsky.infiniteindicator.page.Page;

public class RecyleAdapter extends RecyclingPagerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private OnPageClickListener mOnPageClickListener;
    private List<Page> pages = new ArrayList<>();
    private boolean isLoop = true;

    public RecyleAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
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
    public int getPosition(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.simple_slider_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        final Page page = pages.get(getPosition(position));

        if(page.onPageClickListener != null){
            holder.target.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    page.onPageClickListener.onPageClick(getPosition(position), page);
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
    }

    public void addPage(Page page) {
        pages.add(page);
        notifyDataSetChanged();
    }

    public void removePage(Page page) {
        if (pages.contains(page)) {
            pages.remove(page);
            notifyDataSetChanged();
        }
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
    public void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
        notifyDataSetChanged();
    }

}
