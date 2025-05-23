package com.vinivenditti.pointscards.games.bisca.historical

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.FragmentHistoricalBinding
import com.vinivenditti.pointscards.games.bisca.BiscaViewModel
import com.vinivenditti.pointscards.games.bisca.BiscaViewModelFactory
import com.vinivenditti.pointscards.start.StartViewModelSingleton
import kotlin.jvm.java

class HistoricalFragment : Fragment() {

    private var _binding: FragmentHistoricalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoricalViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoricalBinding.inflate(inflater, container, false)



        return binding.root
    }
}