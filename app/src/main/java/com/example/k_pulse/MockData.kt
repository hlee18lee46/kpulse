package com.example.k_pulse

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ConcertEvent(
    val id: String,
    val artist: String,
    val city: String,
    val venue: String,
    val dateTime: LocalDateTime
)

object MockData {
    val events = listOf(
        ConcertEvent(
            id = "e1",
            artist = "IVE",
            city = "New York",
            venue = "Madison Square Garden",
            dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(19, 30)) // today
        ),
        ConcertEvent(
            id = "e2",
            artist = "SEVENTEEN",
            city = "Boston",
            venue = "TD Garden",
            dateTime = LocalDateTime.now().plusDays(1).withHour(20).withMinute(0)
        )
    )
}
