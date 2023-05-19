<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Cadastrar cliente</title>
    </head>

    <body>
        <g:link action="index">Voltar</g:link>
        <g:form method="POST" url="[controller: 'customer', action: 'save']">
            <fieldset class="form">
                <label for="name">Nome</label>
                <g:textField name="name"/>

                <label for="email">Email</label>
                <g:textField name="email"/>

                <label for="cpfCnpj">CPF/CNPJ</label>
                <g:textField name="cpfCnpj"/>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="save" class="save" value="Salvar"/>
            </fieldset>
        </g:form>
    </body>
</html>
