package com.example.k_pulse

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.button.MaterialButton

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val notifReqCode = 1001

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NotificationHelper.ensureChannel(requireContext())

        view.findViewById<TextView>(R.id.tvCity).text = "New York (mock)"

        view.findViewById<MaterialButton>(R.id.btnEnableNotifs).setOnClickListener {
            requestNotifPermissionIfNeeded()
        }

        view.findViewById<MaterialButton>(R.id.btnTestNotif).setOnClickListener {
            NotificationHelper.show(
                requireContext(),
                "Test alert 🔔",
                "If you see this, notifications are working."
            )
        }

        view.findViewById<MaterialButton>(R.id.btnRunCheck).setOnClickListener {
            val req = OneTimeWorkRequestBuilder<ConcertCheckWorker>().build()
            WorkManager.getInstance(requireContext()).enqueue(req)
        }
    }

    private fun requestNotifPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    notifReqCode
                )
            }
        }
    }
}
