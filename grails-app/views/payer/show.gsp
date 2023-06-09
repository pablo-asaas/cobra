<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Novo pagador</title>
    <asset:stylesheet src="form.css"/>
    <asset:stylesheet src="navigation.css"/>
</head>

<body>
    <div class="container pt-5 pb-3">
        <div class="mb-4">
            <g:link  action="index" class="text-decoration-none navigation-link">
                <i class="bi bi-chevron-left"></i>
                Voltar
            </g:link>
        </div>
        <h1>Editar pagador</h1>
        <g:form name="updatePayerForm" method="PUT" url="[controller: 'payer', action: 'update']">
            <fieldset class="form">
                <g:field name="id" value="${payer.id}" required="true" type="hidden"/>

                <label class="col-form-label" for="name">Name</label>
                <g:field class="form-control" type="text" name="name" value="${payer.name}" required="true"/>

                <label class="col-form-label" for="email">Email</label>
                <g:field class="form-control" type="email" name="email" value="${payer.email}" required="true"/>

                <label class="col-form-label" for="cpfCnpj">Cpf/Cnpj</label>
                <g:field class="form-control" type="text" name="cpfCnpj" value="${payer.cpfCnpj}" maxLength="14" required="true"/>

                <label class="col-form-label" for="phoneNumber">Celular</label>
                <g:field class="form-control" type="text" name="phoneNumber" value="${payer.phoneNumber}" maxLength="13" required="true"/>
            </fieldset>
            <fieldset>
                <g:link action="delete"  id="${payer.id}">
                    <button type="button" class="btn btn-danger">Deletar</button>
                </g:link>
                <g:submitButton name="update" class="btn btn-primary" value="Salvar"/>
            </fieldset>
        </g:form>
    </div>
<g:javascript>
    function handleFormSubmit(event){
        event.preventDefault();

        $.ajax({
            type: "PUT",
            url: "/payer/update",
            data: $(event.target).serialize(),
            dataType: "json",
            success: (data) => {
                alert(data.message)
                window.location.replace(window.location.origin + "/payer/index")
            },
            error: (error) => {
                alert(error.responseJSON.message)
            }
        });
    }
    $(document).ready(() => {
        $("#updatePayerForm").on("submit", handleFormSubmit)
    });
</g:javascript>
</body>
</html>
