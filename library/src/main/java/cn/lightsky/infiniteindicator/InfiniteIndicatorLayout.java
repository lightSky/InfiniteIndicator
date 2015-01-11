package cn.lightsky.infiniteindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import cn.lightsky.infiniteautoscroolview.R;
import cn.lightsky.infiniteindicator.indicator.PageIndicator;
import cn.lightsky.infiniteindicator.indicator.RecyleAdapter;
import cn.lightsky.infiniteindicator.jakewharton.salvage.RecyclingPagerAdapter;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;

import java.lang.reflect.Field;


/**
 * Created by lightSky on 2014/12/22.
 * Thanks to: https://github.com/Trinea/android-auto-scroll-view-pager
 */
public class InfiniteIndicatorLayout extends RelativeLayout implements RecyclingPagerAdapter.DataChangeListener {
    private PageIndicator mIndicator;
    private ViewPager mViewPager;
    private Context mContext;
    private RecyleAdapter mRecyleAdapter;

    public static final int DEFAULT_INTERVAL = 2500;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    /**
     * do nothing when sliding at the last or first item *
     */
    public static final int SLIDE_BORDER_MODE_NONE = 0;
    /**
     * cycle when sliding at the last or first item *
     */
    public static final int SLIDE_BORDER_MODE_CYCLE = 1;
    /**
     * deliver event to parent when sliding at the last or first item *
     */
    public static final int SLIDE_BORDER_MODE_TO_PARENT = 2;

    /**
     * auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL} *
     */
    private long interval = DEFAULT_INTERVAL;
    /**
     * auto scroll direction, default is {@link #RIGHT} *
     */
    private int direction = RIGHT;
    /**
     * whether automatic cycle when auto scroll reaching the last or first item, default is true *
     */
    private boolean isInfinite = true;
    /**
     * whether stop auto scroll when touching, default is true *
     */
    private boolean isStopScrollWhenTouch = true;
    /**
     * how to process when sliding at the last or first item, default is {@link #SLIDE_BORDER_MODE_NONE} *
     */
    private int slideBorderMode = SLIDE_BORDER_MODE_NONE;
    /**
     * whether animating when auto scroll at the last or first item *
     */
//    private boolean isBorderAnimation = true;

    public static final int MSG_WHAT = 0;
    private Handler handler;
    private boolean isAutoScroll = false;
    private boolean isStopByTouch = false;
    private float touchX = 0f, downX = 0f;

    /**
     * Custome Scroller for
     */
    private CustomDurationScroller scroller = null;

    /**
     * Indicator Style Type,default is Circle with no Anim
     */
    public enum IndicatorType{
        Default,
        AnimCircle,
        AnimLine;
    };

    public InfiniteIndicatorLayout(Context context) {
        this(context, null);
    }

    public InfiniteIndicatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfiniteIndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.InfiniteIndicatorLayout,0,0);
        int indicatorType = attributes.getInt(R.styleable.InfiniteIndicatorLayout_indicator_type,IndicatorType.Default.ordinal());

        if(indicatorType == 0)
            LayoutInflater.from(context).inflate(R.layout.layout_default_indicator, this, true);
        else if(indicatorType == 1)
            LayoutInflater.from(context).inflate(R.layout.layout_anim_circle_indicator, this, true);
        else
            LayoutInflater.from(context).inflate(R.layout.layout_anim_line_indicator, this, true);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mRecyleAdapter = new RecyleAdapter(mContext);
        mRecyleAdapter.setDataChangeListener(this);
        mViewPager.setAdapter(mRecyleAdapter);
        handler = new ScrollHandler();
        setViewPagerScroller();

    }

    public <T extends BaseSliderView> void addSlider(T imageContent) {
        mRecyleAdapter.addSlider(imageContent);
    }

    /**
     * according page count and is loop decide the first page to display
     */
    public void initFirstPage() {
        if (isInfinite && mRecyleAdapter.getRealCount() > 1) {
            mViewPager.setCurrentItem(mRecyleAdapter.getRealCount() * 50);
        } else {
            setInfinite(false);
            mViewPager.setCurrentItem(0);
        }
    }

    /**
     * start auto scroll, first scroll delay time is {@link #getInterval()}
     */
    public void startAutoScroll() {
        if (mRecyleAdapter.getRealCount() > 1) {
            isAutoScroll = true;
            sendScrollMessage(interval);
        }
    }

    /**
     * start auto scroll
     *
     * @param delayTimeInMills first scroll delay time
     */
    public void startAutoScroll(int delayTimeInMills) {
        if (mRecyleAdapter.getRealCount() > 1) {
            isAutoScroll = true;
            sendScrollMessage(delayTimeInMills);
        }
    }

    /**
     * stop auto scroll
     */
    public void stopAutoScroll() {
        isAutoScroll = false;
        handler.removeMessages(MSG_WHAT);
    }

    /**
     * set the factor by which the duration of sliding animation will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        scroller.setScrollDurationFactor(scrollFactor);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(MSG_WHAT);
        handler.sendEmptyMessageDelayed(MSG_WHAT, delayTimeInMills);
    }

    /**
     * modify duration of ViewPager
     */
    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);

            scroller = new CustomDurationScroller(getContext(), (Interpolator) interpolatorField.get(null));
            scrollerField.set(mViewPager, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * scroll only once
     */
    public void scrollOnce() {
        PagerAdapter adapter = mViewPager.getAdapter();
        int currentItem = mViewPager.getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }

        int nextItem = (direction == LEFT) ? --currentItem : ++currentItem;

        if (nextItem < 0) {
            if (isInfinite) {
                mViewPager.setCurrentItem(totalCount - 1);
            }
        } else if (nextItem == totalCount) {
            if (isInfinite) {
                mViewPager.setCurrentItem(0);
            }
        } else {
            mViewPager.setCurrentItem(nextItem, true);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (isStopScrollWhenTouch) {
            if ((action == MotionEvent.ACTION_DOWN) && isAutoScroll) {
                isStopByTouch = true;
                stopAutoScroll();
            } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
                startAutoScroll();
            }
        }

        if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT || slideBorderMode == SLIDE_BORDER_MODE_CYCLE) {
            touchX = ev.getX();
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                downX = touchX;
            }
            int currentItem = mViewPager.getCurrentItem();
            PagerAdapter adapter = mViewPager.getAdapter();
            int pageCount = adapter == null ? 0 : adapter.getCount();
            /**
             * current index is first one and slide to right or current index is last one and slide to left.<br/>
             * if slide border mode is to parent, then requestDisallowInterceptTouchEvent false.<br/>
             * else scroll to last one when current item is first one, scroll to first one when current item is last
             * one.
             */
            if ((currentItem == 0 && downX <= touchX) || (currentItem == pageCount - 1 && downX >= touchX)) {
                if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (pageCount > 1) {
                        mViewPager.setCurrentItem(pageCount - currentItem - 1);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void notifyDataChange() {
        if (mIndicator != null)
            mIndicator.notifyDataSetChanged();
    }

    private class ScrollHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_WHAT:
                    scrollOnce();
                    sendScrollMessage(interval);
                default:
                    break;
            }
        }
    }

    /**
     * get auto scroll interval time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     *
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * set auto scroll interval time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     *
     * @param interval the interval to set
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * get auto scroll direction
     *
     * @return {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public int getDirection() {
        return (direction == LEFT) ? LEFT : RIGHT;
    }

    /**
     * set auto scroll direction
     *
     * @param direction {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * whether is infinite loop of viewPager , default is true
     *
     * @return the isInfinite
     */
    public boolean isInfinite() {
        return isInfinite;
    }

    /**
     * set whether is loop when reaching the last or first item, default is true
     *
     * @param isInfinite the isInfinite
     */
    public void setInfinite(boolean isInfinite) {
        this.isInfinite = isInfinite;
        mRecyleAdapter.setLoop(isInfinite);
    }

    /**
     * whether stop auto scroll when touching, default is true
     *
     * @return the stopScroll When Touch
     */
    public boolean isStopScrollWhenTouch() {
        return isStopScrollWhenTouch;
    }

    /**
     * set whether stop auto scroll when touching, default is true
     *
     * @param stopScrollWhenTouch
     */
    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.isStopScrollWhenTouch = stopScrollWhenTouch;
    }

    /**
     * get how to process when sliding at the last or first item
     *
     * @return the slideBorderMode {@link #SLIDE_BORDER_MODE_NONE}, {@link #SLIDE_BORDER_MODE_TO_PARENT},
     * {@link #SLIDE_BORDER_MODE_CYCLE}, default is {@link #SLIDE_BORDER_MODE_NONE}
     */
    public int getSlideBorderMode() {
        return slideBorderMode;
    }

    /**
     * set how to process when sliding at the last or first item
     * will be explore in future version
     * @param slideBorderMode {@link #SLIDE_BORDER_MODE_NONE}, {@link #SLIDE_BORDER_MODE_TO_PARENT},
     *                        {@link #SLIDE_BORDER_MODE_CYCLE}, default is {@link #SLIDE_BORDER_MODE_NONE}
     */
    private void setSlideBorderMode(int slideBorderMode) {
        this.slideBorderMode = slideBorderMode;
    }

    public PageIndicator getPagerIndicator() {
        return mIndicator;
    }

    public enum IndicatorPosition {
        Center("Center_Bottom", R.id.default_center_indicator),
        Center_Bottom("Center_Bottom", R.id.default_center_bottom_indicator),
        Right_Bottom("Right_Bottom", R.id.default_bottom_right_indicator),
        Left_Bottom("Left_Bottom", R.id.default_bottom_left_indicator),
        Center_Top("Center_Top", R.id.default_center_top_indicator),
        Right_Top("Right_Top", R.id.default_center_top_right_indicator),
        Left_Top("Left_Top", R.id.default_center_top_left_indicator);

        private final String name;
        private final int id;

        private IndicatorPosition(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String toString() {
            return name;
        }

        public int getResourceId() {
            return id;
        }
    }

    public void setIndicatorPosition(){
        setIndicatorPosition(IndicatorPosition.Center_Bottom);
    }

    public void setIndicatorPosition(IndicatorPosition presentIndicator) {
        PageIndicator pagerIndicator = (PageIndicator) findViewById(presentIndicator.getResourceId());
        setCustomIndicator(pagerIndicator);
    }

    public void setCustomIndicator(PageIndicator indicator) {
        initFirstPage();
        mIndicator = indicator;
        mIndicator.setViewPager(mViewPager);
//        startAutoScroll();
    }

}
