<%@ page import="cobra.payment.PaymentType; cobra.payment.PaymentStatus" %>

<html>
    <head>
        <meta name="layout" content="main"/>

        <title>Cobranças</title>

        <asset:stylesheet src="grid-table"/>
        <asset:stylesheet src="modal"/>

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

            function handleDeleteClick(event) {
                event.preventDefault()

                if (!confirm("Deseja realmente excluir esta cobrança?")) {
                    return
                }

                $.ajax({
                    method: "DELETE",
                    url: "/payment/delete",
                    data: {
                        id: $(event.delegateTarget).data("id")
                    },
                    dataType: "json",
                    success: (data) => {
                        alert(data.message)
                        location.reload()
                    },
                    error: (error) => {
                        alert(error.responseJSON.message)
                    }
                })
            }

            function handleDepositClick(event){
                if(!confirm("Deseja realmente confirmar o pagamento?")){
                    return
                }
                $.ajax({
                    type: "POST",
                    url: "/payment/confirmPayment/",
                    data: {
                        id: $(event.delegateTarget).data("id"),
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
                $(".delete-button").on("click", handleDeleteClick)
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
        <div class="container pt-5 pb-3">
            <h1 class="mb-4">Cobranças</h1>

            <div class="mb-4">
                <div class="btn-group border">
                    <g:link action="index" class="btn btn-light border-end">
                        Ativas
                    </g:link>

                    <g:link action="index" params="[deleted: true]" class="btn btn-light border-start">
                        Excluídas
                    </g:link>
                </div>

                <button type="button" class="btn btn-success float-end" data-bs-toggle="modal" data-bs-target="#createPaymentModal">
                    Nova cobrança
                </button>
            </div>

            <div class="container-fluid border rounded">
                <div class="row header-row fw-bold border-bottom py-3">
                    <div class="col">ID</div>
                    <div class="col">Pagador</div>
                    <div class="col">Valor</div>
                    <div class="col">Vence em</div>
                    <div class="col">Status</div>
                    <div class="col">Tipo</div>
                    <div class="col">Ações</div>
                </div>
                <g:if test="${paymentList.isEmpty()}">
                    <div class="py-3 text-center">
                        <g:if test="${params.deleted}">
                            Você não possui cobranças excluídas
                        </g:if>
                        <g:else>
                            Você não possui cobranças ativas
                        </g:else>
                    </div>
                </g:if>
                <g:else>
                    <g:each var="payment" in="${paymentList}">
                        <div class="row data-row">
                            <div class="col">
                                <span title="${payment.publicId}">${payment.publicId.substring(0, 8)}</span>
                            </div>
                            <div class="col">${payment.payer.name}</div>
                            <div class="col">${payment.value}</div>
                            <div class="col">${payment.dueDate}</div>
                            <div class="col">${payment.status}</div>
                            <div class="col">${payment.type}</div>
                            <div class="col actions-col">
                                <div class="float-end">
                                    <g:if test="${payment.deleted}">
                                        <button type="button" data-id="${payment.id}" data-due-date="${dateFieldFormat([value: payment.dueDate])}" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#restorePaymentModal" title="Restaurar">
                                            <i class="bi bi-arrow-counterclockwise"></i>
                                        </button>
                                    </g:if>
                                    <g:else>
                                        <g:if test="${payment.status == PaymentStatus.PAID}">
                                            <g:link action="show" controller="receipt" target="_blank" id="${payment.publicId}" class="btn btn-light" title="Comprovante">
                                                <i class="bi bi-receipt"></i>
                                            </g:link>
                                        </g:if>
                                        <g:else>
                                            <g:if test="${payment.status == PaymentStatus.PENDING}">
                                                <button type="button" data-id="${payment.id}" class="deposit-button btn btn-light" title="Confirmar pagamento">
                                                    <i class="bi bi-check-lg"></i>
                                                </button>
                                            </g:if>
                                            <g:link action="show" id="${payment.id}" class="btn btn-light" title="Editar">
                                                <i class="bi bi-pencil-fill"></i>
                                            </g:link>
                                            <button class="btn btn-light delete-button" data-id="${payment.id}" title="Excluir">
                                                <i class="bi bi-trash-fill"></i>
                                            </button>
                                        </g:else>
                                    </g:else>
                                </div>
                            </div>
                        </div>
                    </g:each>
                </g:else>
            </div>
        </div>

        <div class="modal fade" id="createPaymentModal" tabindex="-1" aria-labelledby="createPaymentModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createPaymentModalLabel">Nova cobrança</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" id="createPaymentForm">
                            <div class="mb-3 form-floating">
                                <g:field type="number" min="1" name="payer" required="true" class="form-control" placeholder="Pagador"/>
                                <label for="payer">Pagador</label>
                            </div>
                            <div class="mb-3 form-floating">
                                <g:field type="number" name="value" min="0.01" step="0.01" required="true" class="form-control" placeholder="Valor"/>
                                <label for="value">Valor</label>
                            </div>
                            <div class="mb-3 form-floating">
                                <g:select from="${PaymentType}" name="type" required="true" class="form-select" placeholder="Tipo"/>
                                <label for="type">Tipo</label>
                            </div>
                            <div class="mb-3 form-floating">
                                <g:field type="date" name="dueDate" required="true" class="form-control" placeholder="Vence em"/>
                                <label for="dueDate">Vence em</label>
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
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" id="restorePaymentForm">
                            <g:hiddenField name="id"/>
                            <div class="mb-3 form-floating">
                                <g:field type="date" name="dueDate" required="true" class="form-control" placeholder="Vence em"/>
                                <label for="dueDate">Vence em</label>
                                <div class="form-text">Informe uma nova data de vencimento</div>
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
