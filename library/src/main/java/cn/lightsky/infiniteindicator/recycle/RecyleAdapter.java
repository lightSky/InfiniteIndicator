package cn.lightsky.infiniteindicator.recycle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.lightsky.infiniteindicator.ImageLoader;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;

import static cn.lightsky.infiniteindicator.InfiniteIndicator.PAGE_COUNT_FACTOR;

public class RecyleAdapter extends RecyclingPagerAdapter {

    private ViewBinder mViewBinder;
    private Context mContext;
    private ImageLoader mImageLoader;
    private OnPageClickListener mOnPageClickListener;
    private List<Page> mPageList = new ArrayList<>();
    private boolean isLoop = true;

    public RecyleAdapter(Context context, ViewBinder viewBinder) {
        this(context, viewBinder,null);
    }

    public RecyleAdapter(Context context, ViewBinder viewBinder, OnPageClickListener onPageClickListener) {
        this.mContext = context;
        this.mOnPageClickListener = onPageClickListener;
        this.mViewBinder = viewBinder;
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    public int getRealPosition(int position) {
        return isLoop ? position % getRealCount() : position;
    }

    @Override
    public int getCount() {
        return isLoop ? getRealCount() * PAGE_COUNT_FACTOR : getRealCount();
    }

    public int getRealCount() {
        return mPageList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {

        final Page page = mPageList.get(getRealPosition(position));
        convertView =  mViewBinder.bindView(
                mContext,
                getRealPosition(position),
                page,
                mImageLoader,
                mOnPageClickListener,
                convertView,
                container);

        return convertView;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    public void setPages(List<Page> pages) {
        this.mPageList = pages;
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
