import java.text.SimpleDateFormat

class FormattersTagLib {

    def dateFieldFormat = { attrs ->
        out << new SimpleDateFormat("yyyy-MM-dd").format(attrs.value)
    }
}
