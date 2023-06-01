<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Restaurar pagador</title>
</head>

<body>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Email</th>
            <th>CPF/CNPJ</th>
            <th>Celular</th>
            <th>Criado em</th>
            <th>Acões</th>
        </tr>
        </thead>
        <tbody>
        <g:each var="payer" in="${payerList}">
            <tr>
                <td>${payer.id}</td>
                <td>${payer.name}</td>
                <td>${payer.email}</td>
                <td>${payer.cpfCnpj}</td>
                <td>${payer.phoneNumber}</td>
                <td>${payer.createdAt}</td>
                <td>
                    <g:link action="show" id="${payer.id}">
                        <button class="btn btn-primary">Editar</button>
                    </g:link>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
</body>
</html>