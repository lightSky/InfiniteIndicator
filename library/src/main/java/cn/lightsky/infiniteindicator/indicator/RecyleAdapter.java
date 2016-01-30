package cn.lightsky.infiniteindicator.indicator;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import cn.lightsky.infiniteindicator.R;
import cn.lightsky.infiniteindicator.loader.ImageLoader;
import cn.lightsky.infiniteindicator.jakewharton.salvage.RecyclingPagerAdapter;
import cn.lightsky.infiniteindicator.slideview.PageView;
import cn.lightsky.infiniteindicator.slideview.SliderView;

public class RecyleAdapter<T extends SliderView> extends RecyclingPagerAdapter implements SliderView.BitmapLoadCallBack {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SliderView> mSliderViews;
    private DataChangeListener mDataChangeListener;
    public ImageLoader mImageLoader;
    private boolean isLoop = true;
    public List<PageView> mPageViews = new ArrayList<>();
    private T mType;

    public RecyleAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mSliderViews = new ArrayList<SliderView>();
    }

    public int getRealCount() {
        return mSliderViews.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    public int getPosition(int position) {
        return isLoop ? position % getRealCount() : position;
    }

    @Override
    public int getCount() {
        return isLoop ? getRealCount() * 100 : getRealCount();
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

        PageView pageView = mPageViews.get(getPosition(position));
        mImageLoader.instance(mContext).load(holder.target,pageView.url);

//        mPageViews.get(getPosition(position)).bindView(convertView, holder.target);
        return convertView;
    }

    private static class ViewHolder {
        final ImageView target;

        public ViewHolder(View view) {
            target = (ImageView) view.findViewById(R.id.slider_image);
        }
    }

//    public  <T extends SliderView> void addSlider(T slider) {
//        slider.setOnImageLoadListener(this);
//        mSliderViews.add(slider);
//        notifyDataSetChanged();
//    }
//
//    public  <T extends SliderView> void removeSlider(T slider) {
//        if (mSliderViews.contains(slider)) {
//            mSliderViews.remove(slider);
//            notifyDataSetChanged();
//        }
//    }

    public  <T extends SliderView> void addPage(PageView pageView) {
        mPageViews.add(pageView);
        notifyDataSetChanged();
    }

    public  <T extends SliderView> void removePage(PageView pageView) {
        if (mPageViews.contains(pageView)) {
            mPageViews.remove(pageView);
            notifyDataSetChanged();
        }
    }

//    private void removeSliderAt(int position) {
//        mSliderViews.remove(position);
//        notifyDataSetChanged();
//    }

//    public void removeAllSliders() {
//        mSliderViews.clear();
//        notifyDataSetChanged();
//    }


    /**
     * Add sliders by given type
     *
     * @param pageViews
     * @param sliderType
     */
//    public  void addSliders(List<PageView> pageViews,T sliderType) {
//        mType = (T) sliderType;
//
//        for (PageView pageView : pageViews) {
//
//            try {
//                Constructor constructor = sliderType.getClass().getConstructor(Context.class);
//                T slideView = (T) constructor.newInstance(mContext);
//
//                slideView.setOnImageLoadListener(this);
//
//                if (!TextUtils.isEmpty(pageView.url))
//                    slideView.image(pageView.url);
//                else if (pageView.drawableRes != 0)
//                    slideView.image(pageView.drawableRes);
//                else if (pageView.file != null)
//                    slideView.image(pageView.file);
//                else
//                    continue;
//
//                if (pageView.onSliderClickListener != null)
//                    slideView.setOnSliderClickListener(pageView.onSliderClickListener);
//
//                if (pageView.data != null)
//                    slideView.getBundle()
//                            .putString("extra", pageView.data);
//
//                slideView.imageLoader = mImageLoader;
//                slideView.pageView = pageView;
//                addSlider(slideView);
//
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }


    /**
     * refresh current sliders
     * if new pageviews is less than before,remove unavailable sliders
     * else if there is more refresh data,add new sliders
     *
     * @param newPageViews
     */
//    public void refreshSliders(List<PageView> newPageViews) {
//        Log.i("Test", "before   mSliderViews   size = " + mSliderViews.size());
//        Log.i("Test", "urls  size = " + newPageViews.size());
//        Log.i("Test", " gap size = " + (mSliderViews.size() - newPageViews.size()));
//
//        int dirtySliderSize = mSliderViews.size();
//        if (mSliderViews.size() > newPageViews.size()) {
//            for (int i = dirtySliderSize - 1; i >= newPageViews.size(); i--) {
//                removeSliderAt(i);
//            }
//
//        } else if (dirtySliderSize < newPageViews.size()) {
//            ArrayList<PageView> newSliders = new ArrayList<>();
//            for (int index = 0; index < newPageViews.size(); index++) {
//                if (index < dirtySliderSize) {
//                    PageView oldPageView = mSliderViews.get(index).pageView;
//                    oldPageView = newPageViews.get(index);
//                } else {
//                    newSliders.add(newPageViews.get(index));
//                }
//
//            }
//            addSliders(newSliders,mType);
//        }
//
//        Log.i("Test", "after mSliderViews   size = " + mSliderViews.size());
//
//        for (int index = 0; index < newPageViews.size(); index++) {//refresh
//            PageView pageView = newPageViews.get(index);
//            Object data = null;
//            if (pageView.url != null) {
//                data = pageView.url;
//            } else if (pageView.drawableRes != null) {
//                data = pageView.drawableRes;
//            } else if (pageView.file != null) {
//                data = pageView.file;
//            } else {
//                continue;
//            }
//            mSliderViews.get(index).refreshView(data);
//        }
//    }

    @Override
    public void onLoadStart(SliderView target) {
    }

    @Override
    public void onLoadComplete(SliderView target) {
    }

    @Override
    public void onLoadFail(SliderView target) {
        for (SliderView slider : mSliderViews) {
            if (slider.equals(target)) {
//                removeSlider(target);
                break;
            }
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
