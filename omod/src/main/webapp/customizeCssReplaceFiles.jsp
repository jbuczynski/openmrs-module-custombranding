	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>





    <div class="boxHeader">
         <span style="float: right">
             <a href="#" id="showRetired" onClick="return toggleRowVisibilityForClass('formTable', 'voided');"><spring:message code="general.toggle.retired"/></a>
         </span>
     	<b><spring:message code="custombranding.replace.header" /></b>
     </div>
    ${cssFileNames}


	<select id="cssFilesList" multiple="multiple" items="${cssFilesList}">
        <c:forEach var="cssFile" items="${cssFileNames}">
                 <option value="${cssFile}" title="${cssFileMap[cssFile]}">${cssFile}</option>
        </c:forEach>

    </select>

     <div class="box">
            <form id="uploadCssFile"
                  action="custombranding.form?id=orgUrl&action=upload"
                  method="post" enctype="multipart/form-data">
                <input size="50" type="text" value="${orgUrl}"  />
                <input type="submit" value="Replace" />
            </form>
       </div>

    <input size="50" type="file" name="messageFile"/>
    <form id="replace">
        <input type="submit" value="Replace" />
    </form>


	<%@ include file="/WEB-INF/template/footer.jsp"%>