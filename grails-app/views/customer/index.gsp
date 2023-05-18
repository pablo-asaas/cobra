<!DOCTYPE html>
<html>
    <head>
        <title>Customer</title>
    </head>

    <body>
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