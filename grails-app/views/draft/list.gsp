<%@ page import="draft.Draft" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="${message(code: 'draft.label', default: 'Draft')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>

  <jqgrid:cssResources />
  <jq:resources />
  <jqui:resources />
  <jqgrid:resources />

  <script type="text/javascript">
    function onDblClickRow(id) {
        if (id) {
            var url = "${createLink(action: 'show')}";
            $(location).attr('href', url + "/" + id);
        }
    }

    $(document).ready(function () {
        <jqgrid:grid
            id="author"
            listUrl="${createLink(action: 'listJSON')}"
            editUrl="${createLink(action: 'editJSON')}"
            colNames="'Firstname', 'Lastname', 'id'"
            colModel="{name:'name', editable: true},
                      {name:'address', editable: true},
                      {name:'id', hidden: true}"
            sortName="name"
            caption="${entityName} List"
            height="300"
            filterToolBar="true"
            viewRecords="true"
            onDblClickRow="onDblClickRow">

            <jqgrid:deleteButton
                id="author"
                url="${createLink(action: 'editJSON')}" />

            <jqgrid:editButton
                id="author"
                url="${createLink(action: 'edit')}" />

            <jqgrid:addButton
                id="author"
                url="${createLink(action: 'create')}" />

            <jqgrid:searchButton id="author" />

        </jqgrid:grid>
    });
    </script>

</head>

<body>
<div class="nav">
  <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
  </span>
  <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                             args="[entityName]"/></g:link></span>
</div>

<div class="body">
  <h1><g:message code="default.list.label" args="[entityName]"/></h1>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>

  <jqgrid:wrapper id="author" />

</div>
</body>
</html>
