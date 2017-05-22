package matt.rea.yelpsample.network.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Matt on 5/19/17.
 */

public class GetTokenResponse {

    @Expose
    @SerializedName("access_token")
    public String token;

    @SerializedName("token_type")
    public String tokenType;

}
