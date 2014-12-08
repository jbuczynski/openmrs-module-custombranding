	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>

    <script src="http://code.jquery.com/jquery-latest.min.js"
            type="text/javascript"></script>

    <openmrs:htmlInclude file="/moduleResources/custombranding/custombranding.js" />

    <div class="boxHeader">
     	<b><spring:message code="custombranding.link.admin" /></b>

    <form id="webappFolderPathForm"
         method="post" enctype="text">
       <input size="50" type="file" name="largeLogoFile"/><br/>
       <input type="submit" value="Replace" />
   </form>
    </div>

    <b class="boxHeader"><openmrs:message code="custombranding.boxheader.instruction" /></b>
    <div class="box">
    	<ul>
    			<i><openmrs:message code="custombranding.instruction.text"/></i><
    		<li><i><openmrs:message htmlEscape="false" code="Module.help.findMore"/></i></li>
    	</ul>
    </div>

	<%@ include file="/WEB-INF/template/footer.jsp"%>