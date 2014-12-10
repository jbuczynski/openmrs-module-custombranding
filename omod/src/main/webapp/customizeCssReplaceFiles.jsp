	<%@ include file="/WEB-INF/template/include.jsp"%>

    <%@ include file="/WEB-INF/template/header.jsp"%>

    <%@ include file="localHeader.jsp" %>

    <openmrs:htmlInclude file="/moduleResources/custombranding/custombranding.js" />

    <div class="boxHeader">
         <span style="float: right">
             <a href="#" id="toogleRecursion" onClick="toogleRecursiveSearchingAndList('cssFilesList')"><spring:message code="custombranding.boxheader.toogle"/></a>
         </span>
     	<b><spring:message code="custombranding.replace.header" /></b>
     </div>
    <div class="box" style="display: block;  margin-left: auto; margin-right: auto">
        <table>
            <tr>
                 <td>
                    Upload your css file: <input id="uploadCssFile" size="50" type="file" accept=".css" name="cssFile" >
                </td>
                <td>
                     <select id="cssFilesList" size="8"  onchange="setFileProps()">
                        <c:forEach var="item" items="${cssFileMap}">
                                 <option value="${item.value}" title="${item.key} ">${item.value}</option>
                        </c:forEach>
                    </select>
                 </td>
            </tr>
        </table>
        <div ><p id="messageBox" /></div>
    </div>
     <br>
    <div class="box">
         <input type="submit" value="Replace" onclick="readSingleFile('uploadCssFile')" style="display: block;  margin-left: auto; margin-right: auto" />
    </div>

	<%@ include file="/WEB-INF/template/footer.jsp"%>