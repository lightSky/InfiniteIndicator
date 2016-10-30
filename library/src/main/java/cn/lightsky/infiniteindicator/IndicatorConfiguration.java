package cn.lightsky.infiniteindicator;

import android.content.Context;
import android.support.v4.view.ViewPager;

import java.util.List;

import cn.lightsky.infiniteindicator.indicator.PageIndicator;
import cn.lightsky.infiniteindicator.loader.ImageLoader;
import cn.lightsky.infiniteindicator.page.Page;


/**
 * Created by lightsky on 2016/10/29.
 */

public class IndicatorConfiguration {

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


    private final double scrollFactor;
    private boolean isAutoScroll;
    private final ImageLoader imageLoader;
    private final List<Page> pageViews;
    private int direction;
    private int slideBorderMode;
    private boolean isInfinite;
    private long interval;
    private boolean isStopScrollWhenTouch;
    private final ViewPager.OnPageChangeListener onPageChangeListener;
    private final IndicatorPosition presentIndicator;

    private IndicatorConfiguration(Builder builder) {
        imageLoader = builder.imageLoader;
        pageViews = builder.pageViews;
        isInfinite = builder.isInfinite;
        slideBorderMode = builder.slideBorderMode;
        interval = builder.interval;
        direction = builder.direction;
        isAutoScroll = builder.isAutoScroll;
        scrollFactor = builder.scrollFactor;
        presentIndicator = builder.presentIndicator;
        isStopScrollWhenTouch = builder.isStopScrollWhenTouch;
        onPageChangeListener = builder.onPageChangeListener;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public List<Page> getPageViews() {
        return pageViews;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isInfinite() {
        return isInfinite;
    }

    public long getInterval() {
        return interval;
    }

    public boolean isStopScrollWhenTouch() {
        return isStopScrollWhenTouch;
    }

    public boolean isAutoScroll() {
        return isAutoScroll;
    }

    public double getScrollFactor() {
        return scrollFactor;
    }

    public IndicatorPosition getPresentIndicator() {
        return presentIndicator;
    }

    public int getSlideBorderMode() {
        return slideBorderMode;
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    public static class Builder{
        private ImageLoader imageLoader;
        private List<Page> pageViews;
        private boolean isInfinite = true;
        private boolean isAutoScroll = false;
        private long interval = DEFAULT_INTERVAL;
        private int slideBorderMode = SLIDE_BORDER_MODE_NONE;
        private int direction = RIGHT;
        private boolean isStopScrollWhenTouch = true;
        private double scrollFactor;
        private ViewPager.OnPageChangeListener onPageChangeListener;
        private IndicatorPosition presentIndicator = IndicatorPosition.Center_Bottom;

        public Builder imageLoader(ImageLoader imageLoader) {
            this.imageLoader = imageLoader;
            return this;
        }

        public Builder pages(List<Page> pageViews) {
            this.pageViews = pageViews;
            return this;
        }

        /**
         * set whether is loop when reaching the last or first item, default is true
         *
         * @param isInfinite the isInfinite
         */
        public Builder isInfinite(boolean isInfinite) {
            this.isInfinite = isInfinite;
            return this;
        }

        /**
         * auto scroll direction, default is {@link #RIGHT} *
         */
        public Builder direction(int direction) {
            this.direction = direction;
            return this;
        }

        /**
         * auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL} *
         */
        public Builder internal(long interval) {
            this.interval = interval;
            return this;
        }

        /**
         * whether stop auto scroll when touching, default is true *
         */
        public Builder isStopWhenTouch(boolean isStopScrollWhenTouch) {
            this.isStopScrollWhenTouch = isStopScrollWhenTouch;
            return this;
        }

        /**
         * whether auto scroll when initialize done *
         */
        public Builder isAutoScroll(boolean isAutoScroll) {
            this.isAutoScroll = isAutoScroll;
            return this;
        }

        /**
         * set how to process when sliding at the last or first item
         * will be explore in future versio default is{@link #SLIDE_BORDER_MODE_NONE} *
         *
         * @param slideBorderMode {@link #SLIDE_BORDER_MODE_NONE}, {@link #SLIDE_BORDER_MODE_TO_PARENT},
         *                        {@link #SLIDE_BORDER_MODE_CYCLE}, default is {@link #SLIDE_BORDER_MODE_NONE}
         */
        private Builder setSlideBorderMode(int slideBorderMode) {
            this.slideBorderMode = slideBorderMode;
            return this;
        }

        /**
         * set the factor by which the duration of sliding animation will change
         */
        public Builder setScrollDurationFactor(double scrollFactor) {
            this.scrollFactor = scrollFactor;
            return this;
        }

        public Builder setPosition(IndicatorPosition presentIndicator) {
            this.presentIndicator = presentIndicator;
            return this;
        }

        public Builder setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
            this.onPageChangeListener = onPageChangeListener;
            return this;
        }

        public IndicatorConfiguration build() {
            return new IndicatorConfiguration(this);
        }
    }
}
