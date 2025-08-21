package com.recipebook.datasource.remote

import com.recipebook.datasource.remote.model.SpoonacularRecipeBriefInfo
import com.recipebook.datasource.remote.model.SpoonacularRecipeInformationResponse
import com.recipebook.datasource.remote.model.SpoonacularRecipeIngredient
import com.recipebook.datasource.remote.model.SpoonacularRecipeResponse
import com.recipebook.datasource.remote.model.SpoonacularRecipeSortDirection
import com.recipebook.datasource.remote.model.SpoonacularRecipeSortOption
import com.recipebook.testutils.readFileFromResources
import com.recipebook.testutils.valueListsArgumentsProvider
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.ParameterDeclarations
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import java.io.IOException
import java.util.stream.Stream

internal class SpoonacularRemoteDataSourceTest {
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .build()

    private val subject: SpoonacularRemoteDataSource by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType(),
                )
            )
            .build()
            .create<SpoonacularRemoteDataSource>()
    }

    @BeforeEach
    fun beforeEach() {
        mockWebServer.start()
    }

    @AfterEach
    fun afterEachTest() {
        mockWebServer.close()
    }

    @Test
    @DisplayName(
        "When requestRecipeInformation() is called, " +
                "Then the request query is filled correctly"
    )
    fun requestRecipeInformationTest0() = runTest {
        mockWebServer.enqueue(
            MockResponse.Builder()
                .code(200)
                .body(readFileFromResources("RecipeInformationResponse.json"))
                .build()
        )

        // when
        subject.requestRecipeInformation(1234)

        // then
        assertEquals(
            "/recipes/1234/information",
            mockWebServer.takeRequest().url.encodedPath,
        )
    }

    @Test
    @DisplayName(
        "Given the server returns json response for recipe information, " +
                "When requestRecipeInformation() is called, " +
                "Then the result is filled correctly"
    )
    fun requestRecipeInformationTest1() = runTest {
        // given
        mockWebServer.enqueue(
            MockResponse.Builder()
                .code(200)
                .body(readFileFromResources("RecipeInformationResponse.json"))
                .build()
        )

        // when
        val actualResponse = subject.requestRecipeInformation(0)

        val expectedResponse = SpoonacularRecipeInformationResponse(
            id = 716429,
            title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
            imageUrl = "https://img.spoonacular.com/recipes/716429-556x370.jpg",
            ingredients = listOf(
                SpoonacularRecipeIngredient("1 tbsp butter"),
                SpoonacularRecipeIngredient("about 2 cups frozen cauliflower florets"),
            ),
            summary = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs might be a good recipe",
            sourceWebsiteLink = "http://fullbellysisters.blogspot.com/2012/06/" +
                    "pasta-with-garlic-scallions-cauliflower.html",
            pricePerServing = 163.15f,
        )

        // then
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    @DisplayName(
        "Given the server returns incorrect json response for recipe information, " +
                "When requestRecipeInformation() is called, " +
                "Then the exception is fired"
    )
    fun requestRecipeInformationTest2() = runTest {
        // given
        mockWebServer.enqueue(
            MockResponse.Builder()
                .code(200)
                .body("{ badJson: xxx }")
                .build()
        )

        // then
        assertThrows<SerializationException> {
            // when
            subject.requestRecipeInformation(0)
        }
    }

    @Test
    @DisplayName(
        "Given the server fails to connect, " +
                "When requestRecipeInformation() is called, " +
                "Then the exception is fired"
    )
    fun requestRecipeInformationTest3() = runTest {
        // given
        mockWebServer.enqueue(
            MockResponse.Builder()
                .build()
        )
        mockWebServer.close()

        // then
        assertThrows<IOException> {
            // when
            subject.requestRecipeInformation(0)
        }
    }

    @ParameterizedTest
    @ArgumentsSource(SortOptionAndDirectionArgumentsProvider::class)
    @DisplayName(
        "When requestRecipes() is called, " +
                "Then the request query is filled correctly"
    )
    fun requestRecipesTest0(
        sortOption: SpoonacularRecipeSortOption,
        sortDirection: SpoonacularRecipeSortDirection,
    ) = runTest {
        mockWebServer.enqueue(
            MockResponse.Builder()
                .code(200)
                .body(readFileFromResources("SearchRecipesResponse.json"))
                .build()
        )

        // when
        subject.requestRecipes(
            query = "query123",
            offset = 1111,
            number = 2222,
            sortOption = sortOption,
            sortDirection = sortDirection,
        )

        val sortOptionStr = when (sortOption) {
            SpoonacularRecipeSortOption.CALORIES -> "calories"
            SpoonacularRecipeSortOption.PRICE -> "price"
        }

        val sortDirectionStr = when (sortDirection) {
            SpoonacularRecipeSortDirection.ASCENDING -> "asc"
            SpoonacularRecipeSortDirection.DESCENDING -> "desc"
        }

        // then
        assertEquals(
            "/recipes/complexSearch?" +
                    "addRecipeInformation=true&query=query123&offset=1111&number=2222&" +
                    "sort=$sortOptionStr&sortDirection=$sortDirectionStr",
            mockWebServer.takeRequest().url.run { "$encodedPath?$encodedQuery" },
        )
    }

    @Test
    @DisplayName(
        "Given the server returns json response for recipes, " +
                "When requestRecipes() is called, " +
                "Then the result is filled correctly"
    )
    fun requestRecipesTest1() = runTest {
        // given
        mockWebServer.enqueue(
            MockResponse.Builder()
                .code(200)
                .body(readFileFromResources("SearchRecipesResponse.json"))
                .build()
        )

        // when
        val actualResponse = subject.requestRecipes(
            query = "",
            offset = 0,
            number = 0,
            sortOption = SpoonacularRecipeSortOption.CALORIES,
            sortDirection = SpoonacularRecipeSortDirection.DESCENDING,
        )

        val expectedResponse = SpoonacularRecipeResponse(
            offset = 10,
            number = 2,
            totalResults = 1234,
            recipes = listOf(
                SpoonacularRecipeBriefInfo(
                    id = 1095889,
                    imageUrl = "https://img.spoonacular.com/recipes/1095889-312x231.jpg",
                    title = "Creamy Homemade Tomato Soup",
                    summary = "Creamy Homemade Tomato Soup might be a good recipe",
                    pricePerServing = 118.64f,
                ),
                SpoonacularRecipeBriefInfo(
                    id = 642583,
                    imageUrl = "https://img.spoonacular.com/recipes/642583-312x231.jpg",
                    title = "Farfalle with Peas, Ham and Cream",
                    summary = "The recipe Farfalle with Peas, Ham and Cream can be made <b>",
                    pricePerServing = 77.21f,
                ),
            )
        )

        // then
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    @DisplayName(
        "Given the server returns incorrect json response for recipes, " +
                "When requestRecipes() is called, " +
                "Then the exception is fired"
    )
    fun requestRecipesTest2() = runTest {
        // given
        mockWebServer.enqueue(
            MockResponse.Builder()
                .code(200)
                .body("{ badJson: xxx }")
                .build()
        )

        // then
        assertThrows<SerializationException> {
            // when
            subject.requestRecipes(
                query = "",
                offset = 0,
                number = 0,
                sortOption = SpoonacularRecipeSortOption.CALORIES,
                sortDirection = SpoonacularRecipeSortDirection.DESCENDING,
            )
        }
    }

    @Test
    @DisplayName(
        "Given the server fails to connect, " +
                "When requestRecipes() is called, " +
                "Then the exception is fired"
    )
    fun requestRecipesTest3() = runTest {
        // given
        mockWebServer.enqueue(
            MockResponse.Builder()
                .build()
        )
        mockWebServer.close()

        // then
        assertThrows<IOException> {
            // when
            subject.requestRecipes(
                query = "",
                offset = 0,
                number = 0,
                sortOption = SpoonacularRecipeSortOption.CALORIES,
                sortDirection = SpoonacularRecipeSortDirection.DESCENDING,
            )
        }
    }

    private class SortOptionAndDirectionArgumentsProvider : ArgumentsProvider {
        private val delegateProvider = valueListsArgumentsProvider(
            SpoonacularRecipeSortOption.entries,
            SpoonacularRecipeSortDirection.entries,
        )

        override fun provideArguments(
            parameters: ParameterDeclarations,
            context: ExtensionContext
        ): Stream<out Arguments> {
            return delegateProvider.provideArguments(parameters, context)
        }
    }
}
