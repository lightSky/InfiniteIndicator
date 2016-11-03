InfiniteIndicator
===========================

This project is inspired by the [android-auto-scroll-view-pager](https://github.com/Trinea/android-auto-scroll-view-pager) of [Trinea](https://github.com/Trinea). Use the [salvage](https://github.com/JakeWharton/salvage) lib implement
view recycle adapter.It contains two style.One is CircleIndicator seperated from [Android-ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator).Another is copy from [CircleIndicator](https://github.com/ongakuer/CircleIndicator.).You can custome style and animation.

## Screenshot
<img src="apk/demo.gif" width="380" height="550" alt="Screenshot"/> 

## Demo Download
<a href="apk/demo.apk?raw=true" target="_blank" title="APK Download">APK Donwload</a>


## Setting
You can config all feature in the `IndicatorConfiguration` class.It's build design pattern.


- `interval(long)` set interval time of scroll in milliseconds, default is `DEFAULT_INTERVAL`.
- `direction(int)` set auto scroll direction, default is `RIGHT`.
- `isLoop(boolean)` set whether still scroll when scroll to the end page.
- `isDrawIndicator(boolean)`  whether draw indicator,default is true.
- `isAutoScroll(boolean)`  whether start scroll while notiyDataChange.
- `scrollDurationFactor(double)` set the factor of scroll duration 
- `isStopWhenTouch(boolean)` whether stop scroll while touching, default is true.
- `position` set the position of indicator.You can reference `IndicatorConfiguration.IndicatorPosition` enum
- `imageLoader(ImageLoader)` set the loader engine to load image while page sliding.You can use any image loader library you what,there are several imageloader of Glide ,Picasso and UIL,decide how to load image,is absolutely free.
- `pageResId` set custome page layout,if your page has imageview and you want change it by res,you the id of imageview must is `slider_image` 
- `onPageChangeListener` set click listener to page


`indicator_type`    
the style enum of Indicator,you can set the `indicator_type` in the xml layout to 
change the indicator style.  
- `indicator_default` CirCleIndicator
- `indicator_anim_circle`  AnimCircleIndicator
- `indicator_anim_line` is AnimLineIndicator


## Including In Your Project

`compile 'cn.lightsky.infiniteindicator:library:1.1.0'`

## Usage

``` xml
   <cn.lightsky.infiniteindicator.InfiniteIndicatorLayout
        android:id="@+id/indicator_default_circle"
        app:indicator_type="indicator_anim_circle"
        android:layout_height="wrap_content"
        android:layout_width="match_parent"/>

```

```java
public class AnimIndicatorActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,OnPageClickListener{
    private ArrayList<Page> pageViews;
    private InfiniteIndicator mAnimCircleIndicator;
    private InfiniteIndicator mAnimLineIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_indicator);

        initData();
        mAnimCircleIndicator = (InfiniteIndicator)findViewById(R.id.infinite_anim_circle);
        mAnimCircleIndicator.setImageLoader(new UILoader());
        mAnimCircleIndicator.addPages(pageViews);
        mAnimCircleIndicator.setPosition(InfiniteIndicator.IndicatorPosition.Center);
        mAnimCircleIndicator.setOnPageChangeListener(this);

    }

    private void initData() {
        pageViews = new ArrayList<>();
        pageViews.add(new Page("A ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg",this));
        pageViews.add(new Page("B ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg",this));
        pageViews.add(new Page("C ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg",this));
        pageViews.add(new Page("D ", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg",this));

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

    @Override
    public void onPageSelected(int position) {
        //do something
    }

    @Override
    public void onPageClick(int position, Page page) {
        //do something
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

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

