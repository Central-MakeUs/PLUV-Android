package com.cmc15th.pluv

enum class BottomTab(
    val selectedIconId: Int,
    val unselectedIconId: Int,
    val iconTextId: Int,
    val route: String
) {
    HOME(
        selectedIconId = R.drawable.home_selected,
        unselectedIconId = R.drawable.home_unselected,
        iconTextId = R.string.home,
        route = "HomeTab"
    ),
    FEED(
        selectedIconId = R.drawable.feed_selected,
        unselectedIconId = R.drawable.feed_unselected,
        iconTextId = R.string.feed,
        route = "FeedTab"
    ),
    MY_PAGE(
        selectedIconId = R.drawable.mypage_selected,
        unselectedIconId = R.drawable.mypage_unselected,
        iconTextId = R.string.mypage,
        route = "MypageTab"
    )
}