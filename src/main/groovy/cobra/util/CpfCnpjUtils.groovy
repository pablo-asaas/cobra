package cobra.util

class CpfCnpjUtils {

    private static final CPF_LENGTH = 11
    private static final CNPJ_LENGTH = 14

    public static String formatForPublicVisualization(String cpfCnpj) {
        if (cpfCnpj.length() == CPF_LENGTH) {
            return "***.${cpfCnpj.substring(3, 6)}.${cpfCnpj.substring(6, 9)}-**"
        }

        if (cpfCnpj.length() == CNPJ_LENGTH) {
            return "${cpfCnpj.substring(0, 2)}.${cpfCnpj.substring(2, 5)}.${cpfCnpj.substring(5, 8)}/${cpfCnpj.substring(8, 12)}-${cpfCnpj.substring(12, 14)}"
        }

        return cpfCnpj
    }
}
