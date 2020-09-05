package com.yszln.lib.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yszln.lib.R
import com.yszln.lib.loading.LoadingDialog
import com.yszln.lib.viewmodel.RefreshViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author: yszln
 * @date: 2020/8/7 21:13
 * @description: viewModel activity
 * @history:
 */
abstract class BaseVMActivity<VM : RefreshViewModel> : BaseActivity(),
    SwipeRefreshLayout.OnRefreshListener, IBaseView {

    protected lateinit var mViewModel: VM

    private lateinit var loadingDialog: LoadingDialog

    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initViewModel()
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(this)
        initRefresh()
        observe()
        initClick()
       onRefresh()
    }

    /**
     * 设置刷新头
     */
    private fun initRefresh() {
        swipeRefreshLayout = findViewById(R.id.mRefreshLayout)
        swipeRefreshLayout?.run {
            setOnRefreshListener(this@BaseVMActivity)
        }

        mViewModel.apply {
            mRefreshStatus.observe(this@BaseVMActivity, Observer {
                refreshEnd()
            })
            mLoadingStatus.observe(this@BaseVMActivity, Observer {
                if (it) showLoading() else dismissLoading()

            })
        }


    }

    /**
     * 刷新事件
     */
    override fun onRefresh() {
        swipeRefreshLayout?.isRefreshing = true
        refreshData()
    }

    /**
     * 刷新完毕
     */
    protected fun refreshEnd() {
        swipeRefreshLayout?.isRefreshing = false
    }


    /**
     * 请求数据
     */
    abstract fun refreshData()

    override fun showLoading() {
        loadingDialog.show()
    }

    override fun dismissLoading() {
        loadingDialog.dismiss()
    }


    /**
     * 点击事件
     */
    fun initClick() {}

    /**
     * 设置观察者
     */
    abstract fun observe()

    private fun initViewModel() {
        //取第一个泛型的class
        val vmClazz =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        //创建viewModel
        mViewModel = ViewModelProvider(this).get(vmClazz)

    }
}