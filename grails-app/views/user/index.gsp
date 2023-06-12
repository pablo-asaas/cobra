<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="grid-table.css"/>
    <asset:stylesheet src="modal.css"/>
    <title>Usuários</title>
</head>

<body>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newUserModal">Adicionar</button>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome de usuário</th>
        </tr>
        </thead>
        <tbody>
        <g:each var="user" in="${userList}">
            <tr>
                <td>${user.id}</td>
                <td>
                    <g:if test="${user.username == currentUser}">
                        ${user.username} (Atual)
                    </g:if>
                    <g:else>
                        ${user.username}
                    </g:else>
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>
    <div class="modal fade" id="newUserModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="newUserModalLabel">Adicionar usuário</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
                </div>
                <div class="modal-body">
                    <form id="newUserForm" method="POST">
                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="${usernameParameter ?: 'username'}" id="username" autocapitalize="none"/>
                                <label for="username"><g:message code='springSecurity.login.username.label'/>:</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="password" name="${passwordParameter ?: 'password'}" id="password"/>
                                <label for="password"><g:message code='springSecurity.login.password.label'/>:</label>
                            </div>

                            <div class="float-end">
                                <button type="submit" class="btn btn-success">Criar usuário</button>
                            </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
    <g:javascript>
        function handleFormSubmit(event){
            event.preventDefault();

            $.ajax({
                type: "POST",
                url: "/user/save",
                data: $(event.target).serialize(),
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
            $("#newUserForm").on("submit", handleFormSubmit)
        });
    </g:javascript>
</body>
</html>
