package matt.rea.yelpsample.network;

import static matt.rea.yelpsample.APIConstants.Auth.HEADER_AUTHORIZATION;
import static matt.rea.yelpsample.APIConstants.Yelp.*;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

public interface YelpAPI {

    @GET(SEARCH)
    Observable<Response<SearchResponse>> getBusinesses(
            @Header(HEADER_AUTHORIZATION) String authorization,
            @Query(SEARCH_TERM) String searchTerm,
            @Query(LOCATION) String location,
            @Query(LATITUDE) Long latitude,
            @Query(LONGITUDE) Long longitude,
            @Query(LIMIT) Integer limit,
            @Query(OFFSET) Integer offset
    );

    @GET(AUTOCOMPLETE)
    Observable<Response<AutoCompleteResponse>> getAutoComplete(
            @Header(HEADER_AUTHORIZATION) String authorization,
            @Query(TEXT) String searchTerm,
            @Query(LATITUDE) Long latitude,
            @Query(LONGITUDE) Long longitude
    );

}
