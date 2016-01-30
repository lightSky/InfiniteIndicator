package cn.lightsky.infiniteindicator.ImageLoader;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import cn.lightsky.infiniteindicator.R;

/**
 * Created by lightsky on 16/1/28.
 */
public class PiacassoLoader {

    public static void load(Context context, ImageView targetView, Object mData) {

        if (mData == null) {
            return;
        }

        Picasso p = Picasso.with(context);
        RequestCreator rq = null;
        if (mData instanceof String) {
            rq = p.load((String) mData);
//            PiacassoLoader.load(mContext,targetImageView,(String) mData);
        } else if (mData instanceof File) {
            rq = p.load((File) mData);
//            PiacassoLoader.load(mContext,targetImageView,(File) mData);
        } else if (mData instanceof Integer) {
            rq = p.load((Integer) mData);
//            PiacassoLoader.load(mContext,targetImageView,(Integer) mData);
        }

        rq.into(targetView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

    }

//    protected void loadByPicasso(final View view, ImageView targetImageView,PageView pageview) {
//        final BaseSliderView me = this;
//        Picasso p = Picasso.with(mContext);
//        RequestCreator rq = null;
//
//        if (mUrl != null) {
//            rq = p.load(mUrl);
//        } else if (mFile != null) {
//            rq = p.load(mFile);
//        } else if (mRes != 0) {
//            rq = p.load(mRes);
//        } else {
//            return;
//        }
//
//        if (rq == null) {
//            return;
//        }
//
//        switch (mScaleType) {
//            case Fit:
//                rq.fit();
//                break;
//            case CenterCrop:
//                rq.fit().centerCrop();
//                break;
//            case CenterInside:
//                rq.fit().centerInside();
//                break;
//        }
//
//        rq.into(targetImageView, new Callback() {
//            @Override
//            public void onSuccess() {
//                if (view.findViewById(R.id.loading_bar) != null) {
//                    view.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
//                }
//                if (mBitmapLoadListener != null) {
//                    mBitmapLoadListener.onLoadComplete(me);
//                }
//            }
//
//            @Override
//            public void onError() {
//                if (mBitmapLoadListener != null) {
//                    mBitmapLoadListener.onLoadFail(me);
//                }
//            }
//        });

//}
}
