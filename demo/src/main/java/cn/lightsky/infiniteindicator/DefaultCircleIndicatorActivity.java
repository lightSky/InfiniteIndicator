package cn.lightsky.infiniteindicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cn.light.sky.infiniteindicatordemo.R;
import cn.lightsky.infiniteindicator.indicator.CircleIndicator;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

import java.util.ArrayList;
import java.util.HashMap;

public class DefaultCircleIndicatorActivity extends FragmentActivity  implements BaseSliderView.OnSliderClickListener{
    private InfiniteIndicatorLayout mCustoemIndicatorLayout;
    private  ArrayList<PageInfo> viewInfos;
    private InfiniteIndicatorLayout mDefaultIndicator;
    private HashMap<String,String> url_maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_circle_indicator);

        viewInfos = new ArrayList<PageInfo>();
        viewInfos.add(new PageInfo("Page A", R.drawable.a));
        viewInfos.add(new PageInfo("Page B", R.drawable.b));
        viewInfos.add(new PageInfo("Page C", R.drawable.c));
        viewInfos.add(new PageInfo("Page D", R.drawable.d));

        url_maps = new HashMap<String, String>();
        url_maps.put("Page A", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg");
        url_maps.put("Page B", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg");
        url_maps.put("Page C", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg");
        url_maps.put("Page D", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg");

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
        mDefaultIndicator = (InfiniteIndicatorLayout)findViewById(R.id.indicator_default_circle);
        for(String name : url_maps.keySet()){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.getBundle()
                    .putString("extra",name);
            mDefaultIndicator.addSlider(textSliderView);
        }
        mDefaultIndicator.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
    }

    private void testCustomeCircleIndicator() {
        mCustoemIndicatorLayout = (InfiniteIndicatorLayout)findViewById(R.id.indicator_custome_circle);
        for(String name : url_maps.keySet()){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.getBundle()
                    .putString("extra",name);
            mCustoemIndicatorLayout.addSlider(textSliderView);
        }
        mCustoemIndicatorLayout.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
        CircleIndicator circleIndicator = ((CircleIndicator)mCustoemIndicatorLayout.getPagerIndicator());
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
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
}
