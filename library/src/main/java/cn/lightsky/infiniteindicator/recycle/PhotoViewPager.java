package cn.lightsky.infiniteindicator.recycle;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lightsky on 2016/12/7.
 *
 * support for photoview : https://github.com/chrisbanes/PhotoView
 */

public class PhotoViewPager extends ViewPager{


    public PhotoViewPager(Context context) {
        super(context);
    }

    public PhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        try {
            return super.onInterceptHoverEvent(event);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
