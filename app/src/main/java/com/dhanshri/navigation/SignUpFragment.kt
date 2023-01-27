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
import com.dhanshri.navigation.databinding.FragmentSecondBinding
import java.util.regex.Pattern

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)

//        val inputFromHome  = requireArguments().getString("user_input")
//        binding.textView.text = inputFromHome.toString()
        binding.submitButton.setOnClickListener {
            if (!TextUtils.isEmpty(binding.editName.text.toString()) && !TextUtils.isEmpty(binding.editMail.text.toString())){
                if ((isEmailValid(binding.editMail.text.toString()))){
                    val bundle : Bundle = bundleOf("user_name" to binding.editName.text.toString(), "user_email" to binding.editMail.text.toString())
                    it.findNavController().navigate(R.id.action_secondFragment_to_welcomeFragment, bundle)
                } else {
                    Toast.makeText(requireContext(), "Please correct Email", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(requireContext(), "Please enter details", Toast.LENGTH_LONG).show()
            }

        }


        return binding.root
    }

    fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}