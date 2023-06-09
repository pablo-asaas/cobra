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
        <h1 class="mb-4">Editar pagador</h1>

        <div class="container-fluid border rounded py-3">
            <form id="updatePayerForm" method="PUT">
                <g:hiddenField name="id" value="${payer.id}"/>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="id" value="${payer.id}" disabled="true"/>
                    <label class="col-form-label" for="id">ID</label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="name" value="${payer.name}"/>
                    <label class="col-form-label" for="name">Name</label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="email" name="email" value="${payer.email}"/>
                    <label class="col-form-label" for="email">Email</label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="cpfCnpj" value="${payer.cpfCnpj}" maxLength="14"/>
                    <label class="col-form-label" for="cpfCnpj">CPF/CNPJ</label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="phoneNumber" value="${payer.phoneNumber}" maxLength="13"/>
                    <label class="col-form-label" for="phoneNumber">Celular</label>
                </div>

                <div class="mb-3 form-floating">
                    <span id="createdAt" class="form-control readonly-control">${payer.createdAt}</span>
                    <label for="createdAt">Criado em</label>
                </div>

                <div class="d-flex w-100 justify-content-end">
                    <button type="submit" class="btn btn-success">Salvar</button>
                </div>

            </form>
        </div>
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
