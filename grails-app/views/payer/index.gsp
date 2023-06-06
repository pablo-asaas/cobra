<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:javascript src="application.js"/>
    <title>Index</title>
</head>

<body>
    <h1>Pagadores</h1>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newPayerModal">Adicionar</button>
    <g:link action="index">
        <button type="button" class="btn btn-primary">Ativos</button>
    </g:link>
    <g:link action="index" params="[deleted: true]">
        <button type="button" class="btn btn-primary">Inativos</button>
    </g:link>

    <div class="modal fade" id="newPayerModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="newPayerModalLabel">Adicionar pagador</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
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

                            <label class="col-form-label" for="postalCode">CEP</label>
                            <g:field class="form-control" type="text" name="postalCode" required="true"/>

                            <label class="col-form-label" for="streetName">Rua</label>
                            <g:field class="form-control" type="text" name="streetName" required="true"/>

                            <label class="col-form-label" for="buildingNumber">Número</label>
                            <g:field class="form-control" type="text" name="buildingNumber" required="true"/>

                            <label class="col-form-label" for="complement">Complemento</label>
                            <g:field class="form-control" type="text" name="complement"/>

                            <label class="col-form-label" for="state">Estado</label>
                            <g:field class="form-control" type="text" name="state" required="true"/>

                            <label class="col-form-label" for="city">Cidade</label>
                            <g:field class="form-control" type="text" name="city" required="true"/>

                            <label class="col-form-label" for="neighborhood">Bairro</label>
                            <g:field class="form-control" type="text" name="neighborhood" required="true"/>
                        </fieldset>
                        <fieldset>
                            <g:submitButton class="btn btn-primary" name="save" value="Salvar"/>
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
