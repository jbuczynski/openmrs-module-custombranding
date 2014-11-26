	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>

    div class="boxHeader">
        <span style="float: right">
            <a href="#" id="showRetired" onClick="return toggleRowVisibilityForClass('formTable', 'voided');"><spring:message code="general.toggle.retired"/></a>
        </span>

    </div>
    ${cssFileNames}
    <form:select path="visitTypesToClose" multiple="true" items="${visitTypes}" itemLabel="name" />
	<select id="cssFilesList" multiple="multiple">
        <c:forEach var="cssFile" items="${cssFileNames}">
                 <option value="${cssFile}" title="${cssFileMap[cssFile]}">${cssFile}</option>
        </c:forEach>


      <option value="saab">Saab</option>
      <option value="mercedes">Mercedes</option>
      <option value="audi">Audi</option>
    </select>



	<%@ include file="/WEB-INF/template/footer.jsp"%>