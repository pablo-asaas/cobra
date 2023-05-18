<!DOCTYPE html>
<html>
    <head>
        <title>Create customer</title>
    </head>

    <body>
        <g:form method="POST" url="[controller: 'customer', action: 'save']">
            <fieldset class="form">
                <div>
                    <label for="name">Name</label>
                    <g:textField name="name"/>
                </div>

                <div>
                    <label for="email">Email</label>
                    <g:textField name="email"/>
                </div>

                <div>
                    <label for="cpfCnpj">CPF/CNPJ</label>
                    <g:textField name="cpfCnpj"/>
                </div>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="create" class="save" value="Create">Salvar</g:submitButton>
            </fieldset>
        </g:form>
    </body>
</html>