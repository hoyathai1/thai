<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<header>
    <title></title>
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
        <div class="header-logo-font"><span>헬타이</span></div>
            <div class="header-info">
                <div class="close" onclick="goClose()">
                    <div class="close-ico"></div>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="myNoti">
            <div class="title">
                알림 리스트
            </div>
            <div class="content">
                <div class="noti-list">
                    <c:forEach items="${list.content}" var="noti">
                        <fmt:parseDate value="${noti.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                        <fmt:formatDate pattern="yyyyMMdd" value="${ parsedDateTime }" var="boardDate"/>
                        <div class="noti" onclick="goBoard('${noti.boardId}', '${noti.commentId}', '${noti.category}')">
                            <div class="noti-title">${noti.title}</div>
                            <div class="noti-info">
                                <c:if test="${noti.isUser}">
                                    <div class="author">${noti.author}</div>
                                </c:if>
                                <c:if test="${noti.isUser eq false}">
                                    <div class="author">${noti.author}(${noti.ip})</div>
                                </c:if>

                                <c:if test="${today eq boardDate}">
                                    <div class="noti-date"><fmt:formatDate pattern="HH:mm" value="${ parsedDateTime }"/></div>
                                </c:if>
                                <c:if test="${today ne boardDate}">
                                    <div class="noti-date"><fmt:formatDate pattern="yyyy.MM.dd" value="${ parsedDateTime }"/></div>
                                </c:if>
                            </div>
                            <div class="noti-content">
                                ${noti.content}
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="noti-paging">
                    <c:if test="${pageDto.startPage eq 0}">
                        <div class="page-prev" onclick="movePage(${pageDto.startPage})"><</div>
                    </c:if>
                    <c:if test="${pageDto.startPage ne 0}">
                        <div class="page-prev" onclick="movePage(${pageDto.startPage - 1})"><</div>
                    </c:if>

                    <div class="page-number">
                        <c:if test="${endPage ne -1}">

                        </c:if>
                        <c:forEach var="i" begin="${pageDto.startPage}" end="${pageDto.endPage+1}" step="1">
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
                </div>
            </div>
        </div>
    </div>

</body>
<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>
<script type="text/javascript" src="/js/common/m.menu.js"></script>
<script type="text/javascript" src="/js/common/m.myNoti.js"></script>

<jsp:include page="/views/common/footer.jsp"></jsp:include>