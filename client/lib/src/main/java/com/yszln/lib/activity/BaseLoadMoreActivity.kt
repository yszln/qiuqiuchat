package com.yszln.lib.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.yszln.lib.adapter.LoadMore
import com.yszln.lib.viewmodel.LoadMoreViewModel

/**
 * @author: yszln
 * @date: 2020/8/7 21:13
 * @description: 可以加载更多的activity，基于BaseRecyclerViewAdapterHelper适配器
 * @history:
 */
abstract class BaseLoadMoreActivity<VM : LoadMoreViewModel> : BaseVMActivity<VM>(),
    OnLoadMoreListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLoadMore()
    }

    override fun onRefresh() {
        super.onRefresh()
        loadMore().clearData()
    }

    private fun initLoadMore() {
        loadMore().getLoadModule().setOnLoadMoreListener {
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
    abstract fun loadMore(): LoadMore


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