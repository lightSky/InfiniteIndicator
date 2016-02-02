package cn.lightsky.infiniteindicator.loader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by lightsky on 16/1/28.
 */
public interface ImageLoader {

//    private static ImageLoader singleton;

//    protected Context mContext;

//    public ImageLoader instance(Context context) {
//        if (singleton == null) {
//            synchronized (ImageLoader.class) {
//                if (singleton == null) {
//                    this.mContext = context;
//                    singleton = getImageLoader(context);
//                }
//            }
//        }
//        if (singleton == null)
//            throw new IllegalArgumentException("You should extends ImageLoader and return a instance by getImageLoader");
//
//        return singleton;
//    }
//
//    public ImageLoader getImageLoader(Context context){
//        return null;
//    };

    void initLoader(Context context);

    void load(Context context,ImageView targetView, Object res);

}
