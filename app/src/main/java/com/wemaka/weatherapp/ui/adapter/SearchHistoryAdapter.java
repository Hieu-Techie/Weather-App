package com.wemaka.weatherapp.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.wemaka.weatherapp.R;
import com.wemaka.weatherapp.data.model.SearchHistoryItem;
import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(SearchHistoryItem item);
        void onDeleteClick(SearchHistoryItem item);
    }

    private List<SearchHistoryItem> historyList;
    private OnItemClickListener listener;

    public SearchHistoryAdapter(List<SearchHistoryItem> historyList) {
        this.historyList = historyList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchHistoryItem item = historyList.get(position);
        holder.textView.setText(item.getQuery());

        holder.textView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });

        if (holder.deleteButton != null) {
            holder.deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(item);
                }
            });
        } else {
            Log.e("SearchHistoryAdapter", "Delete button is null at position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void removeItem(SearchHistoryItem item) {
        int index = historyList.indexOf(item);
        if (index != -1) {
            historyList.remove(index);
            notifyItemRemoved(index);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvQuery);
            deleteButton = itemView.findViewById(R.id.ivDelete);
        }
    }
}
