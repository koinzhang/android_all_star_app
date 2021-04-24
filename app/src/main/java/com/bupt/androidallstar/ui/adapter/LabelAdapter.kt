package com.bupt.androidallstar.ui.adapter

import com.bupt.androidallstar.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author zhangyongkang
 * @date 2021/04/24
 * @email zyk970512@163.com
 */
class LabelAdapter(list: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(
        R.layout.item_label, list
    ) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.txt_label, item)
    }
}