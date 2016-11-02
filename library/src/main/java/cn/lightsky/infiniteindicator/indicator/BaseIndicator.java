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

public class BaseIndicator extends LinearLayout implements PageIndicator{

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

    public void onPageSelected(){

    }

    @Override
    public void setViewPager(ViewPager view) {

    }

    @Override
    public void setCurrentItem(int item) {

    }

    @Override
    public void notifyDataSetChanged() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
