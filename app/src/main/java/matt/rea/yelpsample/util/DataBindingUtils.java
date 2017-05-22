package matt.rea.yelpsample.util;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by Matt on 5/19/17.
 */

public class DataBindingUtils {

    public static void bindImageUrl(ImageView imageView, String url, boolean centerCrop, int errorResourceId) {
        Picasso picasso = Picasso.with(imageView.getContext());

        imageView.setImageBitmap(null);

        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(errorResourceId));
            return;
        }

        RequestCreator creator = picasso
                .load(url)
                .noPlaceholder()
                .error(errorResourceId);

        if (centerCrop && (imageView.getWidth() > 0 || imageView.getHeight() > 0)) {
            creator.resize(imageView.getWidth(), imageView.getHeight())
                    .centerCrop();
        }

        creator.into(imageView);
    }

    @BindingAdapter("imageUrl")
    public static void bindImageUrl(ImageView imageView, String url) {
        bindImageUrl(imageView, url, true, android.R.color.darker_gray);
    }

}
