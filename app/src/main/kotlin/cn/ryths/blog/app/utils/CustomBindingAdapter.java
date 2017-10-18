package cn.ryths.blog.app.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

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

}
