package com.yszln.lib.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yszln.lib.ibase.IBaseVM
import com.yszln.lib.loading.EmptyView

class RefreshHelper(
    refreshLayout: SwipeRefreshLayout?,
    iBaseViewModel: IBaseVM,
    mViewModel: BaseViewModel,
    owner: LifecycleOwner,
    mEmptyView: EmptyView?
) {

    init {
        refreshLayout?.apply {
            setOnRefreshListener(iBaseViewModel)
            isRefreshing = true
        }
        //没有刷新控件执行刷新方法
        refreshLayout?:iBaseViewModel.onRefresh()

        mViewModel.mLoadingStatus.observe(owner, Observer{
            if (it) iBaseViewModel.showLoading() else iBaseViewModel.dismissLoading()

        })
        mViewModel.mRefreshStatus.observe(owner, Observer{
            when (it) {
                RefreshStatus.REFRESH -> {
                    //开始刷新
                    mEmptyView?.setLoading()
                }
                RefreshStatus.REFRESH_FAIL -> {
                    //刷新失败
                    refreshLayout?.isRefreshing = false
                    mEmptyView?.setError(iBaseViewModel.isEmptyData())
                }
                RefreshStatus.REFRESH_END -> {
                    //刷新成功
                    refreshLayout?.isRefreshing = false
                    mEmptyView?.setSuccess(iBaseViewModel.isEmptyData())
                }
            }
        })

    }

}