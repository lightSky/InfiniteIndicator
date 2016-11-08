package cn.lightsky.infiniteindicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import cn.light.sky.infiniteindicatordemo.R;
import cn.lightsky.infiniteindicator.indicator.CircleIndicator;

public class DefaultCircleIndicatorActivity extends FragmentActivity {
    private InfiniteIndicator mCustoemCircleIndicator;
    private ArrayList<Page> pageViews;
    private InfiniteIndicator mDefaultIndicator;
    private ArrayList rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_circle_indicator);

        pageViews = new ArrayList<Page>();
//        pageViews.add(new Page("Page A", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg"));
//        pageViews.add(new Page("Page B", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg"));
//        pageViews.add(new Page("Page C", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg"));
//        pageViews.add(new Page("Page D", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg"));

        pageViews.add(new Page("A ", R.drawable.a));
        pageViews.add(new Page("B ", R.drawable.b));
        pageViews.add(new Page("C ", R.drawable.c));
        pageViews.add(new Page("D ", R.drawable.d));
        testCircleIndicator();
        testCustomeCircleIndicator();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDefaultIndicator.stop();
        mCustoemCircleIndicator.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDefaultIndicator.start();
        mCustoemCircleIndicator.start();
    }

    private void testCircleIndicator() {
        mDefaultIndicator = (InfiniteIndicator) findViewById(R.id.indicator_default_circle);
        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                .imageLoader(new UILoader())
                .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                .build();
        mDefaultIndicator.init(configuration);
        mDefaultIndicator.notifyDataChange(pageViews);
        mDefaultIndicator.setCurrentItem(2);
    }

    private void testCustomeCircleIndicator() {
        mCustoemCircleIndicator = (InfiniteIndicator) findViewById(R.id.indicator_custome_circle);
        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                .imageLoader(new PicassoLoader())
                .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                .build();
        mCustoemCircleIndicator.init(configuration);
        mCustoemCircleIndicator.notifyDataChange(pageViews);

        CircleIndicator circleIndicator = ((CircleIndicator) mCustoemCircleIndicator.getPagerIndicator());
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
}
