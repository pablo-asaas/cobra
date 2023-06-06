<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:javascript src="application.js"/>
    <title>Criar conta</title>
</head>

<body>
    <g:form name="registerForm" method="POST" action="save">
        <fieldset class="form">
            <label class="col-form-label" for="username"><g:message code='springSecurity.login.username.label'/>:</label>
            <g:field class="form-control" type="text" name="${usernameParameter ?: 'username'}" id="username" autocapitalize="none" required="true"/>

            <label class="col-form-label" for="password"><g:message code='springSecurity.login.password.label'/>:</label>
            <g:field class="form-control" type="password" name="${passwordParameter ?: 'password'}" id="password" required="true"/>

            <label class="col-form-label" for="name">Nome</label>
            <g:field class="form-control" type="text" name="name" required="true"/>

            <label class="col-form-label" for="email">Email</label>
            <g:field class="form-control" type="email" name="email" required="true"/>

            <label class="col-form-label" for="cpfCnpj">CPF/CNPJ</label>
            <g:field class="form-control" type="text" name="cpfCnpj" maxLength="14" required="true"/>

            <label class="col-form-label" for="postalCode">CEP</label>
            <g:field class="form-control" type="text" name="postalCode" required="true"/>

            <label class="col-form-label" for="streetName">Rua</label>
            <g:field class="form-control" type="text" name="streetName" required="true"/>

            <label class="col-form-label" for="buildingNumber">Número</label>
            <g:field class="form-control" type="text" name="buildingNumber" required="true"/>

            <label class="col-form-label" for="complement">Complemento</label>
            <g:field class="form-control" type="text" name="complement"/>

            <label class="col-form-label" for="state">Estado</label>
            <g:field class="form-control" type="text" name="state" required="true"/>

            <label class="col-form-label" for="city">Cidade</label>
            <g:field class="form-control" type="text" name="city" required="true"/>

            <label class="col-form-label" for="neighborhood">Bairro</label>
            <g:field class="form-control" type="text" name="neighborhood" required="true"/>
        </fieldset>
        <fieldset>
            <g:submitButton class="btn btn-primary" name="save" value="Criar conta"/>
        </fieldset>
    </g:form>
    <g:javascript>
        function validateCpfCnpj(event){

            const cpfCnpj = parseInt($(event.target.cpfCnpj).val())
            if(!cpfCnpj.match(/[0-9]{11}/) || !cpfCnpj.match(/[0-9]{14}/)){
                event.preventDefault();
                alert("CPF ou CNPJ inválido")
            }
        }
        $(document).ready(() => {
            $("#registerForm").on("submit", validateCpfCnpj)
        });
    </g:javascript>
</body>
</html>
