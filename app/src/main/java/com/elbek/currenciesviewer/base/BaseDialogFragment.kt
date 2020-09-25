package com.elbek.currenciesviewer.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.elbek.currenciesviewer.R
import com.elbek.currenciesviewer.base.livedata.LiveArgEvent
import com.elbek.currenciesviewer.base.livedata.LiveEvent
import com.elbek.currenciesviewer.base.snackbar.Snackbar

abstract class BaseDialogFragment<TViewModel> : DialogFragment() where TViewModel : BaseViewModel {

    private val originalScreenOrientationKey: String = ::originalScreenOrientationKey.name
    private val snackbar = Snackbar()

    protected abstract val viewModel: TViewModel
    protected open val screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = false
        setStyle(STYLE_NO_TITLE, R.style.AppTheme)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.destroy()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (arguments == null) {
            arguments = Bundle()
        }

        arguments!!.putInt(originalScreenOrientationKey, activity!!.requestedOrientation)
    }

    override fun onResume() {
        super.onResume()
        activity!!.requestedOrientation = screenOrientation
    }

    override fun onPause() {
        super.onPause()
        activity!!.requestedOrientation = arguments!!.getInt(originalScreenOrientationKey)
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        dialog.setOnKeyListener(DialogInterface.OnKeyListener { _, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                Handler(Looper.getMainLooper()).postDelayed({ onBackPressed() }, 100)
                return@OnKeyListener true
            } else {
                return@OnKeyListener false
            }
        })
    }

    protected open fun bindViewModel() {
        with(viewModel) {
            closeCommand.observe { close() }

            showSnackBarCommand.observe {
                it?.let {
                    snackbar.showMessageWithAction(requireView(), requireContext(), it)
                }
            }

            showMessageCommand.observe { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        }
    }

    protected open fun close() = dismissAllowingStateLoss()

    protected open fun onBackPressed() = viewModel.back()

    fun <T> LiveArgEvent<T>.observe(observer: (item: T?) -> Unit) =
        observe(getSuitableLifecycleOwner(), Observer(observer))

    fun <T> MutableLiveData<T>.observe(observer: (item: T?) -> Unit) =
        observe(getSuitableLifecycleOwner(), Observer(observer))

    fun LiveEvent.observe(block: () -> Unit) =
        this.observe(getSuitableLifecycleOwner(), Observer { block() })

    private fun getSuitableLifecycleOwner() =
        if (view != null) viewLifecycleOwner else this
}

fun DialogFragment.showAllowingStateLoss(fm: FragmentManager, tag: String = this::class.java.name) =
    fm.beginTransaction()
        .add(this, tag)
        .addToBackStack(tag)
        .commitAllowingStateLoss()
