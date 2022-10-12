package co.za.dataleaf.verticalrecyclerview

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.IsoFields
import java.util.*
import kotlin.collections.ArrayList

data class SpinnerItem(val id: Long, val display: String, val item: Any?)

object DateTimeUtil {

    @JvmStatic
    fun getNow(): LocalDateTime? {
        return LocalDateTime.now()
    }

    @JvmStatic
    fun getNowString(formatter: String = "yyyy-MM-dd HH:mm:ss"): String {
        val fmt = DateTimeFormatter.ofPattern(formatter)
        return LocalDateTime.now().format(fmt)
    }

    @JvmStatic
    fun getDateDifference(fromDate: String, toDate: String, formatter: String, locale: Locale = Locale.getDefault()): Period? {

        val fmt: DateTimeFormatter = DateTimeFormatter.ofPattern(formatter, locale)
        val bgn: LocalDate = LocalDate.from(fmt.parse(fromDate))
        val end: LocalDate = LocalDate.from(fmt.parse(toDate))

        return Period.between(
            bgn,
            end
        )
    }

    @JvmStatic
    fun getPeriods(fromDate: String, toDate: String, formatter: String, locale: Locale = Locale.getDefault()): Map<String, Int?> {

        val period = getDateDifference(fromDate, toDate, formatter, locale)
        val weeks = period?.days?.div(7)

        return mapOf("years" to period?.years, "months" to period?.months, "days" to period?.days, "weeks" to weeks)
    }

    @JvmStatic
    fun weekNow(): Int {
        return LocalDateTime.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
    }

    @JvmStatic
    fun lastWeek(yr: Int): Int {
        val is53weekYear = LocalDate.of(yr, 1, 1).dayOfWeek == DayOfWeek.THURSDAY ||
                LocalDate.of(yr, 12, 31).dayOfWeek == DayOfWeek.THURSDAY
        return if (is53weekYear) 53 else 52
    }

    @JvmStatic
    fun setupSpinnerItemWeeks(yr: Int? = null): ArrayList<SpinnerItem> {
        // Get last week of year
        val lastWeek = this.lastWeek(yr ?: LocalDateTime.now().year)

        val items = arrayListOf<SpinnerItem>()
        for (i in 1..lastWeek) {
            items.add(SpinnerItem(i.toLong(), "$i", i))
        }
        return items
    }

    @JvmStatic
    fun setupSpinnerItemYears(year: Long): ArrayList<SpinnerItem> {
        val items = arrayListOf<SpinnerItem>()

        // Past
        for (i in 10 downTo 1) {
            val entry = year - i.toLong()
            items.add(SpinnerItem(entry, "$entry", entry))
        }

        items.add(SpinnerItem(year, "$year", year))

        // Future
        for (i in 1..10) {
            val entry = year + i.toLong()
            items.add(SpinnerItem(entry, "$entry", entry))
        }

        return items
    }
}