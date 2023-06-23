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
        <asset:stylesheet src="navigation"/>
        <asset:stylesheet src="notification-dropdown"/>

        <g:layoutHead/>

        <sec:ifLoggedIn>
            <g:javascript>
                $.ajax({
                    type: "GET",
                    url: "/alertNotification/navbarTrayLatestNotifications",
                    dataType: "json",
                    success: (data) => {
                        if (data.length < 1) {
                            return
                        }

                        const dropdown = $("#notificationDropdown")
                        const dropdownContent = dropdown.find(".dropdown-menu-content")

                        dropdown.find("a[role=button] i").append('<span class="position-absolute top-0 start-100 translate-middle p-1 bg-success rounded-circle"><span class="visually-hidden">Novas notificações</span></span>')

                        dropdownContent.html("")

                        for (const alertNotification of data) {
                            dropdownContent.append('<div class="border-bottom notification"><a href="/alertNotification/show/' + alertNotification.publicId + '" class="text-decoration-none p-3 d-block"><p class="fw-bold mb-1">' + alertNotification.title + '</p><p class="mb-1">' + alertNotification.content + '</p><p class="text-muted mb-0">' + alertNotification.date + '</p></a></div>')
                        }
                    }
                })
            </g:javascript>
        </sec:ifLoggedIn>
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
                        <sec:ifLoggedIn>
                            <li class="nav-item">
                                <a class="nav-link" href="/payer">Pagadores</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/payment">Cobranças</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/user">Usuários</a>
                            </li>
                        </sec:ifLoggedIn>
                    </ul>
                    <ul class="navbar-nav ms-md-auto">
                        <sec:ifLoggedIn>
                            <li class="nav-item">
                                <g:link class="nav-link" controller="customer" action="show">Editar dados</g:link>
                            </li>
                            <li class="nav-item">
                                <div id="notificationDropdown" class="notification-dropdown dropdown">
                                    <a role="button" class="nav-link text-center" style="width: 40px" type="button" data-bs-toggle="dropdown" data-bs-auto-close="outside" aria-expanded="false">
                                        <i class="bi bi-bell-fill" style="position: relative"></i>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-end py-0">
                                        <div class="dropdown-menu-header border-bottom">
                                            <strong class="px-3">Notificações</strong>
                                        </div>
                                        <div class="dropdown-menu-content">
                                            <p class="text-center py-3 mb-0 border-bottom">Você não possui notificações não lidas</p>
                                        </div>
                                        <div class="dropdown-menu-footer">
                                            <a href="/alertNotification" class="text-decoration-none navigation-link">Ver todas</a>
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li class="nav-item">
                                <g:link class="logout-button nav-link">Sair</g:link>
                            </li>
                        </sec:ifLoggedIn>
                        <sec:ifNotLoggedIn>
                            <li class="nav-item">
                                <a class="nav-link" href="/login">Entrar</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/register">Registrar</a>
                            </li>
                        </sec:ifNotLoggedIn>
                    </ul>
                </div>
            </div>
        </nav>

        <g:layoutBody/>

    <g:javascript>
        function logout(event){
            event.preventDefault()

            $.ajax({
                type: "POST",
                url: "/logout",
                success: () => {
                    window.location = "/"
                },
                error: (error) => {
                    alert(error.responseJSON.message)
                }
            })
        }
        $(document).ready(() => {
            $(".logout-button").on("click", logout)
        })
</g:javascript>
    </body>
</html>
