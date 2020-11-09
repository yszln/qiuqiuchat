package com.yszln.lib.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yszln.lib.R
import com.yszln.lib.ibase.IBaseVM
import com.yszln.lib.ibase.ILoadMore
import com.yszln.lib.loading.EmptyView
import com.yszln.lib.loading.LoadingDialog
import com.yszln.lib.viewmodel.BaseViewModel
import com.yszln.lib.viewmodel.LoadHelper
import com.yszln.lib.viewmodel.RefreshHelper
import java.lang.reflect.ParameterizedType

/**
 * @author: yszln
 * @date: 2020/8/7 21:13
 * @description: viewModel activity
 * @history:
 */
abstract class BaseActivity<VM : BaseViewModel> : SuperActivity(),
    SwipeRefreshLayout.OnRefreshListener, IBaseVM, ILoadMore {

    protected lateinit var mViewModel: VM

    protected lateinit var loadingDialog: LoadingDialog

    protected var swipeRefreshLayout: SwipeRefreshLayout? = null

    protected var mEmptyView: EmptyView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initRefresh()
        observer()
        refreshData()
    }

    /**
     * 设置刷新头
     */
    private fun initRefresh() {
        loadingDialog = LoadingDialog(this)
        swipeRefreshLayout = findViewById(R.id.mRefreshLayout)
        mEmptyView = findViewById(R.id.mEmptyView)
        RefreshHelper(
            swipeRefreshLayout,
            this,
            mViewModel,
            this,
            mEmptyView
        )
        LoadHelper(
            this,
            mViewModel,
            this,
            this,
            mEmptyView
        )

    }

    /**
     * 刷新头的监听
     */
    override fun onRefresh() {
        refreshData()
    }


    override fun showLoading() {
        loadingDialog.show()
    }

    override fun dismissLoading() {
        loadingDialog.dismiss()
    }


    private fun initViewModel() {
        //取第一个泛型的class
        val vmClazz =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        //创建viewModel
        mViewModel = ViewModelProvider(this).get(vmClazz)

    }
}