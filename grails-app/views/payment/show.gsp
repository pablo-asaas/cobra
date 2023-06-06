<html>
    <head>
        <meta name="layout" content="main"/>

        <title>Editar cobrança</title>

        <asset:javascript src="application.js"/>

        <g:javascript>
            function handleFormSubmit(event) {
                event.preventDefault();

                $.ajax({
                    type: "PUT",
                    url: "/payment/update",
                    data: $(event.target).serialize(),
                    dataType: "json",
                    success: (data) => {
                        alert(data.message)
                        window.location.replace(window.location.origin + "/payment")
                    },
                    error: (error) => {
                        alert(error.responseJSON.message)
                    }
                });
            }

            function handleDeleteClick(event) {
                event.preventDefault()

                if (!confirm("Deseja realmente excluir esta cobrança?")) {
                    return
                }

                $.ajax({
                    method: "DELETE",
                    url: "/payment/delete",
                    data: {id: ${payment.id}},
                    dataType: "json",
                    success: (data) => {
                        window.location.replace(window.location.origin + "/payment")
                    },
                    error: (error) => {
                        alert(error.responseJSON.message)
                    }
                })
            }

            $(document).ready(() => {
                $("#updatePaymentForm").on("submit", handleFormSubmit)
                $("#deleteButton").on("click", handleDeleteClick)
            });
        </g:javascript>
    </head>

    <body>
        <g:link action="index">Voltar</g:link>

        <h1>Editar cobrança</h1>

        <div class="container-fluid">
            <form method="POST" id="updatePaymentForm">
                <g:hiddenField name="id" value="${payment.id}"/>
                <div class="mb-3">
                    <label for="value">Valor</label>
                    <g:field type="number" name="value" min="0.01" step="0.01" class="form-control" value="${payment.value}"/>
                </div>
                <div class="mb-3">
                    <label for="dueDate">Vence em</label>
                    <g:field type="date" name="dueDate" class="form-control" value="${dateFieldFormat([value: payment.dueDate])}"/>
                </div>
                <div class="float-end">
                    <button type="button" class="btn btn-danger" id="deleteButton">Excluir</button>
                    <button type="submit" class="btn btn-success">Salvar</button>
                </div>
            </form>
        </div>
    </body>
</html>