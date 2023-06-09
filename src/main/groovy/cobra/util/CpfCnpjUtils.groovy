package cobra.util

class CpfCnpjUtils {

    public static String formatForPublicVisualization(String cpfCnpj) {
        if (cpfCnpj.length() == 11) {
            return cpfFormat(cpfCnpj)
        }

        if (cpfCnpj.length() == 14) {
            return cnpjFormat(cpfCnpj)
        }

        return cpfCnpj
    }

    private static String cpfFormat(String cpf) {
        return "***.${cpf.substring(3, 6)}.${cpf.substring(6, 9)}-**"
    }

    private static String cnpjFormat(String cnpj) {
        return "${cnpj.substring(0, 2)}.${cnpj.substring(2, 5)}.${cnpj.substring(5, 8)}/${cnpj.substring(8, 12)}-${cnpj.substring(12, 14)}"
    }
}
