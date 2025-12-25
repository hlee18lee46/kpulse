package com.example.k_pulse

import retrofit2.http.GET
import retrofit2.http.Query

interface EventsApi {

    // View all concerts
    @GET("events")
    suspend fun listEvents(
        @Query("select") select: String =
            "id,provider,title,venue,city,region,country,starts_at,status",
        @Query("order") order: String = "starts_at.asc",
        @Query("limit") limit: Int = 200
    ): List<Event>

    // ✅ City search (THIS FIXES YOUR ERROR)
    @GET("events")
    suspend fun searchByCity(
        @Query("select") select: String =
            "id,provider,title,venue,city,region,country,starts_at,status",
        @Query("order") order: String = "starts_at.asc",
        @Query("city") cityFilter: String,   // ex: "ilike.*Orlando*"
        @Query("limit") limit: Int = 200
    ): List<Event>
}
