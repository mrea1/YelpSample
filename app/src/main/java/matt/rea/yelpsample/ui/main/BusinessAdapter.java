package matt.rea.yelpsample.ui.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matt.rea.yelpsample.R;
import matt.rea.yelpsample.data.business.Business;
import matt.rea.yelpsample.databinding.ListItemBusinessBinding;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder> {

    private List<Business> businessList;
    private BusinessListener mListener;

    public interface BusinessListener {
        void loadMore(int offset);
    }

    public BusinessAdapter(List<Business> items, BusinessListener listener) {
        businessList = items;
        mListener = listener;
    }

    @Override
    public BusinessAdapter.BusinessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BusinessAdapter.BusinessViewHolder.inflateFrom(parent);
    }

    @Override
    public void onBindViewHolder(BusinessAdapter.BusinessViewHolder viewHolder, int position) {
        if (position >= getItemCount() - 1) {
            mListener.loadMore(getItemCount());
        }
        viewHolder.bind(businessList.get(position));
    }

    @Override
    public int getItemCount() {
        if (businessList == null || businessList.isEmpty())
            return 0;
        return businessList.size();
    }

    public void updateItems(List<Business> newItems){
        businessList.addAll(newItems);
        notifyDataSetChanged();
    }

    static class BusinessViewHolder extends RecyclerView.ViewHolder {

        private ListItemBusinessBinding binding;
        private Context mContext;

        public BusinessViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            mContext = itemView.getContext();
        }

        public void bind(Business item) {
            binding.setBusiness(item);
            binding.executePendingBindings();
        }

        static BusinessAdapter.BusinessViewHolder inflateFrom(ViewGroup parent) {
            return new BusinessAdapter.BusinessViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_business, parent, false));
        }
    }
}
