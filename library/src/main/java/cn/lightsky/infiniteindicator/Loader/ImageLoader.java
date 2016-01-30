package cn.lightsky.infiniteindicator.loader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by lightsky on 16/1/28.
 */
public abstract class ImageLoader {

    private static ImageLoader singleton;

    public ImageLoader instance() {
        if (singleton == null) {
            synchronized (ImageLoader.class) {
                if (singleton == null) {
                    singleton = getImageLoader();
                }
            }
        }
        if (singleton == null)
            throw new IllegalArgumentException("You should extends ImageLoader and return a instance by getImageLoader");

        return singleton;
    }

    public ImageLoader getImageLoader(){
        return null;
    };

    public void load(Context context, ImageView targetView, Object mData){};

}
