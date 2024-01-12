<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update</title>
<link rel="stylesheet" href="/static/css/style.css">	
</head>
<body>
	<div class="wrap">
		<h2>Update</h2>
		<form action="/board/update" method="post">
			<table class="tbl_write">
				<tbody>
					<tr>
						<td>
							<input type="text" name="id"
							value="${board.id}">
						</td>
					</tr>				
					<tr>
						<td>
							<input type="text" name="title"
							value="${board.title}">
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="writer"
							value="${board.writer}">
						</td>
					</tr>
					<tr>
						<td>
							<textarea rows="5" cols="60"
								name="content" >${board.content}</textarea>
						</td>
					</tr>
					<tr>
						<td>
							<button type="submit">Update</button>
							<button type="reset">Cancel</button>
						</td>
					</tr>					
				</tbody>
			</table>
		</form>	
	</div>
</body>
</html>