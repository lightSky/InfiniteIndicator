package test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.light.sky.infiniteindicatordemo.R;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.PicassoLoader;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;


public class UpdateSliderActivity extends FragmentActivity{
    private InfiniteIndicator mAnimCircleIndicator;

    private List refreshPageViews = new ArrayList();
    private ArrayList<Page> pageViews = new ArrayList<>();
    private List<Integer> updateUrls = new ArrayList<>();
    private OnPageClickListener onPageClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_indicator);

        updateTest();
    }

    private void updateTest() {
        final Page a = new Page("update same size list", R.drawable.a);
        final Page b = new Page("update smaller size list", R.drawable.b);
        final Page c = new Page("update larger size list", R.drawable.c);
        final Page d = new Page("Leave a launcher", R.drawable.d);

        final Page e = new Page("", R.drawable.c_yypd);
        final Page f = new Page("", R.drawable.b_yypd);
        final Page g = new Page("", R.drawable.c_yypd);
        final Page h = new Page("", R.drawable.ic_launcher);

        onPageClickListener = new OnPageClickListener() {

            @Override
            public void onPageClick(int position, Page page) {
                if (position == 0) {

//                a.res = R.drawable.a_yypd;
//                b.res = R.drawable.b_yypd;
//                c.res = R.drawable.c_yypd;
//                d.res = R.drawable.d_yypd;

                    a.res = R.drawable.a;
                    b.res = R.drawable.b;
                    c.res = R.drawable.c;
                    d.res = R.drawable.d;

                    pageViews.clear();
                    pageViews.add(a);
                    pageViews.add(b);
                    pageViews.add(c);
                    pageViews.add(d);

                    mAnimCircleIndicator.notifyDataChange(pageViews);

                    Toast.makeText(UpdateSliderActivity.this, page.data + "",
                            Toast.LENGTH_LONG).show();
                } else if (position == 1){
                    a.res = R.drawable.a_yypd;
                    b.res = R.drawable.b_yypd;

                    pageViews.clear();
                    pageViews.add(a);
                    pageViews.add(b);

                    mAnimCircleIndicator.notifyDataChange(pageViews);

                    Toast.makeText(UpdateSliderActivity.this, page.data + "",
                            Toast.LENGTH_LONG).show();
                } else if (position == 3) {
                    pageViews.clear();

                    a.res = R.drawable.a;
                    b.res = R.drawable.b;
                    c.res = R.drawable.c;
                    d.res = R.drawable.d;

                    pageViews.add(a);
                    pageViews.add(e);
                    pageViews.add(b);
                    pageViews.add(f);
                    pageViews.add(c);
                    pageViews.add(g);
                    pageViews.add(d);
                    pageViews.add(h);

                    mAnimCircleIndicator.notifyDataChange(pageViews);
                    Toast.makeText(UpdateSliderActivity.this, page.data + "",
                            Toast.LENGTH_LONG).show();
                } else if (position == 3) {
                    a.res = R.drawable.ic_launcher;

                    pageViews.clear();
                    pageViews.add(a);

                    mAnimCircleIndicator.notifyDataChange(pageViews);
                    Toast.makeText(UpdateSliderActivity.this, page.data + "",
                            Toast.LENGTH_LONG).show();
                }
            }
        };


        pageViews.add(a);
        pageViews.add(b);
        pageViews.add(c);
        pageViews.add(d);

        mAnimCircleIndicator = (InfiniteIndicator) findViewById(R.id.infinite_anim_circle);
        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                .imageLoader(new PicassoLoader())
                .onPageClickListener(onPageClickListener)
                .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                .build();
        mAnimCircleIndicator.init(configuration);
        mAnimCircleIndicator.notifyDataChange(pageViews);
    }


    //To save resource ,you should release the res
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
