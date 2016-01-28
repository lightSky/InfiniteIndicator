package test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.light.sky.infiniteindicatordemo.R;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.SliderView;
import cn.lightsky.infiniteindicator.slideview.PageView;

public class AddSlidersActivity extends FragmentActivity implements SliderView.OnSliderClickListener {
    private ArrayList<PageView> pageViews;
    private InfiniteIndicatorLayout mAnimCircleIndicator;
    private List refreshPageViews = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_indicator);

        addSlidersTest();
    }

    private void addSlidersTest() {
        mAnimCircleIndicator = (InfiniteIndicatorLayout) findViewById(R.id.infinite_anim_circle);
        pageViews = new ArrayList<PageView>();
        pageViews.add(new PageView("Page A", R.drawable.a));
        pageViews.add(new PageView("Page B", R.drawable.b));
        pageViews.add(new PageView("Page C", R.drawable.c));
        pageViews.add(new PageView("Page D", R.drawable.d));
        mAnimCircleIndicator.addSliders(pageViews);
        mAnimCircleIndicator.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center);
    }


    //To avoid memory leak ,you should release the res
    @Override
    protected void onPause() {
        super.onPause();
        mAnimCircleIndicator.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnimCircleIndicator.startAutoScroll();
    }


    List<Integer> updateUrls = new ArrayList<>();

    @Override
    public void onSliderClick(SliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
}
