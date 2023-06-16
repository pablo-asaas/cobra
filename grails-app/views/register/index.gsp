<%@ page import="cobra.payment.PaymentStatus" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Criar conta</title>
    <asset:stylesheet src="form.css"/>
</head>

<body>
    <div class="container pt-5 pb-3">
        <h1 class="mb-4">Criar conta</h1>
        <div class="container-fluid border rounded py-3">
            <form class="row" id="registerForm" method="POST">
                <h3 class="mb-4 mt-2">Informações de cadastro</h3>

                <div>
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="text" name="name" required="true"/>
                        <label class="col-form-label" for="name">Nome</label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="email" name="email" required="true"/>
                        <label class="col-form-label" for="email">Email</label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="text" name="cpfCnpj" maxLength="14" required="true"/>
                        <label class="col-form-label" for="cpfCnpj">CPF/CNPJ</label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="text" name="${usernameParameter ?: 'username'}" id="username" autocapitalize="none" required="true"/>
                        <label class="col-form-label" for="username"><g:message code='springSecurity.login.username.label'/></label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="password" name="${passwordParameter ?: 'password'}" id="password" required="true"/>
                        <label class="col-form-label" for="password"><g:message code='springSecurity.login.password.label'/></label>
                    </div>
                </div>

                <h3 class="mb-4 mt-2">Informações de endereço</h3>

                <div class="col-md-4">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="text" name="postalCode" required="true"/>
                        <label class="col-form-label" for="postalCode">CEP</label>
                    </div>
                </div>

                <div class="col-md-8">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="text" name="streetName" required="true"/>
                        <label class="col-form-label" for="streetName">Rua</label>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="text" name="buildingNumber" required="true"/>
                        <label class="col-form-label" for="buildingNumber">Número</label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="text" name="complement"/>
                        <label class="col-form-label" for="complement">Complemento (Opicional)</label>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="text" name="neighborhood" required="true"/>
                        <label class="col-form-label" for="neighborhood">Bairro</label>
                    </div>
                </div>

                <div class="col-md-10">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="text" name="city" required="true"/>
                        <label class="col-form-label" for="city">Cidade</label>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="mb-3 form-floating">
                        <g:field class="form-control" type="text" name="state" required="true"/>
                        <label class="col-form-label" for="state">Estado</label>
                    </div>
                </div>

                <div class="d-flex w-100 justify-content-end">
                    <g:submitButton class="btn btn-success" name="save" value="Criar conta"/>
                </div>
            </form>
        </div>
    </div>
    <g:javascript>
        function validateCpfCnpj(event){
            const cpfCnpj = $(event.target.cpfCnpj).val()
            const isValidCpf = cpfCnpj.match(/^([0-9]{11})$/)
            const isValidCnpj = cpfCnpj.match(/^([0-9]{14})$/)

            return isValidCpf || isValidCnpj
        }
        function handleRegisterSubmit(event){
            event.preventDefault();
            if (!validateCpfCnpj(event)){
                alert("CPF ou CNPJ inválido")
                return
            }

            $.ajax({
                type: "POST",
                url: "/register/save",
                data: $(event.target).serialize(),
                dataType: "json",
                success: (data) => {
                    alert(data.message)
                    location.reload()
                },
                error: (error) => {
                    alert(error.responseJSON.message)
                }
            });
        }

        $(document).ready(() => {
            $("#registerForm").on("submit", handleRegisterSubmit)
        });
    </g:javascript>
</body>
</html>
