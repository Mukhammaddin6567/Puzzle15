package uz.gita.puzzle15MBF.extensions

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import uz.gita.puzzle15MBF.R

fun View.onClick(action: (View) -> Unit) {
    setOnClickListener {
        if (it != null) action(it)
    }
}

fun Fragment.snackMessage(message: String) =
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.color_container
            )
        )
        setTextColor(ContextCompat.getColor(requireContext(), R.color.color_snack_bar))
        show()
    }

fun Fragment.snackErrorMessage(message: String) =
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.color_switch_off
            )
        )
        setTextColor(ContextCompat.getColor(requireContext(), R.color.color_snack_bar))
        show()
    }

fun Fragment.visible(): Int {
    return View.VISIBLE
}

fun Fragment.invisible(): Int {
    return View.INVISIBLE

}

fun Fragment.gone(): Int {
    return View.GONE
}