package cn.lightsky.infiniteindicator.slideview;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import cn.lightsky.infiniteautoscroolview.R;

/**
 * When you want to make your own slider view, you must extends from this class.
 * BaseSliderView provides some useful methods.
 * if you want to show progressbar, you just need to set a progressbar id as @+id/loading_bar.
 *
 * Thanks to :  https://github.com/daimajia/AndroidImageSlider
 */
public abstract class BaseSliderView {

    protected Context mContext;

    private Bundle mBundle;

    /**
     *  image will be displayed in targetImageView if some error occurs during.
     */
    private int mImageResForError;

    /**
     *  image will be displayed in targetImageView if empty url(null or emtpy String).
     */
    private int mImageResForEmpty;

    private String mUrl;
    private File mFile;
    private int mRes;

    protected OnSliderClickListener mOnSliderClickListener;

    private boolean mIsShowErrorView;

    private BitmapLoadCallBack mBitmapLoadListener;

    /**
     * Scale type of the image.
     */
    private ScaleType mScaleType = ScaleType.Fit;

    public enum ScaleType{
        CenterCrop, CenterInside, Fit, FitCenterCrop
    }

    protected BaseSliderView(Context context) {
        mContext = context;
        this.mBundle = new Bundle();
    }

    /**
     * the placeholder image when loading image from url or file.
     * @param resId Image resource id
     * @return
     */
    public BaseSliderView showImageResForEmpty(int resId){
        mImageResForEmpty = resId;
        return this;
    }

    /**
     * if you set isShowErrorView false, this will set a error placeholder image.
     * @param resId image resource id
     * @return
     */
    public BaseSliderView showImageResForError(int resId){
        mImageResForError = resId;
        return this;
    }

    public int getImageResForEmpty(){
        return mImageResForEmpty;
    }

    public int getImageResForError(){
        return mImageResForError;
    }

    /**
     * determine whether remove the image which failed to download or load from file
     * @param disappear
     * @return
     */
    public BaseSliderView isShowErrorView(boolean disappear){
        mIsShowErrorView = disappear;
        return this;
    }

    /**
     * set a url as a image that preparing to load
     * @param url
     * @return
     */
    public BaseSliderView image(String url){
        if(mFile != null || mRes != 0){
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mUrl = url;
        return this;
    }

    /**
     * set a file as a image that will to load
     * @param file
     * @return
     */
    public BaseSliderView image(File file){
        if(mUrl != null || mRes != 0){
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mFile = file;
        return this;
    }

    public BaseSliderView image(int res){
        if(mUrl != null || mFile != null){
            throw new IllegalStateException("Call multi image function," +
                    "you only have permission to call it once");
        }
        mRes = res;
        return this;
    }

    public String getUrl(){
        return mUrl;
    }

    public boolean isShowErrorView(){
        return mIsShowErrorView;
    }

    public Context getContext(){
        return mContext;
    }

    /**
     * set click listener on a slider image
     * @param l
     * @return
     */
    public BaseSliderView setOnSliderClickListener(OnSliderClickListener l){
        mOnSliderClickListener = l;
        return this;
    }

    /**
     * When you want to implement your own slider view, please call this method in the end in `getView()` method
     * @param v the whole view
     * @param targetImageView where to place image
     */
    protected void bindEventAndShow(final View v, ImageView targetImageView){
        final BaseSliderView me = this;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(mOnSliderClickListener != null){
                mOnSliderClickListener.onSliderClick(me);
            }
            }
        });

        if (targetImageView == null)
            return;

        mBitmapLoadListener.onLoadStart(me);

//     loadByImageLoader(v,targetImageView);
        loadByPicasso(v,targetImageView);
   }

    protected void loadByPicasso(final View v, ImageView targetImageView) {
        final BaseSliderView me = this;
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

        if (getImageResForEmpty() != 0) {
            rq.placeholder(getImageResForEmpty());
        }

        if (getImageResForError() != 0) {
            rq.error(getImageResForError());
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
                if (v.findViewById(R.id.loading_bar) != null) {
                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
                }
                if (mBitmapLoadListener != null) {
                    mBitmapLoadListener.onLoadComplete(me);
                }
            }

            @Override
            public void onError() {
                if (mBitmapLoadListener != null) {
                    mBitmapLoadListener.onLoadFail(me);
                }
            }
        });

    }

    public BaseSliderView setScaleType(ScaleType type){
        mScaleType = type;
        return this;
    }

    public ScaleType getScaleType(){
        return mScaleType;
    }

    /**
     * the extended class have to implement getView(), which is called by the adapter,
     * every extended class response to render their own view.
     * @return
     */
    public abstract View getView();

    /**
     * set a listener to get a message , if load error.
     * @param l
     */
    public void setOnImageLoadListener(BitmapLoadCallBack l){
        mBitmapLoadListener = l;
    }

    public interface OnSliderClickListener {
        public void onSliderClick(BaseSliderView slider);
    }

    /**
     * when you have some extra information, please put it in this bundle.
     * @return
     */
    public Bundle getBundle(){
        return mBundle;
    }

    public interface BitmapLoadCallBack {
        public void onLoadStart(BaseSliderView target);
        public void onLoadComplete(BaseSliderView target);
        public void onLoadFail(BaseSliderView target);
    }
}
