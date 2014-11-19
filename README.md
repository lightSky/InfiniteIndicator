InfiniteAutoScrollView
===========================

This project is inspired by the auto-scroll-viewpager of [Trinea](https://github.com/Trinea). Use the [salvage](https://github.com/JakeWharton/salvage) lib which the implementation of Generic view recycler and ViewPage PagerAdapter .
It contains a cycle pager indicator seperated from ViewPagerIndicator.The page is still scrolling forward but the indicator is recycle.

Usage
===========================  

You can directly check it out from github by Android Studio.

```

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import com.viewpagerindicator.CirclePageIndicator;

public class MainActivity extends Activity {
    private AutoScrollViewPager mAutoScrollViewPager;
    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        List<Integer> mDrawableResIds = new ArrayList<Integer>();
        mDrawableResIds.add(R.drawable.a);
        mDrawableResIds.add(R.drawable.b);
        mDrawableResIds.add(R.drawable.c);
        mAutoScrollViewPager = (AutoScrollViewPager)findViewById(R.id.view_pager);
        mAutoScrollViewPager.setAdapter(new PageAdapter(this,mDrawableResIds).setInfiniteLoop(true));
        mAutoScrollViewPager.setInterval(5000);
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mAutoScrollViewPager);
        mIndicator.setRealViewCount(mDrawableResIds.size());
        mAutoScrollViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mDrawableResIds.size());
        mAutoScrollViewPager.startAutoScroll();
    }
}

```

```
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:background="@android:color/black"
    android:layout_gravity="center"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager
        android:id="@+id/view_pager"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        />

    <com.viewpagerindicator.CirclePageIndicator
        android:id="@+id/indicator"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_alignBottom="@id/view_pager"
        android:layout_gravity="bottom"
        android:paddingBottom="30dp"
        />
</FrameLayout>

```
