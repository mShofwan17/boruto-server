package com.example

import com.example.models.Hero

object PageUtil {
    private fun calculatePage(page: Int): Map<String, Int?> {
        var prev: Int? = page
        var next: Int? = page

        if (page in 1..4) {
            next = next?.plus(1)
        }

        if (page in 2..5) {
            prev = prev?.minus(1)
        }
        if (page == 1) prev = null
        if (page == 5) next = null

        return mapOf("prev" to prev, "next" to next)
    }

    fun prevPage(
        page: Int,
        items: List<Hero> = emptyList(),
        limit: Int = 0
    ): Int? {
        return if (items.isNotEmpty()) calculatePageWithLimit(items,page,limit)["prev"]
        else calculatePage(page)["prev"]
    }

    fun nextPage(
        page: Int,
        items: List<Hero> = emptyList(),
        limit: Int = 0
    ): Int? {
        return if (items.isNotEmpty()) calculatePageWithLimit(items,page,limit)["next"]
        else calculatePage(page)["next"]
    }

    private fun calculatePageWithLimit(
        items: List<Hero>,
        page: Int,
        limit: Int
    ): Map<String, Int?> {
        val allHeroes = items.windowed(
            size = limit,
            step = limit,
            partialWindows = true
        )

        require(page <= allHeroes.size)
        val prevPage = if (page == 1) null else page - 1
        var nextPage = if (page == allHeroes.size) null else page + 1

        return mapOf("prev" to prevPage, "next" to nextPage)
    }
}