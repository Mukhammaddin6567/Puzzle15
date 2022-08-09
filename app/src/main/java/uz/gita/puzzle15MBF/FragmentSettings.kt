package uz.gita.puzzle15MBF

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.gita.puzzle15MBF.database.MySharedPreferences
import uz.gita.puzzle15MBF.databinding.DialogClearRecordsBinding
import uz.gita.puzzle15MBF.databinding.FragmentSettingsBinding
import uz.gita.puzzle15MBF.extensions.onClick
import uz.gita.puzzle15MBF.extensions.snackErrorMessage
import uz.gita.puzzle15MBF.extensions.snackMessage

class FragmentSettings : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        registerOnBackPressed()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonBack.onClick { findNavController().popBackStack() }

            switchFastStart.isChecked =
                MySharedPreferences.getBoolean(getString(R.string.shp_fast_start), false)
            switchFastStart.setOnCheckedChangeListener { _, isChecked ->
                when {
                    isChecked -> MySharedPreferences.putBoolean(
                        getString(R.string.shp_fast_start),
                        true
                    )
                    else -> MySharedPreferences.putBoolean(
                        getString(R.string.shp_fast_start),
                        false
                    )
                }
            }

            switchFastPassage.isChecked =
                MySharedPreferences.getBoolean(getString(R.string.shp_fast_passage), false)
            switchFastPassage.setOnCheckedChangeListener { _, isChecked ->
                when {
                    isChecked -> MySharedPreferences.putBoolean(
                        getString(R.string.shp_fast_passage),
                        true
                    )
                    else -> MySharedPreferences.putBoolean(
                        getString(R.string.shp_fast_passage),
                        false
                    )
                }
            }

            switchSound.isChecked =
                MySharedPreferences.getBoolean(resources.getString(R.string.shp_sound), false)
            switchSound.setOnCheckedChangeListener { _, isChecked ->
                MySharedPreferences.putBoolean(
                    resources.getString(R.string.shp_sound),
                    isChecked
                )
            }

            switchMusic.isChecked =
                MySharedPreferences.getBoolean(resources.getString(R.string.shp_music), false)
            switchMusic.setOnCheckedChangeListener { _, isChecked ->
                MySharedPreferences.putBoolean(
                    resources.getString(R.string.shp_music),
                    isChecked
                )
            }
            buttonClearRecord.onClick {
                if (MySharedPreferences.getString(
                        resources.getString(R.string.shp_records),
                        null
                    ) == null
                ) snackErrorMessage(getString(R.string.no_record))
                else showDialog()
            }
        }

    }

    private fun showDialog() {
        val binding = DialogClearRecordsBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        binding.buttonClearRecordNo.onClick {
            dialog.dismiss()
        }
        binding.buttonClearRecordYes.onClick {
            MySharedPreferences.putString(resources.getString(R.string.shp_records), null)
            snackMessage(getString(R.string.success_record))
            dialog.dismiss()
        }
    }

    private fun registerOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

