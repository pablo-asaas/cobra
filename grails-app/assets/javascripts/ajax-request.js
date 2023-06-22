class AjaxRequest {

    static #defaultErrorMessage = "Ocorreu um erro desconhecido"
    static #successActionTimeout = 1250

    static onFormSubmit(type, url, target, redirectTo) {
        target.find(".alert").remove()

        $.ajax({
            type,
            url,
            data: target.serialize(),
            dataType: "json"
        })
            .done((data) => {
                target.prepend(this.#buildAlertElement(data.message, true))

                setTimeout(() => {
                    if (redirectTo) {
                        location.replace(redirectTo)
                        return
                    }

                    location.reload()
                }, this.#successActionTimeout)
            })
            .fail((error) => {
                let errorMessage = this.#defaultErrorMessage

                if (error.responseJSON) {
                    errorMessage = error.responseJSON.message
                }

                target.prepend(this.#buildAlertElement(errorMessage, false))
            })
    }

    static onButtonClick(type, url, data) {
        $.ajax({ type, url, data })
            .done((data) => location.reload())
            .fail((error) => {
                let errorMessage = this.#defaultErrorMessage

                if (error.responseJSON) {
                    errorMessage = error.responseJSON.message
                }

                $("body").append(this.#buildFloatingAlertElement(errorMessage, false))
            })
    }

    static #buildAlertElement(message, isSuccess) {
        const alertElement = $("<div></div>")

        alertElement.addClass("alert")
        alertElement.text(message)

        if (isSuccess) {
            alertElement.addClass("alert-success")
        } else {
            alertElement.addClass("alert-danger")
        }

        return alertElement
    }

    static #buildFloatingAlertElement(message, isSuccess) {
        const alertElement = this.#buildAlertElement(message, isSuccess)

        alertElement.addClass("alert-dismissible fade show floating-alert")
        alertElement.append(this.#buildCloseAlertButton())

        return alertElement
    }

    static #buildCloseAlertButton() {
        const closeButton = $("<button type='button'></button>")

        closeButton.addClass("btn-close ps-3")
        closeButton.attr("data-bs-dismiss", "alert")
        closeButton.attr("aria-label", "close")

        return closeButton
    }
}