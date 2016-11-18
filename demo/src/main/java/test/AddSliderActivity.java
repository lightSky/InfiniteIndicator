package test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.light.sky.infiniteindicatordemo.R;
import cn.lightsky.infiniteindicator.GlideLoader;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;

public class AddSliderActivity extends FragmentActivity implements
        OnPageClickListener {
    private ArrayList<Page> pageViews;
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
        pageViews = new ArrayList<Page>();
        pageViews.add(new Page("Page A", R.drawable.a));
        pageViews.add(new Page("Page B", R.drawable.b));
        pageViews.add(new Page("Page C", R.drawable.c));
        pageViews.add(new Page("Page D", R.drawable.d));

        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                .imageLoader(new GlideLoader())
                .position(IndicatorConfiguration.IndicatorPosition.Center)
                .build();
        mAnimCircleIndicator.init(configuration);
        mAnimCircleIndicator.notifyDataChange(pageViews);
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

    @Override
    public void onPageClick(int position, Page page) {
        Toast.makeText(this, "position = "+position, Toast.LENGTH_SHORT).show();
    }
}
