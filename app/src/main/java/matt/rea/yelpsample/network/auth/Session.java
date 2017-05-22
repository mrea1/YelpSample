package matt.rea.yelpsample.network.auth;

import matt.rea.yelpsample.Storage;

/**
 * Created by Matt on 5/19/17.
 */

public class Session {

    private GetTokenResponse mGetTokenResponse;
    private final Storage storage;

    public Session(Storage storage) {
        this.storage = storage;
        mGetTokenResponse = storage.getSession();
    }

    public boolean isLoggedIn() {
        return null != mGetTokenResponse;
    }

    public GetTokenResponse getTokenResponse(){
        return mGetTokenResponse;
    }

    public String getSessionToken() {
        return mGetTokenResponse.token;
    }

    public void setSession(GetTokenResponse tokenResponse) {
        mGetTokenResponse = tokenResponse;
        storage.setSession(tokenResponse);
    }
}
