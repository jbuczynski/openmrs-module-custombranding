	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>

    <script src="http://code.jquery.com/jquery-latest.min.js"
            type="text/javascript"></script>

    <openmrs:htmlInclude file="/moduleResources/custombranding/custombranding.js" />

    <div class="boxHeader">
         <span style="float: right">
             <a href="#" id="toogleRecursion" onClick="toogleRecursiveSearchingAndList('cssFilesList')"><spring:message code="custombranding.boxheader.toogle"/></a>
         </span>
     	<b><spring:message code="custombranding.replace.header" /></b>
     </div>



    <fieldset>
        <div>

            Upload your css file: <input id="uploadCssFile" size="50" type="file" name="cssFile"/>
         <input type="submit" value="Replace" onclick="dbRequest('replaceCssFile')"/>
       </div>


    </fieldset>
    <select id="cssFilesList" size="18" >
        <c:forEach var="item" items="${cssFileMap}">
                 <option value="${item.value}" title="${item.key} ">${item.value}</option>
        </c:forEach>
    </select>
	<%@ include file="/WEB-INF/template/footer.jsp"%>