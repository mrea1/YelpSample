package matt.rea.yelpsample.ui.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matt.rea.yelpsample.R;
import matt.rea.yelpsample.databinding.ListItemSearchBinding;

public class PreviousSearchesAdapter extends RecyclerView.Adapter<PreviousSearchesAdapter.SearchViewHolder> {

    interface SearchListener {
        void onSearchTermSelected(String term);
    }

    private List<String> searchList;
    private SearchListener listener;

    public PreviousSearchesAdapter(List<String> searches, SearchListener listener) {
        searchList = searches;
        this.listener = listener;
    }

    @Override
    public PreviousSearchesAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PreviousSearchesAdapter.SearchViewHolder.inflateFrom(parent, listener);
    }

    @Override
    public void onBindViewHolder(PreviousSearchesAdapter.SearchViewHolder viewHolder, int position) {
        viewHolder.bind(searchList.get(position));
    }

    @Override
    public int getItemCount() {
        if (searchList == null || searchList.isEmpty())
            return 0;
        return searchList.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {

        private ListItemSearchBinding binding;
        private Context mContext;
        private SearchListener mListener;

        public SearchViewHolder(View itemView, SearchListener listener) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            mContext = itemView.getContext();
            mListener = listener;
        }

        public void bind(String term) {
            binding.searchTerm.setText(term);
            binding.searchTerm.setOnClickListener( v -> mListener.onSearchTermSelected(binding.searchTerm.getText().toString()));
        }

        static PreviousSearchesAdapter.SearchViewHolder inflateFrom(ViewGroup parent, SearchListener listener) {
            return new PreviousSearchesAdapter.SearchViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_search, parent, false), listener);
        }
    }
}
