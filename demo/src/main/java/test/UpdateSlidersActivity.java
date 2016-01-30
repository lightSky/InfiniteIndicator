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


public class UpdateSlidersActivity extends FragmentActivity {
    private InfiniteIndicator mAnimCircleIndicator;

    private List refreshPageViews = new ArrayList();
    private ArrayList<PageView> pageViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_indicator);

        updateTest();
    }

    private List<Integer> updateUrls = new ArrayList<>();

    private void updateTest() {
        mAnimCircleIndicator = (InfiniteIndicator) findViewById(R.id.infinite_anim_circle);

        final PageView a = new PageView("update same size list", R.drawable.a);
        final PageView b = new PageView("update smaller size list", R.drawable.b);
        final PageView c = new PageView("update larger size list", R.drawable.c);
        final PageView d = new PageView("Leave a launcher", R.drawable.d);

        final PageView e = new PageView("", R.drawable.c_yypd);
        final PageView f = new PageView("", R.drawable.b_yypd);
        final PageView g = new PageView("", R.drawable.c_yypd);

        a.onSliderClickListener = new SliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(SliderView slider) {

                a.drawableRes = R.drawable.a_yypd;
                b.drawableRes = R.drawable.b_yypd;
                c.drawableRes = R.drawable.c_yypd;
                d.drawableRes = R.drawable.d_yypd;

                pageViews.clear();
                pageViews.add(a);
                pageViews.add(b);
                pageViews.add(c);
                pageViews.add(d);

                mAnimCircleIndicator.refreshSliders(pageViews);

                Toast.makeText(UpdateSlidersActivity.this, slider.getBundle().get("extra") + "",
                        Toast.LENGTH_LONG).show();
            }
        };

        b.onSliderClickListener = new SliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(SliderView slider) {
                a.drawableRes = R.drawable.a_yypd;
                b.drawableRes = R.drawable.b_yypd;

                pageViews.clear();
                pageViews.add(a);
                pageViews.add(b);

                mAnimCircleIndicator.refreshSliders(pageViews);

                Toast.makeText(UpdateSlidersActivity.this, slider.getBundle().get("extra") + "",
                        Toast.LENGTH_LONG).show();
            }
        };

        c.onSliderClickListener = new SliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(SliderView slider) {
                pageViews.clear();
                pageViews.add(a);
                pageViews.add(e);
                pageViews.add(b);
                pageViews.add(f);
                pageViews.add(c);
                pageViews.add(g);
                pageViews.add(d);

                mAnimCircleIndicator.refreshSliders(pageViews);
                Toast.makeText(UpdateSlidersActivity.this, slider.getBundle().get("extra") + "",
                        Toast.LENGTH_LONG).show();
            }
        };

        d.onSliderClickListener = new SliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(SliderView slider) {

                a.drawableRes = R.drawable.ic_launcher;

                pageViews.clear();
                pageViews.add(a);

                mAnimCircleIndicator.refreshSliders(pageViews);
                Toast.makeText(UpdateSlidersActivity.this, slider.getBundle().get("extra") + "",
                        Toast.LENGTH_LONG).show();

            }
        };

        pageViews.add(a);
        pageViews.add(b);
        pageViews.add(c);
        pageViews.add(d);

        mAnimCircleIndicator.setImageLoader(new PicassoLoader());
        mAnimCircleIndicator.addSliders(pageViews);
    }


    //To avoid memory leak ,you should release the res
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
}
