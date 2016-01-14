package cn.lightsky.infiniteindicator.slideview;

import java.io.File;

/**
 * Created by xushuai on 2014/12/25.
 */
public class PageView {
    public String data;
    public String url;
    public Integer drawableRes;
    public File file;
    public int imageResForError;
    public int imageResForEmpty;

    public SliderView.OnSliderClickListener onSliderClickListener;
    public SliderView.ScaleType scaleType;

    public PageView(String data, Integer drawableRes) {
        this.data = data;
        this.drawableRes = drawableRes;
    }

    public PageView(String data, String url) {
        this.data = data;
        this.url = url;
    }
}
