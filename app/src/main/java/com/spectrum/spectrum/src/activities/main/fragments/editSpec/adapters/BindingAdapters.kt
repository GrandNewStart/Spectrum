package com.spectrum.spectrum.src.activities.main.fragments.editSpec.adapters

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.editSpec.EditSpecFragment
import com.spectrum.spectrum.src.activities.main.fragments.editSpec.EditSpecViewModel
import com.spectrum.spectrum.src.adapters.EduItemAdapter
import com.spectrum.spectrum.src.adapters.ExpItemAdapter
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.adapters.CertItemAdapter
import com.spectrum.spectrum.src.models.CertItem
import com.spectrum.spectrum.src.models.EduItem
import com.spectrum.spectrum.src.models.ExpItem
import com.spectrum.spectrum.src.models.JobGroup

object BindingAdapters {

    @BindingAdapter("edit_spec_job_group_1", "edit_spec_fragment", requireAll = true)
    @JvmStatic
    fun bindJobGroup1(chip: Chip, item: JobGroup?, fragment: EditSpecFragment) {
        chip.apply {
            item?.let {
                text = it.name
                isCloseIconVisible = true
                setOnCloseIconClickListener {
                    fragment.apply {
                        mViewModel.apply {
                            mJobGroup1 = mJobGroup2
                            if (mJobGroup2 != null) mJobGroup2 = null
                        }
                        mBinding.apply {
                            jobGroup1 = mViewModel.mJobGroup1
                            jobGroup2 = mViewModel.mJobGroup2
                        }
                    }
                }
            }
        }
    }

    @BindingAdapter("edit_spec_job_group_2", "edit_spec_fragment", requireAll = true)
    @JvmStatic
    fun bindJobGroup2(chip: Chip, item: JobGroup?, fragment: EditSpecFragment) {
        chip.apply {
            item?.let {
                text = it.name
                isCloseIconVisible = true
                setOnCloseIconClickListener {
                    fragment.mViewModel.mJobGroup2 = null
                    fragment.mBinding.jobGroup2 = null
                }
            }
        }
    }

    @BindingAdapter("edit_spec_age")
    @JvmStatic
    fun bindAgeTextView(textView: TextView, age: Int?) {
        textView.apply {
            age?.let {
                text = it.toString()
                setTextColor(resources.getColor(R.color.black, null))
            }
        }
    }

    @BindingAdapter("edit_spec_sex", "edit_spec_male", "edit_spec_female", "edit_spec_view_model")
    @JvmStatic
    fun bindSexRadioGroup(radioGroup: RadioGroup, sex: String?, male: RadioButton, female: RadioButton, viewModel: EditSpecViewModel) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == male.id) {
                viewModel.mSex = "M"
            }
            if (checkedId == female.id) {
                viewModel.mSex = "F"
            }
        }
        sex?.let {
            radioGroup.findViewById<RadioButton>(R.id.male_button).isChecked = it == "M"
            radioGroup.findViewById<RadioButton>(R.id.female_button).isChecked = it == "F"
        }
    }

    @BindingAdapter("edit_spec_edu_items")
    @JvmStatic
    fun bindEduItems(recyclerView: RecyclerView, items: ObservableArrayList<EduItem>?) {
        val eduItems = items ?: ObservableArrayList()
        recyclerView.apply {
            visibility = if (eduItems.isEmpty()) View.GONE else View.VISIBLE
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = Helpers.dp2px(4)
                        outRect.bottom = Helpers.dp2px(4)
                    }
                })
            }
            adapter = EduItemAdapter(eduItems)
        }
    }

    @BindingAdapter("edit_spec_exp_items")
    @JvmStatic
    fun bindExpItems(recyclerView: RecyclerView, items: ObservableArrayList<ExpItem>?) {
        val expItems = items ?: ObservableArrayList()
        recyclerView.apply {
            visibility = if (expItems.isEmpty()) View.GONE else View.VISIBLE
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = Helpers.dp2px(4)
                        outRect.bottom = Helpers.dp2px(4)
                    }
                })
            }
            adapter = ExpItemAdapter(expItems)
        }
    }

    @BindingAdapter("edit_spec_cert_items")
    @JvmStatic
    fun bindCertItems(recyclerView: RecyclerView, items: ObservableArrayList<CertItem>?) {
        val certItems = items ?: ObservableArrayList()
        recyclerView.apply {
            visibility = if (certItems.isEmpty()) View.GONE else View.VISIBLE
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = Helpers.dp2px(4)
                        outRect.bottom = Helpers.dp2px(4)
                    }
                })
            }
            adapter = CertItemAdapter(certItems)
        }
    }

    @BindingAdapter("edit_spec_fragment")
    @JvmStatic
    fun bindCheckbox(checkBox: CheckBox, fragment: EditSpecFragment) {
        checkBox.apply { fragment.apply {
            setOnCheckedChangeListener { _, isChecked ->
                Log.d("BA", "---> CHECKBOX: $isChecked")
            }
        } }
    }

    @BindingAdapter("edit_spec_fragment")
    @JvmStatic
    fun bindCommentEditText(editText: TextInputEditText, fragment: EditSpecFragment) {
        editText.apply { fragment.apply {
            addTextChangedListener { s ->
                val t = s.toString().trim()
                if (t.isEmpty()) {
                    mViewModel.mComments = null
                    return@addTextChangedListener
                }
                mViewModel.mComments = t
            }
        } }
    }

}