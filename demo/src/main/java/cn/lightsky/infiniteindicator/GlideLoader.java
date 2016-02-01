package cn.lightsky.infiniteindicator;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.lightsky.infiniteindicator.loader.ImageLoader;

/**
 * Created by lightsky on 16/1/31.
 */
public class GlideLoader implements ImageLoader {

    @Override
    public void initLoader(Context context) {

    }

    @Override
    public void load(Context context, ImageView targetView, Object res) {

        if (res instanceof String){
            Glide.with(context)
                    .load((String) res)
                    .centerCrop()
//                .placeholder(R.drawable.a)
                    .crossFade()
                    .into(targetView);
        }
    }
}
