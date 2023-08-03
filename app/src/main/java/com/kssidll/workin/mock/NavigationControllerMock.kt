package com.kssidll.workin.mock

import com.kssidll.workin.*

/**
 * Please don't look, leave me some dignity
 */
class NavigationControllerMock(
    private val mockCurrentlyAt: String = NavigationDestinations.DASHBOARD_ROUTE
): INavigationController {
    override val currentlyAt: String
        get() = mockCurrentlyAt

    override fun popToDashboard() {
        error("how did you call this, this is a mock controller")
    }

    override fun navigateDashboard() {
        error("how did you call this, this is a mock controller")
    }

    override fun navigateWorkouts() {
        error("how did you call this, this is a mock controller")
    }
}