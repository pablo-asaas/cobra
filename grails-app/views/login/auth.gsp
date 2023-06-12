<html>
<head>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <title>Login</title>
    <asset:stylesheet src="form.css"/>
    <asset:stylesheet src="navigation.css"/>
</head>

<body>
<div class="container mt-5 mx-auto" style="max-width: 576px">
    <h1 class="mb-4">Fazer login</h1>

    <g:if test='${flash.message}'>
        <div class="login_message">${flash.message}</div>
    </g:if>

    <div class="container-fluid border rounded py-3">
        <form action="login/authenticate"  method="POST" autocomplete="off">
                <div  class="mb-3 form-floating">
                    <g:field class="form-control" type="text" name="${usernameParameter ?: 'username'}" id="username" autocapitalize="none"/>
                    <label class="col-form-label" for="username"><g:message code='springSecurity.login.username.label'/>:</label>
                </div>

                <div  class="mb-3 form-floating">
                    <g:field class="form-control" type="password" name="${passwordParameter ?: 'password'}" id="password"/>
                    <label class="col-form-label" for="password"><g:message code='springSecurity.login.password.label'/>:</label>
                </div>

                <div class="d-flex w-100 justify-content-between">
                    <g:link controller="register" action="index" class="btn btn-light">
                        Criar conta
                    </g:link>
                    <button type="submit" class="btn btn-success">Login</button>
                </div>
        </form>
    </div>
</div>
</body>
</html>
