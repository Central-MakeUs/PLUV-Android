package com.cmc15th.pluv.navigation

import com.cmc15th.pluv.core.designsystem.R

enum class BottomTab(
    val selectedIconId: Int,
    val unselectedIconId: Int,
    val iconTextId: Int,
    val route: BottomTabRoute
) {
    HOME(
        selectedIconId = R.drawable.home_selected,
        unselectedIconId = R.drawable.home_unselected,
        iconTextId = R.string.home,
        route = BottomTabRoute.Home
    ),
    FEED(
        selectedIconId = R.drawable.feed_selected,
        unselectedIconId = R.drawable.feed_unselected,
        iconTextId = R.string.feed,
        route = BottomTabRoute.Feed
    ),
    MY_PAGE(
        selectedIconId = R.drawable.mypage_selected,
        unselectedIconId = R.drawable.mypage_unselected,
        iconTextId = R.string.mypage,
        route = BottomTabRoute.Mypage
    )
}