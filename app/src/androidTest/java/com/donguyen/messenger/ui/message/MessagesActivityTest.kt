package com.donguyen.messenger.ui.message

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.rule.ActivityTestRule
import com.donguyen.messenger.R
import com.donguyen.messenger.base.BaseActivity
import com.donguyen.messenger.ui.BaseActivityTest
import com.donguyen.messenger.util.onView
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

/**
 * Created by DoNguyen on 29/10/18.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MessagesActivityTest : BaseActivityTest() {

    @get:Rule
    val activityTestRule = ActivityTestRule(MessagesActivity::class.java)

    override fun activityTestRule(): ActivityTestRule<out BaseActivity> {
        return activityTestRule
    }

    private fun onRecyclerView(): ViewInteraction {
        return onView(R.id.recycler_view)
    }

    private fun onDeleteMenuItem(): ViewInteraction {
        return onView(R.id.delete_menu_item)
    }

    @Test
    fun deleteTheFirstMessage() {
        // check that delete menu item does not appear on the screen
        onDeleteMenuItem().check(doesNotExist())

        // WHEN long click on the first item
        onRecyclerView().perform(
                RecyclerViewActions.actionOnItemAtPosition<MessageViewHolder>(
                        0,
                        longClick())
        )

        // THEN the delete menu item appear
        onDeleteMenuItem().check(matches(isDisplayed()))

        val firstItemBefore = getItemIdAt(0)

        // WHEN click on the delete menu item
        onDeleteMenuItem().perform(click())

        val firstItemAfter = getItemIdAt(0)

        // THEN the first item has been deleted
        check(firstItemBefore != firstItemAfter)
    }

    private fun getItemIdAt(position: Int): Long {
        var itemId: Long = -1
        onRecyclerView().check { view, _ ->
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter as MessagesAdapter
            itemId = adapter.getItemId(position)
        }

        return itemId
    }

    // TODO - write tests for deleting an attachment
}