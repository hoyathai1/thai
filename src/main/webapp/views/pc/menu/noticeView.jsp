<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/menu/noticeView.css">
<%--<link rel="stylesheet"  type="text/css" href="/css/pc/board/base.css">--%>
<html>
<head>
    <meta property="og:title" content="헬타이">
    <meta property="og:url" content="http://hellowthai.com/">
    <meta property="og:image" content="/img/logo.png">
    <meta property="og:description" content="태국정보를 공유하는 커뮤님티입니다.">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <title>헬타이</title>
</head>
<body>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="principal"/>
    </sec:authorize>

    <jsp:useBean id="now" class="java.util.Date" />
    <fmt:formatDate pattern="yyyyMMdd" value="${ now }" var="today" />

    <div class="div_load_image">
        <img src="/img/loading.gif" style="width:100px; height:100px;">
    </div>

    <div class="top-gnb">
        <div class="description">
            <h6>헬타이 - 태국 종합 커뮤니티</h6>
        </div>
        <div class="menu">
            <nav>
                <ul>
                    <li onclick="goNotice()">공지사항</li>
                    <c:if test="${empty principal}">
                        <li onclick="goSignUp()">회원가입</li>
                        <li onclick="goLogin()">로그인</li>
                    </c:if>
                    <c:if test="${not empty principal}">
                        <c:if test="${principal.userAuth eq 'ROLE_ADMIN'}">
                            <li onclick="javascript:location.href='/admin/main'">
                                관리자
                            </li>
                        </c:if>
                        <li onclick="goMyAccount()">프로필</li>
                        <li onclick="goMyList()">내가쓴글</li>
                        <li onclick="goMyComment()">내 댓글</li>
                        <li onclick="goBookmark()">저장글</li>
                        <li onclick="goLogout()">로그아웃</li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>

    <div class="container">
        <div class="category">
            <div class="list">
                <c:forEach items="${allCategory}" var="category">
                    <div class="name" onclick="goCategory('${category.id}')">${category.name}</div>
                </c:forEach>
            </div>
        </div>

        <div class="subject">
            <div class="subject-area">
                <h1>공지사항</h1>
            </div>
        </div>

        <div class="content">
            <div class="view">
                <div class="title">
                    ${dto.title}
                </div>
                <div class="info">
                    <div class="left">
                        <div class="author">${dto.author}</div>
                        <div class="createDate">${dto.createDate}</div>
                    </div>
                    <div class="right">
                    </div>
                </div> <%--.info--%>
                <div class="contents">
                    ${dto.contents}
                </div>

                <div class="view-btn">
                    <div class="left">

                    </div>
                    <div class="right">
                        <button onclick="goList()">목록</button>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <input type="hidden" name="boardNum" value="${search.boardNum}">
    <input type="hidden" name="pageNum" value="${search.pageNum}">
    <input type="hidden" name="pageSize" value="${search.pageSize}">

    <input type="hidden" name="loginId" value="${principal.userId}">
    <input type="hidden" name="loginName" value="${principal.name}">
    <input type="hidden" name="isLogin" value="${principal != null}">
    <input type="hidden" name="commentPage" value="">
    <input type="hidden" name="subPageNum" value="">

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/pc/menu/noticeView.js"></script>
    <script type="text/javascript" src="/js/pc/base.js"></script>
</body>
</html>


