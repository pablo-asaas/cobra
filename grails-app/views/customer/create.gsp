<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Create customer</title>
    </head>

    <body>
        <g:link action="index">Back</g:link>
        <g:form method="POST" url="[controller: 'customer', action: 'save']">
            <fieldset class="form">
                    <label for="name">Name</label>
                    <g:textField name="name"/>

                    <label for="email">Email</label>
                    <g:textField name="email"/>

                    <label for="cpfCnpj">CPF/CNPJ</label>
                    <g:textField name="cpfCnpj"/>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="create" class="save" value="Create">Salvar</g:submitButton>
            </fieldset>
        </g:form>
    </body>
</html>