<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detail</title>
<link rel="stylesheet" href="/static/css/style.css">	
</head>
<body>
	<div class="wrap">
		<h2>Detail</h2>
			<table class="tbl_write">
				<tbody>
					<tr>
						<td>
							<input type="text" name="id"
							value="${board.id}" readonly>
						</td>
					</tr>				
					<tr>
						<td>
							<input type="text" name="title"
							value="${board.title}" readonly>
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="writer"
							value="${board.writer}" readonly>
						</td>
					</tr>
					<tr>
						<td>
							<textarea rows="5" cols="60"
								name="content" readonly>${board.content}</textarea>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/board/update?id=${board.id}">
								<button type="button">Edit</button>
							</a>
							<a href="/board/delete?id=${board.id}"
								onclick="return confirm('삭제하시겠습니까?')">
								<button type="button">Delete</button>
							</a>
							<a href="/board/">
								<button type="button">List</button>
							</a>
						</td>
					</tr>					
				</tbody>
			</table>
	</div>
</body>
</html>