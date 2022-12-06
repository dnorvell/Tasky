package com.norvellium.tasky.auth.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
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
import com.norvellium.tasky.core.presentation.collectLifecycleFlow
import com.norvellium.tasky.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(), CoroutineScope by MainScope() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectLifecycleFlow(viewModel.loginState) { state ->
            binding.ivCheck.isVisible = state.isValidEmail

            if (state.isPasswordVisible) binding.etPassword.transformationMethod = null
            else binding.etPassword.transformationMethod = PasswordTransformationMethod()
            // Move cursor to end when visibility is toggled
            binding.etPassword.setSelection(binding.etPassword.length())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

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

        binding.ivShowPassword.setOnClickListener {
            viewModel.togglePasswordVisibility()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is LoginEvent.ValidationSuccess -> {
                        viewModel.login()
                    }
                    is LoginEvent.ValidationFailed -> {
                        Toast.makeText(
                            requireContext(),
                            event.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is LoginEvent.LoginFailed -> {
                        Toast.makeText(
                            requireContext(),
                            event.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is LoginEvent.LoginSucceeded -> {
                        val action = LoginFragmentDirections.actionNavLoginToNavAgenda()
                        findNavController().navigate(action)
                    }
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            viewModel.validateLogin()
        }

        binding.llRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionNavLoginToNavRegister()
            findNavController().navigate(action)
        }
    }
}