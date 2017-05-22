package matt.rea.yelpsample;

public class APIConstants {

    public class Yelp {

        public static final String BASE_URL = "https://api.yelp.com/v3/";

        public static final String AUTH_BASE_URL = "https://api.yelp.com/oauth2/";

        public static final String SEARCH = "businesses/search";

        public static final String AUTOCOMPLETE = "autocomplete";

        public static final String SEARCH_TERM = "term";

        public static final String TEXT = "text";

        public static final String LATITUDE = "latitude";

        public static final String LONGITUDE = "longitude";

        public static final String LOCATION = "location";

        public static final String LIMIT = "limit";

        public static final String OFFSET = "offset";

    }

    public class Auth {

        public static final String AUTHENTICATE = "token";
        public static final String HEADER_AUTHORIZATION = "Authorization";

        public static final String CLIENT_ID = "client_id";
        public static final String CLIENT_SECRET = "client_secret";

        public static final String GRANT_TYPE = "grant_type";
        public static final String GRANT_TYPE_VALUE = "client_credentials";

        public static final String CLIENT_ID_VALUE = "0NnG53zH571sbC9IpphMJw";
        public static final String CLIENT_ID_SECRET = "gzTpl9KWL83synnD6OpRrJFLhk1mS9BFe2iMPUjEUjjtUyUBGYdL8qscKPgxZwo2";
    }
}
