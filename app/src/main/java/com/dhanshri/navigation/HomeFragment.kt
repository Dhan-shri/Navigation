package com.dhanshri.navigation

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.dhanshri.navigation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.btnSignup.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment)
//            if (!TextUtils.isEmpty(binding.editTextTextPersonName2.text.toString())){
//                val bundle : Bundle = bundleOf("user_input" to binding.editTextTextPersonName2.text.toString())
//                it.findNavController().navigate(R.id.action_homeFragment_to_secondFragment, bundle)
//            } else{
//                Toast.makeText(requireContext(), "Please enter something",Toast.LENGTH_LONG).show()
//            }

        }
        binding.btnTerms.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_termsFragment)
        }
        return binding.root
    }
}
