package matt.rea.yelpsample.data.location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Matt on 5/19/17.
 */

public class Location {
    @SerializedName("city")
    public String city;

    @SerializedName("country")
    public String country;

    @SerializedName("address2")
    public String address2;

    @SerializedName("address3")
    public String address3;

    @SerializedName("state")
    public String state;

    @SerializedName("address1")
    public String address1;

    @SerializedName("zip_code")
    public String zipCode;
}
