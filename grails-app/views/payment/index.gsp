<html>
    <head>
        <meta name="layout" content="main"/>

        <title>Cobranças</title>

        <asset:javascript src="application.js"/>

        <g:javascript>
            function handleCreateSubmit(event) {
                event.preventDefault();

                $.ajax({
                    type: "POST",
                    url: "/payment/save",
                    data: $(event.target).serialize(),
                    dataType: "json",
                    success: (data) => {
                        alert(data.message)
                        location.reload()
                    },
                    error: (error) => {
                        alert(error.responseJSON.message)
                    }
                });
            }

            function handleRestoreSubmit(event) {
                event.preventDefault()

                $.ajax({
                    type: "POST",
                    url: "/payment/restore/",
                    data: $(event.target).serialize(),
                    dataType: "json",
                    success: (data) => {
                        alert(data.message)
                        location.reload()
                    },
                    error: (error) => {
                        alert(error.responseJSON.message)
                    }
                });
            }

            function handleDepositClick(event){
                if(!confirm("Deseja realmente confirmar o pagamento?")){
                    return
                }
                $.ajax({
                    type: "POST",
                    url: "/payment/confirmPayment/",
                    data: {
                        id: $(event.target).data("id"),
                        deposit: true
                    },
                    dataType: "json",
                    success: (data) => {
                        alert(data.message)
                        location.reload()
                    },
                    error: (error) => {
                        alert(error.responseJSON.message)
                    }
                });
            }

            $(document).ready(() => {
                $("#createPaymentForm").on("submit", handleCreateSubmit)
                $("#restorePaymentForm").on("submit", handleRestoreSubmit)
                $(".deposit-button").on("click", handleDepositClick)

                $('#restorePaymentModal').on('show.bs.modal', (event) => {
                    const button = $(event.relatedTarget)

                    const id = button.data('id')
                    const dueDate = button.data('due-date')

                    $('#restorePaymentForm input[name=id]').val(id)
                    $('#restorePaymentForm input[name=dueDate]').val(dueDate)
                })
            });
        </g:javascript>
    </head>

    <body>
        <h1>Cobranças</h1>

        <g:link action="index" class="btn btn-primary">
            Ativas
        </g:link>

        <g:link action="index" params="[deleted: true]" class="btn btn-primary">
            Excluídas
        </g:link>

        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createPaymentModal">
            Nova cobrança
        </button>

        <div class="container-fluid">
            <div class="row font-weight-bold">
                <div class="col">Pagador</div>
                <div class="col">Valor</div>
                <div class="col">Vence em</div>
                <div class="col">Status</div>
                <div class="col">Tipo</div>
                <div class="col">Criada em</div>
                <div class="col">Ações</div>
            </div>
            <g:each var="payment" in="${paymentList}">
                <div class="row link-row py-3">
                    <div class="col">${payment.payer.name}</div>
                    <div class="col">${payment.value}</div>
                    <div class="col">${payment.dueDate}</div>
                    <div class="col">${payment.status}</div>
                    <div class="col">${payment.type}</div>
                    <div class="col">${payment.createdAt}</div>
                    <div class="col">
                        <g:if test="${payment.deleted}">
                            <button type="button" data-id="${payment.id}" data-due-date="${dateFieldFormat([value: payment.dueDate])}" class="btn btn-primary" data-toggle="modal" data-target="#restorePaymentModal">
                                Restaurar
                            </button>
                        </g:if>
                        <g:else>
                            <g:if test="${payment.status == cobra.payment.PaymentStatus.PENDING}">
                                <button type="button" data-id="${payment.id}" class="deposit-button btn btn-primary">
                                    Dinheiro
                                </button>
                            </g:if>
                            <g:link action="show" id="${payment.id}">
                                Editar
                            </g:link>
                        </g:else>
                    </div>
                </div>
            </g:each>
        </div>

        <div class="modal fade" id="createPaymentModal" tabindex="-1" aria-labelledby="createPaymentModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createPaymentModalLabel">Nova cobrança</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" id="createPaymentForm">
                            <div class="mb-3">
                                <label for="payer">Pagador</label>
                                <g:field type="number" min="1" name="payer" required="true" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label for="value">Valor</label>
                                <g:field type="number" name="value" min="0.01" step="0.01" required="true" class="form-control"/>
                            </div>
                            <div class="mb-3">
                                <label for="type">Tipo</label>
                                <g:select from="${cobra.payment.PaymentType}" name="type" required="true" class="form-select"/>
                            </div>
                            <div class="mb-3">
                                <label for="dueDate">Vence em</label>
                                <g:field type="date" name="dueDate" required="true" class="form-control"/>
                            </div>
                            <div class="float-end">
                                <button type="button" class="btn" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-success">Salvar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="restorePaymentModal" tabindex="-1" aria-labelledby="restorePaymentModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="restorePaymentModalLabel">Restaurar cobrança</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" id="restorePaymentForm">
                            <g:hiddenField name="id"/>
                            <div class="mb-3">
                                <label for="dueDate">Vence em</label>
                                <g:field type="date" name="dueDate" required="true" class="form-control"/>
                                <small class="form-text text-muted">Informe uma nova data de vencimento</small>
                            </div>
                            <div class="float-end">
                                <button type="button" class="btn" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-success">Restaurar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
