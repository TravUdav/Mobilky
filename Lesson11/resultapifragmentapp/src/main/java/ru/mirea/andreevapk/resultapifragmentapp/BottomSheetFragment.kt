package ru.mirea.andreevapk.resultapifragmentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        val textViewResult = view.findViewById<TextView>(R.id.textViewResult)

        val arguments = arguments
        if (arguments != null) {
            val info = arguments.getString("info")
            textViewResult.text = info

            val resultBundle = Bundle()
            resultBundle.putString("key", info)

            parentFragmentManager.setFragmentResult("requestKey", resultBundle)
        }

        return view
    }
}
