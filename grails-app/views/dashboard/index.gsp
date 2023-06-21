<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Dashboard</title>
</head>

<body>
    <div class="container pt-5 pb-3">
        <div class="row">
            <div class="col-sm-3 mb-3">
                <div class="card border-success">
                    <div class="card-body">
                        <h5 class="card-title text-success">Faturamento mensal</h5>
                        <p class="card-text"><g:currencyFormat value="${monthlyBilling}"/></p>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 mb-3">
                <div class="card  border-warning">
                    <div class="card-body">
                        <h5 class="card-title text-warning">Cobranças pendentes</h5>
                        <p class="card-text">${pendingPaymentsAmount}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 mb-3">
                <div class="card border-danger">
                    <div class="card-body">
                        <h5 class="card-title text-danger">Cobranças vencidas</h5>
                        <p class="card-text">${overduePaymentsAmount}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 mb-3">
                <div class="card  border-success">
                    <div class="card-body">
                        <h5 class="card-title text-success">Total a receber</h5>
                        <p class="card-text"><g:currencyFormat value="${totalReceivable}"/></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <div class="card border">
                    <div class="card-body">
                        <h5 class="card-title">Gráfico de Cobranças</h5>
                        <p class="card-text">Não há nada</p>
                    </div>
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <div class="card border">
                    <div class="card-body">
                        <h5 class="card-title">Gráfico de Faturamento</h5>
                        <p class="card-text">Não há nada</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
