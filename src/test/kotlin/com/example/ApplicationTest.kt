package com.example

import com.example.models.ApiResponse
import com.example.plugins.configureRouting
import com.example.repositories.IHeroRepository
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    private val heroRepository: IHeroRepository by inject(IHeroRepository::class.java)
    @Test
    fun `access_root_endpoint, assert_correct_information`() {
        testApplication {
            application { configureRouting() }
            client.get("/").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = status
                )

                assertEquals(
                    expected = "Welcome to Boruto API!",
                    actual = bodyAsText()
                )
            }
        }
    }

    @OptIn(InternalAPI::class)
    @Test
    fun `access_all_heroes_endpoint,query all pages ,assert correct information`() = testApplication {
        environment { developmentMode = false }
        application { configureRouting() }

        val pages = 1..5
        pages.forEach { page ->
            client.get("boruto/heroes?page=$page").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = status
                )
                val actual = Json.decodeFromString<ApiResponse>(body())
                val expected = ApiResponse(
                    success = true,
                    message = "Success",
                    prevPage = PageUtil.prevPage(page),
                    nextPage = PageUtil.nextPage(page),
                    heroes = heroRepository.heroes[page],
                    lastUpdated = actual.lastUpdated
                )


                println("EXPECTED: $expected")
                println("ACTUAL: $actual")

                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    }

    @Test
    fun `search heroes endpoint, assert single correct information`() {
        testApplication {
            environment { developmentMode = false }
            application { configureRouting() }

            val nameHeroes = "sas"
            client.get("boruto/heroes/search?name=$nameHeroes").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = status
                )

                val actual = Json.decodeFromString<ApiResponse>(body()).heroes?.size

                assertEquals(
                    expected = 1,
                    actual = actual
                )
            }
        }
    }


    @Test
    fun `search heroes endpoint, assert multiple correct information`() {
        testApplication {
            environment { developmentMode = false }
            application { configureRouting() }

            val nameHeroes = "sa"
            client.get("boruto/heroes/search?name=$nameHeroes").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = status
                )

                val actual = Json.decodeFromString<ApiResponse>(body()).heroes?.size

                assertEquals(
                    expected = 3,
                    actual = actual
                )
            }
        }
    }


}
