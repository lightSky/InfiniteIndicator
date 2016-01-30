package cn.lightsky.infiniteindicator.slideview;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import cn.lightsky.infiniteindicator.ImageLoader.ImageLoader;
import cn.lightsky.infiniteindicator.ImageLoader.PiacassoLoader;
import cn.lightsky.infiniteindicator.R;

/**
 * When you want to make your own slider view, you must extends from this class.
 * BaseSliderView provides some useful methods.
 * if you want to show progressbar, you just need to set a progressbar id as @+id/loading_bar.
 * <p/>
 * Thanks to :  https://github.com/daimajia/AndroidImageSlider
 */
public abstract class SliderView {

    protected Context mContext;

    private Bundle mBundle;

    public PageView pageView;

    /**
     * image will be displayed in targetImageView if some error occurs during.
     */
    private int mImageResForError;

    /**
     * image will be displayed in targetImageView if empty url(null or emtpy String).
     */
    private int mImageResForEmpty;

    private boolean mIsShowErrorView;

    private String mUrl;
    private File mFile;
    private int mRes;
    private Object mData;

    protected OnSliderClickListener mOnSliderClickListener;

    private BitmapLoadCallBack mBitmapLoadListener;

    private View viewHolder;
    private ImageView targetView;

    /**
     * Scale type of the image.
     */
    private ScaleType mScaleType = ScaleType.Fit;

    public enum ScaleType {
        CenterCrop, CenterInside, Fit, FitCenterCrop
    }

    protected SliderView(Context context) {
        mContext = context;
        this.mBundle = new Bundle();
    }

    /**
     * the placeholder image when loading image from url or file.
     *
     * @param resId Image resource id
     * @return
     */
    public SliderView showImageResForEmpty(int resId) {
        mImageResForEmpty = resId;
        return this;
    }

    /**
     * if you set isShowErrorView false, this will set a error placeholder image.
     *
     * @param resId image resource id
     * @return
     */
    public SliderView showImageResForError(int resId) {
        mImageResForError = resId;
        return this;
    }

    public int getImageResForEmpty() {
        return mImageResForEmpty;
    }

    public int getImageResForError() {
        return mImageResForError;
    }

    /**
     * determine whether remove the image which failed to download or load from file
     *
     * @param disappear
     * @return
     */
    public SliderView isShowErrorView(boolean disappear) {
        mIsShowErrorView = disappear;
        return this;
    }


    /**
     * set a url as a image that preparing to load
     *
     * @param url
     * @return
     */
    public SliderView image(Object data) {
        if (data instanceof String) {
            if (mFile != null || mRes != 0) {
                throw new IllegalStateException("Call multi image function," +
                        "you only have permission to call it once");
            }
        } else if (data instanceof File){
            if (mUrl != null || mRes != 0) {
                throw new IllegalStateException("Call multi image function," +
                        "you only have permission to call it once");
            }
        } else if (data instanceof Integer){
            if (mUrl != null || mFile != null) {
                throw new IllegalStateException("Call multi image function," +
                        "you only have permission to call it once");
            }
        } else {
            throw new IllegalArgumentException("Not support data type,only can be url,drawableRes and file");
        }

        mData = data;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean isShowErrorView() {
        return mIsShowErrorView;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * set click listener on a slider image
     *
     * @param l
     * @return
     */
    public SliderView setOnSliderClickListener(OnSliderClickListener l) {
        mOnSliderClickListener = l;
        return this;
    }

    /**
     * When you want to implement your own slider view, please call this method in the end in `getView()` method
     *
     * @param v               the whole view
     * @param targetImageView where to place image
     */
    protected void bingView(final View v, ImageView targetImageView) {
        this.viewHolder = v;
        this.targetView = targetImageView;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null) {
                    mOnSliderClickListener.onSliderClick(SliderView.this);
                }
            }
        });

        if (targetImageView == null)
            return;

        mBitmapLoadListener.onLoadStart(SliderView.this);

        loadByPicasso(v, targetImageView);
    }

    public void refreshView(Object data) {
        mData = data;
        //hasn't bindview just return,but refresh res
        if (this.viewHolder == null || this.targetView == null)
            return;

        loadByPicasso(this.viewHolder, this.targetView);
    }

    protected void loadByPicasso(final View view, ImageView targetImageView) {
        if (view == null || targetImageView == null)
            throw new IllegalArgumentException("view or imageview is null");

//         if (mData instanceof String) {
//             PiacassoLoader.load(mContext,targetImageView,(String) mData);
//        } else if (mData instanceof File){
//             PiacassoLoader.load(mContext,targetImageView,(File) mData);
//        } else if (mData instanceof Integer){
//             PiacassoLoader.load(mContext,targetImageView,(Integer) mData);
//        }else {
//            return;
//        }

        final SliderView me = this;
        Picasso p = Picasso.with(mContext);
        RequestCreator rq = null;

        if (mUrl != null) {
            rq = p.load(mUrl);
        } else if (mFile != null) {
            rq = p.load(mFile);
        } else if (mRes != 0) {
            rq = p.load(mRes);
        } else {
            return;
        }

        if (rq == null) {
            return;
        }

        switch (mScaleType) {
            case Fit:
                rq.fit();
                break;
            case CenterCrop:
                rq.fit().centerCrop();
                break;
            case CenterInside:
                rq.fit().centerInside();
                break;
        }

        rq.into(targetImageView, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
            }
        });
    }

    public SliderView setScaleType(ScaleType type) {
        mScaleType = type;
        return this;
    }

    public ScaleType getScaleType() {
        return mScaleType;
    }

    /**
     * the extended class have to implement getView(), which is called by the adapter,
     * every extended class response to render their own view.
     *
     * @return
     */
    public abstract View getView();

    /**
     * set a listener to get a message , if load error.
     *
     * @param l
     */
    public void setOnImageLoadListener(BitmapLoadCallBack l) {
        mBitmapLoadListener = l;
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
