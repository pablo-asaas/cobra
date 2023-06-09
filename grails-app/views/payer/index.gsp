<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="grid-table.css"/>
    <asset:stylesheet src="modal.css"/>
    <title>Index</title>
</head>

<body>
    <h1>Pagadores</h1>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newPayerModal">Adicionar</button>
    <g:link action="index">
        <button type="button" class="btn btn-primary">Ativos</button>
    </g:link>
    <g:link action="index" params="[deleted: true]">
        <button type="button" class="btn btn-primary">Inativos</button>
    </g:link>

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
                        <g:if test="${payer.deleted}">
                            <button type="button" data-id="${payer.id}" class="restore-button btn btn-primary">Restaurar</button>
                        </g:if>
                        <g:else>
                            <g:link action="show" id="${payer.id}">
                                <button class="btn btn-primary">Editar</button>
                            </g:link>
                        </g:else>
                    </td>
                </tr>
            </g:each>
        </tbody>
    </table>
    <div class="modal fade" id="newPayerModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="newPayerModalLabel">Adicionar pagador</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
                </div>
                <div class="modal-body">
                    <g:form name="createPayerForm" method="POST" url="[controller: 'payer', action: 'save']">
                        <fieldset class="form">
                            <label class="col-form-label" for="name">Nome</label>
                            <g:field class="form-control" type="text" name="name" required="true"/>

                            <label class="col-form-label" for="email">Email</label>
                            <g:field class="form-control" type="email" name="email" required="true"/>

                            <label class="col-form-label" for="cpfCnpj">Cpf/Cnpj</label>
                            <g:field class="form-control" type="text" name="cpfCnpj" maxLength="14" required="true"/>

                            <label class="col-form-label" for="phoneNumber">Celular</label>
                            <g:field class="form-control" type="text" name="phoneNumber" maxLength="13" required="true"/>
                        </fieldset>
                        <fieldset>
                            <g:submitButton class="btn btn-primary" name="save" value="Salvar"/>
                        </fieldset>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
<g:javascript>
    function handleFormSubmit(event){
        event.preventDefault();

        $.ajax({
            type: "POST",
            url: "/payer/save",
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
        $("#createPayerForm").on("submit", handleFormSubmit)
        $(".restore-button").on("click", restorePayer)
    });
</g:javascript>
</body>
</html>
