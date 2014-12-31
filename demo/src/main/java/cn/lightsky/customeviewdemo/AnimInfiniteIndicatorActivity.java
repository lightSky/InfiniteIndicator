package cn.lightsky.customeviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import com.lightsky.infiniteindicator.indicator.CircleIndicator;
import com.lightsky.infiniteindicator.slideview.BaseSliderView;
import com.lightsky.infiniteindicator.slideview.DefaultSliderView;
import com.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import sky.light.com.customeviewdemo.R;


public class AnimInfiniteIndicatorActivity extends FragmentActivity implements BaseSliderView.OnSliderClickListener{
    private  ArrayList<PageInfo> viewInfos;
    private InfiniteIndicatorLayout mAnimCircleIndicator;
    private InfiniteIndicatorLayout mAnimLineIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_indicator);

        viewInfos = new ArrayList<PageInfo>();
        viewInfos.add(new PageInfo("a", R.drawable.a));
        viewInfos.add(new PageInfo("bb", R.drawable.b));
        viewInfos.add(new PageInfo("ccc", R.drawable.c));
        viewInfos.add(new PageInfo("dddd", R.drawable.d));

        testAnimCircleIndicator();
        testAnimLineIndicator();
    }

    //To avoid memory leak ,you should release the res
    @Override
    protected void onPause() {
        super.onPause();
        mAnimCircleIndicator.stopAutoScroll();
        mAnimLineIndicator.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnimCircleIndicator.startAutoScroll();
        mAnimLineIndicator.startAutoScroll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this,CircleDefaultActivity.class);
        startActivity(intent);
        return true;
    }

    private void testAnimCircleIndicator() {
        mAnimCircleIndicator = (InfiniteIndicatorLayout)findViewById(R.id.infinite_anim_circle);
        for(PageInfo name : viewInfos){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView
                    .image(name.getDrawableRes())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.getBundle()
                    .putString("extra", name.getData());
            mAnimCircleIndicator.addSlider(textSliderView);
        }
        mAnimCircleIndicator.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center);
    }

    private void testAnimLineIndicator() {
        mAnimLineIndicator = (InfiniteIndicatorLayout)findViewById(R.id.infinite_anim_line);
        for(PageInfo name : viewInfos){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView
                    .image(name.getDrawableRes())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.getBundle()
                    .putString("extra", name.getData());
            mAnimLineIndicator.addSlider(textSliderView);
        }
        mAnimLineIndicator.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
}
