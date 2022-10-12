package co.za.dataleaf.verticalrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.za.dataleaf.verticalrecyclerview.adapters.SpinnerAdapter
import co.za.dataleaf.verticalrecyclerview.adapters.WeekAdapter
import co.za.dataleaf.verticalrecyclerview.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeekAdapter

    private var mLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = WeekAdapter {
            setWeekChoice(it)
        }

        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.HORIZONTAL)
        binding.weeksRecyclerView.addItemDecoration(dividerItemDecoration)

        mLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false
        )
        binding.weeksRecyclerView.layoutManager = mLayoutManager
        binding.weeksRecyclerView.setHasFixedSize(true)
        binding.weeksRecyclerView.adapter = adapter
        spinnerYears()
    }

    private fun setWeekChoice(item: SpinnerItem) {
        binding.tvWeekChoice.text = item.display
    }

    private fun spinnerWeeks() {
//        val c = Calendar.getInstance()
        val currentWeek = DateTimeUtil.weekNow()

        val items = DateTimeUtil.setupSpinnerItemWeeks()
    }

    private fun spinnerYears() {
        val year = LocalDateTime.now().year.toLong()

        val items = DateTimeUtil.setupSpinnerItemYears(year)

        setupSpinner(binding.sYear, items) { position ->
            val selectedYear = items[position]
            val currentWeek = DateTimeUtil.weekNow()
            val wks = DateTimeUtil.setupSpinnerItemWeeks(selectedYear.id.toInt())
            adapter.setData(wks.toMutableList())
            binding.weeksRecyclerView.scrollToPosition(currentWeek)
        }

        binding.sYear.setSelection(10, false) // 1 is the start
    }

    private fun setupSpinner(spinner: Spinner, items: List<SpinnerItem>, select: Long? = null,
                             selected: (position: Int) -> Unit) {

        spinner.adapter = SpinnerAdapter(this,
            android.R.layout.simple_spinner_item,
            0,
            items)

        spinner.isSelected = false
        select?.let {
            spinner.setSelection(it.toInt(), false)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position < 0 || position >= items.size) {
                    return
                }

                // CALLBACK
                selected(position)
            }
        }
    }
}