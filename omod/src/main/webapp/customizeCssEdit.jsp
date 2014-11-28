	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>
<script src="http://code.jquery.com/jquery-latest.min.js"
        type="text/javascript"></script>
<script>
  function ajaxRequest() {

        var text = $("#cssFilesList option:selected").text();
        $.ajax({
                type: 'GET',
                url: "/openmrs/module/custombranding/CssContent.form?name=" + text,
                dataType: 'text',
                async: true,
                success: function(response) {
                      $('#contentBox').text(response);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.status + " " + jqXHR.responseText);
                }
        });
}
</script>
 <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>

    <div class="boxHeader">
        <span style="float: right">
            <a href="#" id="showRetired" onClick="return toggleRowVisibilityForClass('formTable', 'voided');"><spring:message code="custombranding.boxheader.toogle"/></a>
        </span>
    	<b><spring:message code="custombranding.edit.header" /></b>
    </div>

	<textarea id="contentBox" name="Css file content" rows="20" cols="90" type="_moz">Content ...</textarea>


    <select id="cssFilesList" size="18" items="${cssFilesList}" onchange="ajaxRequest()">
        <c:forEach var="cssFile" items="${cssFileNames}">
                 <option value="${cssFile}" title="${cssFileMap[cssFile]}">${cssFile}</option>
        </c:forEach>

    </select>
    <br>

     <div >
     <table>
         <tr>
             <td>
                 <form id="save" action="/openmrs/module/custombranding/dbRequest.form" method="POST">
                     <input type="hidden" name="action" value="updateCssFile">
                     <input type="submit" value="save"  name="updateCssFile"/>
                 </form>
             </td>
             <td>
                  <form id="validate">
                     <input type="hidden" name="action" value="validate">
                     <input type="submit" value="validate" />
                 </form>
             </td>
         </tr>
     </table>


     </div>


	<%@ include file="/WEB-INF/template/footer.jsp"%>