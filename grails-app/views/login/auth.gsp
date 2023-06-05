<html>
<head>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <title>Login</title>
</head>

<body>
<div id="login">
    <div class="inner">
        <div class="fheader"><g:message code='springSecurity.login.header'/></div>

        <g:if test='${flash.message}'>
            <div class="login_message">${flash.message}</div>
        </g:if>

        <g:form url="login/authenticate"  method="POST" autocomplete="off">
            <fieldset class="form">
                <label class="col-form-label" for="username"><g:message code='springSecurity.login.username.label'/>:</label>
                <g:field class="form-control" type="text" name="${usernameParameter ?: 'username'}" id="username" autocapitalize="none"/>

                <label class="col-form-label" for="password"><g:message code='springSecurity.login.password.label'/>:</label>
                <g:field class="form-control" type="password" name="${passwordParameter ?: 'password'}" id="password"/>
            </fieldset>
            <fieldset>
                <g:submitButton class="btn btn-primary" name="submit" value="${message(code: 'springSecurity.login.button')}"/>
            </fieldset>
        </g:form>
    </div>
</div>
</body>
</html>
