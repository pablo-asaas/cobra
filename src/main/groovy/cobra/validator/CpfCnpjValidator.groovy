package cobra.validator

class CpfCnpjValidator {

    public static boolean validate(String cpfCnpj) {
        if(cpfCnpj.size() == 11) {
            return validateCpf(cpfCnpj)
        }
        if(cpfCnpj.size() == 14) {
            return validateCnpj(cpfCnpj)
        }
        return false
    }

    private static boolean validateCpf(String cpf) {
        if (cpf == "00000000000" || cpf == "11111111111" ||
            cpf == ("22222222222") || cpf == ("33333333333") ||
            cpf == ("44444444444") || cpf == ("55555555555") ||
            cpf == ("66666666666") || cpf == ("77777777777") ||
            cpf == ("88888888888") || cpf == ("99999999999") ||
            (cpf.length() != 11))
            return(false)

        char dig10, dig11
        int soma, resultado, peso

        try{
            soma = 0
            peso = 10
            for (int i =0; i < 9; i++) {
                int num = (int)(cpf.charAt(i) - 48)
                soma += (num * peso)
                peso--
            }
            resultado = 11 - (soma % 11)
            if((resultado == 10) || (resultado == 11)) {
                dig10 = '0'
            }else {
                dig10 = (char)(resultado + 48)
            }

            soma = 0
            peso = 11
            for (int i =0; i < 10; i++) {
                int num = (int)(cpf.charAt(i) - 48)
                soma += (num * peso)
                peso--
            }
            resultado = 11 - (soma % 11)
            if((resultado == 10) || (resultado == 11)) {
                dig11 = '0'
            }else {
                dig11 = (char)(resultado + 48)
            }

            if((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) {
                return true
            }
            return false

        }catch (RuntimeException e){
            return false
        }
    }

    private static boolean validateCnpj(String cnpj) {
        if (cnpj == "00000000000000" || cnpj == "11111111111111" ||
                cnpj == ("22222222222222") || cnpj == ("33333333333333") ||
                cnpj == ("44444444444444") || cnpj == ("55555555555555") ||
                cnpj == ("66666666666666") || cnpj == ("77777777777777") ||
                cnpj == ("88888888888888") || cnpj == ("99999999999999") ||
                (cnpj.length() != 14))
            return(false)

        char dig13, dig14
        int soma, resultado, peso

        try{

            soma = 0
            peso = 2
            for (int i = 11; i >= 0; i--) {
                int num = (int)(cnpj.charAt(i) - 48)
                soma += (num * peso)
                peso++

                if (peso == 10) peso = 2
            }

            resultado = (soma % 11)

            if ((resultado == 0) || (resultado == 1)) {
                dig13 = '0'
            }else {
                dig13 = (char)(11 - resultado + 48)
            }

            soma = 0
            peso = 2
            for (int i = 12; i >= 0; i--) {
                int num = (int)(cnpj.charAt(i) - 48)
                soma += (num * peso)
                peso++

                if (peso == 10) peso = 2
            }

            resultado = (soma % 11)

            if ((resultado == 0) || (resultado == 1)) {
                dig14 = '0'
            }else {
                dig14 = (char)(11 - resultado + 48)
            }

            if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13))) {
                return true
            }

            return false
        }catch (RuntimeException e) {
            return false
        }
    }
}
