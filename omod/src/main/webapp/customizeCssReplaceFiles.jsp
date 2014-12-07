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
    <div class="box">
        <table  style="display: block;  margin-left: auto; margin-right: auto" >
            <tr>
                 <td>
                    Upload your css file: <input id="uploadCssFile" size="50" type="file" name="cssFile" >
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
    </div>
    <div class="box">
         <input type="submit" value="Replace" onclick="readSingleFile('uploadCssFile')" style="display: block;  margin-left: auto; margin-right: auto" />
    </div>

	<%@ include file="/WEB-INF/template/footer.jsp"%>