package cobra.util

import javax.swing.text.MaskFormatter

class CpfCnpjUtils {

    private static final Integer CPF_LENGTH = 11
    private static final Integer CNPJ_LENGTH = 14

    public static String formatForPublicVisualization(String cpfCnpj) {
        if (cpfCnpj.length() == CPF_LENGTH) {
            return "***.${cpfCnpj.substring(3, 6)}.${cpfCnpj.substring(6, 9)}-**"
        }

        if (cpfCnpj.length() == CNPJ_LENGTH) {
            MaskFormatter cnpjMaskFormatter = new MaskFormatter("##.###.###/####-##")
            cnpjMaskFormatter.setValueContainsLiteralCharacters(false);

            return cnpjMaskFormatter.valueToString(cpfCnpj)
        }

        return cpfCnpj
    }
}
