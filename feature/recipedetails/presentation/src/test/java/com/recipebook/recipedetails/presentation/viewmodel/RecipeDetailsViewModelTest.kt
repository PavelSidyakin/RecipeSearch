package com.recipebook.recipedetails.presentation.viewmodel

import com.recipebook.recipedetails.domain.api.RecipeDetailsInteractor
import com.recipebook.recipedetails.domain.model.RecipeDetails
import com.recipebook.recipedetails.presentation.model.ErrorType
import com.recipebook.recipedetails.presentation.model.RecipeDetailsScreenState
import com.recipebook.testutils.collectEmits
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.IOException

internal class RecipeDetailsViewModelTest {

    private val recipeId = 1111
    private val recipeDetailsInteractor: RecipeDetailsInteractor = mockk()

    private val subject = RecipeDetailsViewModel(
        recipeId = recipeId,
        recipeDetailsInteractor = recipeDetailsInteractor,
    )

    @BeforeEach
    fun beforeEachTest() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun afterEachTest() {
        Dispatchers.resetMain()
    }

    @Test
    @DisplayName(
        "Given recipeDetailsInteractor.requestRecipeDetails() returns recipe details, " +
                "When the screen is launched, " +
                "Then the details are displayed"
    )
    fun onLaunchTest0() {
        val recipeName = "recipeName"
        val recipeImageUrl = "recipeImageUrl"
        val ingredients = "ingredient0\ningredient1\ningredient2"
        val instructions = "instructions"
        val sourceWebsiteLink = "sourceWebsiteLink"
        val isFavorite = true
        val price = 1111.111f

        // mock
        every { recipeDetailsInteractor.observeRecipeDetails(recipeId) } returns emptyFlow()

        // given
        coEvery { recipeDetailsInteractor.requestRecipeDetails(recipeId) } returns RecipeDetails(
            recipeId = recipeId,
            recipeName = recipeName,
            recipeImageUrl = recipeImageUrl,
            ingredients = ingredients,
            instructions = instructions,
            sourceWebsiteLink = sourceWebsiteLink,
            isFavorite = isFavorite,
            price = price,
        )

        // when
        subject.onLaunch()

        val expectedState = RecipeDetailsScreenState(
            recipeId = recipeId,
            recipeName = recipeName,
            recipeImageUrl = recipeImageUrl,
            ingredients = ingredients,
            instructions = instructions,
            sourceWebsiteLink = sourceWebsiteLink,
            price = price,
            isFavorite = isFavorite,
            errorType = null,
        )

        // then
        assertEquals(expectedState, subject.stateFlow.value)
    }

    @Test
    @DisplayName(
        "Given recipeDetailsInteractor.requestRecipeDetails() throws IOException, " +
                "When the screen is launched, " +
                "Then the network error is displayed"
    )
    fun onLaunchTest1() {
        // mock
        every { recipeDetailsInteractor.observeRecipeDetails(recipeId) } returns emptyFlow()

        // given
        coEvery { recipeDetailsInteractor.requestRecipeDetails(recipeId) } throws IOException()

        // when
        subject.onLaunch()

        // then
        assertEquals(ErrorType.NETWORK, subject.stateFlow.value.errorType)
    }

    @Test
    @DisplayName(
        "Given recipeDetailsInteractor.requestRecipeDetails() throws non-IOException, " +
                "When the screen is launched, " +
                "Then the general error is displayed"
    )
    fun onLaunchTest2() {
        // mock
        every { recipeDetailsInteractor.observeRecipeDetails(recipeId) } returns emptyFlow()

        // given
        coEvery { recipeDetailsInteractor.requestRecipeDetails(recipeId) } throws RuntimeException()

        // when
        subject.onLaunch()

        // then
        assertEquals(ErrorType.GENERAL, subject.stateFlow.value.errorType)
    }

    @Test
    @DisplayName(
        "Given the screen is started, " +
                "When recipeDetailsInteractor.observeRecipeDetails() emits a new recipe details, " +
                "Then the new details are displayed"
    )
    fun onLaunchTest3() {
        val recipeName = "recipeName"
        val recipeImageUrl = "recipeImageUrl"
        val ingredients = "ingredient0\ningredient1\ningredient2"
        val instructions = "instructions"
        val sourceWebsiteLink = "sourceWebsiteLink"
        val isFavorite = true
        val price = 1111.111f

        val observeRecipeDetailsFlow = MutableSharedFlow<RecipeDetails>()
        // mock
        every { recipeDetailsInteractor.observeRecipeDetails(recipeId) } returns observeRecipeDetailsFlow
        coEvery { recipeDetailsInteractor.requestRecipeDetails(recipeId) } returns mockk(relaxed = true)

        // given
        subject.onLaunch()

        runTest {
            // when
            observeRecipeDetailsFlow.emit(
                RecipeDetails(
                    recipeId = recipeId,
                    recipeName = recipeName,
                    recipeImageUrl = recipeImageUrl,
                    ingredients = ingredients,
                    instructions = instructions,
                    sourceWebsiteLink = sourceWebsiteLink,
                    isFavorite = isFavorite,
                    price = price,
                )
            )

            val expectedState = RecipeDetailsScreenState(
                recipeId = recipeId,
                recipeName = recipeName,
                recipeImageUrl = recipeImageUrl,
                ingredients = ingredients,
                instructions = instructions,
                sourceWebsiteLink = sourceWebsiteLink,
                price = price,
                isFavorite = isFavorite,
                errorType = null,
            )

            // then
            assertEquals(expectedState, subject.stateFlow.value)
        }
    }

    @ParameterizedTest(name = "initialIsFavorite={0}")
    @ValueSource(booleans = [true, false])
    @DisplayName(
        "Given the screen is launched and displayed data, " +
                "When favorite icon is clicked, " +
                "Then the interactor is updated with opposite favorite status"
    )
    fun onFavoriteClickedTest0(initialIsFavorite: Boolean) {
        // mock
        coEvery { recipeDetailsInteractor.setFavorite(recipeId, any()) } just Runs

        // mock
        every { recipeDetailsInteractor.observeRecipeDetails(recipeId) } returns emptyFlow()

        // given
        coEvery {
            recipeDetailsInteractor.requestRecipeDetails(recipeId)
        } returns mockk<RecipeDetails>(relaxed = true).also { details ->
            every { details.isFavorite } returns initialIsFavorite
        }
        subject.onLaunch()

        // when
        subject.onFavoriteClicked()

        // then
        coVerify { recipeDetailsInteractor.setFavorite(recipeId, !initialIsFavorite) }
    }

    @Test
    @DisplayName(
        "When the back button is clicked, " +
                "Then the external event OnBackButtonClicked is fired"
    )
    fun onBackButtonClickedTest0() {
        runTest {
            val externalEvents = subject.externalEventsFlow.collectEmits {
                // when
                subject.onBackButtonClicked()

            }

            assertEquals(RecipeDetailsExternalEvent.OnBackButtonClicked, externalEvents[0])
        }
    }
}
