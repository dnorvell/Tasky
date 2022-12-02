package com.norvellium.tasky.auth.presentation.login

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.norvellium.tasky.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
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
            // This is called whenever loginState changes
            Log.v("test", state.toString())
            binding.ivCheck.isVisible = state.isValidEmail
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        binding.etEmailAddress.addTextChangedListener { text: Editable? ->
            if( text!!.isNotBlank()) {
                viewModel.checkIfEmailValid(text)
            }
        }

        // TODO listen for events from view model
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is LoginEvent.ValidationSuccess -> {
                        Toast.makeText(
                            requireContext(),
                            "time to login",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is LoginEvent.ValidationFailed -> {
                        Toast.makeText(
                            requireContext(),
                            event.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.llRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionNavLoginToNavRegister()
            findNavController().navigate(action)
        }
    }

    fun <T> Fragment.collectLifecycleFlow(flow: Flow<T>, onCollect: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest {
                    onCollect(it)
                }
            }
        }
    }

}