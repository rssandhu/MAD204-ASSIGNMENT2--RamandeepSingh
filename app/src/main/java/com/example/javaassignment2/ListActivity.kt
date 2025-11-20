

/**
 *     Course: [MAD204-01] - Lab Assignment 2
 *     Student: Ramandeep Singh, A00194321
 *     Date: 2025-11-20
 *
 * ListActivity: Displays a RecyclerView list of countries supporting
 * click, long press to delete with undo via Snackbar,
 * swipe to delete gesture, and sort button functionality.
 */

package com.example.javaassignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class ListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var sortButton: Button
    private val countries = mutableListOf(
        "Canada", "Brazil", "India", "Germany", "Australia",
        "South Africa", "Japan", "Mexico", "France", "China"
    )
    private lateinit var adapter: CountryAdapter

    private var deletedCountry: String? = null
    private var deletedPosition: Int = -1

    /**
     * Initializes the RecyclerView, adapter, and swipe-to-delete feature.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recyclerView = findViewById(R.id.recyclerViewCountries)
        emptyTextView = findViewById(R.id.textViewEmpty)
        sortButton = findViewById(R.id.buttonSort)

        // Initialize adapter with click and long click handlers
        adapter = CountryAdapter(countries,
            { country -> Toast.makeText(this, "You selected: $country", Toast.LENGTH_SHORT).show() }, // short click
            { position -> deleteItem(position) } // long click
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        updateEmptyView() // Show/hide empty message based on data

        // Sort button to sort countries alphabetically
        sortButton.setOnClickListener {
            countries.sort()
            adapter.notifyDataSetChanged()
            updateEmptyView()
        }

        // Swipe to delete setup using ItemTouchHelper
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                // No move support needed
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Swipe deletes the item at swiped position
                val position = viewHolder.adapterPosition
                deleteItem(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    /**
     * Deletes an item from the list and shows undo Snackbar.
     * @param position Position of item to delete
     */
    private fun deleteItem(position: Int) {
        deletedCountry = countries[position]
        deletedPosition = position
        countries.removeAt(position)
        adapter.notifyItemRemoved(position)
        updateEmptyView()

        // Snackbar to undo deletion
        Snackbar.make(recyclerView, "Deleted: $deletedCountry", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                deletedCountry?.let {
                    countries.add(deletedPosition, it)
                    adapter.notifyItemInserted(deletedPosition)
                    updateEmptyView()
                }
            }.show()
    }

    /**
     * Updates UI elements based on if the list is empty.
     */
    private fun updateEmptyView() {
        if (countries.isEmpty()) {
            emptyTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            sortButton.isEnabled = false
        } else {
            emptyTextView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            sortButton.isEnabled = true
        }
    }
}

/**
 * RecyclerView Adapter for displaying the list of countries.
 * @param countries List of country names
 * @param onClick Lambda for short click event
 * @param onLongClick Lambda for long press event to delete
 */
class CountryAdapter(
    private val countries: List<String>,
    private val onClick: (String) -> Unit,
    private val onLongClick: (Int) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    /**
     * ViewHolder class holding reference to each list item's views.
     */
    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName: TextView = itemView.findViewById(R.id.textViewCountry)

        init {
            // Short click
            itemView.setOnClickListener {
                onClick(countries[adapterPosition])
            }
            // Long click to delete
            itemView.setOnLongClickListener {
                onLongClick(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.countryName.text = countries[position]
    }

    override fun getItemCount(): Int = countries.size
}
