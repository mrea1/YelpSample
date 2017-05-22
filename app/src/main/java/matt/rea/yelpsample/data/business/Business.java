package matt.rea.yelpsample.data.business;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import matt.rea.yelpsample.data.location.Coordinates;
import matt.rea.yelpsample.data.location.Location;

public class Business {

    @SerializedName("rating")
    public Double rating;

    @SerializedName("price")
    public String price;

    @SerializedName("phone")
    public String phone;

    @SerializedName("id")
    public String id;

    @SerializedName("is_closed")
    public Boolean isClosed;

    @SerializedName("categories")
    public List<BusinessCategory> categories = null;

    @SerializedName("review_count")
    public Integer reviewCount;

    @SerializedName("name")
    public String name;

    @SerializedName("url")
    public String url;

    @SerializedName("coordinates")
    public Coordinates coordinates;

    @SerializedName("image_url")
    public String imageUrl;

    @SerializedName("location")
    public Location location;

    @SerializedName("distance")
    public Double distance;

    @SerializedName("transactions")
    public List<String> transactions = null;

}