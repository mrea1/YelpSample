package matt.rea.yelpsample;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    public static Storage storage;

    @Override
    public void onCreate() {
        super.onCreate();
        if (storage == null) {
            storage = new Storage(this);
        }
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public Storage getStorage() {
        return storage;
    }

}
