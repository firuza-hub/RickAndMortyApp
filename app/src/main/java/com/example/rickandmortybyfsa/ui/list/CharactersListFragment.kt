package com.example.rickandmortybyfsa.ui.list

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmortybyfsa.R
import com.example.rickandmortybyfsa.adapters.CharacterListItemAdapter
import com.example.rickandmortybyfsa.data.remote.CharacterApiStatus
import com.example.rickandmortybyfsa.data.remote.models.CharacterDetails
import com.example.rickandmortybyfsa.databinding.FragmentCharactersListBinding


class CharactersListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: CharactersListViewModel by lazy {
        ViewModelProvider(
            this,
            CharactersListViewModel.Factory(requireActivity().application)
        )[CharactersListViewModel::class.java]
    }
    private lateinit var adapter: CharacterListItemAdapter
    private lateinit var binding: FragmentCharactersListBinding
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_characters_list, container, false)

        adapter = CharacterListItemAdapter { navigateToDetailsView(it) }
        binding.rvCharacters.adapter = adapter

        binding.btnNext.setOnClickListener {
            if (viewModel.hasNextPage()) {
                viewModel.currentPage++
                viewModel.loadData()
            }
        }
        binding.btnPrev.setOnClickListener {
            if (viewModel.hasPrevPage()) {
                viewModel.currentPage--
                viewModel.loadData()
            }
        }

        setupSwipe()
        setupMenu()
        loadRecyclerViewData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.characterList.observe(requireActivity()) {
            adapter.setData(it.results)

            binding.btnNext.isClickable = viewModel.hasNextPage()
            binding.btnPrev.isClickable = viewModel.hasPrevPage()
            binding.tvPage.text = "${viewModel.currentPage} of ${it.info.pages}"
        }
        viewModel.status.observe(requireActivity(), Observer {
            mSwipeRefreshLayout.isRefreshing = it == CharacterApiStatus.LOADING
        })
    }


    private fun navigateToDetailsView(data: CharacterDetails) {
        Navigation.findNavController(binding.root)
            .navigate(CharactersListFragmentDirections.actionListFragmentToDetailFragment(data))
    }


    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
                val menuItem = menu.findItem(R.id.app_bar_search)
                val searchView = menuItem.actionView as SearchView
                searchView.queryHint = "Search character"

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        viewModel.filterByName(p0 ?: "")
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        viewModel.filterByName(p0 ?: "")
                        return false
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onRefresh() {
        loadRecyclerViewData()
    }

    private fun setupSwipe() {
        mSwipeRefreshLayout = binding.swipe
        mSwipeRefreshLayout.setOnRefreshListener(this)
        mSwipeRefreshLayout.setColorSchemeResources(
            R.color.black,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )

    }
    private fun loadRecyclerViewData() {
        // Showing refresh animation before making http call
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.loadData()
    }

}