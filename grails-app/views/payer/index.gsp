<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>

    <title>Index</title>

    <asset:stylesheet src="grid-table.css"/>
    <asset:stylesheet src="modal.css"/>

    <asset:javascript src="postalCodeUtils.js"/>
    <asset:javascript src="payer/payer-index.js"/>
</head>

<body>
    <div class="container pt-5 pb-3">
        <h1 class="mb-4">Pagadores</h1>

        <div class="mb-4">
            <div class="btn-group border">
                <g:link action="index" class="btn btn-light border-end">
                    Ativos
                </g:link>

                <g:link action="index" params="[deleted: true]" class="btn btn-light border-start">
                    Excluídos
                </g:link>
            </div>
            <button type="button" class="btn btn-success float-end" data-bs-toggle="modal" data-bs-target="#newPayerModal">
                Novo Pagador
            </button>
        </div>

        <div class="container-fluid border rounded">
            <div class="row header-row fw-bold border-bottom py-3">
                <div class="col">ID</div>
                <div class="col">Nome</div>
                <div class="col">Email</div>
                <div class="col">CPF/CNPJ</div>
                <div class="col">Celular</div>
                <div class="col">Criado em</div>
                <div class="col">Ações</div>
            </div>
            <g:if test="${payerList.isEmpty()}">
                <div class="py-3 text-center">
                    <g:if test="${params.deleted}">
                        Você não possui pagadores excluídos
                    </g:if>
                    <g:else>
                        Você não possui pagadores ativos
                    </g:else>
                </div>
            </g:if>
            <g:else>
                <g:each var="payer" in="${payerList}">
                    <div class="row data-row">
                        <div class="col">${payer.id}</div>
                        <div class="col">${payer.name}</div>
                        <div class="col text-break">${payer.email}</div>
                        <div class="col">${cpfCnpjFormat([value: payer.cpfCnpj])}</div>
                        <div class="col">${phoneNumberFormat([value: payer.phoneNumber])}</div>
                        <div class="col">${dateFormat([value: payer.createdAt])}</div>
                        <div class="col actions-col">
                            <div class="float-end">
                                <g:if test="${payer.deleted}">
                                    <button type="button" data-id="${payer.id}" class="restore-button btn btn-light" title="Restaurar">
                                        <i class="bi bi-arrow-counterclockwise"></i>
                                    </button>
                                </g:if>
                                <g:else>
                                    <g:link action="show" id="${payer.id}" class="btn btn-light" title="Editar">
                                        <i class="bi bi-pencil-fill"></i>
                                    </g:link>
                                    <button data-id="${payer.id}" class="btn btn-light delete-button" title="Excluir">
                                        <i class="bi bi-trash-fill"></i>
                                    </button>
                                </g:else>
                            </div>
                        </div>
                    </div>
                </g:each>
            </g:else>
        </div>
    </div>
    <div class="modal fade" id="newPayerModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="newPayerModalLabel">Adicionar pagador</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
                </div>
                <div class="modal-body">
                    <form id="createPayerForm" method="POST">
                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="name" required="true"/>
                                <label for="name">Nome</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="email" name="email" required="true"/>
                                <label for="email">Email</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="cpfCnpj" maxLength="14" required="true"/>
                                <label for="cpfCnpj">Cpf/Cnpj</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="phoneNumber" maxLength="13" required="true"/>
                                <label for="phoneNumber">Celular</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="postalCode" required="true"/>
                                <label for="postalCode">CEP</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="streetName" required="true" disabled="true"/>
                                <label for="streetName">Rua</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="buildingNumber" required="true"/>
                                <label for="buildingNumber">Número</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="complement"/>
                                <label for="complement">Complemento</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="state" required="true" disabled="true"/>
                                <label  for="state">Estado</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="city" required="true" disabled="true"/>
                                <label for="city">Cidade</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <g:field class="form-control" type="text" name="neighborhood" required="true" disabled="true"/>
                                <label for="neighborhood">Bairro</label>
                            </div>
                            <div class="float-end">
                                <button type="submit" class="btn btn-success">Salvar</button>
                            </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
