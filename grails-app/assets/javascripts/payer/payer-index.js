function handleCreateSubmit(event) {
    event.preventDefault()
    AjaxRequest.onFormSubmit("POST", "/payer/save", $(event.target))
}

function handleRestoreClick(event) {
    if (!confirm("Deseja realmente restaurar este pagador?")) {
        return
    }

    AjaxRequest.onButtonClick("POST", "/payer/restore", {
        id: $(event.delegateTarget).data("id")
    })
}

function handleDeleteClick(event) {
    if (!confirm("Deseja realmente excluir este pagador?")) {
        return
    }

    AjaxRequest.onButtonClick("DELETE", "/payer/delete", {
        id: $(event.delegateTarget).data("id")
    })
}

$(document).ready(() => {
    $("#createPayerForm").on("submit", handleCreateSubmit)
    $(".restore-button").on("click", handleRestoreClick)
    $(".delete-button").on("click", handleDeleteClick)
    $("#postalCode").on("blur", searchAddress)
})
