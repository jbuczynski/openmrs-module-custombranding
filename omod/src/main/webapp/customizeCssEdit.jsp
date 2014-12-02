	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>
<script src="http://code.jquery.com/jquery-latest.min.js"
        type="text/javascript"></script>
<script>
  function getFileContent() {

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

                }
        });
}

	  function toogleRecursiveSearchingAndList(elementId) {

            $.ajax({
                    type: 'GET',
                    url: "/openmrs/module/custombranding/SearchCssFiles.form",
                    dataType: 'text',
                    async: true,
                    success: function(response) {
                         document.getElementById(elementId).options.length = 0;



                            jQuery.each( $.parseJSON(response), function(i, val) {
                                var x = document.getElementById(elementId);
                                var option = document.createElement("option");
                                option.text = i;
                                option.value = i;
                                option.title = val;
                                x.add(option);

                            });



                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        alert(jqXHR.status + " " + jqXHR.responseText);
                    }
            });
        }

        function dbRequest(action) {
                    if( $("#cssFilesList option:selected").text() !== '') {
                        $.ajax({
                                type: 'POST',
                                url: "/openmrs/module/custombranding/dbRequest.form",
                                dataType: 'text',
                                async: true,
                                data: {
                                        'action': action,
                                        'content':   document.getElementById('contentBox').value
                                        },
                                success: function(response) {
                                      location.reload(true);
                                },
                                error: function() {
                                    location.reload(true);
                                }
                        });
                    }
                }


</script>

    <div class="boxHeader">
        <span style="float: right">
            <a href="#" id="toogleRecursion" onClick="toogleRecursiveSearchingAndList('cssFilesList')"><spring:message code="custombranding.boxheader.toogle"/></a>
        </span>
    	<b><spring:message code="custombranding.edit.header" /></b>
    </div>

	<textarea id="contentBox" name="Css file content" rows="20" cols="90" type="_moz">Content ...</textarea>


    <select id="cssFilesList" size="18"  onchange="getFileContent()">
        <c:forEach var="cssFile" items="${cssFileNames}">
                 <option value="${cssFile}" title="${cssFileMap[cssFile]} ">${cssFile}</option>
        </c:forEach>

    </select>
    <br>

     <div >
     <table>
         <tr>
             <td>
                 <button onclick="dbRequest('updateCssFile')">Save</button>
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