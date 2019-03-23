<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/letters.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/dp/jquery.ui.datepicker-ru.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#letter_date").datepicker();
	});
</script>
<title><s:message code="appname" /></title>
</head>
<body>
	<div class="header">
		<h1>
			<s:message code="appname" />
		</h1>
	</div>
	
	<div class="lform">
		<sf:form modelAttribute="letterForm" enctype="multipart/form-data"
			method="POST">
			<sf:errors path="*" cssClass="lformerror" element="div" />
			<c:if test="${not empty errors}">
				<div class="lformerror">${errors}</div>
			</c:if>

			<table>
				<tr>
					<td><s:message code="letter.number" /> *</td>
					<td><sf:input path="docNumber" /></td>
					<td><sf:errors path="docNumber" cssClass="lerror" /></td>
				</tr>
				<tr>
					<td><s:message code="letter.date" /> *</td>
					<td><sf:input id="letter_date" path="creationDate" /></td>
					<td><sf:errors path="creationDate" cssClass="lerror" /></td>
				</tr>
				<tr>
					<td><s:message code="letter.theme" /> *</td>
					<td><sf:input path="title" /></td>
					<td><sf:errors path="title" cssClass="lerror" /></td>
				</tr>
				<tr>
					<td><s:message code="letter.published" /></td>
					<td><sf:checkbox path="published" /></td>
				</tr>
				<tr>
					<td><s:message code="letter.file" /> *</td>
					<td><sf:input type="file" path="attachedFile" /></td>
					<td><sf:errors path="attachedFile" cssClass="lerror" /></td>
				</tr>
				<tr>
					<td><s:message code="letter.notation" /></td>
					<td><sf:textarea path="textContent" rows="12" cols="23" /></td>
					<td><sf:errors path="textContent" cssClass="lerror" /></td>
				</tr>
			</table>

			<input type="submit" value="<s:message code="addletter" />" />
		</sf:form>

	</div>

	<div class="splitter"></div>

	<table class="ltable">
		<thead class="ltablehead">
			<tr>
				<td><s:message code="letter.number" /></td>
				<td><s:message code="letter.date" /></td>
				<td><s:message code="letter.theme" /></td>
				<td><s:message code="letter.published" /></td>
				<td><s:message code="letter.file" /></td>
				<td style="width: 500px;"><s:message code="letter.notation" /></td>
			</tr>
		</thead>
		<c:forEach items="${letters}" var="letter">
			<tr class="ltablerow">
				<td>${letter.docNumber}</td>
				<td><s:eval
						expression="@dateUtilBean.formatDate(letter.creationDate)" /></td>
				<td>${letter.title}</td>
				<td><c:choose>
						<c:when test="${letter.published}">
							<s:message code="yes" />
						</c:when>
						<c:otherwise>
							<s:message code="no" />
							,
							<s:url value="publish/{lid}" var="pub_url">
								<s:param name="lid" value="${letter.id}" />
							</s:url>
							<a href="${pub_url}"><s:message code="publish" /></a>
						</c:otherwise>
					</c:choose></td>
				<td><a href="file/${letter.id}"><s:message
							code="letter.file" /></a></td>
				<td>${letter.textContent}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>