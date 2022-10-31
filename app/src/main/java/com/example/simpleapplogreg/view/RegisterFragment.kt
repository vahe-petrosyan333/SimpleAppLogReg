package com.example.simpleapplogreg.view

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simpleapplogreg.R
import com.example.simpleapplogreg.data.UserModel
import com.example.simpleapplogreg.data.UserViewModel
import com.example.simpleapplogreg.databinding.FragmentRegisterBinding
import com.example.simpleapplogreg.view.validatorEditText.OnValidListener


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mUserModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        mUserModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.register.setOnClickListener {
            if (isValid(binding.root))
                insertDataToDatabase()
        }
        return binding.root
    }

    private fun insertDataToDatabase() {
        val firstName = binding.userName.text.toString()
        val lastName = binding.userLastName.text.toString()
        val email = binding.email.text.toString()
        val pass = binding.pass.text.toString()
        if (inputCheck(firstName, lastName, email, pass)) {
            val userModel = UserModel(0, firstName, lastName, email, pass)
            mUserModel.addUser(userModel)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(
        firstName: String,
        lastName: String,
        email: String,
        pass: String
    ): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName)
                && TextUtils.isEmpty(email) && TextUtils.isEmpty(pass))
    }

    fun isValid(root: View): Boolean {
        if (root is ViewGroup) {
            for (view in root.children) {
                isValid(view)
            }
        } else if (root is OnValidListener) {
            return root.validate()
        }
        return false
    }
}