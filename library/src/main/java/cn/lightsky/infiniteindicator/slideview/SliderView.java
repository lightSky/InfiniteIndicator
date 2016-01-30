package cn.lightsky.infiniteindicator.slideview;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.lightsky.infiniteindicator.loader.ImageLoader;

/**
 * When you want to make your own slider view, you must extends from this class.
 * BaseSliderView provides some base methods.
 *
 * <p/>
 */
public abstract class SliderView {

    protected Context mContext;
    private Bundle mBundle;
    public PageView pageView;
    private Object res;
    private View viewHolder;
    private ImageView targetView;
    protected OnSliderClickListener mOnSliderClickListener;
    private BitmapLoadCallBack mBitmapLoadListener;
    public ImageLoader imageLoader;

    protected SliderView(Context context) {
        mContext = context;
        this.mBundle = new Bundle();
    }

    /**
     * set a url as a image that preparing to load
     *
     * @param url
     * @return
     */
    public SliderView image(Object data) {
        res = data;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * set click listener on a slider image
     *
     * @param listener
     * @return
     */
    public SliderView setOnSliderClickListener(OnSliderClickListener listener) {
        mOnSliderClickListener = listener;
        return this;
    }

    /**
     * When you want to implement your own slider view, please call this method in the end in `getView()` method
     *
     * @param view               the whole view
     * @param targetView where to place image
     */
    public void bindView(final View view, ImageView targetView) {
        this.viewHolder = view;
        this.targetView = targetView;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null) {
                    mOnSliderClickListener.onSliderClick(SliderView.this);
                }
            }
        });

        if (targetView == null)
            return;

        mBitmapLoadListener.onLoadStart(SliderView.this);
        imageLoader.instance(mContext).load(targetView, res);
    }

    public void refreshView(Object res) {
        this.res = res;
        //hasn't bindview just return,but refresh res
        if (viewHolder == null || targetView == null)
            return;

        imageLoader.instance(mContext).load(targetView,res);
    }

    /**
     * the extended class have to implement getView(), which is called by the adapter,
     * every extended class response to render their own view.
     *
     * @return
     */
    public abstract View getView(int position, View view, ViewGroup container);

    /**
     * set a listener to get a message , if load error.
     *
     * @param loadCallBack
     */
    public void setOnImageLoadListener(BitmapLoadCallBack loadCallBack) {
        mBitmapLoadListener = loadCallBack;
    }

    public interface OnSliderClickListener {
        public void onSliderClick(SliderView slider);
    }

    /**
     * when you have some extra information, please put it in this bundle.
     *
     * @return
     */
    public Bundle getBundle() {
        return mBundle;
    }

    public interface BitmapLoadCallBack {
        void onLoadStart(SliderView target);

        void onLoadComplete(SliderView target);

        void onLoadFail(SliderView target);
    }
}
