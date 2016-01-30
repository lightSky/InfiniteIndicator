package cn.lightsky.infiniteindicator.slideview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.lightsky.infiniteindicator.R;

/**
 * a simple slider view, which just show an image. If you want to make your own slider view,
 * <p/>
 * just extend BaseSliderView, and implement getView() method.
 */
public class SimpleSliderView extends SliderView {

    public SimpleSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;

        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.simple_slider_view, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        bindView(view, holder.target);
        return view;
    }

    private static class ViewHolder {
        final ImageView target;

        public ViewHolder(View view) {
            target = (ImageView) view.findViewById(R.id.slider_image);
        }
    }
}
