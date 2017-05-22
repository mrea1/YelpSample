package matt.rea.yelpsample.ui.settings;

import matt.rea.yelpsample.Storage;

/**
 * Created by Matt on 5/19/17.
 */

public class Settings {

    private final Storage storage;

    public Settings(Storage storage) {
        this.storage = storage;
    }

    public String getCity(){
        return storage.getSettingCity();
    }

    public void setCity(String city) {
        storage.setSettingCity(city);
    }
}
