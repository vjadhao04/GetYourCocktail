package com.drinks.getyourcocktail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.drinks.getyourcocktail.api.DrinksApiService
import com.drinks.getyourcocktail.api.RetrofitHelper
import com.drinks.getyourcocktail.databinding.FragmentMainBinding
import com.drinks.getyourcocktail.repository.ApiDataRepository
import com.drinks.getyourcocktail.utils.DrinkListRVAdapter
import com.drinks.getyourcocktail.utils.InternetHelper
import com.drinks.getyourcocktail.viewmodels.MainViewModel
import com.drinks.getyourcocktail.viewmodels.MainViewModelFactory
import com.drinks.getyourcocktail.viewmodels.SharedViewModel


class MainFragment : Fragment(), DetailsOnClickListener {
    private var _binding: FragmentMainBinding? = null;
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private val binding get() = _binding!!;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val api = RetrofitHelper.getRetrofitInstance().create(DrinksApiService::class.java)
        val apiDataRepository = ApiDataRepository(api)
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(apiDataRepository, api)
        ).get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        if (InternetHelper.isOnline(requireContext())) {
            mainViewModel.getSearchedData("Mojito")
        } else {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage("Make sure that WI-FI or mobile data is turned on, then try again")
                .setCancelable(false)
                .setPositiveButton("Retry", DialogInterface.OnClickListener { dialog, id ->
                    val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
                    if (Build.VERSION.SDK_INT >= 26) {
                        ft.setReorderingAllowed(false)
                    }
                    ft.detach(this).attach(this).commit()
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    requireActivity().finish()
                })

            val alert = dialogBuilder.create()
            alert.setTitle("No Internet Connection")
            alert.setIcon(R.mipmap.ic_launcher)
            alert.show()
        }

        mainViewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
            binding.drinkListRV.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = DrinkListRVAdapter(it, requireContext(), this@MainFragment)
            }

        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.length > 1) {
                        mainViewModel.getSearchedData(p0)
                        mainViewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
                            val adapter =
                                DrinkListRVAdapter(it, requireContext(), this@MainFragment)
                            binding.drinkListRV.adapter = adapter
                        })

                    }
                }

                return true
            }

            override fun onQueryTextChange(msg: String): Boolean {

                return false
            }
        })


        return binding.root
    }

    override fun onClick(id: Int) {
        sharedViewModel.drinkId = id
        Log.d("checking id", sharedViewModel.drinkId.toString())
        findNavController().navigate(R.id.action_mainFragment_to_drinkDetailsFragment)
    }


}

interface DetailsOnClickListener {
    abstract fun onClick(id: Int)
}