package com.drinks.getyourcocktail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.drinks.getyourcocktail.api.DrinksApiService
import com.drinks.getyourcocktail.api.RetrofitHelper
import com.drinks.getyourcocktail.databinding.FragmentDrinkDetailsBinding
import com.drinks.getyourcocktail.repository.ApiDataRepository
import com.drinks.getyourcocktail.viewmodels.DrinkDetailsViewmodel
import com.drinks.getyourcocktail.viewmodels.DrinkDetailsViewmodelFactory
import com.drinks.getyourcocktail.viewmodels.MainViewModel
import com.drinks.getyourcocktail.viewmodels.MainViewModelFactory
import com.drinks.getyourcocktail.viewmodels.SharedViewModel


class DrinkDetailsFragment : Fragment() {
    private lateinit var _binding: FragmentDrinkDetailsBinding
    private val binding get() = _binding;
    private lateinit var mainViewModel: MainViewModel
    private lateinit var drinkDetailsViewmodel: DrinkDetailsViewmodel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = RetrofitHelper.getRetrofitInstance().create(DrinksApiService::class.java)
        val apiDataRepository = ApiDataRepository(api)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        drinkDetailsViewmodel =
            ViewModelProvider(this, DrinkDetailsViewmodelFactory(apiDataRepository, api)).get(
                DrinkDetailsViewmodel::class.java
            )


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDrinkDetailsBinding.inflate(inflater, container, false)
        if (sharedViewModel.drinkId != -1) {

            drinkDetailsViewmodel.getDrink(sharedViewModel.drinkId)
            drinkDetailsViewmodel.drinkLiveData.observe(viewLifecycleOwner, Observer {
                Log.d("testing single data", it.drinks.toString())
                val data = it.drinks[0]
                binding.apply {
                    nametxt.text = data.strDrink
                    instructiontxt.text = data.strInstructions
                    categorytxt.text = data.strCategory
                    val ingredient =
                        data.strIngredient1 + "\n" + data.strIngredient2 + "\n" + data.strIngredient3 + "\n" + data.strIngredient4 + "\n"
                    measurementtxt.text = ingredient
                    Glide.with(requireContext()).load(data.strDrinkThumb.toUri())
                        .into(binding.drinkThumb)
                }
            })
        }




        return binding.root

    }


}