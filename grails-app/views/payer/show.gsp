<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Novo pagador</title>
</head>

<body>
    <h1>Editar pagador</h1>
    <g:link  action="index">
        <button class="btn btn-primary">Voltar</button>
    </g:link>
    <g:form method="PUT" url="[controller: 'payer', action: 'update']">
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
            <g:actionSubmit action="delete" class="btn btn-danger" value="Deletar"/>
            <g:actionSubmit action="update" class="btn btn-primary" value="Salvar"/>
        </fieldset>
    </g:form>
</body>
</html>
