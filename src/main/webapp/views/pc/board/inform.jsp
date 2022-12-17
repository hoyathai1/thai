<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/board/view.css">
<link rel="stylesheet"  type="text/css" href="/css/pc/board/base.css">
<html>
<head>
    <title>게시글</title>
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
                <li>프로필</li>
                <li>내가쓴글</li>
                <li>저장글</li>
                <c:if test="${empty principal}">
                    <li onclick="goSignUp()">회원가입</li>
                    <li onclick="goLogin()">로그인</li>
                </c:if>
                <c:if test="${not empty principal}">
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
        <div class="subject-area" onclick="goCategory('${boardCategory.id}')">
            <div class="ico-thai"></div>
            <h1>${boardCategory.name}</h1>
        </div>

        <div class="search-area">
            <input class="search-input" placeholder="검색어를 입력하세요." value="${search.content}">
            <button class="search-btn" onclick="search()">검색</button>
        </div>
    </div>

    <div class="types">
        <div class="list">
            <div class="type" id="all" onclick="goType('all')">전체</div>
            <div class="type" id="best" onclick="goBest()">인기</div>
            <c:forEach items="${boardType}" var="type">
                <div class="type" id="${type.type}" onclick="goType('${type.type}')">${type.name}</div>
            </c:forEach>
        </div>
    </div>

    <div class="content">
        <div class="view">
            <div class="title">
                ${board.title}
            </div>
            <div class="info">
                <div class="left">
                    <div class="author">${board.username}</div>
                    <div class="createDate">${board.createDate}</div>
                </div>
                <div class="right">
                </div>
            </div> <%--.info--%>
            <div class="contents">
                ${board.contents}
            </div>

            <div class="border-line">
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
<input type="hidden" name="keyword" value="${search.keyword}">
<input type="hidden" name="content" value="${search.content}">

<input type="hidden" name="loginId" value="${principal.userId}">
<input type="hidden" name="loginName" value="${principal.name}">
<input type="hidden" name="isLogin" value="${principal != null}">

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/pc/board/view.js"></script>
<script type="text/javascript" src="/js/pc/base.js"></script>
</body>
</html>


