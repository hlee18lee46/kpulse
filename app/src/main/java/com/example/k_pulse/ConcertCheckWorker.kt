package com.example.k_pulse

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.time.LocalDateTime

class ConcertCheckWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val userCity = "New York"

        val now = LocalDateTime.now()
        val next24h = now.plusHours(24)

        val matches = MockData.events.filter { event ->
            event.city.equals(userCity, ignoreCase = true) &&
                    !event.dateTime.isBefore(now) &&
                    event.dateTime.isBefore(next24h)
        }

        if (matches.isNotEmpty()) {
            val e = matches.first()

            NotificationHelper.show(
                context = applicationContext,
                title = "K-pop concert near you 🎤",
                message = "${e.artist} • ${e.venue} • ${e.dateTime.toLocalDate()}"
            )
        }

        return Result.success()
    }
}
