package matt.rea.yelpsample.network;

import android.graphics.Region;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import matt.rea.yelpsample.data.business.Business;

public class SearchResponse {

    @SerializedName("total")
    public Integer total;

    @SerializedName("businesses")
    public List<Business> businesses = null;

    @SerializedName("region")
    public Region region;

}
