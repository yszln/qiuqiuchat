package com.yszln.lib.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.yszln.lib.adapter.LoadMore
import com.yszln.lib.viewmodel.LoadMoreViewModel

/**
 * @author: yszln
 * @date: 2020/8/9 22:01
 * @description: 可以加载更多的fragment，基于BaseRecyclerViewAdapterHelper适配器
 * @history:
 */
abstract class BaseLoadMoreFragment<VM : LoadMoreViewModel> : BaseVMFragment<VM>(),
    OnLoadMoreListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoadMore()

    }

    override fun onRefresh() {
        super.onRefresh()
        loadMore()?.clearData()
    }

    private fun initLoadMore() {
        loadMore()?.getLoadModule()?.setOnLoadMoreListener {
            loadMoreData()
        }
        mViewModel.mLoadMoreStatus.observe(this, Observer {
            loadMore()?.run {
                if((getItemCount()%20==0)){
                    //能被20整除，有下一页
                    getLoadModule()?.loadMoreComplete()
                }else{
                    getLoadModule()?.loadMoreEnd()
                }

            }

        })
    }

    /**
     * 加载更多的适配器
     */
    abstract fun loadMore(): LoadMore?


    /**
     * 加载更多监听
     */
    override fun onLoadMore() {
        loadMoreData()
    }


    /**
     * 加载更多
     */
    abstract fun loadMoreData()
}