package com.bupt.androidallstar.data

import cn.bmob.v3.BmobObject

/**
 * @author zhangyongkang
 * @date 2021/04/10
 * @email zyk970512@163.com
 */
class AndroidOutdate : BmobObject() {
    var name: String? = null
    var starNum: String? = null
    var description: String? = null
    var githubUrl: String? = null
    var kindFirst: String? = null
    var kindSecond: String? = null
    var reason: String? = null
    var replaceLibrary: String? = null
    var updateTime: String? = null
}
