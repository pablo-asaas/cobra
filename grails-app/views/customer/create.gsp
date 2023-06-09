<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Cadastrar cliente</title>
    </head>

    <body>
        <g:link action="index">Voltar</g:link>
        <g:form name="createCustomerForm" method="POST" action="save">
            <fieldset class="form">
                <label for="name">Nome</label>
                <g:field type="text" name="name" required="true"/>

                <label for="email">Email</label>
                <g:field type="email" name="email" required="true"/>

                <label for="cpfCnpj">CPF/CNPJ</label>
                <g:field type="text" name="cpfCnpj" maxLength="14" required="true"/>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="save" class="save" value="Salvar"/>
            </fieldset>
        </g:form>
        <g:javascript>
            function handleFormSubmit(event){
                event.preventDefault();

                $.ajax({
                    type: "POST",
                    url: "/customer/save",
                    data: $(event.target).serialize(),
                    dataType: "json",
                    success: (data) => {
                        alert(data.message)
                        window.location.replace(window.location.origin + "/customer/index")
                    },
                    error: (error) => {
                        alert(error.responseJSON.message)
                    }
                });
            }
            $(document).ready(() => {
                $("#createCustomerForm").on("submit", handleFormSubmit)
            });
        </g:javascript>
    </body>
</html>
