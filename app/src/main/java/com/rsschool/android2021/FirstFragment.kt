package com.rsschool.android2021

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.android2021.SecondFragment.Companion.newInstance
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            val min = min_value.text.toString()
            val max = max_value.text.toString()

            var errorMessage = ""
            if (min == "" || max == "")
                errorMessage = "Error! The required values are not entered."
            else if (max.toLong() > Int.MAX_VALUE || min.toLong() > Int.MAX_VALUE)
                errorMessage = "Error! The minimum or maximum value is too large."
            else if (max.toInt() < min.toInt())
                errorMessage = "Error! The maximum number is greater than the minimum."

            if (errorMessage.isEmpty()) {
                activity?.let {
                    (it as MainActivity).openSecondFragment(min.toInt(), max.toInt())
                }
            } else {
                val toast = Toast.makeText(
                    activity,
                    errorMessage,
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}