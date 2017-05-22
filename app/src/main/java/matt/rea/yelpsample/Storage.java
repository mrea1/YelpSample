package matt.rea.yelpsample;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashSet;

import matt.rea.yelpsample.network.auth.GetTokenResponse;

/**
 * Created by rush on 5/19/17.
 */

public class Storage {

    private static final String DEFAULT_CITY = "Austin, TX";

    private static final String PREFS = "localStorage";
    private static final String PREF_SESSION_TOKEN = "sessionToken";
    private static final String PREF_SETTING_CITY = "settingCity";
    private static final String PREF_PREVIOUS_SEARCHES = "previousSearches";

    final private App application;

    public Storage(App app) {
        application = app;
    }

    public void setSession(GetTokenResponse tokenInformation) {
        setString(PREF_SESSION_TOKEN, tokenInformation.token);
    }

    public GetTokenResponse getSession() {
        String token = getString(PREF_SESSION_TOKEN);
        if (TextUtils.isEmpty(token)) {
            return null;
        }

        GetTokenResponse tokenResponse = new GetTokenResponse();
        tokenResponse.token = token;
        return tokenResponse;
    }

    public String getSettingCity() {
        String city = getString(PREF_SETTING_CITY);
        if (TextUtils.isEmpty(city)) {
            setSettingCity(DEFAULT_CITY);
            return DEFAULT_CITY;
        }

        return city;
    }

    public void setSettingCity(String city) {
        setString(PREF_SETTING_CITY, city);
    }

    public HashSet<String> getPreviousSearches() {
        return getStringSet(PREF_PREVIOUS_SEARCHES);
    }

    public void setPreviousSearches(HashSet<String> searches) {
        setStringSet(PREF_PREVIOUS_SEARCHES, searches);
    }

    public void clearPreviousSearches() {
        application.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putStringSet(PREF_PREVIOUS_SEARCHES, new HashSet<>())
                .apply();
    }

    private String getString(String key) {
        return application.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(key, null);
    }

    private HashSet<String> getStringSet(String key) {
        HashSet<String> set = new HashSet<>();
        set = (HashSet<String>) application.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getStringSet(key, set);
        if (set.isEmpty()) {
            setStringSet(key, set);
        }
        return set;
    }

    private void setStringSet(String key, HashSet<String> searches) {
        application.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putStringSet(key, searches)
                .apply();
    }

    private void setString(String key, String value) {
        application.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .apply();
    }
}
