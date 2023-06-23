package cobra.util

import java.text.SimpleDateFormat

class DateUtils {

    public static Date getStartOfDay() {
        Calendar calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar.getTime()
    }

    public static Date getEndOfDay() {
        Calendar calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)

        return calendar.getTime()
    }

    public static String format(Date date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(date)
        } catch (Exception exception) {
            exception.printStackTrace()
            return ''
        }
    }

    public static String formatWithTime(Date date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(date)
        } catch (Exception exception) {
            exception.printStackTrace()
            return ''
        }
    }

    public static Date getStartOfMonth() {
        return getStartOfMonth(new Date())
    }

    public static Date getStartOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)

        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar.getTime()
    }

    public static Date getEndOfMonth() {
        return getEndOfMonth(new Date())
    }

    public static Date getEndOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)

        int monthLastDay = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)

        calendar.set(Calendar.DAY_OF_MONTH, monthLastDay)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)

        return calendar.getTime()
    }

    public static Date subtractOrAddMonths(int quantity) {
        Calendar calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, quantity)

        return calendar.getTime()
    }
}
