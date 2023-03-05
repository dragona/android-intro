package mg.x261.myviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MyPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public MyPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3; // we have 3 pages
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_pager_item, container, false);

        TextView textView = view.findViewById(R.id.text_view);
        textView.setText("Page " + (position + 1));

        // set background color based on page number
        int backgroundColor = android.R.color.white;
        switch (position) {
            case 0:
                backgroundColor = android.R.color.white;
                break;
            case 1:
                backgroundColor = android.R.color.holo_red_light;
                break;
            case 2:
                backgroundColor = android.R.color.holo_green_light;
                break;
        }
        textView.setBackgroundResource(backgroundColor);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
