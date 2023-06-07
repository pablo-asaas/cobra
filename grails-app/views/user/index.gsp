<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Usuários</title>
</head>

<body>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome de usuário</th>
        </tr>
        </thead>
        <tbody>
        <g:each var="user" in="${userList}">
            <tr>
                <td>${user.id}</td>
                <td>
                    <g:if test="${user.username == currentUser}">
                        ${user.username} (Atual)
                    </g:if>
                    <g:else>
                        ${user.username}
                    </g:else>
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>
</body>
</html>
