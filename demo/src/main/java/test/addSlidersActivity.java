package test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.light.sky.infiniteindicatordemo.R;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.PicassoLoader;
import cn.lightsky.infiniteindicator.slideview.PageView;
import cn.lightsky.infiniteindicator.slideview.SliderView;

public class AddSlidersActivity extends FragmentActivity implements SliderView.OnSliderClickListener {
    private ArrayList<PageView> pageViews;
    private InfiniteIndicator mAnimCircleIndicator;
    private List refreshPageViews = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_indicator);

        addSlidersTest();
    }

    private void addSlidersTest() {
        mAnimCircleIndicator = (InfiniteIndicator) findViewById(R.id.infinite_anim_circle);
        pageViews = new ArrayList<PageView>();
        pageViews.add(new PageView("Page A", R.drawable.a));
        pageViews.add(new PageView("Page B", R.drawable.b));
        pageViews.add(new PageView("Page C", R.drawable.c));
        pageViews.add(new PageView("Page D", R.drawable.d));
        mAnimCircleIndicator.setImageLoader(new PicassoLoader());
        mAnimCircleIndicator.addSliders(pageViews);
        mAnimCircleIndicator.setIndicatorPosition(InfiniteIndicator.IndicatorPosition.Center);
    }


    //In case memory leak ,you should release the res
    @Override
    protected void onPause() {
        super.onPause();
        mAnimCircleIndicator.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnimCircleIndicator.start();
    }


    List<Integer> updateUrls = new ArrayList<>();

    @Override
    public void onSliderClick(SliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
}
