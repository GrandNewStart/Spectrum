package com.spectrum.spectrum.src.activities.main.fragments.createPost

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentCreatePostBinding
import com.spectrum.spectrum.src.models.Info
import com.spectrum.spectrum.src.models.Spec

class CreatePostViewModel: ViewModel() {

    var mMySpec: Spec? = null

    private var mTypes = ArrayList<String>()
    private var mSubTypes = ArrayList<String>()

    fun backButtonAction(fragment: CreatePostFragment) {
        fragment.findNavController().popBackStack()
    }

    fun doneButtonAction(fragment: CreatePostFragment) {

    }

    fun bindViews(binding: FragmentCreatePostBinding) {
        binding.apply {
            viewModel = this@CreatePostViewModel

            // TEST CODE START
             mMySpec = Spec("DUMMY_ID", "이예영", "06/28 20:25 업데이트됨",
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
            mTypes = arrayListOf("종류", "취업준비", "n차합격", "최종합격")
            mSubTypes = arrayListOf("주제", "자유고민", "자유후기")
            spec = mMySpec
            // TEST CODE END
        }
    }

    fun proceedToEditSpec(fragment: CreatePostFragment) {
        fragment.findNavController().navigate(R.id.create_post_to_edit_fragment)
    }

}