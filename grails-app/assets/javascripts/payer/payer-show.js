function handleUpdateSubmit(event) {
    event.preventDefault()
    AjaxRequest.onFormSubmit("PUT", "/payer/update", $(event.target), "/payer")
}

$(document).ready(() => {
    $("#updatePayerForm").on("submit", handleUpdateSubmit)
})
