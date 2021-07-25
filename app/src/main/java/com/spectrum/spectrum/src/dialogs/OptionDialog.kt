package com.spectrum.spectrum.src.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.adapters.OptionAdapter
import com.spectrum.spectrum.src.adapters.OptionClickListener
import com.spectrum.spectrum.src.models.Option

class OptionDialog(private val fm: FragmentManager): BottomSheetDialogFragment(), OptionClickListener {

    private var mOptions = ArrayList<Option>()
    private var mOnItemClicked: (position: Int)->Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_option, container, false)
        view.apply {
            findViewById<RecyclerView>(R.id.options_recycler_view).apply {
                adapter = OptionAdapter(mOptions, this@OptionDialog)
            }
        }
        return view
    }

    fun setOptions(options: ArrayList<Option>): OptionDialog {
        mOptions = options
        return this
    }

    fun setOnItemClickListener(onItemClicked: (position:Int)->Unit): OptionDialog {
        mOnItemClicked = onItemClicked
        return this
    }

    fun showDialog() {
        show(fm, null)
    }

    override fun onItemClicked(position: Int) {
        mOnItemClicked(position)
        dismiss()
    }

}