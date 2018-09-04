package com.example.vthai.sidecarprototype.adapters

import android.arch.lifecycle.Observer
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vthai.sidecarprototype.R
import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.viewmodels.DoctorViewModel
import kotlinx.android.synthetic.main.content_doctor_costs.view.*
import kotlinx.android.synthetic.main.content_doctor_overview.view.*

class DoctorPagerAdapter(var context: AppCompatActivity, var viewModel: DoctorViewModel): PagerAdapter() {

    lateinit var overviewView: View
    lateinit var costsView: View
    var costsAdapter: DoctorCostsRecyclerAdapter? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = if(position == 0) {
            val temp = LayoutInflater.from(context).inflate(R.layout.content_doctor_overview, container, false)
            overviewView = temp
            viewModel.doctor.observe(context, doctorObserver)
            temp
        } else {
            val temp = LayoutInflater.from(context).inflate(R.layout.content_doctor_costs, container, false)
            costsView = temp
            setupCostsView(temp)
            viewModel.doctor.observe(context, costsObserver)
            temp
        }
        container.addView(view)
        return view
    }

    private fun setupCostsView(view: View) {
        costsAdapter = DoctorCostsRecyclerAdapter(context)
        view.costsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.costsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        view.costsRecyclerView.adapter = costsAdapter
    }

    private val doctorObserver = Observer<Doctor>() {
        overviewView.addressSubHeaderValueTextView.text = it?.address
        overviewView.phoneSubHeaderValueTextView.text = it?.phone
        val sb = StringBuilder()
        for(specialty in it?.specialties ?: ArrayList()) {
            sb.appendln(specialty)
        }
        overviewView.specialtiesSubHeaderValueTextView.text = sb.toString()
        overviewView.pricesSubHeaderValueTextView.text = it?.prices
    }

    private val costsObserver = Observer<Doctor>() {
        costsAdapter?.costList = it?.doctorCosts ?: ArrayList()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return MAX_DOCTOR_PAGES
    }

    companion object {
        const val MAX_DOCTOR_PAGES = 2
    }
}