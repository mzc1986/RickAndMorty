package com.ds.rickandmorty

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.View
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ds.rickandmorty.databinding.ActivityMainBinding
import com.ds.rickandmorty.model.Character

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: CharacterListAdapters
    private lateinit var manager: LinearLayoutManager

    var currentItems = 0
    var totalItems = 0
    var scrolledOutItems = 0
    var pageCount = 0
    var countPages = 0
    var isScrolling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        adapter = CharacterListAdapters()
        manager = LinearLayoutManager(this)

        binding.rvCharacters.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.layoutManager = manager

        if (isNetworkAvailable(this)) {
            binding.swipeRefresh.isRefreshing = true
        } else {
            Toast.makeText(this, resources.getString(R.string.check_internet), Toast.LENGTH_LONG).show()
        }

        viewModel.allCharactersLiveData.observe(this, { character: Character? ->
            adapter.addResult(character?.results)
            pageCount = character?.info?.pages!!
            binding.progressBar.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        })

        viewModel.filteredCharLiveData.observe(this, { character: Character? ->
            if (character != null) {
                adapter.clearAdapter()
                adapter.addResult(character.results)
                pageCount = character.info?.pages!!
            } else {
                Toast.makeText(this, resources.getString(R.string.no_result), Toast.LENGTH_LONG).show()
            }
            binding.swipeRefresh.isRefreshing = false
        })

        binding.swipeRefresh.setOnRefreshListener {
            adapter.clearAdapter()
            viewModel.getPages(1)
            binding.swipeRefresh.isRefreshing = false
        }

        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager.childCount
                totalItems = manager.itemCount
                scrolledOutItems = manager.findFirstVisibleItemPosition()
                if (isScrolling && ((currentItems + scrolledOutItems) == totalItems)) {
                    countPages++
                    isScrolling = false
                    binding.progressBar.visibility = View.VISIBLE
                    if (countPages <= pageCount) viewModel.getPages(countPages)
                }
            }
        })
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (TextUtils.isEmpty(query)) {
                    Toast.makeText(this@MainActivity, resources.getString(R.string.enter_text), Toast.LENGTH_SHORT).show()
                } else {
                    adapter.clearAdapter()
                    countPages = 0
                    viewModel.searchCharacters(query, "alive")
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }
}