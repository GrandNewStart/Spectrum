package com.spectrum.spectrum.src.activities.main.fragments.createPost

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.databinding.FragmentCreatePostBinding
import com.spectrum.spectrum.src.models.Info
import com.spectrum.spectrum.src.models.Spec

class CreatePostViewModel: ViewModel() {

    private var mSelectedSpec: Spec? = null

    fun backButtonAction(fragment: CreatePostFragment) {
        fragment.findNavController().popBackStack()
    }

    fun doneButtonAction(fragment: CreatePostFragment) {

    }

    fun bindViews(binding: FragmentCreatePostBinding) {
        binding.apply {
            viewModel = this@CreatePostViewModel

            // TEST CODE START
                mSelectedSpec = Spec("DUMMY_ID","UIUX 인턴 준비 스펙", "06/28 20:25 업데이트됨",
                    arrayListOf(
                        Info(0, "23세"),
                        Info(1, "여성"),
                        Info(2, "IT/인터넷"),
                        Info(3, "디자인")
                    ),
                    arrayListOf(),
                    arrayListOf(),
                    arrayListOf(),
                    ""
                )
            // TEST CODE END

            selectedSpec = mSelectedSpec
        }
    }

}