package matt.rea.yelpsample.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import matt.rea.yelpsample.data.business.Business;
import matt.rea.yelpsample.data.business.BusinessCategory;

/**
 * Created by Matt on 5/19/17.
 */

public class AutoCompleteResponse {
    @SerializedName("terms")
    public List<String> searchTerms;

    @SerializedName("businesses")
    public List<Business> businesses;

    @SerializedName("categories")
    public List<BusinessCategory> categories;
}
