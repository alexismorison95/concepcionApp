package com.morris.concepcionapp.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar

import com.morris.concepcionapp.R


class EmergenciasFragment : Fragment() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_emergencias, container, false)

        setViews(view)

        setListenners()

        return view
    }

    private fun setViews(view: View) {

        // Toolbar
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = "Emergencias"
    }

    private fun setListenners() {

        // Toolbar
        toolbar.setNavigationOnClickListener { activity?.finish() }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            EmergenciasFragment()
    }
}