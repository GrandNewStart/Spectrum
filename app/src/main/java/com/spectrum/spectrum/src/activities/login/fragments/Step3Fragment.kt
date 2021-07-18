package com.spectrum.spectrum.src.activities.login.fragments

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.dialogs.JobGroupDialog
import com.spectrum.spectrum.src.adapters.CertItemAdapter
import com.spectrum.spectrum.src.adapters.EduItemAdapter
import com.spectrum.spectrum.src.adapters.ExpItemAdapter
import com.spectrum.spectrum.src.models.CertItem
import com.spectrum.spectrum.src.models.EduItem
import com.spectrum.spectrum.src.models.ExpItem
import com.spectrum.spectrum.src.models.JobGroup
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.customs.CustomTextInputLayout
import com.spectrum.spectrum.src.dialogs.*

class Step3Fragment: BaseFragment() {

    private var mAge: Int? = null
    private var mSex: String? = null
    private var mJobGroup1: JobGroup? = null
    private var mJobGroup2: JobGroup? = null
    private var mJobGroup3: JobGroup? = null
    private var mEduItems = ObservableArrayList<EduItem>()
    private var mExpItems = ObservableArrayList<ExpItem>()
    private var mCertItems = ObservableArrayList<CertItem>()
    private var mComments: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in_step3, container, false)
        view.apply {
            // 건너뛰기
            findViewById<MaterialTextView>(R.id.skip).setOnClickListener {
                activity?.startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
            // 연령 설정
            findViewById<MaterialCardView>(R.id.age_card).apply {
                val textView = findViewById<MaterialTextView>(R.id.age_text)
               setOnClickListener {
                   AgePickerDialog(context).apply {
                       setOnAgePickedListener { age ->
                           mAge = age
                           textView.apply {
                               text = age.toString()
                               setTextColor(resources.getColor(R.color.spectrumGray3, null))
                           }
                       }
                       show()
                   }
               }
            }
            // 성별 설정
            findViewById<RadioGroup>(R.id.sex_radio_group).apply {
                setOnCheckedChangeListener { _, checkedId ->
                    if (checkedId == R.id.male) mSex = "M"
                    if (checkedId == R.id.female) mSex = "F"
                }
            }
            // 직군 설정
            val scrollView = findViewById<HorizontalScrollView>(R.id.group_chip_scroll_view)
            val chip1 = findViewById<Chip>(R.id.chip_1)
            val chip2 = findViewById<Chip>(R.id.chip_2)
            val chip3 = findViewById<Chip>(R.id.chip_3)
            findViewById<MaterialCardView>(R.id.group_card).setOnClickListener {
                JobGroupDialog(context)
                    .setOnSaveListener { first, second, third ->
                        mJobGroup1 = first
                        mJobGroup2 = second
                        mJobGroup3 = third
                        scrollView.visibility = View.GONE
                        chip1.visibility = View.GONE
                        chip2.visibility = View.GONE
                        chip3.visibility = View.GONE
                        mJobGroup1?.let {
                            scrollView.visibility = View.VISIBLE
                            chip1.visibility = View.VISIBLE
                            chip1.text = it.name
                        }
                        mJobGroup2?.let {
                            chip2.visibility = View.VISIBLE
                            chip2.text = it.name
                        }
                        mJobGroup3?.let {
                            chip3.visibility = View.VISIBLE
                            chip3.text = it.name
                        }
                    }
                    .setPreSelectedItems(mJobGroup1, mJobGroup2, mJobGroup3)
                    .show()
            }
            chip1.setOnCloseIconClickListener {
                mJobGroup1 = mJobGroup2
                mJobGroup2 = mJobGroup3
                mJobGroup3 = null
                mJobGroup1?.let { chip1.text = it.name }
                mJobGroup2?.let { chip2.text = it.name }
                scrollView.visibility = if (mJobGroup1 == null) View.GONE else View.VISIBLE
                chip1.visibility = if (mJobGroup1 == null) View.GONE else View.VISIBLE
                chip2.visibility = if (mJobGroup2 == null) View.GONE else View.VISIBLE
                chip3.visibility = View.GONE
            }
            chip2.setOnCloseIconClickListener {
                mJobGroup2 = mJobGroup3
                mJobGroup3 = null
                mJobGroup2?.let { chip2.text = it.name }
                chip2.visibility = if (mJobGroup2 == null) View.GONE else View.VISIBLE
                chip3.visibility = View.GONE
            }
            chip3.setOnCloseIconClickListener {
                mJobGroup3 = null
                chip3.visibility = View.GONE
            }
            // 학력 설정
            findViewById<ImageButton>(R.id.academic_plus).setOnClickListener {
                EducationDialog(context)
                    .setOnSaveListener { item ->
                        mEduItems.add(item)
                        findViewById<RecyclerView>(R.id.academic_recycler_view).apply {
                            visibility = if (mEduItems.isEmpty()) View.GONE else View.VISIBLE
                            adapter?.notifyDataSetChanged()
                        }
                    }.show()
            }
            findViewById<RecyclerView>(R.id.academic_recycler_view).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = EduItemAdapter(mEduItems)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = dp2px(4)
                        outRect.bottom = dp2px(4)
                    }
                })
            }
            // 경력 설정
            findViewById<ImageButton>(R.id.experiences_plus).setOnClickListener {
                ExperienceDialog(context)
                    .setOnSaveListener { item ->
                        mExpItems.add(item)
                        findViewById<RecyclerView>(R.id.experience_recycler_view).apply {
                            visibility = if (mExpItems.isEmpty()) View.GONE else View.VISIBLE
                            adapter?.notifyDataSetChanged()
                        }
                    }.show()
            }
            findViewById<RecyclerView>(R.id.experience_recycler_view).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = ExpItemAdapter(mExpItems)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = dp2px(4)
                        outRect.bottom = dp2px(4)
                    }
                })
            }
            // 자격/어학 설정
            findViewById<ImageButton>(R.id.certification_plus).setOnClickListener {
                CertificationDialog(context)
                    .setOnSaveListener { item ->
                        mCertItems.add(item)
                        findViewById<RecyclerView>(R.id.certification_recycler_view).apply {
                            visibility = if (mCertItems.isEmpty()) View.GONE else View.VISIBLE
                            adapter?.notifyDataSetChanged()
                        }
                    }.show()
            }
            findViewById<RecyclerView>(R.id.certification_recycler_view).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = CertItemAdapter(mCertItems)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = dp2px(4)
                        outRect.bottom = dp2px(4)
                    }
                })
            }
            // 기타 스펙 설정
            findViewById<CustomTextInputLayout>(R.id.others_text_input_layout).apply {
                addOnEditTextAttachedListener { layout ->
                    layout.editText?.apply {
                        addTextChangedListener {
                            val input = it.toString().trim()
                            mComments = if (input.isEmpty()) null else input
                        }
                    }
                }
            }
            // 저장
            findViewById<MaterialButton>(R.id.save).setOnClickListener {
                Log.d(TAG, "---> AGE: $mAge")
                Log.d(TAG, "---> SEX: $mSex")
                Log.d(TAG, "---> GROUP1: ${mJobGroup1?.name}")
                Log.d(TAG, "---> GROUP2: ${mJobGroup2?.name}")
                Log.d(TAG, "---> GROUP3: ${mJobGroup3?.name}")
                Log.d(TAG, "---> EDUCATIONS(${mEduItems.size})")
                for (item in mEduItems) { Log.d(TAG, "      ㄴ: ${item.school}") }
                Log.d(TAG, "---> EXPERIENCES(${mExpItems.size})")
                for (item in mExpItems) { Log.d(TAG, "      ㄴ: ${item.company}") }
                Log.d(TAG, "---> CERTIFICATIONS(${mCertItems.size})")
                for (item in mCertItems) { Log.d(TAG, "      ㄴ: ${item.name}") }
                Log.d(TAG, "---> COMMENTS: $mComments")
            }
        }
        return view
    }
}