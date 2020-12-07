package com.yszln.lib.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.yszln.lib.ibase.IBaseVM
import com.yszln.lib.ibase.ILoadMore
import com.yszln.lib.loading.EmptyView

class LoadHelper(
    iLoadMore: ILoadMore,
    mViewModel: BaseViewModel,
    owner: LifecycleOwner,
    iBaseView: IBaseVM,
    mEmptyView: EmptyView?
) {

    init {
        iLoadMore.getLoadModule()?.setOnLoadMoreListener(iLoadMore)
        mViewModel.mRefreshStatus.observe(owner, Observer{
            when (it) {
                RefreshStatus.LOAD_END -> {
                    //加载完毕
                    iLoadMore.getLoadModule()?.loadMoreEnd()

                    mEmptyView?.setSuccess(iBaseView.isEmptyData())
                }
                RefreshStatus.LOAD -> {
                    //开始加载
                }
                RefreshStatus.LOAD_COMPLETE -> {
                    //加载完成
                    iLoadMore.getLoadModule()?.loadMoreComplete()

                    mEmptyView?.setSuccess(iBaseView.isEmptyData())
                }
                RefreshStatus.LOAD_FAIL -> {
                    //加载失败
                    iLoadMore.getLoadModule()?.loadMoreFail()

                    mEmptyView?.setError(iBaseView.isEmptyData())
                }
            }
        })
    }
}