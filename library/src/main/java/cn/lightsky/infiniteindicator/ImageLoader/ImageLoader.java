package cn.lightsky.infiniteindicator.ImageLoader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by lightsky on 16/1/28.
 */
public interface ImageLoader {

    void init(Context context);

    void load(ImageView targeView, Object res);
}
