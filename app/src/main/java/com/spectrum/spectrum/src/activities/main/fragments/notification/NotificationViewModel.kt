package com.spectrum.spectrum.src.activities.main.fragments.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spectrum.spectrum.src.activities.main.fragments.notification.adapters.NotificationAdapter
import com.spectrum.spectrum.src.activities.main.fragments.notification.models.Notification
import kotlinx.coroutines.launch

class NotificationViewModel: ViewModel() {

    private var mNotifications = ArrayList<Notification>()
    private var mIsDataLoaded = false
    private var mIsLoading = false
    private var mTotalPages = 1
    private var mCurrentPage = 1

    fun bindViews(fragment: NotificationFragment) {
        // TEST CODE START
            if (!mIsDataLoaded) {
                mNotifications.add(Notification(null, null, "작성한 글", "스펙쌓자 님이 의견을 남기셨습니다.", true))
                mNotifications.add(Notification(null, null, "작성한 글", "스펙짱 님이 의견을 남기셨습니다.", false))
                mNotifications.add(Notification(null, null, "관심 기업", "스펙트럼 분석이 업3데이트 되었습니다.", false))
                mNotifications.add(Notification(null, null, "작성한 글", "스펙고수 님이 의견을 남기셨습니다.", false))
            }
        // TEST CODE END
        fragment.mBinding.apply {
            notificationRecyclerView.apply {
                adapter = NotificationAdapter(mNotifications)
            }
        }
    }

    private fun getNotifications() {
        if (mIsLoading) return
        if (mCurrentPage > mTotalPages) return
        viewModelScope.launch {

        }
    }

}