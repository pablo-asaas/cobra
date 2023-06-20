<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>
</head>
<body>
    <div class="container pt-5 pb-3">
        <div class="row">
            <div class="col-md-5 mt-4">
                <h1 class="mb-5 mt-3" style="line-height: 2em;text-align: center;">Bem-vindo ao Cobra!</h1>

                <h3 style="line-height: 2em;text-align: center;">
                    A vida sempre Cobra,
                    e nós te ajudamos no gerenciamento das suas cobranças de forma rápida, simples e segura.
                </h3>
                <div class="mt-4 d-grid gap-2 col-6 mx-auto">
                    <g:link controller="login" class="btn btn-success btn-lg mx-2">
                        Já tenho uma conta
                    </g:link>
                    <g:link controller="register" class="btn btn-success btn-lg mx-2">
                        Criar uma conta
                    </g:link>
                </div>
            </div>
            <div class="col-md-7">
                <g:img file="home.jpg" width="100%" alt="Imagem Homepage"/>
                <p style="text-align: center">
                    <a href="https://www.freepik.com/free-vector/online-payment-isometric-design-concept-illustration_13805798.htm#query=isometric%20finance&position=4&from_view=search&track=ais">
                    Image by macrovector</a> on Freepik
                </p>
            </div>
        </div>
    </div>
</body>
</html>
