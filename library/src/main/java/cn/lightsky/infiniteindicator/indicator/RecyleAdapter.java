package cn.lightsky.infiniteindicator.indicator;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import cn.lightsky.infiniteindicator.jakewharton.salvage.RecyclingPagerAdapter;
import cn.lightsky.infiniteindicator.slideview.PageView;
import cn.lightsky.infiniteindicator.slideview.SimpleSliderView;
import cn.lightsky.infiniteindicator.slideview.SliderView;

public class RecyleAdapter extends RecyclingPagerAdapter implements SliderView.BitmapLoadCallBack {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SliderView> mSliderViews;
    private DataChangeListener mDataChangeListener;
    private boolean isLoop = true;

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
        return (mSliderViews.get(getPosition(position))).getView();
    }

    public  <T extends SliderView> void addSlider(T slider) {
        slider.setOnImageLoadListener(this);
        mSliderViews.add(slider);
        notifyDataSetChanged();
    }

    public  <T extends SliderView> void removeSlider(T slider) {
        if (mSliderViews.contains(slider)) {
            mSliderViews.remove(slider);
            notifyDataSetChanged();
        }
    }

    private void removeSliderAt(int position) {
        mSliderViews.remove(position);
        notifyDataSetChanged();
    }

    public void removeAllSliders() {
        mSliderViews.clear();
        notifyDataSetChanged();
    }

    //    public void refreshSliders(List<Object> urls) {
//        Log.i("Test", "before   mSliderViews   size = " + mSliderViews.size());
//        Log.i("Test", "urls  size = " + urls.size());
//        Log.i("Test", " gap size = " + (mSliderViews.size() - urls.size()));
//
//        int sliderSize = mSliderViews.size();
//        if (mSliderViews.size() > urls.size()) {
//            for (int i = sliderSize - 1 ; i >= urls.size()  ; i--) {
//                removeSliderAt(i);
//            }
//
//        } else if(mSliderViews.size() < urls.size()){
//            ArrayList<PageView> newSliders = new ArrayList<>();
//            for (int i = 0; i < urls.size() - sliderSize; i++) {
//                PageView pageView = new PageView();
//                pageView.scaleType = templePageView.scaleType;
//                pageView.onSliderClickListener = templePageView.onSliderClickListener;
//
//                Object url = urls.get(i);
//                if (url instanceof String)
//                    pageView.url = (String)url;
//                else if (url instanceof Integer)
//                    pageView.drawableRes = (Integer)url;
//                else if (url instanceof File)
//                    pageView.file = (File)url;
//                else
//                    continue;
//
//                newSliders.add(pageView);
////                addSlider(pageView);
//            }
//            addSliders(newSliders);
//        }
//
//        Log.i("Test", "after mSliderViews   size = " + mSliderViews.size());
//
//        for (int index = 0; index < mSliderViews.size(); index++) {
//            mSliderViews.get(index).refreshView(urls.get(index));
//        }

    private List<PageView> mPageViews = new ArrayList<>();

    public <T extends SliderView> void addSliders(List<PageView> pageViews,Class sliderType) {

        for (PageView pageView : pageViews) {
            SimpleSliderView slideView = new SimpleSliderView(mContext);
            try {
                Constructor constructor = sliderType.getConstructor(Context.class);
                SimpleSliderView str2 = (SimpleSliderView) constructor.newInstance(mContext);


            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //在调用方法的时候也需要传递同样类型的对象

            slideView.setOnImageLoadListener(this);

            if (!TextUtils.isEmpty(pageView.url))
                slideView.image(pageView.url);
            else if (pageView.drawableRes != 0)
                slideView.image(pageView.drawableRes);
            else if (pageView.file != null)
                slideView.image(pageView.file);
            else
                continue;

            if (pageView.scaleType != null)
                slideView.setScaleType(pageView.scaleType);

            if (pageView.onSliderClickListener != null)
                slideView.setOnSliderClickListener(pageView.onSliderClickListener);

            if (pageView.data != null)
                slideView.getBundle()
                        .putString("extra", pageView.data);

            if (pageView.imageResForError != 0)
                slideView.showImageResForError(pageView.imageResForError);

            if (pageView.imageResForEmpty != 0)
                slideView.showImageResForEmpty(pageView.imageResForEmpty);

            addSlider(slideView);
        }

    }


    public void refreshSliders(List<PageView> newPageViews) {
        Log.i("Test", "before   mSliderViews   size = " + mSliderViews.size());
        Log.i("Test", "urls  size = " + newPageViews.size());
        Log.i("Test", " gap size = " + (mSliderViews.size() - newPageViews.size()));

        int dirtySliderSize = mSliderViews.size();
        if (mSliderViews.size() > newPageViews.size()) {
            for (int i = dirtySliderSize - 1; i >= newPageViews.size(); i--) {
                removeSliderAt(i);
            }

        } else if (dirtySliderSize < newPageViews.size()) {
            ArrayList<PageView> newSliders = new ArrayList<>();

            for (int index = 0; index < newPageViews.size(); index++) {
                if (index < dirtySliderSize) {
                    PageView oldPageView = mSliderViews.get(index).pageView;
                    oldPageView = newPageViews.get(index);
                } else {
                    newSliders.add(newPageViews.get(index));
                }

            }
            addSliders(newSliders);
        }

        Log.i("Test", "after mSliderViews   size = " + mSliderViews.size());

        for (int index = 0; index < newPageViews.size(); index++) {
            PageView pageView = newPageViews.get(index);
            Object data = null;
            if (pageView.url != null) {
                data = pageView.url;
            } else if (pageView.drawableRes != null) {
                data = pageView.drawableRes;
            } else if (pageView.file != null) {
                data = pageView.file;
            } else {
                continue;
            }
            mSliderViews.get(index).refreshView(data);
        }
    }

//    }

    @Override
    public void onLoadStart(SliderView target) {
    }

    @Override
    public void onLoadComplete(SliderView target) {
    }

    @Override
    public void onLoadFail(SliderView target) {

        if (target.isShowErrorView()) {
            return;
        }

        for (SliderView slider : mSliderViews) {
            if (slider.equals(target)) {
                removeSlider(target);
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
