package cn.lightsky.infiniteindicator;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by lightsky on 16/1/28.
 */
public interface ImageLoader {

    void load(Context context,ImageView targetView, Object res);

}
