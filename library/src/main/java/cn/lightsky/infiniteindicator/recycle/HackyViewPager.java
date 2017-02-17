package cn.lightsky.infiniteindicator.recycle;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lightsky on 2016/12/7.
 *
 * support for photoview : https://github.com/chrisbanes/PhotoView,
 * The solution is ugly. But consider it doesn't do additional stuff,and won't
 * cause effect for developer ,so handle it temporarily.
 */

public class HackyViewPager extends ViewPager{


    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
