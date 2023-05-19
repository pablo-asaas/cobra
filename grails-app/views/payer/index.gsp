<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Index</title>
</head>

<body>
    <h1>Payers</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Email</th>
                <th>CPF/CNPJ</th>
                <th>Celular</th>
                <th>Criado em</th>
            </tr>
        </thead>
        <tbody>
            <g:each var="payer" in="${params}">
                <tr>
                    <td>${payer.id}</td>
                    <td>${payer.name}</td>
                    <td>${payer.email}</td>
                    <td>${payer.cpfCnpj}</td>
                    <td>${payer.phoneNumber}</td>
                    <td>${payer.createdAt}</td>
                </tr>
            </g:each>
        </tbody>
    </table>
</body>
</html>
