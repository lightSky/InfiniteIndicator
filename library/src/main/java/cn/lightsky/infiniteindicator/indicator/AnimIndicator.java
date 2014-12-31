package cn.lightsky.infiniteindicator.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.AnimatorInflater;
import com.nineoldandroids.animation.AnimatorSet;

import cn.lightsky.infiniteautoscroolview.R;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

public class AnimIndicator extends LinearLayout implements PageIndicator{

    private final static int DEFAULT_INDICATOR_WIDTH = 5;

    private ViewPager mViewPager;

    private OnPageChangeListener mViewPagerOnPageChangeListener;

    private int mIndicatorMargin;

    private int mIndicatorWidth;

    private int mIndicatorHeight;

    private int mAnimatorResId = R.animator.scale_with_alpha;

    private int mIndicatorBackground = R.drawable.white_radius;

    private int mCurrentPage = 0;

    private AnimatorSet mAnimationOut;
    private AnimatorSet mAnimationIn;

    public AnimIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public AnimIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        handleTypedArray(context, attrs);
        mAnimationOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, mAnimatorResId);
        mAnimationOut.setInterpolator(new LinearInterpolator());
        mAnimationIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, mAnimatorResId);
        mAnimationIn.setInterpolator(new ReverseInterpolator());
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.AnimIndicator);
            mIndicatorWidth =
                    typedArray.getDimensionPixelSize(R.styleable.AnimIndicator_ci_width, -1);
            mIndicatorHeight =
                    typedArray.getDimensionPixelSize(R.styleable.AnimIndicator_ci_height, -1);
            mIndicatorMargin =
                    typedArray.getDimensionPixelSize(R.styleable.AnimIndicator_ci_margin, -1);
            mAnimatorResId = typedArray.getResourceId(R.styleable.AnimIndicator_ci_animator,
                    R.animator.scale_with_alpha);
            mIndicatorBackground = typedArray.getResourceId(R.styleable.AnimIndicator_ci_drawable,
                    R.drawable.white_radius);
            typedArray.recycle();
        }

        mIndicatorWidth =
                (mIndicatorWidth == -1) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;
        mIndicatorHeight =
                (mIndicatorHeight == -1) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorHeight;
        mIndicatorMargin =
                (mIndicatorMargin == -1) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorMargin;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        createIndicators(viewPager);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mViewPager.setCurrentItem(item);
        mCurrentPage = item;
        invalidate();
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {

        if (mViewPager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }

        mViewPagerOnPageChangeListener = onPageChangeListener;
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void notifyDataSetChanged() {
        createIndicators(mViewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageScrolled(position, positionOffset,
                    positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageSelected(position);
        }

        if(getChildAt(((RecyleAdapter) mViewPager.getAdapter()).getPosition(mCurrentPage))==null)
            return;

        mAnimationIn.setTarget(getChildAt(((RecyleAdapter) mViewPager.getAdapter()).getPosition(mCurrentPage)));
        mAnimationIn.start();
        mAnimationOut.setTarget(getChildAt(((RecyleAdapter) mViewPager.getAdapter()).getPosition(position)));
        mAnimationOut.start();

        mCurrentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private void createIndicators(ViewPager viewPager) {
        removeAllViews();

        if(((RecyleAdapter)viewPager.getAdapter())==null) {
            return;
        }

        int count = ((RecyleAdapter)viewPager.getAdapter()).getRealCount();
        if (count <= 1) {
            return;
        }

        for (int i = 0; i < count; i++) {
            View Indicator = new View(getContext());
            Indicator.setBackgroundResource(mIndicatorBackground);
            addView(Indicator, mIndicatorWidth, mIndicatorHeight);
            LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();
            lp.leftMargin = mIndicatorMargin;
            lp.rightMargin = mIndicatorMargin;
            Indicator.setLayoutParams(lp);

            mAnimationOut.setTarget(Indicator);
            mAnimationOut.start();
        }

        mAnimationOut.setTarget(getChildAt(mCurrentPage));
        mAnimationOut.start();
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
