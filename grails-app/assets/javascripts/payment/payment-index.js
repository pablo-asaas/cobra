function handleCreateSubmit(event) {
    event.preventDefault()
    AjaxRequest.onFormSubmit("POST", "/payment/save", $(event.target))
}

function handleRestoreSubmit(event) {
    event.preventDefault()
    AjaxRequest.onFormSubmit("POST", "/payment/restore", $(event.target))
}

function handleDeleteClick(event) {
    if (!confirm("Deseja realmente excluir esta cobrança?")) {
        return
    }

    AjaxRequest.onButtonClick("DELETE", "/payment/delete", {
        id: $(event.delegateTarget).data("id")
    })
}

function handleDepositClick(event){
    if(!confirm("Deseja realmente confirmar o pagamento?")) {
        return
    }

    AjaxRequest.onButtonClick("POST", "/payment/confirmPayment", {
        id: $(event.delegateTarget).data("id"),
        deposit: true
    })
}

function loadPayerSelectOptions(selectElement) {
    $.ajax({
        async: false,
        type: "GET",
        url: "/payer/ajaxPayerList",
        dataType: "json",
        success: (data) => {
            const elementList = []

            for (const payer of data) {
                const payerElement = $("<option>")

                payerElement.val(payer.id)
                payerElement.text(payer.name)

                elementList.push(payerElement)
            }

            selectElement.append(elementList)
        }
    })
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

    const payerSelect = $("#payer")

    loadPayerSelectOptions(payerSelect)

    payerSelect.select2({
        theme: "bootstrap-5",
        dropdownParent: $("#createPaymentModal"),
        selectOnClose: true,
        language: {
            noResults: () => "Nenhum pagador encontrado"
        },
        placeholder: "Selecione um pagador"
    })
})
