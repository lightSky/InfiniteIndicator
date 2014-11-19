package cn.lightsky.customeviewdemo;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import sky.light.com.customeviewdemo.R;
import com.viewpagerindicator.CirclePageIndicator;


public class MainActivity extends Activity {
    private static final String DEBUG_TAG = "Activity__ActionEvent";
    private AutoScrollViewPager mAutoScrollViewPager;
    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        List<Integer> mDrawableResIds = new ArrayList<Integer>();
        mDrawableResIds.add(R.drawable.a);
        mDrawableResIds.add(R.drawable.b);
        mDrawableResIds.add(R.drawable.c);
        mDrawableResIds.add(R.drawable.d);
        mAutoScrollViewPager = (AutoScrollViewPager)findViewById(R.id.view_pager);
        mAutoScrollViewPager.setAdapter(new PageAdapter(this,mDrawableResIds).setInfiniteLoop(true));
        mAutoScrollViewPager.setInterval(5000);
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mAutoScrollViewPager);
        mIndicator.setRealViewCount(mDrawableResIds.size());
        mAutoScrollViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mDrawableResIds.size());
        mAutoScrollViewPager.startAutoScroll();
    }
}
