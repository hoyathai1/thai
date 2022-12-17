<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/menu/account.css">
<link rel="stylesheet"  type="text/css" href="/css/pc/board/base.css">
<html>
<head>
    <title></title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<div class="top-gnb">
    <div class="description">
        <h6>헬타이 - 태국 종합 커뮤니티</h6>
    </div>
    <div class="menu">
        <nav>
            <ul>
                <li onclick="goMyAccount()">프로필</li>
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

    <div class="account">
        <div class="password">
            <h4>비밀번호 입력<span class="fnt-red">*</span></h4>
            <div class="ipt">
                <input class="ipt" type="password" name="password1" placeholder="비밀번호를 입력해주세요." maxlength="20">
                <input class="ipt" type="password" name="password2" placeholder="비밀번호를 입력해주세요." maxlength="20">
            </div>
        </div>
        <div class="info">
            <span class="fnt-gray" id="password-info">4~20자이상 입니다.</span>
            <span class="fnt-red" id="password-matches" style="display: none">비밀번호가 일치하지 않습니다.</span>
        </div>
    </div>

    <div class="account-btn-area">
        <button class="btn-pwd" onclick="modifyPwd()">변경</button>
    </div>
</div>

<input type="hidden" name="pageNum" value="${search.pageNum}">
<input type="hidden" name="pageSize" value="${search.pageSize}">
<input type="hidden" name="keyword" value="${search.keyword}">

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/pc/base.js"></script>
<script type="text/javascript" src="/js/pc/menu/accountPwd.js"></script>
</body>
</html>
