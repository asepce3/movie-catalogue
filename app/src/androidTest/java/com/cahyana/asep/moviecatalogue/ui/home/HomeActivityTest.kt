package com.cahyana.asep.moviecatalogue.ui.home

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import androidx.test.espresso.contrib.RecyclerViewActions
import com.cahyana.asep.moviecatalogue.R
import com.cahyana.asep.moviecatalogue.utils.EspressoIdlingResource
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before

class HomeActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.tab_movie)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

    @Test
    fun loadDetailMovies() {
        onView(withId(R.id.tab_movie)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_movie_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_title)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.tab_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withId(R.id.tab_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_tvshow_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tvshow_desc)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavorite() {
        onView(withId(R.id.tab_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.tab_favorite)).perform(click())
        onView(withId(R.id.rv_favorite_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun loadDetailFavorite() {
        onView(withId(R.id.tab_movie)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(3, ClickOnBtn(R.id.btn_favorite)))

        onView(withId(R.id.tab_favorite)).perform(click())
        onView(withId(R.id.rv_favorite_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_movie))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_desc)).check(matches(not(withText(""))))
        onView(withId(R.id.btn_favorite)).perform(click())
    }

    @Test
    fun addMovieToFavorite() {
        onView(withId(R.id.tab_movie)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ClickOnBtn(R.id.btn_favorite)))

        onView(withId(R.id.tab_favorite)).perform(click())
        onView(withId(R.id.rv_favorite_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_movie))
            .perform(RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(5))

        onView(withId(R.id.rv_favorite_movie)).perform(RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ClickOnBtn(R.id.btn_favorite)))
    }

    @Test
    fun addTvShowToFavorite() {
        onView(withId(R.id.tab_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ClickOnBtn(R.id.btn_favorite)))

        onView(withId(R.id.tab_favorite)).perform(click())
        onView(withText(R.string.fav_tv_show)).perform(click())
        onView(withId(R.id.rv_favorite_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_tvshow))
            .perform(RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(5))

        onView(withId(R.id.rv_favorite_tvshow)).perform(RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ClickOnBtn(R.id.btn_favorite)))
    }

    @Test
    fun addDetailMovieToFavorite() {
        onView(withId(R.id.tab_movie)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_title)).check(matches(not(withText(""))))
        onView(withId(R.id.btn_favorite)).perform(click())
        pressBack()

        onView(withId(R.id.tab_favorite)).perform(click())
        onView(withText(R.string.fav_movies)).perform(click())
        onView(withId(R.id.rv_favorite_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite_movie))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_title)).check(matches(not(withText(""))))

        onView(withId(R.id.btn_favorite)).perform(click())
    }

    @Test
    fun addDetailTvShowToFavorite() {
        onView(withId(R.id.tab_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_tvshow_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tvshow_title)).check(matches(not(withText(""))))
        onView(withId(R.id.tv_tvshow_desc)).check(matches(not(withText(""))))
        onView(withId(R.id.btn_favorite)).perform(click())
        pressBack()

        onView(withId(R.id.tab_favorite)).perform(click())
        onView(withText(R.string.fav_tv_show)).perform(click())
        onView(withId(R.id.rv_favorite_tvshow)).check(matches(isDisplayed()))
        onView(withText("TVSHOW")).check(matches(isDisplayed()))

        onView(withId(R.id.rv_favorite_tvshow)).perform(RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ClickOnBtn(R.id.btn_favorite)))
    }

    @Test
    fun deleteFromFavoriteMovie() {
        onView(withId(R.id.tab_movie)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(1, ClickOnBtn(R.id.btn_favorite)))

        onView(withId(R.id.tab_favorite)).perform(click())
        onView(withText(R.string.fav_movies)).perform(click())
        onView(withId(R.id.rv_favorite_movie)).check(matches(isDisplayed()))
        onView(allOf(withText("MOVIE"))).check(matches(isDisplayed()))

        onView(withId(R.id.rv_favorite_movie)).perform(RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ClickOnBtn(R.id.btn_favorite)))

        onView(withId(R.id.rv_favorite_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun deleteFromFavoriteTvShow() {
        onView(withId(R.id.tab_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(1, ClickOnBtn(R.id.btn_favorite)))

        onView(withId(R.id.tab_favorite)).perform(click())
        onView(withText(R.string.fav_tv_show)).perform(click())
        onView(withId(R.id.rv_favorite_tvshow)).check(matches(isDisplayed()))
        onView(allOf(withText("TVSHOW"))).check(matches(isDisplayed()))

        onView(withId(R.id.rv_favorite_tvshow)).perform(RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ClickOnBtn(R.id.btn_favorite)))

        onView(withId(R.id.rv_favorite_tvshow)).check(matches(isDisplayed()))
    }

    @Test
    fun searchMovie() {
        onView(withId(R.id.tab_movie)).perform(click())
        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("naruto"), pressImeActionButton())
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(3))

        onView(withId(R.id.option_refresh)).perform(click())
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(3))
    }

    @Test
    fun searchTvShow() {
        onView(withId(R.id.tab_tvshow)).perform(click())
        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("dragon"), pressImeActionButton())
        onView(withId(R.id.rv_tvshow))
            .perform(RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(3))

        onView(withId(R.id.option_refresh)).perform(click())
        onView(withId(R.id.rv_tvshow))
            .perform(RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(3))
    }

    @Test
    fun searchFavoriteMovie() {
        val message = "No Item like naruto found"
        onView(withId(R.id.tab_favorite)).perform(click())
        onView(withText(R.string.fav_movies)).perform(click())
        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("naruto"), pressImeActionButton())
        Thread.sleep(1000)
        onView(withText(message)).check(matches(isDisplayed()))
    }

    @Test
    fun searchFavoriteTvShow() {
        val message = "No Item like naruto found"
        onView(withId(R.id.tab_favorite)).perform(click())
        onView(withText(R.string.fav_tv_show)).perform(click())
        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("naruto"), pressImeActionButton())
        Thread.sleep(1000)
        onView(withText(message)).check(matches(isDisplayed()))
    }

    inner class ClickOnBtn(private val id: Int) : ViewAction {
        internal val click = click()

        override fun getConstraints(): Matcher<View> = click.constraints

        override fun getDescription() = "Click on button with id: $id"

        override fun perform(uiController: UiController?, view: View?) {
            click.perform(uiController, view?.findViewById(id))
        }
    }
}
