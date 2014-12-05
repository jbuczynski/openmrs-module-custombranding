	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>

    <script src="http://code.jquery.com/jquery-latest.min.js"
            type="text/javascript"></script>

    <openmrs:htmlInclude file="/moduleResources/custombranding/custombranding.js" />

    <div class="boxHeader">
     	<b><spring:message code="custombranding.replace.header" /></b>
    </div>

    <form id="resetChanges">
         <input type="hidden" name="action" value="reset">
         <input type="submit" value="reset" />
     </form>

    <b class="boxHeader"><openmrs:message code="custombranding.boxheader.instruction" /></b>
    <div class="box">
    	<ul>
    		<li><i><openmrs:message code="Module.help.load"/></i></li>
    		<c:if test="${fn:length(moduleList) > 0}">
    			<li><i><openmrs:message code="Module.help.unload"/></i></li>
    			<li><i><openmrs:message code="Module.help.startStop"/></i></li>
    			<li><i><openmrs:message code="Module.help.update"/></i></li>
    		</c:if>
    		<li><i><openmrs:message htmlEscape="false" code="Module.help.findMore"/></i></li>
    	</ul>
    </div>

	<%@ include file="/WEB-INF/template/footer.jsp"%>