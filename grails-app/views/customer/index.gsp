<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Customer</title>
    </head>

    <body>
        <g:link action="create">New customer</g:link>
        <h1>Customers</h1>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>CPF/CNPJ</th>
                    <th>Created at</th>
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