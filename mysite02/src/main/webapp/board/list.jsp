<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<tr>
						<td>3</td>
						<%-- <td style = "text-align: left; padding-left: ${vo.depth*20}px"><a href="">세 번째 글입니다.</a></td> --%>
						<td style = "text-align: left; padding-left: ${0*20}px">
						<a href="">세 번째 글입니다.</a></td>
						<td>안대혁</td>
						<td>3</td>
						<td>2015-10-11 12:04:20</td>
						<td><a href="" class="del">삭제</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td style = "text-align: left; padding-left: ${1*20}px">
						<!-- c:if --- 1 일때 표시? -->
						<img src = "${pageContext.servletContext.contextPath }/assets/images/reply.png">
						<a href="">두 번째 글입니다.</a></td>
						<td>안대혁</td>
						<td>3</td>
						<td>2015-10-02 12:04:12</td>
						<td><a href="" class="del">삭제</a></td>
					</tr>
					<tr>
						<td>1</td>
						<td style = "text-align: left; padding-left: ${2*20}px">
						<img src = "${pageContext.servletContext.contextPath }/assets/images/reply.png">
						<a href="">첫 번째 글입니다.</a></td>
						<td>안대혁</td>
						<td>3</td>
						<td>2015-09-25 07:24:32</td>
						<td><a href="" class="del">삭제</a></td>
					</tr>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li> <!-- 프리브페이지가 있을 때만(1) 표시 -->
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li> <!-- 페이징개수는 항상 5개, 옅게 표시 disable? -->
						<li><a href="">▶</a></li> <!-- 넥스트페이지가 있을 때만(1) 표시 -->
					</ul>
				</div>
				<!-- pager 추가 -->

				<div class="bottom">
					<a href="" id="new-book">글쓰기</a>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>