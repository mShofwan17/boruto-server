package com.example.routes

import com.example.models.ApiResponse
import com.example.repositories.IHeroRepository
import com.example.repositories.alternative.HeroRepositoryAlternative
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.getAllHeroesAlternative() {
    val heroRepository : HeroRepositoryAlternative by inject()

    get("boruto/heroes-alternative") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 3
            val response = heroRepository.getAllHeroes(
                page = page,
                limit = limit
            )
            call.respond(message = response)
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(success = false, message = "${e.message} : This is Should be an Integer."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException){
            call.respond(
                message = ApiResponse(success = false, message = "${e.message} : Heroes not Found."),
                status = HttpStatusCode.NotFound
            )
        }

    }
}