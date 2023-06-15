package cobra.util

import grails.util.Holders

class MessageUtils {

    private static Locale locale = new Locale('pt', 'BR')

    public static String getMessage(String messageCode, List arguments) {
        try {
            return Holders.applicationContext.getBean("messageSource").getMessage(messageCode, arguments as Object[], locale)
        } catch(Exception exception) {
            exception.printStackTrace()
            return ''
        }
    }
}
