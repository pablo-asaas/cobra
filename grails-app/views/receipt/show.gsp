<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Comprovante de pagamento</title>
    </head>

    <body>
        <div class="container-fluid mx-auto w-25 py-5">
            <div class="row">
                <div class="col">
                    <h1 class="font-weight-bold">Comprovante de pagamento</h1>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col font-weight-bold">
                    Data de pagamento
                </div>
                <div class="col">
                    ${payment.paymentDate}
                </div>
            </div>
            <div class="row">
                <div class="col font-weight-bold">
                    Valor
                </div>
                <div class="col">
                    R$ ${payment.value}
                </div>
            </div>
            <div class="row">
                <div class="col font-weight-bold">
                    Tipo de pagamento
                </div>
                <div class="col">
                    ${payment.type}
                </div>
            </div>
            <div class="row">
                <div class="col font-weight-bold">
                    Data de vencimento
                </div>
                <div class="col">
                    ${payment.dueDate}
                </div>
            </div>
            <div class="row mt-3">
                <div class="col">
                    <h2 class="font-weight-bold">Dados do pagador</h2>
                </div>
            </div>
            <div class="row">
                <div class="col font-weight-bold">
                    Nome
                </div>
                <div class="col">
                    ${payment.payer.name}
                </div>
            </div>
            <div class="row">
                <div class="col font-weight-bold">
                    CPF/CNPJ
                </div>
                <div class="col">
                    ${payment.payer.cpfCnpj}
                </div>
            </div>
            <div class="row mt-3">
                <div class="col">
                    <h2 class="font-weight-bold">Dados do cobrador</h2>
                </div>
            </div>
            <div class="row">
                <div class="col font-weight-bold">
                    Nome
                </div>
                <div class="col">
                    ${payment.customer.name}
                </div>
            </div>
            <div class="row">
                <div class="col font-weight-bold">
                    CPF/CNPJ
                </div>
                <div class="col">
                    ${payment.customer.cpfCnpj}
                </div>
            </div>
        </div>
    </body>
</html>