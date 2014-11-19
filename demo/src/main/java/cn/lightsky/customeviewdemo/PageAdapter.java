package cn.lightsky.customeviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import com.jakewharton.salvage.RecyclingPagerAdapter;
import sky.light.com.customeviewdemo.R;

public class PageAdapter  extends RecyclingPagerAdapter{

	private Context mContext;
	private LayoutInflater inflater;

    public static final String DEBUG_TAG = "ImageView__ActionEvent";
    private boolean isInfiniteLoop;
    List<Integer> drawableIds;
    int size;

    public PageAdapter(Context context,List<Integer> drawableIds) {
		mContext = context;
		inflater = LayoutInflater.from(context);
        size = drawableIds == null ? 0 : drawableIds.size();
        this.drawableIds = drawableIds;
        isInfiniteLoop = false;
	}
	
    /**
     * get really position
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return  isInfiniteLoop ? position % size : position;
    }

    @Override
	public int getCount() {
        return isInfiniteLoop ? Integer.MAX_VALUE : size;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup container) {
        ViewHolder holder = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_page, container, false);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        }

        holder.image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"onClick",Toast.LENGTH_SHORT).show();
            }
        });

        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext,"OnLongClick",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        holder.image.setImageResource(drawableIds.get(getPosition(position)));
        return convertView;
	}

    private static class ViewHolder {
        ImageView image;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public PageAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
