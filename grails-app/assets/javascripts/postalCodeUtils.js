function searchAddress(event) {
    const postalCode = $(event.target).val()
    const validPostalCode = /^([0-9]{8})$/

    if (!validPostalCode.test(postalCode)) {
        alert("CEP inválido!")
        return
    }

    $.getJSON("https://viacep.com.br/ws/"+ postalCode +"/json/?callback=?", function(dados) {
        if (!("erro" in dados)) {
            $(event.target.form.streetName).val(dados.logradouro).prop( "disabled", false )
            $(event.target.form.neighborhood).val(dados.bairro).prop( "disabled", false )
            $(event.target.form.city).val(dados.localidade).prop( "disabled", false )
            $(event.target.form.state).val(dados.uf).prop( "disabled", false )
        }
        else {
            alert("CEP não encontrado.")
        }
    })
}