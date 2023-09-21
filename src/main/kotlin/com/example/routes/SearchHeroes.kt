package com.example.routes


import com.example.repositories.IHeroRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.searchHeroes(){
    val repository : IHeroRepository by inject()

    get("boruto/heroes/search") {
        val name = call.request.queryParameters["name"]
        val response = repository.searchHeroes(name)

        call.respond(
            message = response,
            status = HttpStatusCode.OK
        )
    }
}