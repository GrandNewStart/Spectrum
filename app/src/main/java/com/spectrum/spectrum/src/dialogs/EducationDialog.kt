package com.spectrum.spectrum.src.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.DialogEducationBinding
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.models.Degree
import com.spectrum.spectrum.src.models.Education
import com.spectrum.spectrum.src.models.Graduate
import com.spectrum.spectrum.src.models.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.GET
import java.lang.Exception

class EducationDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private lateinit var mBinding: DialogEducationBinding
    private var onSaveListener: (education: Education)->Unit = {}
    var mDegrees = ArrayList<Degree>()
    var mLocations = ArrayList<Location>()
    var mGraduates = ArrayList<Graduate>()
    val mGradeSums = listOf("총점", "4.3", "4.5")
    var mDegree: Degree? = null
    var mLocation: Location? = null
    var mGraduate: Graduate? = null
    var mSchool: String? = null
    var mMajor: String? = null
    var mGrade: Double? = null
    var mGradeSum: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_education, null, false)
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
        setContentView(mBinding.root)
        getEduData(mBinding)
    }

    fun setOnSaveListener(onSaveListener: (education: Education)->Unit): EducationDialog {
        this.onSaveListener = onSaveListener
        return this
    }

    fun saveButtonAction() {
        if (mDegree?.id == 0 || mGraduate?.id == 0) {
            Toast.makeText(context, Constants.required_academic_information, Toast.LENGTH_SHORT).show()
            return
        }
        val item = Education(mLocation, mGraduate, mDegree, mSchool?.trim(), mMajor?.trim(), mGrade)
        onSaveListener(item)
        dismiss()
    }

    private fun getEduData(binding: DialogEducationBinding) {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                retrofit.create(EducationApi::class.java).getEduData().apply {
                    if (isSuccess) {
                        mLocations = result.locationList
                        mDegrees = result.degreeList
                        mGraduates = result.graduateList

                        mLocations[0].data = "지역"
                        mDegrees[0].data = "학교 구분"
                        mGraduates[0].data = "졸업 여부"

                        binding.dialog = this@EducationDialog
                        return@launch
                    }
                    Log.e(TAG, "---> EDU DATA FETCH FAILURE: $message")
                    Toast.makeText(context, Constants.request_failed, Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (e: Exception) {
            Log.e(TAG, "---> EDU DATA FETCH FAILURE: $e")
            Toast.makeText(context, Constants.request_failed, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        val TAG = EducationDialog::class.java.simpleName
    }

}

interface EducationApi {
    @GET("/app/datas/edu")
    suspend fun getEduData(): EduDataResponse
}

data class EduDataResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Result
) {
    data class Result(
        var degreeList: ArrayList<Degree>,
        var graduateList: ArrayList<Graduate>,
        var locationList: ArrayList<Location>
    )
}