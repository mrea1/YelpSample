package matt.rea.yelpsample.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ncapdevi.fragnav.FragNavController;

import matt.rea.yelpsample.R;
import matt.rea.yelpsample.databinding.ActivityMainBinding;
import matt.rea.yelpsample.ui.main.MainFragment;

public class YelpActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation {

    private ActivityMainBinding binding;
    protected FragNavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MainFragment fragment = MainFragment.newInstance();

        mNavController = new FragNavController(savedInstanceState, getSupportFragmentManager(), R.id.contentFrame, fragment);
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.push(fragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavController != null) {
            if (mNavController.isRootFragment()) {
                return;
            }
            mNavController.pop();
        }
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.replace(fragment);
        }
    }

    @Override
    public void popFragment() {
        if (mNavController != null) {
            mNavController.pop();
        }
    }

}
