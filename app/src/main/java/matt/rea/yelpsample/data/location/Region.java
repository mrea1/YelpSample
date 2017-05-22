package matt.rea.yelpsample.data.location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Matt on 5/19/17.
 */

public class Region {
    @SerializedName("center")
    public Center center;

    public class Center {
        @SerializedName("latitude")
        public Double latitude;

        @SerializedName("longitude")
        public Double longitude;
    }
}
