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
                    <label class="col-form-label" for="id">
                        <g:message code="label.id"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="name" value="${payer.name}"/>
                    <label class="col-form-label" for="name">
                        <g:message code="label.name"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="email" name="email" value="${payer.email}"/>
                    <label class="col-form-label" for="email">
                        <g:message code="label.email"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="cpfCnpj" value="${payer.cpfCnpj}" maxLength="14"/>
                    <label class="col-form-label" for="cpfCnpj">
                        <g:message code="label.cpfCnpj"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="phoneNumber" value="${payer.phoneNumber}" maxLength="13"/>
                    <label class="col-form-label" for="phoneNumber">
                        <g:message code="label.phoneNumber"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <span id="createdAt" class="form-control readonly-control">${payer.createdAt}</span>
                    <label for="createdAt">
                        <g:message code="label.createdAt"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="postalCode" required="true" value="${payer.postalCode}"/>
                    <label for="postalCode">
                        <g:message code="label.address.postalCode"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="streetName" required="true" value="${payer.streetName}"/>
                    <label for="streetName">
                        <g:message code="label.address.streetName"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="buildingNumber" required="true" value="${payer.buildingNumber}"/>
                    <label for="buildingNumber">
                        <g:message code="label.address.buildingNumber"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="complement" value="${payer.complement}"/>
                    <label for="complement">
                        <g:message code="label.address.complement"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="state" required="true" value="${payer.state}"/>
                    <label  for="state">
                        <g:message code="label.address.state"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="city" required="true" value="${payer.city}"/>
                    <label for="city">
                        <g:message code="label.address.city"/>
                    </label>
                </div>

                <div class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="neighborhood" required="true" value="${payer.neighborhood}"/>
                    <label for="neighborhood">
                        <g:message code="label.address.neighborhood"/>
                    </label>
                </div>

                <div class="d-flex w-100 justify-content-end">
                    <button type="submit" class="btn btn-success">
                        <g:message code="button.save"/>
                    </button>
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
