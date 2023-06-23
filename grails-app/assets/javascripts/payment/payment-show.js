function handleUpdateSubmit(event) {
    event.preventDefault()
    AjaxRequest.onFormSubmit("PATCH", "/payment/update", $(event.target), "/payment")
}

$(document).ready(() => {
    $("#updatePaymentForm").on("submit", handleUpdateSubmit)
})
