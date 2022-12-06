package com.norvellium.tasky.agenda.presentation.agenda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.norvellium.tasky.R
import com.norvellium.tasky.databinding.FragmentAgendaBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@AndroidEntryPoint
class AgendaFragment : Fragment(), CoroutineScope by MainScope() {

    companion object {
        fun newInstance() = AgendaFragment()
    }

    private val viewModel: AgendaViewModel by viewModels()

    private var _binding: FragmentAgendaBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgendaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            findNavController().popBackStack()
            findNavController().navigate(R.id.nav_login)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}