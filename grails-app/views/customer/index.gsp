<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Cliente</title>
    </head>

    <body>
        <g:link action="create">Novo cliente</g:link>
        <h1>Clientes</h1>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>CPF/CNPJ</th>
                    <th>Criado em</th>
                </tr>
            </thead>
            <tbody>
                <g:each var="customer" in="${customers}">
                    <tr>
                        <td>${customer.id}</td>
                        <td>${customer.name}</td>
                        <td>${customer.email}</td>
                        <td>${customer.cpfCnpj}</td>
                        <td>${customer.createdAt}</td>
                    </tr>
                </g:each>
            </tbody>
        </table>
    </body>
</html>