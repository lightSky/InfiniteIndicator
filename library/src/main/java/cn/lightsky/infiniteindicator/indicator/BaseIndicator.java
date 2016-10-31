package cn.lightsky.infiniteindicator.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import cn.lightsky.infiniteindicator.recycle.RecyleAdapter;

/**
 * Created by lightsky on 2016/10/31.
 */

public class BaseIndicator extends LinearLayout {

    protected int realCount;
    private ViewPager mViewPager;

    public BaseIndicator(Context context) {
        super(context);
    }

    public BaseIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected int getCurrentPosition(){

        return 0;
    }

    protected int getRealPosition(int position){
        return ((RecyleAdapter) mViewPager.getAdapter()).getRealPosition(position);
    }

    private View getRealChildAt(int position) {
        return getChildAt(getRealPosition(position));
    }
}
