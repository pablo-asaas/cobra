<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Novo pagador</title>
</head>

<body>
    <h1>Cadastrar pagador</h1>
    <g:link action="index">Voltar</g:link>
    <g:form method="POST" url="[controller: 'payer', action: 'save']">
        <fieldset class="form">
            <label for="name">Name</label>
            <g:field type="text" name="name" required="true"/>

            <label for="email">Email</label>
            <g:field type="email" name="email" required="true"/>

            <label for="cpfCnpj">Cpf/Cnpj</label>
            <g:field type="text" name="cpfCnpj" maxLength="14" required="true"/>

            <label for="phoneNumber">Celular</label>
            <g:field type="text" name="phoneNumber" maxLength="13" required="true"/>
        </fieldset>
        <fieldset>
            <g:submitButton name="save" class="save" value="Salvar"/>
        </fieldset>
    </g:form>
</body>
</html>
