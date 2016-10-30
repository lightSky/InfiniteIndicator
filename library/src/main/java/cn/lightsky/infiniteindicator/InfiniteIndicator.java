package cn.lightsky.infiniteindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import cn.lightsky.infiniteindicator.indicator.PageIndicator;
import cn.lightsky.infiniteindicator.indicator.RecyleAdapter;
import cn.lightsky.infiniteindicator.jakewharton.salvage.RecyclingPagerAdapter;

import static android.R.attr.direction;
import static cn.lightsky.infiniteindicator.IndicatorConfiguration.SLIDE_BORDER_MODE_CYCLE;
import static cn.lightsky.infiniteindicator.IndicatorConfiguration.SLIDE_BORDER_MODE_TO_PARENT;


/**
 * Created by lightSky on 2014/12/22.
 * Thanks to: https://github.com/Trinea/android-auto-scroll-view-pager
 */
public class InfiniteIndicator extends RelativeLayout implements RecyclingPagerAdapter.DataChangeListener {
    private final ScrollHandler handler;
    private PageIndicator mIndicator;
    private ViewPager mViewPager;
    private Context mContext;
    private RecyleAdapter mRecyleAdapter;
    private boolean isStopByTouch = false;
    private float touchX = 0f, downX = 0f;
    private DurationScroller scroller;
    private IndicatorConfiguration configuration;
    private boolean isScrolling;
    public static final int MSG_SCROLL = 1000;

    public InfiniteIndicator(Context context) {
        this(context, null);
    }

    public InfiniteIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfiniteIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.InfiniteIndicator, 0, 0);
        int indicatorType = attributes.getInt(R.styleable.InfiniteIndicator_indicator_type, 0);

        if (indicatorType == 0)
            LayoutInflater.from(context).inflate(R.layout.layout_default_indicator, this, true);
        else if (indicatorType == 1)
            LayoutInflater.from(context).inflate(R.layout.layout_anim_circle_indicator, this, true);
        else
            LayoutInflater.from(context).inflate(R.layout.layout_anim_line_indicator, this, true);

        handler = new ScrollHandler(this);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mRecyleAdapter = new RecyleAdapter(mContext);
        mRecyleAdapter.setDataChangeListener(this);
        mViewPager.setAdapter(mRecyleAdapter);
        setViewPagerScroller();
    }


    public void init(IndicatorConfiguration configuration){
        this.configuration = configuration;
        scroller.setScrollDurationFactor(configuration.getScrollFactor());
        mRecyleAdapter.setIsLoop(configuration.isInfinite());
        mRecyleAdapter.setImageLoader(configuration.getImageLoader());

        if (mRecyleAdapter.getImageLoader() == null)
            throw new RuntimeException("You should set ImageLoader first");

        initIndicator();
        notifyData(configuration);
        initFirstPage();
        if (configuration.isAutoScroll()) {
            start();
        }
    }

    private void notifyData(IndicatorConfiguration configuration) {
        if (configuration.getPageViews() != null && !configuration.getPageViews().isEmpty()){
            mRecyleAdapter.setPages(configuration.getPageViews());
            mRecyleAdapter.notifyDataSetChanged();
        }
    }

    public PageIndicator getPagerIndicator() {
        return mIndicator;
    }

    public void initIndicator() {
        mIndicator = (PageIndicator) findViewById(configuration.getPresentIndicator().getResourceId());
        mIndicator.setViewPager(mViewPager);
        if (configuration.getOnPageChangeListener() != null && mIndicator != null){
            mIndicator.setOnPageChangeListener(configuration.getOnPageChangeListener());
        }
    }

    /**
     * according page count and is loop decide the first page to display
     */
    private void initFirstPage() {
        if (configuration.isInfinite() && mRecyleAdapter.getRealCount() > 1) {
            mViewPager.setCurrentItem(mRecyleAdapter.getRealCount() * 50 -
                    (mRecyleAdapter.getRealCount() * 50 % mRecyleAdapter.getRealCount()));
        } else {
            mViewPager.setCurrentItem(0);
        }
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    /**
     * start auto scroll
     *
     * @param delayTimeInMills first scroll delay time,default is 2500ms
     */
    public void start() {
        start(configuration.getInterval());
    }

    public void start(long delayTimeInMills) {
        if (mRecyleAdapter.getRealCount() > 1
                && isScrolling == false
                && configuration.isInfinite()) {
            isScrolling = true;
            sendScrollMessage(delayTimeInMills);
        }
    }

    /**
     * stop auto scroll
     */
    public void stop() {
        isScrolling = false;
        handler.removeMessages(MSG_SCROLL);
    }

    private void sendScrollMessage() {
        sendScrollMessage(configuration.getInterval());
    }

    /**
     * remove messages before, keeps one message is running at most
     **/
    private void sendScrollMessage(long delayTimeInMills) {
        handler.removeMessages(MSG_SCROLL);
        handler.sendEmptyMessageDelayed(MSG_SCROLL, delayTimeInMills);
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

            scroller = new DurationScroller(getContext(), (Interpolator) interpolatorField.get(null));
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

        int nextItem = (direction == IndicatorConfiguration.LEFT) ? --currentItem :
        ++currentItem;

        if (nextItem < 0) {
            if (configuration.isInfinite()) {
                mViewPager.setCurrentItem(totalCount - 1);
            }
        } else if (nextItem == totalCount) {
            if (configuration.isInfinite()) {
                mViewPager.setCurrentItem(0);
            }
        } else {
            mViewPager.setCurrentItem(nextItem, true);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (configuration.isStopScrollWhenTouch()) {
            if ((action == MotionEvent.ACTION_DOWN) && isScrolling) {
                isStopByTouch = true;
                stop();
            } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
                start();
            }
        }

        if (configuration.getSlideBorderMode() == SLIDE_BORDER_MODE_TO_PARENT ||
                configuration.getSlideBorderMode() ==
                SLIDE_BORDER_MODE_CYCLE) {
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
                if (configuration.getSlideBorderMode() == SLIDE_BORDER_MODE_TO_PARENT) {
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
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void notifyDataChange() {
        if (mIndicator != null)
            mIndicator.notifyDataSetChanged();
    }

    public static class ScrollHandler extends Handler {
        public WeakReference<InfiniteIndicator> mLeakActivityRef;

        public ScrollHandler(InfiniteIndicator infiniteIndicatorLayout) {
            mLeakActivityRef = new WeakReference<InfiniteIndicator>(infiniteIndicatorLayout);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            InfiniteIndicator infiniteIndicatorLayout = mLeakActivityRef.get();
            if (infiniteIndicatorLayout != null) {
                switch (msg.what) {
                    case MSG_SCROLL:
                        infiniteIndicatorLayout.scrollOnce();
                        infiniteIndicatorLayout.sendScrollMessage();
                    default:
                        break;
                }
            }
        }
    }

}
