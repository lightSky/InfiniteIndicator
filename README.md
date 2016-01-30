InfiniteIndicator
===========================

This project is inspired by the [android-auto-scroll-view-pager](https://github.com/Trinea/android-auto-scroll-view-pager) of [Trinea](https://github.com/Trinea). Use the [salvage](https://github.com/JakeWharton/salvage) lib implement
view recycle adapter.It contains two style.One is CircleIndicator seperated from [Android-ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator).Another is copy from [CircleIndicator](https://github.com/ongakuer/CircleIndicator.).You can custome style and animation.

## Screenshot
<img src="apk/demo.gif" width="380" height="550" alt="Screenshot"/> 

## Demo Download
<a href="apk/demo.apk?raw=true" target="_blank" title="APK Download">APK Donwload</a>


## Setting
- `setInterval(long)` set interval time of scroll in milliseconds, default is `DEFAULT_INTERVAL`.
- `setDirection(int)` set auto scroll direction, default is `RIGHT`.
- `setInfinite(boolean)` set whether infinite scroll when auto scroll reaching the last or first item, default is true.
- `setScrollDurationFactor(double)` set the factor by which the duration of sliding animation will change.
- `setStopScrollWhenTouch(boolean)` set whether stop auto scroll when touching, default is true.
- `setIndicatorPosition` set present position of indicator.
- `startAutoScroll()` start auto scroll, delay time is `getInterval()`.
- `startAutoScroll(int)` start auto scroll delayed.
- `stopAutoScroll()` stop auto scroll.

indicator_type:the style enum of Indicator
- `indicator_default` CirCleIndicator
- `indicator_anim_circle`  AnimCircleIndicator
- `indicator_anim_line` is AnimLineIndicator

You can custome different anim or slideview for indicator.

## Including In Your Project

`compile 'cn.lightsky.infiniteindicator:library:1.0.5'`

## Usage

``` xml
   <cn.lightsky.infiniteindicator.InfiniteIndicatorLayout
        android:id="@+id/indicator_default_circle"
        app:indicator_type="indicator_anim_circle"
        android:layout_height="wrap_content"
        android:layout_width="match_parent"/>

```

```java
public class AnimIndicatorActivity extends FragmentActivity implements SliderView.OnSliderClickListener,ViewPager.OnPageChangeListener{
    private  ArrayList<PageView> pageViews;
    private InfiniteIndicator mAnimCircleIndicator;
    private InfiniteIndicator mAnimLineIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_indicator);

        initData();
        mAnimCircleIndicator = (InfiniteIndicator)findViewById(R.id.infinite_anim_circle);
        mAnimCircleIndicator.setImageLoader(new PicassoLoader());
        mAnimCircleIndicator.addSliders(pageViews);
        mAnimCircleIndicator.setIndicatorPosition(InfiniteIndicator.IndicatorPosition.Center);
        mDefaultIndicator.setOnPageChangeListener(this);
    }

    private void initData() {
        pageViews = new ArrayList<>();
        pageViews.add(new PageView("Page A", R.drawable.a,this));
        pageViews.add(new PageView("Page B", R.drawable.b,this));
        pageViews.add(new PageView("Page C", R.drawable.c,this));
        pageViews.add(new PageView("Page D", R.drawable.d,this));
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
    protected void onDestroy() {
        mAnimCircleIndicator.release();
        super.onDestroy();
    }
    @Override
    public void onSliderClick(SliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {}
}


```

Thanks  
[android-auto-scroll-view-pager](https://github.com/Trinea/android-auto-scroll-view-pager)  
[AndroidImageSlider](https://github.com/daimajia/AndroidImageSlider)  
[CircleIndicator](https://github.com/ongakuer/CircleIndicator)  
[Android-ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator)  

**About me**  
Weibo: [light_sky](http://www.weibo.com/lightSkyStreet)  
Blog: [lightskystreet.com](http://www.lightskystreet.com/) &nbsp;&nbsp;&nbsp;&nbsp;[light_sky](http://blog.csdn.net/xushuaic)     
Email: lightsky.cn@gmail.com  

