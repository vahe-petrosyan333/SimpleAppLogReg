package com.example.simpleapplogreg.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simpleapplogreg.R
import com.example.simpleapplogreg.data.UserModel
import com.example.simpleapplogreg.data.UserViewModel
import com.example.simpleapplogreg.databinding.FragmentLoginBinding
import com.example.simpleapplogreg.view.validatorEditText.OnValidListener


class LoginFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var usersList: List<UserModel>

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        getAllUsers()
        binding.login.setOnClickListener {
            if (isValid(binding.root))
                login()
        }
        return binding.root
    }

    private fun getAllUsers() {
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { users ->
            usersList = mutableListOf()
            usersList = users
        })
    }

    private fun login() {
        userViewModel.login(binding.email.text.toString(), binding.pass.text.toString())
        userViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), "No such user!", Toast.LENGTH_LONG).show()
            }
        })

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