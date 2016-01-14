package cn.lightsky.infiniteindicator.slideview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import cn.lightsky.infiniteindicator.R;

/**
 * a simple slider view, which just show an image. If you want to make your own slider view,
 *
 * just extend BaseSliderView, and implement getView() method.
 */
public class SimpleSliderView extends SliderView {

    public SimpleSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.simple_slider_view,null);
        ImageView target = (ImageView)v.findViewById(R.id.slider_image);
        bingView(v, target);
        return v;
    }
}
