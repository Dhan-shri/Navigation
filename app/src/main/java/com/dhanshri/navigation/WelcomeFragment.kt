package com.dhanshri.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.dhanshri.navigation.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_welcome, container, false)

        val welcomeText = requireArguments().getString("user_name" )
        val emailText = requireArguments().getString("user_email")
        binding.welcomeTxt.text = "Welcome ${welcomeText.toString()}"
        binding.txtEmail.text = emailText.toString()

        binding.returnTerm.setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment_to_termsFragment)
        }

        return binding.root
    }

}