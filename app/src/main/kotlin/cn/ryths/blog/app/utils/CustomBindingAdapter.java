package cn.ryths.blog.app.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;

public class CustomBindingAdapter {

    @BindingAdapter("bind:drawableTopTint")
    public static void drawableTint(TextView textView, Integer color) {
        Log.d(CustomBindingAdapter.class.getSimpleName(), "running");
        Drawable[] drawables = textView.getCompoundDrawables();
        for (int i = 0; i < drawables.length; i++) {
            if (drawables[i] != null) {
                drawables[i].setTint(color);
            }
        }

    }

}
