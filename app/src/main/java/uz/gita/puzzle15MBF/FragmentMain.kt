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
import uz.gita.puzzle15MBF.databinding.DialogAboutBinding
import uz.gita.puzzle15MBF.databinding.DialogExitAppBinding
import uz.gita.puzzle15MBF.databinding.DialogRecordsBinding
import uz.gita.puzzle15MBF.databinding.FragmentMainBinding
import uz.gita.puzzle15MBF.extensions.gone
import uz.gita.puzzle15MBF.extensions.onClick
import uz.gita.puzzle15MBF.extensions.snackErrorMessage
import uz.gita.puzzle15MBF.extensions.visible


class FragmentMain : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonStart.onClick {
                findNavController().navigate(R.id.action_fragmentMain_to_fragmentGame)
            }
            buttonRecords.onClick {
                if (MySharedPreferences.getString(
                        resources.getString(R.string.shp_records),
                        null
                    ) == null
                ) snackErrorMessage(resources.getString(R.string.no_record))
                else dialogRecords()
            }
            buttonSettings.onClick {
                findNavController().navigate(R.id.action_fragmentMain_to_fragmentSettings)
            }
            buttonAbout.onClick {
                dialogAbout()
            }
            buttonExit.onClick {
                dialogExit()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    dialogExit()
                }
            })
    }

    private fun dialogRecords() {
        val currentBinding = DialogRecordsBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(currentBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val temp =
            MySharedPreferences.getString(resources.getString(R.string.shp_records), null)!!
                .split("#")

        val values = mutableListOf<Int>()
        for (element in temp) {
            values.add(element.toInt())
        }
        values.sort()
//        Toast.makeText(requireContext(), values.toString(), Toast.LENGTH_SHORT).show()
        when {
            values.size == 1 -> {
                currentBinding.firstPlace.text =
                    resources.getString(R.string.tv_records_dialog, values[0].toString())
                currentBinding.firstPlace.visibility = visible()
                currentBinding.img1.visibility = visible()
                currentBinding.secondPlace.visibility = gone()
                currentBinding.img2.visibility = gone()
                currentBinding.thirdPlace.visibility = gone()
                currentBinding.img3.visibility = gone()
            }
            values.size == 2 -> {
                currentBinding.firstPlace.text =
                    resources.getString(R.string.tv_records_dialog, values[0].toString())
                currentBinding.secondPlace.text =
                    resources.getString(R.string.tv_records_dialog, values[1].toString())
                currentBinding.firstPlace.visibility = visible()
                currentBinding.img1.visibility = visible()
                currentBinding.secondPlace.visibility = visible()
                currentBinding.img2.visibility = visible()
                currentBinding.thirdPlace.visibility = gone()
                currentBinding.img3.visibility = gone()
            }
            values.size >= 3 -> {
                currentBinding.firstPlace.text =
                    resources.getString(R.string.tv_records_dialog, values[0].toString())
                currentBinding.secondPlace.text =
                    resources.getString(R.string.tv_records_dialog, values[1].toString())
                currentBinding.thirdPlace.text =
                    resources.getString(R.string.tv_records_dialog, values[2].toString())
                currentBinding.firstPlace.visibility = visible()
                currentBinding.img1.visibility = visible()
                currentBinding.secondPlace.visibility = visible()
                currentBinding.img2.visibility = visible()
                currentBinding.thirdPlace.visibility = visible()
                currentBinding.img3.visibility = visible()
            }
        }
        currentBinding.buttonAboutOk.onClick {
            dialog.dismiss()
        }
    }

    private fun dialogAbout() {
        val currentBinding = DialogAboutBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(currentBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        currentBinding.buttonAboutOk.onClick {
            dialog.dismiss()
        }
    }

    private fun dialogExit() {
        val currentBinding = DialogExitAppBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(currentBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(true)
        currentBinding.buttonExitNo.onClick {
            dialog.dismiss()
        }
        currentBinding.buttonExitYes.onClick {
            dialog.dismiss()
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

