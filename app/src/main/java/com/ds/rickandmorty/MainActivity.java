package com.ds.rickandmorty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.ds.rickandmorty.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    private CharacterListAdapters adapter;
    private LinearLayoutManager manager;

    int currentItems, totalItems, scrolledOutItems;
    int pageCount, countPages;
    Boolean isScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        adapter = new CharacterListAdapters();
        manager = new LinearLayoutManager(this);

        binding.rvCharacters.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvCharacters.setAdapter(adapter);
        binding.rvCharacters.setLayoutManager(manager);

        if(isNetworkAvailable(this)){
            binding.swipeRefresh.setRefreshing(true);
        } else {
            Toast.makeText(this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }

        viewModel.getAllCharactersLiveData().observe(this, character -> {
            adapter.addResult(character.getResults());
            pageCount = character.getInfo().getPages();
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.swipeRefresh.setRefreshing(false);
        });

        viewModel.getFilteredCharLiveData().observe(this, character -> {
            if(character != null) {
                adapter.clearAdapter();
                adapter.addResult(character.getResults());
                pageCount = character.getInfo().getPages();
            } else {
                Toast.makeText(this, getResources().getString(R.string.no_result), Toast.LENGTH_LONG).show();
            }

            binding.swipeRefresh.setRefreshing(false);
        });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clearAdapter();
                viewModel.getPages(1);
                binding.swipeRefresh.setRefreshing(false);
            }
        });

        binding.rvCharacters.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling == true && (currentItems + scrolledOutItems == totalItems)){
                    countPages++;

                    isScrolling = false;
                    binding.progressBar.setVisibility(View.VISIBLE);
                    if(countPages <= pageCount)
                            viewModel.getPages(countPages);
                }
            }
        });
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.main_menu, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_text), Toast.LENGTH_SHORT).show();
                } else {
                    adapter.clearAdapter();
                    countPages = 0;
                    viewModel.searchCharacters(query, "alive");
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
}