package cobra.util

class DateUtils {

    public static Date getStartOfDay() {
        Calendar calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar.getTime()
    }
}
