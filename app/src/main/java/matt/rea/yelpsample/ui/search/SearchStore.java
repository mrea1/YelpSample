package matt.rea.yelpsample.ui.search;

import java.util.HashSet;

import matt.rea.yelpsample.Storage;

/**
 * Created by Matt on 5/22/17.
 */

public class SearchStore {

    private final Storage storage;

    public SearchStore(Storage storage) {
        this.storage = storage;
    }

    public HashSet<String> getPreviousSearches(){
        return storage.getPreviousSearches();
    }

    public void setPreviousSearches(HashSet<String> searches){
        storage.setPreviousSearches(searches);
    }

    public void addSearch(String search) {
        storage.getPreviousSearches().add(search);
    }

    public void removeSearch(String search) {

    }

    public void clearSearches() {
        storage.clearPreviousSearches();
    }
}
