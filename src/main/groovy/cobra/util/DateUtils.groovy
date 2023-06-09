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
}
