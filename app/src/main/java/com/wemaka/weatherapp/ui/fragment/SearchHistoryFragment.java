package com.wemaka.weatherapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wemaka.weatherapp.R;
import com.wemaka.weatherapp.data.local.SearchHistoryDAO;
import com.wemaka.weatherapp.data.local.SearchHistoryDatabaseHelper;
import com.wemaka.weatherapp.data.model.SearchHistoryItem;
import com.wemaka.weatherapp.ui.adapter.SearchHistoryAdapter;
import com.wemaka.weatherapp.ui.fragment.SearchMenuFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryFragment extends Fragment {

    public static final String TAG = "SearchHistoryFragment";
    private RecyclerView recyclerView;
    private Button btnBack;
    private SearchHistoryAdapter adapter;
    private List<SearchHistoryItem> searchHistory = new ArrayList<>();
    private SearchHistoryDAO searchHistoryDAO;

    public SearchHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_history, container, false);

        SearchHistoryDatabaseHelper dbHelper = new SearchHistoryDatabaseHelper(getContext());
        searchHistoryDAO = new SearchHistoryDAO(dbHelper);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SearchHistoryAdapter(searchHistory);
        recyclerView.setAdapter(adapter);

        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            MainFragment mainFragment = new MainFragment();
            transaction.replace(R.id.placeHolder, mainFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        adapter.setOnItemClickListener(new SearchHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchHistoryItem item) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                Fragment existing = fragmentManager.findFragmentByTag(SearchMenuFragment.TAG);

                if (existing == null) {
                    SearchMenuFragment searchMenuFragment = SearchMenuFragment.newInstance();
                    Bundle args = new Bundle();
                    args.putString("query", item.getQuery());
                    searchMenuFragment.setArguments(args);

                    searchMenuFragment.show(fragmentManager, SearchMenuFragment.TAG);
                }
            }

            @Override
            public void onDeleteClick(SearchHistoryItem item) {
                searchHistoryDAO.deleteSearchHistory(item.getQuery());
                adapter.removeItem(item);
            }
        });

        loadSearchHistory();

        return view;
    }

    private void loadSearchHistory() {
        searchHistory.clear();
        searchHistory.addAll(searchHistoryDAO.getAllSearchHistory());
        adapter.notifyDataSetChanged();
    }
}
