<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Editar dados</title>
    <asset:stylesheet src="form.css"/>
    <asset:stylesheet src="navigation.css"/>
</head>

<body>
<div class="container pt-5 pb-3">
    <h1 class="mb-4">Editar dados</h1>

    <div class="container-fluid border rounded py-3">
        <form id="updateCustomerForm" method="PUT">
            <g:hiddenField name="id" value="${customer.id}"/>

            <div class="mb-3 form-floating">
                <g:field class="form-control" type="text" name="name" value="${customer.name}"/>
                <label class="col-form-label" for="name">Name</label>
            </div>

            <div class="mb-3 form-floating">
                <g:field class="form-control" type="email" name="email" value="${customer.email}"/>
                <label class="col-form-label" for="email">Email</label>
            </div>

            <div class="mb-3 form-floating">
                <g:field class="form-control" type="text" name="cpfCnpj" value="${customer.cpfCnpj}" maxLength="14"/>
                <label class="col-form-label" for="cpfCnpj">CPF/CNPJ</label>
            </div>

            <div class="mb-3 form-floating">
                <g:field class="form-control" type="text" name="postalCode" required="true" value="${customer.postalCode}"/>
                <label for="postalCode">CEP</label>
            </div>

            <div class="mb-3 form-floating">
                <g:field class="form-control" type="text" name="streetName" required="true" value="${customer.streetName}"/>
                <label for="streetName">Rua</label>
            </div>

            <div class="mb-3 form-floating">
                <g:field class="form-control" type="text" name="buildingNumber" required="true" value="${customer.buildingNumber}"/>
                <label for="buildingNumber">Número</label>
            </div>

            <div class="mb-3 form-floating">
                <g:field class="form-control" type="text" name="complement" value="${customer.complement}"/>
                <label for="complement">Complemento</label>
            </div>

            <div class="mb-3 form-floating">
                <g:field class="form-control" type="text" name="state" required="true" value="${customer.state}"/>
                <label  for="state">Estado</label>
            </div>

            <div class="mb-3 form-floating">
                <g:field class="form-control" type="text" name="city" required="true" value="${customer.city}"/>
                <label for="city">Cidade</label>
            </div>

            <div class="mb-3 form-floating">
                <g:field class="form-control" type="text" name="neighborhood" required="true" value="${customer.neighborhood}"/>
                <label for="neighborhood">Bairro</label>
            </div>

            <div class="mb-3 form-floating">
                <span id="createdAt" class="form-control readonly-control">${dateFormat([value: customer.createdAt])}</span>
                <label for="createdAt">Criado em</label>
            </div>

            <div class="d-flex w-100 justify-content-end">
                <button type="submit" class="btn btn-success">Salvar</button>
            </div>

        </form>
    </div>
</div>
<g:javascript>
    function handleUpdateSubmit(event){
        event.preventDefault()

        AjaxRequest.onFormSubmit("PUT", "/customer/update", $(event.target), "/")
    }

    $(document).ready(() => {
        $("#updateCustomerForm").on("submit", handleUpdateSubmit)
    })
</g:javascript>
</body>
</html>