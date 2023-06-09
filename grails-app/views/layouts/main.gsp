<!doctype html>
<html lang="en" class="no-js">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>

        <title>
            <g:layoutTitle default="..."/> - Cobra
        </title>

        <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>

        <asset:stylesheet src="global.css"/>

        <g:layoutHead/>
    </head>

    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand mb-0 h1" href="/">Cobra</a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Alternar navegação">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="/payer">Pagadores</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/payment">Cobranças</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/user">Usuários</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <g:layoutBody/>
    </body>
</html>
