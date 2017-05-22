package matt.rea.yelpsample.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Matt on 5/19/17.
 */

public class KeyboardUtil {

    public KeyboardUtil() {
    }

    public static void hideSoftKeyboard(AppCompatActivity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }

        hideSoftKeyboard(activity.getCurrentFocus(), activity);
    }

    public static void hideSoftKeyboard(View view, Activity activity) {

        if (view == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if ( ! inputMethodManager.isActive()) {
            return;
        }

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftKeyboard(AppCompatActivity activity, View inputView) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(inputView, InputMethodManager.SHOW_IMPLICIT);
    }
}
