<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:javascript src="application.js"/>
    <title>Usuários</title>
</head>

<body>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newUserModal">Adicionar</button>
    <div class="modal fade" id="newUserModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="newUserModalLabel">Adicionar usuário</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <g:form name="newUserForm" method="POST" action="save">
                        <fieldset class="form">
                            <label class="col-form-label" for="username"><g:message code='springSecurity.login.username.label'/>:</label>
                            <g:field class="form-control" type="text" name="${usernameParameter ?: 'username'}" id="username" autocapitalize="none"/>

                            <label class="col-form-label" for="password"><g:message code='springSecurity.login.password.label'/>:</label>
                            <g:field class="form-control" type="password" name="${passwordParameter ?: 'password'}" id="password"/>
                        </fieldset>
                        <fieldset>
                            <g:submitButton class="btn btn-primary" name="save" value="Criar usuário"/>
                        </fieldset>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
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
