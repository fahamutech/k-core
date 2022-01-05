package com.fahamutech.kcore.user.fragment

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.fahamutech.kcore.R
import com.fahamutech.kcore.user.database.connector.HomeDataSource
import com.fahamutech.kcore.user.database.noSql.HomeNoSqlDatabase

class HomeArticlesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_home_user_user, container, false)
        return initView(inflate, HomeNoSqlDatabase(inflate.context))
    }

    private fun initView(view: View, homeDataSource: HomeDataSource): View {
        val recyclerView: RecyclerView? = view.findViewById(R.id.home_cat_recy)
        val swipeRefreshLayout: SwipeRefreshLayout? = view.findViewById(R.id.home_cat_swipe)
        homeDataSource.getCategory(recyclerView, swipeRefreshLayout)
        swipeRefreshLayout?.setOnRefreshListener {
            homeDataSource.getCategory(
                recyclerView,
                swipeRefreshLayout
            )
        }
        return view
    }
}