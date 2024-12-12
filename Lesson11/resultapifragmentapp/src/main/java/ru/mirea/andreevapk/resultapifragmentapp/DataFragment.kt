package ru.mirea.andreevapk.resultapifragmentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class DataFragment : Fragment() {
    private var editTextInfo: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_data, container, false)

        editTextInfo = view.findViewById(R.id.editTextInfo)
        val buttonOpenBottomSheet = view.findViewById<Button>(R.id.buttonOpenBottomSheet)

        buttonOpenBottomSheet.setOnClickListener { v: View? ->
            val info = editTextInfo!!.getText().toString()

            val bundle = Bundle()
            bundle.putString("info", info)

            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.setArguments(bundle)
            bottomSheetFragment.show(parentFragmentManager, "BottomSheetFragment")
        }

        return view
    }
}
