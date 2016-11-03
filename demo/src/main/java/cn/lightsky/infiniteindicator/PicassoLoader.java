package cn.lightsky.infiniteindicator;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

/**
 * Created by lightsky on 16/1/28.
 */

public class PicassoLoader implements ImageLoader {

    public PicassoLoader getImageLoader(Context context) {
        return new PicassoLoader();
    }

    @Override
    public void load(Context context,ImageView targetView, Object res) {
        if (res == null) {
            return;
        }

        Picasso picasso = Picasso.with(context);
        RequestCreator requestCreator = null;

        if (res instanceof String) {
            requestCreator = picasso.load((String) res);
        } else if (res instanceof File) {
            requestCreator = picasso.load((File) res);
        } else if (res instanceof Integer) {
            requestCreator = picasso.load((Integer) res);
        }

        requestCreator
                .fit()
                .tag(context)
                .into(targetView);

    }

}
