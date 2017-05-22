package matt.rea.yelpsample.network.auth;

import static matt.rea.yelpsample.APIConstants.Auth.AUTHENTICATE;
import static matt.rea.yelpsample.APIConstants.Auth.CLIENT_ID;
import static matt.rea.yelpsample.APIConstants.Auth.CLIENT_SECRET;
import static matt.rea.yelpsample.APIConstants.Auth.GRANT_TYPE;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthAPI {

    @FormUrlEncoded
    @POST(AUTHENTICATE)
    Observable<Response<GetTokenResponse>> getToken(
            @Field(GRANT_TYPE) String grantType,
            @Field(CLIENT_ID) String clientId,
            @Field(CLIENT_SECRET) String clientSecret
    );

}
