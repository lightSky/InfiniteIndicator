package cn.lightsky.infiniteindicator.recycle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import cn.lightsky.infiniteindicator.ImageLoader;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;

/**
 * Created by lightsky on 2016/11/18.
 *
 * extract getView for customing view binding
 */

public interface ViewBinder {

    View bindView(Context context,
                  int position,
                  Page page,
                  ImageLoader imageLoader,
                  OnPageClickListener onPageClickListener,
                  View convertView,
                  ViewGroup container);
}
