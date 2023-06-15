<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Notificações</title>
        <style>
            .read { opacity: 0.5; }
        </style>
    </head>

    <body>
        <div class="container pt-5 pb-3" style="max-width: 576px">
            <h1 class="mb-4">Notificações</h1>
            <g:if test="${notificationList.isEmpty()}">
                <div class="card card-body">
                    <p class="card-text text-center">Você não possui notificações</p>
                </div>
            </g:if>
            <g:else>
                <g:each var="notification" in="${notificationList}">
                    <g:set var="notificationTypeCode" value="NotificationType.${notification.type}"/>
                    <div class="card card-body p-0 mb-3">
                        <g:link action="show" id="${notification.publicId}" class="text-decoration-none p-3 d-block ${!notification.unread ? 'read' : ''}" style="color: inherit">
                            <p class="fw-bold mb-1">
                                <g:message code="${notificationTypeCode}.title"/>
                            </p>
                            <p class="mb-1">
                                <g:message code="${notificationTypeCode}.content" args="[notification.payment.payer.name, currencyFormat([value: notification.payment.value])]"/>
                            </p>
                            <p class="text-muted mb-0">
                                <g:datetimeFormat value="${notification.createdAt}"/>
                            </p>
                        </g:link>
                    </div>
                </g:each>
            </g:else>
        </div>
    </body>
</html>