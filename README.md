InfiniteIndicator
===========================

This project is inspired by the [android-auto-scroll-view-pager](https://github.com/Trinea/android-auto-scroll-view-pager) of [Trinea](https://github.com/Trinea). Use the [salvage](https://github.com/JakeWharton/salvage) lib implement
view recycle adapter.It contains two style.One is CircleIndicator seperated from [Android-ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator).Another is copy from [CircleIndicator](https://github.com/ongakuer/CircleIndicator.).You can custome style and animation.

## Screenshot
![Screenshot](apk/demo.gif) 

## Demo Download
<a href="apk/demo.apk?raw=true" target="_blank" title="APK Download">±æµÿœ¬‘ÿ</a>  


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

You can custome different anim and set for indicator.

## Usage
- include this library, use

``` xml
   <com.lightsky.infiniteindicator.InfiniteIndicatorLayout
        android:id="@+id/indicator_default_circle"
        app:indicator_type="indicator_anim_circle"
        android:layout_height="wrap_content"
        android:layout_weight="match_parent"/>

```

```java
public class MainActivity extends Activity {
    private InfiniteIndicatorLayout mDefaultIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDefaultIndicator = (InfiniteIndicatorLayout)findViewById(R.id.indicator_default_circle);
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps = new HashMap<String, String>();
        url_maps.put("Page A", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg");
        url_maps.put("Page B", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg");
        url_maps.put("Page C", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg");
        url_maps.put("Page D", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg");

        for(String name : url_maps.keySet()){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.getBundle()
                    .putString("extra",name);
            mDefaultIndicator.addSlider(textSliderView);
        }
        mDefaultIndicator.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
        mDefaultIndicator.startAutoScroll();
    }

        @Override
        protected void onPause() {
            super.onPause();
            mDefaultIndicator.stopAutoScroll();
        }

        @Override
        protected void onResume() {
            super.onResume();
            mDefaultIndicator.startAutoScroll();
        }
}

```

Thanks  
[android-auto-scroll-view-pager](https://github.com/Trinea/android-auto-scroll-view-pager)  
[AndroidImageSlider](https://github.com/daimajia/AndroidImageSlider)  
[CircleIndicator](https://github.com/ongakuer/CircleIndicator)  
[Android-ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator)

About me:
