import cobra.util.CpfCnpjUtils
import cobra.util.CurrencyUtils
import cobra.util.DateUtils
import cobra.util.PhoneUtils

import java.text.SimpleDateFormat

class FormattersTagLib {

    def dateFieldFormat = { attrs ->
        out << new SimpleDateFormat("yyyy-MM-dd").format(attrs.value)
    }

    def dateFormat = { attrs, body ->
        out << body() << DateUtils.format(attrs.value)
    }

    def datetimeFormat = { attrs, body ->
        out << body() << DateUtils.formatWithTime(attrs.value)
    }

    def currencyFormat = { attrs, body ->
        out << body() << "R\$ ${CurrencyUtils.format(attrs.value)}"
    }

    def cpfCnpjFormat = { attrs, body ->
        out << body() << CpfCnpjUtils.formatForPublicVisualization(attrs.value)
    }

    def phoneNumberFormat = { attrs, body ->
        out << body() << PhoneUtils.format(attrs.value)
    }
}
