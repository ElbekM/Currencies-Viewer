package com.example.currenciesviewer.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.currenciesviewer.R
import com.example.currenciesviewer.base.BaseDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyFragment : BaseDialogFragment<CurrencyViewModel>() {

    override val viewModel: CurrencyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_currency, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()
        viewModel.init()
    }

    override fun close() {
        activity?.finish()
    }

    private fun initViews() {

    }

    companion object {
        fun newInstance() = CurrencyFragment()
    }
}
