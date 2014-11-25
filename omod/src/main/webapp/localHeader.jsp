<ul id="menu">
	<li class="first">
		<a href="${pageContext.request.contextPath}/admin"><spring:message code="admin.title.short"/></a>
	</li>
	<li <c:if test='<%= request.getRequestURI().contains("htmlForms") %>'>class="active"</c:if>>
		<a href="${pageContext.request.contextPath}/module/custombranding/customizeCssEdit.form">
			<spring:message code="custombranding.menu.edit"/>
		</a>
	</li>
	<li <c:if test='<%= request.getRequestURI().contains("htmlFormFromFile") %>'>class="active"</c:if>>
		<a href="${pageContext.request.contextPath}/module/custombranding/customizeCssReplaceFiles.form">
			<spring:message code="custombranding.menu.replace"/>
		</a>
	</li>
</ul>