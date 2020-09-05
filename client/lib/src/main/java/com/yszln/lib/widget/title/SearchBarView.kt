package com.yszln.lib.widget.title

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.yszln.lib.R
import com.yszln.lib.utils.StatusBarUtil
import com.yszln.lib.widget.input.SearchEditText
import kotlinx.android.synthetic.main.view_title_search.view.*

class SearchBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun setHint(title: String?) {
        titleSearch.hint = title
    }

    init {
        View.inflate(context, R.layout.view_title_search, this)

        titleBlack.setOnClickListener {
            if (context is Activity) {
                context.onBackPressed()
            }
        }

        if (context is AppCompatActivity) {
            StatusBarUtil.immersive(context)
            StatusBarUtil.setPaddingSmart(context, this)
            StatusBarUtil.darkMode(context)
        }

    }

    public fun setSearchListener(listener: SearchEditText.OnSearchListener) {
        titleSearch.onSearchListener = listener
    }
}