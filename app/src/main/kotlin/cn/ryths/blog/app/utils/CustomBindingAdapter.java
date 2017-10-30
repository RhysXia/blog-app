package cn.ryths.blog.app.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomBindingAdapter {

    @BindingAdapter(value = "drawableIconTint")
    public static void drawableTint(TextView textView, Integer color) {
        Drawable[] drawables = textView.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                drawable.setTint(color);
            }
        }
    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String src) {
        if(src == null){
            return;
        }
        Picasso.with(imageView.getContext())
                .load(src)
                .into(imageView);
    }

    @BindingAdapter("android:text")
    public static void text(TextView textView, Date date) {
        String strDate = new SimpleDateFormat("yyyy/MM/dd HH:ss").format(date);
        textView.setText(strDate);
    }

    @BindingAdapter("app:loadLocalImage")
    public static void loadLocalImage(ImageView imageView, String path) {
        if(path == null){
            return;
        }
        File file = new File(path);
        Picasso.with(imageView.getContext())
                .load(file)
                .into(imageView);
    }
}
