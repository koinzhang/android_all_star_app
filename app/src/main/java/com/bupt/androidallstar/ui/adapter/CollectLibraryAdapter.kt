package com.bupt.androidallstar.ui.adapter

import com.bupt.androidallstar.R
import com.bupt.androidallstar.database.entity.AndroidLibraryEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author zhangyongkang
 * @date 2021/04/10
 * @email zyk970512@163.com
 */
class CollectLibraryAdapter(list: MutableList<AndroidLibraryEntity>) :
    BaseQuickAdapter<AndroidLibraryEntity, BaseViewHolder>(
        R.layout.item_library_recommend, list
    ) {
    override fun convert(holder: BaseViewHolder, item: AndroidLibraryEntity) {
        holder.setText(R.id.txt_name, item.name)
            .setText(R.id.txt_star_num, item.starNum)
            .setText(R.id.txt_description, item.description)
    }
}