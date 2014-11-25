	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>

    div class="boxHeader">
        <span style="float: right">
            <a href="#" id="showRetired" onClick="return toggleRowVisibilityForClass('formTable', 'voided');"><spring:message code="general.toggle.retired"/></a>
        </span>
    	<b><spring:message code="htmlformentry.manage.header" /></b>
    </div>

	<!--<textarea name="${status.expression}" rows="3" cols="40" type="_moz">${status.value}</textarea>-->
	<textarea name="Css file content" rows="3" cols="40" type="_moz">Content ...</textarea>



	<%@ include file="/WEB-INF/template/footer.jsp"%>