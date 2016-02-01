package cn.lightsky.infiniteindicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import cn.light.sky.infiniteindicatordemo.R;
import cn.lightsky.infiniteindicator.page.OnPageClickListener;
import cn.lightsky.infiniteindicator.page.Page;


public class AnimIndicatorActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,OnPageClickListener{
    private ArrayList<Page> pageViews;
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
        pageViews.add(new Page("A ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg",this));
        pageViews.add(new Page("B ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg",this));
        pageViews.add(new Page("C ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg",this));
        pageViews.add(new Page("D ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg",this));
        
//        pageViews.add(new Page("Page A", R.drawable.a,this));
//        pageViews.add(new Page("Page B", R.drawable.b,this));
//        pageViews.add(new Page("Page C", R.drawable.c,this));
//        pageViews.add(new Page("Page D", R.drawable.d,this));
    }

    //To avoid memory leak ,you should release the res
    @Override
    protected void onPause() {
        super.onPause();
        mAnimCircleIndicator.stop();
        mAnimLineIndicator.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnimCircleIndicator.start();
        mAnimLineIndicator.start();
    }

    @Override
    protected void onDestroy() {
        mAnimCircleIndicator.release();
        mAnimLineIndicator.release();
        super.onDestroy();
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
        mAnimCircleIndicator.setImageLoader(new UILoader());
        mAnimCircleIndicator.addPages(pageViews);
        mAnimCircleIndicator.setPosition(InfiniteIndicator.IndicatorPosition.Center);
        mAnimCircleIndicator.setOnPageChangeListener(this);
    }

    private void testAnimLineIndicator() {
        mAnimLineIndicator = (InfiniteIndicator)findViewById(R.id.infinite_anim_line);
        mAnimLineIndicator.setImageLoader(new PicassoLoader());
        mAnimLineIndicator.addPages(pageViews);
        mAnimLineIndicator.setPosition(InfiniteIndicator.IndicatorPosition.Center);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        Toast.makeText(this,"page selected"+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageClick(int position, Page page) {
        Toast.makeText(this," click page --- "+position,Toast.LENGTH_SHORT).show();
    }

}
