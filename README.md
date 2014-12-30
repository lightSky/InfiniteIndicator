InfiniteAutoScrollView
===========================

This project is inspired by the auto-scroll-viewpager of [Trinea](https://github.com/Trinea). Use the [salvage](https://github.com/JakeWharton/salvage) lib which the implementation of
;Generic view recycler and ViewPage PagerAdapter .
The indicator contains two style.One is CircleIndicator seperated from ViewPagerIndicator.Another is copy from https://github.com/ongakuer/CircleIndicator.

## Usage
- include this library, use

``` xml
   <com.lightsky.infiniteindicator.InfiniteIndicatorLayout
        android:id="@+id/infinite_anim_circle"
        android:layout_width="match_parent"
        app:indicator_type="indicator_anim_circle"
        android:layout_height="0dp"
        android:layout_weight="1"/>

```

```
public class MainActivity extends Activity {
    private InfiniteIndicatorLayout mDefaultIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDefaultIndicator = (InfiniteIndicatorLayout)findViewById(R.id.infinite_view_pager);
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        // initialize a SliderLayout
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

- `startAutoScroll()` start auto scroll, delay time is `getInterval()`.
- `startAutoScroll(int)` start auto scroll delayed.
- `stopAutoScroll()` stop auto scroll.

## Setting
- `setInterval(long)` set auto scroll time in milliseconds, default is `DEFAULT_INTERVAL`.
- `setDirection(int)` set auto scroll direction, default is `RIGHT`.
- `setInfinite(boolean)` set whether automatic cycle when auto scroll reaching the last or first item, default is true.
- `setScrollDurationFactor(double)` set the factor by which the duration of sliding animation will change.
- `setSlideBorderMode(int)` set how to process when sliding at the last or first item, default is `SLIDE_BORDER_MODE_NONE`.
- `setStopScrollWhenTouch(boolean)` set whether stop auto scroll when touching, default is true.
- `setBorderAnimation(boolean)` set whether animating when auto scroll at the last or first item, default is true.

