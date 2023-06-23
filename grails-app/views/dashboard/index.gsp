<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Dashboard</title>
    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
    </script>
</head>

<body>
    <div class="container pt-5 pb-3">
        <div class="row">
            <div class="col-sm-3 mb-3">
                <div class="card border-success">
                    <div class="card-body">
                        <h5 class="card-title text-success">Faturamento mensal</h5>
                        <p class="card-text"><g:currencyFormat value="${cardInfo.monthlyBilling}"/></p>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 mb-3">
                <div class="card  border-warning">
                    <div class="card-body">
                        <h5 class="card-title text-warning">Cobranças pendentes</h5>
                        <p class="card-text">${cardInfo.pendingPaymentsAmount}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 mb-3">
                <div class="card border-danger">
                    <div class="card-body">
                        <h5 class="card-title text-danger">Cobranças vencidas</h5>
                        <p class="card-text">${cardInfo.overduePaymentsAmount}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 mb-3">
                <div class="card  border-success">
                    <div class="card-body">
                        <h5 class="card-title text-success">Total a receber</h5>
                        <p class="card-text"><g:currencyFormat value="${cardInfo.totalReceivable}"/></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <div class="card border">
                    <div class="card-body">
                        <h5 class="card-title">Gráfico de Cobranças</h5>
                        <canvas data-graph="${doughnutGraphInfo}" id="lastThreeMonthBilling" style="width:100%;max-width:700px"></canvas>
                    </div>
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <div class="card border">
                    <div class="card-body">
                        <h5 class="card-title">Tipos de pagamento utilizado</h5>
                        <canvas data-graph="${doughnutGraphInfo}" id="paymentTypeUsed" style="width:100%;max-width:700px"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <g:javascript>
        const doughnutGraphData = $.parseJSON($('#paymentTypeUsed').attr('data-graph'))
        const doughnutXValues = ["Pix", "Cartão de crédito", "Cartão de debito", "Boleto"]
        const doughnutYValues = [doughnutGraphData.pix, doughnutGraphData.credit_card, doughnutGraphData.debit_card, doughnutGraphData.payment_slip]
        const doughnutBarColors = [
            "#5873FC",
            "#5B97E5",
            "#58CCFC",
            "#55EFF2"
        ]
        buildChart("paymentTypeUsed", "doughnut", doughnutXValues, doughnutYValues, doughnutBarColors, true)

        const barGraphData = $.parseJSON($('#lastThreeMonthBilling').attr('data-graph'))
        const barXValues = lastThreeMonths()
        const barYValues = [123, 456, 432]
        const barBarColors = [
            "#5873FC",
            "#5B97E5",
            "#58CCFC",
            "#55EFF2"
        ]
        buildChart("lastThreeMonthBilling", "bar", barXValues, barYValues, barBarColors, false)

        function buildChart(graphId, graphType, xValues, yValues, barColors, display) {
            new Chart(graphId, {
                type: graphType,
                data: {
                    labels: xValues,
                    datasets: [{
                        backgroundColor: barColors,
                        data: yValues
                    }]
                },
                options: {
                    legend: {display: display}
                }

            })
        }
        function lastThreeMonths() {
            const formatter = new Intl.DateTimeFormat('pt-BR', { month: 'short' })
            const now = new Date()
            const current = formatter.format(now)
            const last = formatter.format(subtractMonths(now, 1))
            const lastButOne = formatter.format(subtractMonths(now, 2))
            return [lastButOne, last, current]
        }

        function subtractMonths(date, months) {
            const dateCopy = new Date(date)
            dateCopy.setMonth(dateCopy.getMonth() - months)
            return dateCopy
        }
    </g:javascript>
</body>
</html>
