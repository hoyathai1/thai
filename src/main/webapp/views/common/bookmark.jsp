<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<header>
    <meta property="og:title" content="헬타이">
    <meta property="og:url" content="http://hellowthai.com/">
    <meta property="og:image" content="/img/logo.png">
    <meta property="og:description" content="태국정보를 공유하는 커뮤님티입니다.">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <title>헬타이</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/css/m.base.css">
    <link rel="stylesheet" type="text/css" href="/css/m.common.css">
    <link rel="stylesheet"  type="text/css" href="/css/m.menu.css">
</header>
<body>

    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="principal"/>
    </sec:authorize>

    <jsp:useBean id="now" class="java.util.Date" />
    <fmt:formatDate pattern="yyyyMMdd" value="${ now }" var="today" />

    <div class="menu-header-top">
        <div class="prev" onclick="goBack()">
            <div class="prev-ico"></div>
        </div>
        <div class="header-logo-font"><span>헬타이</span></div>
            <div class="header-info">
                <div class="close" onclick="goClose()"></div>
                <div class="close-ico"></div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="myList">
            <div class="title">
                저장글
            </div>
            <div class="content">
                <div class="board-list">
                    <c:if test="${empty list.content}">
                        <div class="not-result">
                            저장글이 존재하지 않습니다.
                        </div>
                    </c:if>
                    <c:if test="${not empty list.content}">
                        <c:forEach items="${list.content}" var="board">
                            <fmt:parseDate value="${board.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                            <div class="board moveClick" onclick="moveBoard('${board.boardId}', '${board.category}')">
                                <div class="board-title">${board.title}</div>
                                <div class="board-info">
                                    <fmt:formatDate pattern="yyyyMMdd" value="${ parsedDateTime }" var="boardDate"/>

                                    <c:if test="${today eq boardDate}">
                                        <div><fmt:formatDate pattern="HH:mm" value="${ parsedDateTime }"/></div>
                                    </c:if>
                                    <c:if test="${today ne boardDate}">
                                        <div><fmt:formatDate pattern="yyyy.MM.dd" value="${ parsedDateTime }"/></div>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
                <c:if test="${not empty list.content}">
                    <div class="board-paging">
                        <c:if test="${pageDto.startPage eq 0}">
                            <div class="page-prev" onclick="movePage(${pageDto.startPage})"><</div>
                        </c:if>
                        <c:if test="${pageDto.startPage ne 0}">
                            <div class="page-prev" onclick="movePage(${pageDto.startPage - 1})"><</div>
                        </c:if>

                        <div class="page-number">
                        <c:forEach var="i" begin="${pageDto.startPage}" end="${pageDto.endPage}" step="1">
                            <c:if test="${pageDto.curPage eq i}">
                                <div class="on" onclick="movePage(${i})">${i+1}</div>
                            </c:if>
                            <c:if test="${pageDto.curPage ne i}">
                                <div onclick="movePage(${i})">${i+1}</div>
                            </c:if>
                        </c:forEach>
                        </div>

                        <c:if test="${pageDto.endPage eq 0}">
                            <div class="page-next" onclick="movePage(${pageDto.endPage})">></div>
                        </c:if>
                        <c:if test="${pageDto.endPage ne 0}">
                            <div class="page-next" onclick="movePage(${pageDto.endPage + 1})">></div>
                        </c:if>

                        <div class="page-move">>></div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

</body>
<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>
<script type="text/javascript" src="/js/common/m.menu.js"></script>
<script type="text/javascript" src="/js/common/m.bookmark.js"></script>

<jsp:include page="/views/common/footer.jsp"></jsp:include>