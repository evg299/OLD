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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/moment.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/letters.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/dp/jquery.ui.datepicker-ru.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#letter_date").datepicker();

		updateTable();

		$(document).delegate("a.publish_link", "click", function() {
			var td = $(this).parent();

			$.ajax({
				url : "lettersAjax/publish",
				dataType : "json",
				data : "lid=" + $(this).attr("lid"),
				success : function(data) {
					if (data)
						td.text("Да");
				}
			});

			return false;
		});

		$(".lformerror").hide();
		$("#sflform").ajaxForm({
			url : "lettersAjax",
			dataType : 'json',

			beforeSubmit : function(formData, jqForm, options) {
				$.each(formData, function(i, item) {
					if("attachedFile" == item.name)
						if(!item.value){
							alert("Выберите файл со сканом");
							return false;
						}
				});
				
				return true;
			},

			success : function(data) {
				if ("succsess" == data.status) {
					$("#sflform").resetForm();
					clearErrors();
					updateTable();
				} else {
					clearErrors();
					
					$(".lformerror").show();
					$.each(data.errors, function(i, error) {
						$(".lformerror").append("<div>" + (i+1) + ". "  + error.errorText + "</div>");
						
						if("creationDate" == error.field) {
							$("#creationDateError").append("<div class=\"lerror\">" + error.errorText + "</div>");
						}
						
						if("docNumber" == error.field) {
							$("#docNumberError").append("<div class=\"lerror\">" + error.errorText + "</div>");
						}
						
						if("title" == error.field) {
							$("#titleError").append("<div class=\"lerror\">" + error.errorText + "</div>");
						}
					});
				}
			}
		});
	});
	
	function clearErrors() {
		$(".lformerror").empty();
		$(".lformerror").hide();
		$("#creationDateError").empty();
		$("#docNumberError").empty();
		$("#titleError").empty();
	}
	
	function formatDate(date) {
		/* var curr_date = date.getDate();
	    var curr_month = date.getMonth() + 1;
	    var curr_year = date.getFullYear();
	    return curr_date + "." + curr_month + "." + curr_year; */
	    
	    return moment(date).format("DD.MM.YY");
	}

	function updateTable() {
		$("#l_table tr.ltablerow").remove();

		$.ajax({
					url : "lettersAjax/list",
					dataType : "json",
					success : function(data) {
						$.each(
										data,
										function(i, item) {
											var row = "<tr class=\"ltablerow\">";
											row += "<td>" + item["docNumber"]
													+ "</td>";
											row += "<td>"
													+  formatDate(new Date(item["creationDate"]));
											+"</td>";
											row += "<td>" + item["title"]
													+ "</td>";

											if (item["published"]) {
												row += "<td>Да</td>";
											} else {
												row += "<td>Нет, <a class=\"publish_link\" lid=\""+ item["id"] + "\" href=\"#"+ item["id"] + "\">Опубликовать</a></td>";
											}

											row += "<td><a href=\"letters/file/" + item["id"] + "\">JPG/PDF-скан</a></td>";
											row += "<td>" + item["textContent"]
													+ "</td>";
											row += "</tr>";

											$("#l_table").append(row);
										});
					}
				});
	}
</script>
<title><s:message code="appname" /></title>
</head>
<body>
	<div class="header">
		<h1>
			<s:message code="appname" />
			(AJAX)
		</h1>
	</div>
	

	<div class="lform">
		<sf:form id="sflform" modelAttribute="letterForm"
			enctype="multipart/form-data" method="POST">
			<div class="lformerror"></div>

			<table>
				<tr>
					<td><s:message code="letter.number" /> *</td>
					<td><sf:input path="docNumber" /></td>
					<td id="docNumberError"><sf:errors path="docNumber"
							cssClass="lerror" /></td>
				</tr>
				<tr>
					<td><s:message code="letter.date" /> *</td>
					<td><sf:input id="letter_date" path="creationDate" /></td>
					<td id="creationDateError"><sf:errors path="creationDate"
							cssClass="lerror" /></td>
				</tr>
				<tr>
					<td><s:message code="letter.theme" /> *</td>
					<td><sf:input path="title" /></td>
					<td id="titleError"><sf:errors path="title" cssClass="lerror" /></td>
				</tr>
				<tr>
					<td><s:message code="letter.published" /></td>
					<td><sf:checkbox path="published" /></td>
				</tr>
				<tr>
					<td><s:message code="letter.file" /> *</td>
					<td><sf:input type="file" path="attachedFile" /></td>
					<td id="fileError"><sf:errors path="attachedFile"
							cssClass="lerror" /></td>
				</tr>
				<tr>
					<td><s:message code="letter.notation" /></td>
					<td><sf:textarea path="textContent" rows="12" cols="23" /></td>
					<td id="textContentError"><sf:errors path="textContent"
							cssClass="lerror" /></td>
				</tr>
			</table>

			<input type="submit" value="<s:message code="addletter" />" />
		</sf:form>

	</div>

	<div class="splitter"></div>

	<table id="l_table" class="ltable">
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
	</table>
</body>
</html>