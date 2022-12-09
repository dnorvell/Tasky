package com.norvellium.tasky.auth.presentation.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.norvellium.tasky.auth.presentation.login.LoginEvent
import com.norvellium.tasky.auth.presentation.login.LoginViewModel
import com.norvellium.tasky.core.presentation.collectLifecycleFlow
import com.norvellium.tasky.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private val viewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectLifecycleFlow(viewModel.registerState) { state ->
            binding.ivUsernameCheck.isVisible = state.isValidUsername
            binding.ivEmailCheck.isVisible = state.isValidEmail

            if (state.isPasswordVisible) binding.etPassword.transformationMethod = null
            else binding.etPassword.transformationMethod = PasswordTransformationMethod()
            // Move cursor to end when visibility is toggled
            binding.etPassword.setSelection(binding.etPassword.length())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        binding.etUsername.addTextChangedListener { text: Editable? ->
            if (text!!.isNotBlank()) {
                viewModel.checkIfUsernameValid(text)
            }
        }

        binding.etEmailAddress.addTextChangedListener { text: Editable? ->
            if (text!!.isNotBlank()) {
                viewModel.checkIfEmailValid(text)
            }
        }

        binding.etPassword.addTextChangedListener { text: Editable? ->
            if (text!!.isNotBlank()) {
                viewModel.updatePassword(text)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is RegisterEvent.ValidationSuccess -> {
                        viewModel.register()
                    }
                    is RegisterEvent.ValidationFailed -> {
                        Toast.makeText(
                            requireContext(),
                            event.message?.asString(requireContext()),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is RegisterEvent.RegistrationFailed -> {
                        Toast.makeText(
                            requireContext(),
                            event.message?.asString(requireContext()),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is RegisterEvent.RegistrationSucceeded -> {
                        Toast.makeText(
                            requireContext(),
                            "you may now login",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }

        binding.btnGetStarted.setOnClickListener {
            viewModel.validateRegistration()
        }

        binding.ivShowPassword.setOnClickListener {
            viewModel.togglePasswordVisibility()
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}