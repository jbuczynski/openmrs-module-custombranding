	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>





    <div class="boxHeader">
         <span style="float: right">
             <a href="#" id="showRetired" onClick="return toggleRowVisibilityForClass('formTable', 'voided');"><spring:message code="custombranding.boxheader.toogle"/></a>
         </span>
     	<b><spring:message code="custombranding.replace.header" /></b>
     </div>
    ${cssFileNames}




    <fieldset>

     <form id="replaceCssFileForm"
        method="post" enctype="multipart/form-data">
        Upload your css file: <input size="50" type="file" name="cssFile"/>
     <input type="submit" value="Replace" />
     </form>
    <form id="save" action="/openmrs/module/custombranding/dbRequest.form" method="POST">
          <input type="hidden" name="action" value="updateCssFile">
         <input type="submit" value="add"  name="updateCssFile"/>
     </form>

	<select id="cssFilesList" multiple="multiple" items="${cssFilesList}">
        <c:forEach var="cssFile" items="${cssFileNames}">
                 <option value="${cssFile}" title="${cssFileMap[cssFile]}">${cssFile}</option>
        </c:forEach>

    </select>
    </fieldset>

	<%@ include file="/WEB-INF/template/footer.jsp"%>