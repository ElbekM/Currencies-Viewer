package com.example.currenciesviewer.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currenciesviewer.R
import com.example.currenciesviewer.base.BaseDialogFragment
import com.example.currenciesviewer.screens.adapter.ValuteViewAdapter
import kotlinx.android.synthetic.main.fragment_currency.*
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

    override fun bindViewModel() {
        super.bindViewModel()

        with(viewModel) {
            valuteList.observe {
                it?.let { valutes ->
                    var adapter = valutesRecyclerView.adapter as? ValuteViewAdapter
                    if (adapter == null) {
                        adapter = ValuteViewAdapter()
                        valutesRecyclerView.adapter = adapter
                    }

                    adapter.setValutes(valutes)
                }
            }

            publicationDate.observe { date ->
                date?.let { exchangeDateTextView.text = it }
            }
        }
    }

    override fun close() {
        activity?.finish()
    }

    private fun initViews() {
        valutesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        fun newInstance() = CurrencyFragment()
    }
}
