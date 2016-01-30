package cn.lightsky.infiniteindicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import cn.light.sky.infiniteindicatordemo.R;
import cn.lightsky.infiniteindicator.indicator.CircleIndicator;
import cn.lightsky.infiniteindicator.slideview.SliderView;
import cn.lightsky.infiniteindicator.slideview.PageView;

public class DefaultCircleIndicatorActivity extends FragmentActivity implements SliderView.OnSliderClickListener {
    private InfiniteIndicator mCustoemIndicatorLayout;
    private ArrayList<PageView> mPageViews;
    private InfiniteIndicator mDefaultIndicator;
    private ArrayList rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_circle_indicator);


        mPageViews = new ArrayList<PageView>();
        mPageViews.add(new PageView("Page A", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg"));
        mPageViews.add(new PageView("Page B", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg"));
        mPageViews.add(new PageView("Page C", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg"));
        mPageViews.add(new PageView("Page D", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg"));

        testCircleIndicator();
        testCustomeCircleIndicator();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDefaultIndicator.stopAutoScroll();
        mCustoemIndicatorLayout.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDefaultIndicator.startAutoScroll();
        mCustoemIndicatorLayout.startAutoScroll();
    }

    private void testCircleIndicator() {
        mDefaultIndicator = (InfiniteIndicator) findViewById(R.id.indicator_default_circle);
        mDefaultIndicator.setImageLoader(new PicassoLoader());
        mDefaultIndicator.addSliders(mPageViews);
        mDefaultIndicator.setIndicatorPosition(InfiniteIndicator.IndicatorPosition.Center_Bottom);
        mDefaultIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("TAG", "onPage Scrolled .......");
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "onPage onPageSelected .......");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("TAG", "onPage onPageScrollStateChanged .......");
            }
        });
    }

    private void testCustomeCircleIndicator() {
        mCustoemIndicatorLayout = (InfiniteIndicator) findViewById(R.id.indicator_custome_circle);
        mCustoemIndicatorLayout.setImageLoader(new PicassoLoader());
        mCustoemIndicatorLayout.addSliders(mPageViews);
        mCustoemIndicatorLayout.setIndicatorPosition(InfiniteIndicator.IndicatorPosition.Center_Bottom);

        CircleIndicator circleIndicator = ((CircleIndicator) mCustoemIndicatorLayout.getPagerIndicator());
        final float density = getResources().getDisplayMetrics().density;
        circleIndicator.setBackgroundColor(0xFFCCCCCC);
        circleIndicator.setRadius(5 * density);
        circleIndicator.setPageColor(0x880000FF);
        circleIndicator.setFillColor(0xFF888888);
        circleIndicator.setStrokeColor(0xFF000000);
        circleIndicator.setStrokeWidth(2 * density);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    @Override
    public void onSliderClick(SliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
}
