package cn.lightsky.customeviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import com.lightsky.infiniteindicator.indicator.CircleIndicator;
import com.lightsky.infiniteindicator.slideview.BaseSliderView;
import com.lightsky.infiniteindicator.slideview.DefaultSliderView;

import java.util.ArrayList;
import java.util.HashMap;

import sky.light.com.customeviewdemo.R;


public class CircleDefaultActivity extends FragmentActivity  implements BaseSliderView.OnSliderClickListener{
    private InfiniteIndicatorLayout mCustoemIndicatorLayout;

    private  ArrayList<PageInfo> viewInfos;
    private InfiniteIndicatorLayout mDefaultIndicator;
    private InfiniteIndicatorLayout mAnimCircleIndicator;
    private InfiniteIndicatorLayout mAnimLineIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        viewInfos = new ArrayList<PageInfo>();
        viewInfos.add(new PageInfo("a", R.drawable.a));
        viewInfos.add(new PageInfo("bb", R.drawable.b));
        viewInfos.add(new PageInfo("ccc", R.drawable.c));
        viewInfos.add(new PageInfo("dddd", R.drawable.d));

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
        mDefaultIndicator = (InfiniteIndicatorLayout)findViewById(R.id.infinite_view_pager);
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg");
        url_maps.put("Big Bang Theory", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/30933093.jpg");//err
        url_maps.put("House of Cards", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg");
        url_maps.put("Game of Thrones", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg");

        // initialize a SliderLayout
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
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg");
        url_maps.put("Big Bang Theory", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/30933093.jpg");//err
        url_maps.put("House of Cards", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg");
        url_maps.put("Game of Thrones", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg");

        mCustoemIndicatorLayout = (InfiniteIndicatorLayout)findViewById(R.id.infinite_custome_circle);
        // initialize a SliderLayout
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
//        circleIndicator.setFillColor(0xFF00FF);
        circleIndicator.setFillColor(0xFF888888);
        circleIndicator.setStrokeColor(0xFF000000);
        circleIndicator.setStrokeWidth(2 * density);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this,AnimInfiniteIndicatorActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
}
