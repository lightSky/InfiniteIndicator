package cn.lightsky.infiniteindicator.ImageLoader;

import android.content.Context;
import android.util.Log;
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
            public static final String TAG = "Test";

            @Override
            public void onSuccess() {

                Log.i(TAG, "onSuccess: ");

            }

            @Override
            public void onError() {
                Log.i(TAG, "onSuccess: ");
            }
        });

    }
}
