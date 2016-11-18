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
import java.util.List;

import cn.lightsky.infiniteindicator.indicator.PageIndicator;
import cn.lightsky.infiniteindicator.recycle.RecyclingPagerAdapter;
import cn.lightsky.infiniteindicator.recycle.RecyleAdapter;


/**
 * Created by lightSky on 2014/12/22.
 */
public class InfiniteIndicator extends RelativeLayout implements
        RecyclingPagerAdapter.DataChangeListener, ViewPager.OnPageChangeListener {

    private Context mContext;
    private ViewPager mViewPager;
    private PageIndicator mIndicator;
    private RecyleAdapter mRecyleAdapter;
    private DurationScroller scroller;
    private final ScrollHandler handler;
    private boolean isScrolling;
    private boolean isStopByTouch;
    private float downX = 0f;
    private float touchX = 0f;
    public static final int MSG_SCROLL = 1000;
    public static final int PAGE_COUNT_FACTOR = 100;
    private IndicatorConfiguration configuration;

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

        if (indicatorType == 0) {
            LayoutInflater.from(context).inflate(R.layout.layout_default_indicator, this, true);
        } else if (indicatorType == 1) {
            LayoutInflater.from(context).inflate(R.layout.layout_anim_circle_indicator, this, true);
        } else {
            LayoutInflater.from(context).inflate(R.layout.layout_anim_line_indicator, this, true);
        }

        attributes.recycle();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        handler = new ScrollHandler(this);
    }

    public void init(IndicatorConfiguration configuration) {
        this.configuration = configuration;
        mRecyleAdapter = new RecyleAdapter(mContext
                ,configuration.getViewBinder()
                ,configuration.getOnPageClickListener());
        mRecyleAdapter.setDataChangeListener(this);
        mViewPager.setAdapter(mRecyleAdapter);
        mViewPager.addOnPageChangeListener(this);
        mRecyleAdapter.setIsLoop(configuration.isLoop());
        mRecyleAdapter.setImageLoader(configuration.getImageLoader());
        setScroller();
        initIndicator();
    }

    public void notifyDataChange(List<Page> pages) {
        if (pages != null && !pages.isEmpty()) {
            mRecyleAdapter.setPages(pages);
        }

        scrollToIndex(0);
        if (configuration.isAutoScroll()) {
            start();
        }

    }

    public void initIndicator() {
        if (configuration.isDrawIndicator()) {
            mIndicator = (PageIndicator) findViewById(configuration.getPageIndicator().getResourceId());
            mIndicator.setViewPager(mViewPager);
        }
    }

    /**
     * return PageIndicator for develop to custome indicator
     *
     * @return
     */
    public PageIndicator getPagerIndicator() {
        return mIndicator;
    }

    /**
     * scroll to given index page by loop and offset
     * page to display
     */
    private void scrollToIndex(int offset) {
        if (configuration.isLoop() && getRealCount() > 1) {
            mViewPager.setCurrentItem(getIndex(offset));
        } else {
            mViewPager.setCurrentItem(offset);
        }
        if (mIndicator != null) {
            mIndicator.setCurrentItem(offset);
        }
    }

    /**
     * get the item index of viewpager by given offset
     *
     * @param offset the real index of pages
     * @return
     */
    private int getIndex(int offset) {
        return getRealCount() * PAGE_COUNT_FACTOR / 2 -
                (getRealCount() * PAGE_COUNT_FACTOR / 2 % getRealCount
                        ()) + offset;
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
        if (configuration == null) {
            throw new RuntimeException("You should init a configuration first");
        }

        if (getRealCount() > 1
                && isScrolling == false
                && configuration.isLoop()) {
            isScrolling = true;
            sendScrollMessage(delayTimeInMills);
        }
    }

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
    private void setScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);
            scroller = new DurationScroller(getContext(), (Interpolator) interpolatorField.get(null));
            scrollerField.set(mViewPager, scroller);
            scroller.setScrollDurationFactor(configuration.getScrollFactor());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getRealCount() {
        return mRecyleAdapter.getRealCount();
    }

    private int getRealPosition(int position) {
        return mRecyleAdapter.getRealPosition(position);
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

        int nextItem = (configuration.getDirection() == IndicatorConfiguration.LEFT)
                ? --currentItem
                : ++currentItem;

        if (nextItem < 0) {
            if (configuration.isLoop()) {
                mViewPager.setCurrentItem(totalCount - 1);
            }
        } else if (nextItem == totalCount) {
            if (configuration.isLoop()) {
                mViewPager.setCurrentItem(0);
            }
        } else {
            mViewPager.setCurrentItem(nextItem, true);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (configuration == null) {
            return super.dispatchTouchEvent(ev);
        }

        int action = MotionEventCompat.getActionMasked(ev);
        if (configuration.isStopWhenTouch()) {
            if ((action == MotionEvent.ACTION_DOWN) && isScrolling) {
                isStopByTouch = true;
                stop();
            } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
                start();
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void notifyDataChange() {
        if (mIndicator != null) {
            mIndicator.notifyDataSetChanged();
        }
    }

    public static class ScrollHandler extends Handler {

        public WeakReference<InfiniteIndicator> mWeakReference;

        public ScrollHandler(InfiniteIndicator infiniteIndicatorLayout) {
            mWeakReference = new WeakReference<InfiniteIndicator>(infiniteIndicatorLayout);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            InfiniteIndicator infiniteIndicatorLayout = mWeakReference.get();
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mIndicator != null) {
            mIndicator.onPageScrolled(getRealPosition(position), positionOffset, positionOffsetPixels);
        }

        if (configuration.getOnPageChangeListener() != null) {
            configuration.getOnPageChangeListener().onPageScrolled(
                    getRealPosition(position), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mIndicator != null) {
            mIndicator.onPageSelected(getRealPosition(position));
        }
        if (configuration.getOnPageChangeListener() != null) {
            configuration.getOnPageChangeListener().onPageSelected(getRealPosition(position));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mIndicator != null) {
            mIndicator.onPageScrollStateChanged(state);
        }
        if (configuration.getOnPageChangeListener() != null) {
            configuration.getOnPageChangeListener().onPageScrollStateChanged(state);
        }
    }

    public void setCurrentItem(int index) {
        if (index > getRealCount() - 1) {
            throw new IndexOutOfBoundsException("index is " + index + "current " +
                    "list size is " + getRealCount());
        }
        scrollToIndex(index);
    }
}
