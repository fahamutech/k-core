package com.fahamutech.kcore.admin.fragment

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.fahamutech.kcore.R
import com.fahamutech.kcore.admin.database.connector.HomeDataSource
import com.fahamutech.kcore.admin.database.noSql.HomeNoSqlDatabase
import com.google.firebase.firestore.ListenerRegistration

class HomeArticlesFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var listenerRegistration: ListenerRegistration? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inflate = inflater.inflate(R.layout.fragment_home, container, false)
        return initView(inflate, HomeNoSqlDatabase(inflate.context))
    }

    private fun initView(view: View, homeDataSource: HomeDataSource): View {
        recyclerView = view.findViewById(R.id.home_cat_recy)
        swipeRefreshLayout = view.findViewById(R.id.home_cat_swipe)
        listenerRegistration =
            homeDataSource.getCategory(recyclerView, swipeRefreshLayout) as ListenerRegistration?
        swipeRefreshLayout?.setOnRefreshListener {
            listenerRegistration = homeDataSource.getCategory(
                recyclerView,
                swipeRefreshLayout
            ) as ListenerRegistration?
        }
        return view
    }

    override fun onDestroy() {
        if (listenerRegistration != null) {
            listenerRegistration!!.remove()
        }
        super.onDestroy()
    }
}