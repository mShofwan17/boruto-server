package com.example.routes

import com.example.models.ApiResponse
import com.example.repositories.IHeroRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.getAllHeroes() {
    val heroRepository : IHeroRepository by inject()

    get("boruto/heroes") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..5)

            val response = heroRepository.getAllHeroes(page)
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