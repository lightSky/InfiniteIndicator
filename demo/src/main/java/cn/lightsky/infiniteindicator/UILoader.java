package cn.lightsky.infiniteindicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by lightsky on 16/1/28.
 */

public class UILoader implements ImageLoader {

    private DisplayImageOptions options;

    private boolean isInited;

    public UILoader getImageLoader(Context context) {
        UILoader uilLoader = new UILoader();
        initLoader(context);
        return uilLoader;
    }

    public void initLoader(Context context) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

        //显示图片的配置
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.placeholder)
//                .showImageOnFail(R.drawable.error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        isInited = true;
    }

    @Override
    public void load(Context context,ImageView targetView, Object res) {

        if (!isInited)
            initLoader(context);

        if (res == null) {
            return;
        }

        if (res instanceof String) {
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage((String) res, targetView,options);
        } else {
            com.nostra13.universalimageloader.core.ImageLoader.getInstance()
                    .displayImage("drawable://" + res, targetView,options);

        }
    }
}
