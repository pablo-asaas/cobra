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
            <g:if test="${alertNotificationList.isEmpty()}">
                <div class="card card-body">
                    <p class="card-text text-center">Você não possui notificações</p>
                </div>
            </g:if>
            <g:else>
                <g:each var="alertNotification" in="${alertNotificationList}">
                    <g:set var="alertNotificationTypeCode" value="AlertNotificationType.${alertNotification.type}"/>
                    <div class="card card-body p-0 mb-3">
                        <g:link action="show" id="${alertNotification.publicId}" class="text-decoration-none p-3 d-block ${!alertNotification.unread ? 'read' : ''}" style="color: inherit">
                            <p class="fw-bold mb-1">
                                <g:message code="${alertNotificationTypeCode}.title"/>
                            </p>
                            <p class="mb-1">
                                <g:message code="${alertNotificationTypeCode}.content" args="[alertNotification.payment.payer.name, currencyFormat([value: alertNotification.payment.value])]"/>
                            </p>
                            <p class="text-muted mb-0">
                                <g:datetimeFormat value="${alertNotification.createdAt}"/>
                            </p>
                        </g:link>
                    </div>
                </g:each>
            </g:else>
        </div>
    </body>
</html>