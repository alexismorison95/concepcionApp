package com.morris.concepcionapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar

import com.morris.concepcionapp.R


class RemiseriasFragment : Fragment() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_remiserias, container, false)

        setViews(view)

        setListenners()

        return view
    }

    private fun setViews(view: View) {

        // Toolbar
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = "Remiserias"
    }

    private fun setListenners() {

        // Toolbar
        toolbar.setNavigationOnClickListener { activity?.finish() }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RemiseriasFragment()
    }
}
