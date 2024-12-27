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
				<form id="search_form"
					action="${pageContext.request.contextPath }/board?p=1"
					method="post">
					<input type="text" id="kwd" name="kwd" value=${kwd }><input
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
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>[${vo.idx }]</td>
							<td style="text-align: left; padding-left: ${vo.depth*20}px">
								<c:if test="${vo.depth > 0 }">
									<img id="profile"
										src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:if> <a
								href="${pageContext.servletContext.contextPath }/board?a=view&id=${vo.id }&p=${param.p }">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:if test="${vo != null && authUser.id == vo.userId }">
								<td><a
									href="${pageContext.request.contextPath }/board?a=delete&id=${vo.id }"
									class="del">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><c:choose>
								<c:when test="${param.p == 1 }">
							◀
							</c:when>
								<c:otherwise>
									<a
										href="${pageContext.request.contextPath }/board?p=${param.p-1 }&kwd=${kwd }">◀</a>
								</c:otherwise>
							</c:choose></li>
						<c:choose>
							<c:when test="${1 <= numberOfPage}">
								<li
									class='${param.p == (param.p > 5 ? param.p-4 : 1) ? "selected" : "" }'><a
									href="${pageContext.request.contextPath }/board?p=${param.p > 5 ? param.p-4 : 1 }&kwd=${kwd }">${param.p > 5 ? param.p-4 : 1 }</a></li>
							</c:when>
							<c:otherwise>
								<li>1</li>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${2 <= numberOfPage}">
								<li
									class='${param.p == (param.p > 5 ? param.p-3 : 2) ? "selected" : "" }'><a
									href="${pageContext.request.contextPath }/board?p=${param.p > 5 ? param.p-3 : 2 }&kwd=${kwd }">${param.p > 5 ? param.p-3 : 2 }</a></li>
							</c:when>
							<c:otherwise>
								<li>2</li>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${3 <= numberOfPage}">
								<li
									class='${param.p == (param.p > 5 ? param.p-2 : 3) ? "selected" : "" }'><a
									href="${pageContext.request.contextPath }/board?p=${param.p > 5 ? param.p-2 : 3 }&kwd=${kwd }">${param.p > 5 ? param.p-2 : 3 }</a></li>
							</c:when>
							<c:otherwise>
								<li>3</li>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${4 <= numberOfPage}">
								<li
									class='${param.p == (param.p > 5 ? param.p-1 : 4) ? "selected" : "" }'><a
									href="${pageContext.request.contextPath }/board?p=${param.p > 5 ? param.p-1 : 4 }&kwd=${kwd }">${param.p > 5 ? param.p-1 : 4 }</a></li>
							</c:when>
							<c:otherwise>
								<li>4</li>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${5 <= numberOfPage}">
								<li
									class='${param.p == (param.p > 5 ? param.p : 5) ? "selected" : "" }'><a
									href="${pageContext.request.contextPath }/board?p=${param.p > 5 ? param.p : 5 }&kwd=${kwd }">${param.p > 5 ? param.p : 5 }</a></li>
							</c:when>
							<c:otherwise>
								<li>5</li>
							</c:otherwise>
						</c:choose>
						<li><c:choose>
								<c:when test="${param.p == numberOfPage }">
							▶
							</c:when>
								<c:otherwise>
									<a
										href="${pageContext.request.contextPath }/board?p=${param.p+1 }&kwd=${kwd }">▶</a>
								</c:otherwise>
							</c:choose></li>
					</ul>
				</div>
				<!-- pager 추가 -->
				<c:if test="${authUser != null}">
					<div class="bottom">
						<a
							href="${pageContext.request.contextPath }/board?a=writeform&p=${param.p }"
							id="new-book">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>