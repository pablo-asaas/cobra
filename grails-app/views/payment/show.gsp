<html>
    <head>
        <meta name="layout" content="main"/>

        <title>Editar cobrança</title>

        <asset:stylesheet src="navigation"/>
        <asset:stylesheet src="form"/>

        <asset:javascript src="payment/payment-show.js"/>
    </head>

    <body>
        <div class="container pt-5 pb-3">
            <div class="mb-4">
                <g:link action="index" class="text-decoration-none navigation-link">
                    <i class="bi bi-chevron-left"></i>
                    Voltar
                </g:link>
            </div>

            <h1 class="mb-4">Editar cobrança</h1>

            <div class="container-fluid border rounded py-3">
                <form method="POST" id="updatePaymentForm">
                    <g:hiddenField name="id" value="${payment.id}"/>
                    <div class="mb-3 form-floating">
                        <span id="id" class="form-control readonly-control">${payment.publicId}</span>
                        <label for="id">ID</label>
                    </div>
                    <div class="mb-3 form-floating">
                        <span id="payer" class="form-control readonly-control">${payment.payer.name}</span>
                        <label for="payer">Pagador</label>
                    </div>
                    <div class="mb-3 form-floating">
                        <g:field type="number" name="value" min="0.01" step="0.01" class="form-control" value="${payment.value}" placeholder="Valor"/>
                        <label for="value">Valor</label>
                    </div>
                    <div class="mb-3 form-floating">
                        <g:field type="date" name="dueDate" class="form-control" value="${dateFieldFormat([value: payment.dueDate])}" placeholder="Vence em"/>
                        <label for="dueDate">Vence em</label>
                    </div>
                    <div class="mb-3 form-floating">
                        <span id="status" class="form-control readonly-control">${payment.status}</span>
                        <label for="status">Status</label>
                    </div>
                    <div class="mb-3 form-floating">
                        <span id="type" class="form-control readonly-control">${payment.type}</span>
                        <label for="type">Tipo</label>
                    </div>
                    <div class="mb-3 form-floating">
                        <span id="createdAt" class="form-control readonly-control">${payment.createdAt}</span>
                        <label for="createdAt">Criada em</label>
                    </div>
                    <div class="d-flex w-100 justify-content-end">
                        <button type="submit" class="btn btn-success">Salvar</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
