package com.example.currenciesviewer.base

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.currenciesviewer.livedata.LiveArgEvent
import com.example.currenciesviewer.livedata.LiveEvent

abstract class BaseDialogFragment<TViewModel> : DialogFragment() where TViewModel : BaseViewModel {

    protected abstract val viewModel: TViewModel

    private val originalScreenOrientationKey: String = ::originalScreenOrientationKey.name
    protected open val screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = false
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

        viewModel.start()
    }

    override fun onPause() {
        super.onPause()

        activity!!.requestedOrientation = arguments!!.getInt(originalScreenOrientationKey)

        viewModel.stop()
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

            showMessageCommand.observe { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        }
    }

    protected open fun close() = dismissAllowingStateLoss()

    protected open fun onBackPressed() = viewModel.back()

    fun <T> LiveArgEvent<T>.observe(observer: (item: T?) -> Unit) =
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

val Fragment.parent: Any?
    get() = parentFragment ?: activity

inline fun <reified T> Fragment.castParent(): T? = parent as? T