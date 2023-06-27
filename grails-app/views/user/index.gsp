<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="grid-table.css"/>
    <asset:stylesheet src="modal.css"/>
    <title>Usuários</title>
</head>

<body>
    <div class="container pt-5 pb-3">
        <h1 class="mb-4">Usuários</h1>

        <div class="mb-4 d-md-flex justify-content-md-end">
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#newUserModal">
                Adicionar
            </button>
        </div>

        <div class="container-fluid border rounded">
            <div class="row header-row fw-bold border-bottom py-3">
                <div class="col">ID</div>
                <div class="col">Nome de usuário</div>
                <div class="col">Criado em</div>
            </div>
            <g:each var="user" in="${userList}">
                <div class="row data-row">
                    <div class="col">${user.id}</div>
                    <div class="col">
                        <g:if test="${user.username == currentUser}">
                            ${user.username} (Atual)
                        </g:if>
                        <g:else>
                            ${user.username}
                        </g:else>
                    </div>
                    <div class="col">${dateFormat([value: user.createdAt])}</div>
                </div>
            </g:each>
        </div>
    </div>
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

            AjaxRequest.onFormSubmit("POST", "/user/save", $(event.target))
        }
        $(document).ready(() => {
            $("#newUserForm").on("submit", handleFormSubmit)
        });
    </g:javascript>
</body>
</html>
