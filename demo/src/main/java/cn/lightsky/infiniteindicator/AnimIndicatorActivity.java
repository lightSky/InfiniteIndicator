package cn.lightsky.infiniteindicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import cn.light.sky.infiniteindicatordemo.R;
import cn.lightsky.infiniteindicator.slideview.SliderView;
import cn.lightsky.infiniteindicator.slideview.PageView;


public class AnimIndicatorActivity extends FragmentActivity implements SliderView.OnSliderClickListener{
    private  ArrayList<PageView> pageViews;
    private InfiniteIndicator mAnimCircleIndicator;
    private InfiniteIndicator mAnimLineIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_indicator);

        initData();
        testAnimCircleIndicator();
        testAnimLineIndicator();
    }

    private void initData() {
        pageViews = new ArrayList<>();
        pageViews.add(new PageView("Page A", R.drawable.a));
        pageViews.add(new PageView("Page B", R.drawable.b));
        pageViews.add(new PageView("Page C", R.drawable.c));
        pageViews.add(new PageView("Page D", R.drawable.d));
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
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this,DefaultCircleIndicatorActivity.class);
        startActivity(intent);
        return true;
    }

    private void testAnimCircleIndicator() {
        mAnimCircleIndicator = (InfiniteIndicator)findViewById(R.id.infinite_anim_circle);
        mAnimCircleIndicator.setImageLoader(new PicassoLoader());
        mAnimCircleIndicator.addSliders(pageViews);
        mAnimCircleIndicator.setIndicatorPosition(InfiniteIndicator.IndicatorPosition.Center);
    }

    private void testAnimLineIndicator() {
        mAnimLineIndicator = (InfiniteIndicator)findViewById(R.id.infinite_anim_line);
        mAnimLineIndicator.setImageLoader(new PicassoLoader());
        mAnimLineIndicator.addSliders(pageViews);
        mAnimLineIndicator.removeAllSliders();
        mAnimLineIndicator.setIndicatorPosition(InfiniteIndicator.IndicatorPosition.Center);
    }

    @Override
    public void onSliderClick(SliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
}
