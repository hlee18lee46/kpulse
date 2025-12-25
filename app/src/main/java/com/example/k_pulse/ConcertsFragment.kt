package com.example.k_pulse

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class ConcertsFragment : Fragment(R.layout.fragment_concerts) {

    private val api: EventsApi by lazy {
        SupabaseClient.retrofit.create(EventsApi::class.java)
    }

    private val adapter = EventsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etCity = view.findViewById<TextInputEditText>(R.id.etCity)
        val btnSearch = view.findViewById<MaterialButton>(R.id.btnSearch)
        val btnAll = view.findViewById<MaterialButton>(R.id.btnAll)
        val tvSub = view.findViewById<TextView>(R.id.tvSub)
        val progress = view.findViewById<ProgressBar>(R.id.progress)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        fun runSearch(city: String) {
            val q = city.trim()
            if (q.isEmpty()) {
                tvSub.text = "Enter a city to search."
                adapter.submit(emptyList())
                return
            }

            lifecycleScope.launch {
                progress.visibility = View.VISIBLE
                tvSub.text = "Searching concerts in \"$q\"..."

                try {
                    // Supabase PostgREST filter: ilike.*<text>*
                    val events = api.searchByCity(cityFilter = "ilike.*$q*")
                    adapter.submit(events)
                    tvSub.text = "Found ${events.size} concerts matching \"$q\""
                } catch (e: Exception) {
                    tvSub.text = "Error: ${e.message}"
                } finally {
                    progress.visibility = View.GONE
                }
            }
        }

        btnSearch.setOnClickListener {
            runSearch(etCity.text?.toString().orEmpty())
        }

        // Pressing search on keyboard
        etCity.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                runSearch(etCity.text?.toString().orEmpty())
                true
            } else false
        }

        // View all concerts
        btnAll.setOnClickListener {
            lifecycleScope.launch {
                progress.visibility = View.VISIBLE
                tvSub.text = "Loading all concerts..."

                try {
                    val events = api.listEvents()
                    adapter.submit(events)
                    tvSub.text = "Showing ${events.size} concerts"
                } catch (e: Exception) {
                    tvSub.text = "Error: ${e.message}"
                } finally {
                    progress.visibility = View.GONE
                }
            }
        }
    }
}
