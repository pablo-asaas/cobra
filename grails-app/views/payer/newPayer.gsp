<%--
  Created by IntelliJ IDEA.
  User: asaas
  Date: 17/05/2023
  Time: 15:42
--%>

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

        <label for="name">Email</label>
        <g:textField name="email"/>

        <label for="name">Cpf/Cnpj</label>
        <g:textField name="cpfCnpj"/>

        <button type="submit">Enviar</button>
    </g:form>
</body>
</html>