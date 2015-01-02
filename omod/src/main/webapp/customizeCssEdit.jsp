	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>

    <openmrs:htmlInclude file="/moduleResources/custombranding/custombranding.js" />

    <div class="boxHeader">
        <span style="float: right">
            <a href="#" id="toogleRecursion" onClick="toogleRecursiveSearchingAndList('cssFilesList')"><spring:message code="custombranding.boxheader.toogle"/></a>
        </span>
    	<b><spring:message code="custombranding.edit.header" /></b>
    </div>
    <table>
        <tr>
             <td>
                <textarea id="contentBox" name="Css file content" rows="20" cols="90" type="_moz">Content ...</textarea>
            </td>
            <td>
                <select id="cssFilesList" size="18"  onchange="getFileContent()">
                    <c:forEach var="item" items="${cssFileMap}">
                             <option value="${item.value}" title="${item.key} ">${item.value}</option>
                    </c:forEach>
                </select>
             </td>
        </tr>
    </table>
    </div>
     <br>
    <div >
         <table>
             <tr>
                 <td>
                     <button onclick="dbRequest('updateCssFile' )">Save</button>
                 </td>
                 <td>
                      <form id="validate">
                         <input type="hidden" name="action" value="validate">
                         <input type="submit" value="validate" disabled="true"/>
                     </form>
                 </td>
             </tr>
         </table>
     </div>


	<%@ include file="/WEB-INF/template/footer.jsp"%>