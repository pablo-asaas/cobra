<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>New Payer</title>
</head>

<body>
    <h1>New Payer</h1>
    <g:form name="newPayer" method="POST" url="[controller: 'payer', action: 'save']">
        <label for="name">Name</label>
        <g:textField name="name"/>

        <label for="email">Email</label>
        <g:textField name="email"/>

        <label for="cpfCnpj">Cpf/Cnpj</label>
        <g:textField name="cpfCnpj"/>

        <label for="phoneNumber">Celular</label>
        <g:textField name="phoneNumber"/>

        <button type="submit">Enviar</button>
    </g:form>
</body>
</html>
