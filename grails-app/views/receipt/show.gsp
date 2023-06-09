<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Comprovante de pagamento</title>
    </head>

    <body>
        <div class="card mt-5 mx-auto" style="max-width: 576px">
            <div class="card-body">
                <h1 class="mb-3 h2">Comprovante de pagamento</h1>
                <div class="d-flex justify-content-between">
                    <strong>Data de pagamento</strong>
                    <span>
                        <g:dateFormat value="${payment.paymentDate}"/>
                    </span>
                </div>
                <div class="d-flex justify-content-between">
                    <strong>Valor</strong>
                    <span>
                        <g:currencyFormat value="${payment.value}"/>
                    </span>
                </div>
                <div class="d-flex justify-content-between">
                    <strong>Tipo de pagamento</strong>
                    <span>
                        <g:message code="PaymentType.${payment.type}"/>
                    </span>
                </div>
                <div class="d-flex justify-content-between">
                    <strong>Data de vencimento</strong>
                    <span>
                        <g:dateFormat value="${payment.dueDate}"/>
                    </span>
                </div>

                <h2 class="h3 mt-3">Dados do pagador</h2>
                <div class="d-flex justify-content-between">
                    <strong>Nome</strong>
                    <span>${payment.payer.name}</span>
                </div>
                <div class="d-flex justify-content-between">
                    <strong>CPF/CNPJ</strong>
                    <span>
                        <g:cpfCnpjFormat value="${payment.payer.cpfCnpj}"/>
                    </span>
                </div>

                <h2 class="h3 mt-3">Dados do recebedor</h2>
                <div class="d-flex justify-content-between">
                    <strong>Nome</strong>
                    <span>${payment.customer.name}</span>
                </div>
                <div class="d-flex justify-content-between">
                    <strong>CPF/CNPJ</strong>
                    <span>
                        <g:cpfCnpjFormat value="${payment.customer.cpfCnpj}"/>
                    </span>
                </div>
            </div>
        </div>
    </body>
</html>