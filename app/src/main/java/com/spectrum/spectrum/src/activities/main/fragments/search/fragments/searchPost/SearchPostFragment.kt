package com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchPost

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.search.SearchFragment
import com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchPost.adapters.PostAdapter
import com.spectrum.spectrum.src.activities.main.fragments.search.models.Post
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.customs.BaseFragment
import kotlinx.android.synthetic.main.activity_log_in.view.*

class SearchPostFragment: BaseFragment() {

    var mPosts = ArrayList<Post>()
    var mRecyclerView: RecyclerView? = null
    var mNoItemsText: TextView? = null
    private var mAdapter = PostAdapter(mPosts)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search_post, container, false)
        view.apply {
            findViewById<SwitchCompat>(R.id.filter_switch).apply {

            }
            mNoItemsText = findViewById(R.id.no_items_text)
            findViewById<RecyclerView>(R.id.post_recycler_view).apply {
                mRecyclerView = this
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
                if (itemDecorationCount == 0) {
                    addItemDecoration(object : RecyclerView.ItemDecoration(){
                        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                            super.getItemOffsets(outRect, view, parent, state)
                            outRect.top = Helpers.dp2px(12)
                            outRect.bottom = Helpers.dp2px(12)
                            outRect.left = Helpers.dp2px(16)
                            outRect.right = Helpers.dp2px(16)
                        }
                    })
                }
                setOnScrollChangeListener { _, _, _, _, _ ->
                    if (!canScrollVertically(1)) {
                        (parentFragment as SearchFragment).apply {
                            mViewModel.searchPost(this)
                        }
                    }
                }
                adapter = mAdapter
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        mRecyclerView?.visibility = if (mPosts.isEmpty()) View.GONE else View.VISIBLE
        mNoItemsText?.visibility = if (mPosts.isEmpty()) View.VISIBLE else View.GONE
    }

    fun addItems(newItems: ArrayList<Post>) {
        mAdapter.addItems(newItems)
    }

    fun clearItems() {
        mAdapter.notifyItemRangeRemoved(0, mPosts.size)
        mPosts.clear()
    }

}

