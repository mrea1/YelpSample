package matt.rea.yelpsample.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import matt.rea.yelpsample.R;

/**
 * Created by Matt on 5/22/17.
 */

public class YelpProgressDialog extends AlertDialog {

    public YelpProgressDialog(@NonNull Context context) {
        super(context, R.style.YelpProgressDialog);
        init(context);
    }

    private void init(Context context) {
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setView(LayoutInflater.from(context).inflate(R.layout.yelp_progress, null, false));
    }

}
