package matt.rea.yelpsample.ui;

import android.app.ActionBar;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import matt.rea.yelpsample.R;


public abstract class BaseFragment extends Fragment {

    protected FragmentNavigation mFragmentNavigation;
    private YelpProgressDialog mDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof FragmentNavigation)) {
            throw new IllegalStateException(
                    "Activity must implement interface " + FragmentNavigation.class.getSimpleName());
        }
        mFragmentNavigation = (FragmentNavigation) context;
        mDialog = new YelpProgressDialog(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDialog.hide();
    }

    public void goBack() {
        mFragmentNavigation.popFragment();
    }

    public void showProgress() {
        mDialog.show();
    }

    protected void setTitle(String title) {
        ActionBar actionBar =  getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(""); // hack for truncating title
            actionBar.setTitle(title);
        }
    }

    public void hideProgress() {
        mDialog.hide();
    }

    public void showErrorDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> dialog.cancel())
                .create()
                .show();
    }

    public interface FragmentNavigation {

        void pushFragment(Fragment fragment);

        void replaceFragment(Fragment fragment);

        void popFragment();
    }
}
