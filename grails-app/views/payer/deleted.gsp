<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:javascript src="application.js"/>
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
        <g:each var="payer" in="${deletedPayerList}">
            <tr>
                <td>${payer.id}</td>
                <td>${payer.name}</td>
                <td>${payer.email}</td>
                <td>${payer.cpfCnpj}</td>
                <td>${payer.phoneNumber}</td>
                <td>${payer.createdAt}</td>
                <td>
                    <button type="button" data-id="${payer.id}" class="restore-button btn btn-primary">Restaurar</button>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <g:javascript>
        function restorePayer(event){

            const id = $(event.target).data("id")

            $.ajax({
                type: "POST",
                url: "/payer/restore/" + id,
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
            $(".restore-button").on("click", restorePayer)
        });
    </g:javascript>
</body>
</html>