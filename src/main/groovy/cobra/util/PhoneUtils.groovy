package cobra.util

class PhoneUtils {
    
    public static String format(String phoneNumber) {
        String ddi = phoneNumber.substring(0, 2)
        String ddd = phoneNumber.substring(2, 4)

        return "+${ddi} (${ddd}) ${phoneNumber.substring(4, 9)}-${phoneNumber.substring(9)}"
    }
    
}
