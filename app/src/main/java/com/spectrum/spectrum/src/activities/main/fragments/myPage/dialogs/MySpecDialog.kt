package com.spectrum.spectrum.src.activities.main.fragments.myPage.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.DialogMySpecBinding
import com.spectrum.spectrum.src.activities.main.fragments.myPage.interfaces.MyPageApi
import com.spectrum.spectrum.src.activities.main.fragments.myPage.models.Spec
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.dialogs.OptionDialog
import com.spectrum.spectrum.src.models.Option
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MySpecDialog(private val fragment: BaseFragment): Dialog(fragment.requireContext(), R.style.AppTheme) {

    private lateinit var mBinding: DialogMySpecBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_my_spec, null, false)
        mBinding.dialog = this
        setCanceledOnTouchOutside(false)
        window?.apply {
            setBackgroundDrawable(ColorDrawable())
            setDimAmount(0.0f)
            attributes.let { params ->
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = WindowManager.LayoutParams.MATCH_PARENT
                params.windowAnimations = R.style.DialogSlideAnimation
            }
            setGravity(Gravity.END)
        }
        getMySpec()
        setContentView(mBinding.root)
    }

    fun menuButtonAction() {
        val options = ArrayList<Option>().apply {
            add(Option(Constants.edit, R.drawable.icon_edit))
            add(Option(Constants.delete, R.drawable.icon_trash))
        }
        OptionDialog(fragment.parentFragmentManager)
            .setOptions(options)
            .setOnItemClickListener { position ->
                if (position == 0) {
                    proceedToEditSpec()
                }
                if (position == 1) { showDeleteSpecDialog() }
            }
            .showDialog()
    }

    private fun showDeleteSpecDialog() {
        AlertDialog.Builder(context)
            .setMessage(Constants.ask_delete_spec)
            .setPositiveButton(Constants.yes) { _, _ -> deleteMySpec() }
            .create()
            .show()
    }

    fun proceedToEditSpec() {
        fragment.findNavController().navigate(R.id.my_page_to_edit_spec)
        dismiss()
    }

    @SuppressLint("SetTextI18n")
    private fun getMySpec() {
        CoroutineScope(Dispatchers.Main).launch {
            retrofit.create(MyPageApi::class.java).getMySpec().apply {
                if (isSuccess) {
                    findViewById<ImageButton>(R.id.menu_button).visibility = View.VISIBLE
                    findViewById<MaterialCardView>(R.id.my_spec_card_view).visibility = View.VISIBLE
                    findViewById<ConstraintLayout>(R.id.no_spec_view).visibility = View.GONE
                    result.apply {
                        findViewById<TextView>(R.id.your_spec_text).text = "$username 님의 스펙"
                        findViewById<TextView>(R.id.update_time_text).text = "$updatedAt 업데이트됨"
                        bindChipGroup(findViewById(R.id.user_info_chip_group), this)
                    }
                    return@launch
                }
                else {
                    findViewById<ImageButton>(R.id.menu_button).visibility = View.GONE
                    findViewById<MaterialCardView>(R.id.my_spec_card_view).visibility = View.GONE
                    findViewById<ConstraintLayout>(R.id.no_spec_view).visibility = View.VISIBLE
                }
            }
        }
    }

    private fun deleteMySpec() {
        CoroutineScope(Dispatchers.Main).launch {
            retrofit.create(MyPageApi::class.java).deleteMySpec().apply {
                if (isSuccess) {
                    Log.e(TAG, "---> MY SPEC DELETE SUCCESS")
                    getMySpec()
                    return@launch
                }
                Log.e(TAG, "---> MY SPEC DELETE FAILURE: $message")
                fragment.showToast(Constants.request_failed)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindChipGroup(chipGroup: ChipGroup, spec: Spec) {
        chipGroup.apply {
            Chip(context).apply {
                text = "${spec.age}세"
                setEnsureMinTouchTargetSize(false)
                setChipBackgroundColorResource(R.color.spectrumSilver2)
                setTextAppearance(R.style.ChipText)
                addView(this)
            }
            Chip(context).apply {
                text = spec.sex
                setEnsureMinTouchTargetSize(false)
                setChipBackgroundColorResource(R.color.spectrumSilver2)
                setTextAppearance(R.style.ChipText)
                addView(this)
            }
            spec.jobGroups?.forEach {
                Chip(context).apply {
                    text = it.name
                    setEnsureMinTouchTargetSize(false)
                    setChipBackgroundColorResource(R.color.spectrumSilver2)
                    setTextAppearance(R.style.ChipText)
                    addView(this)
                }
            }
        }
    }

    companion object {
        val TAG = MySpecDialog::class.java.simpleName.toString()
    }

}