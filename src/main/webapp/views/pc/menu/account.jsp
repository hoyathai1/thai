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

    <div class="account">
        <div class="name">
            <h4>닉네임 변경</h4>
            <div class="ipt">
                <input class="ipt" type="text" name="name" value="${user.name}" id="name-ipt" placeholder="닉네임을 입력해주세요." maxlength="10">
            </div>
        </div>
        <div class="info">
            <span class="fnt-gray" id="name-info">공백없이 한글,영문,숫자만 입력 가능 (2자 이상, 10자 이하)</span>
            <span class="fnt-red" id="name-duplicate" style="display: none">중복된 닉네임이 있습니다.</span>
        </div>
        <br>
        <div class="email">
            <h4>이메일 변경</h4>
            <div class="ipt">
                <input class="ipt email1" type="text" name="email-addr1" value="${email1}">
                <span>@</span>
                <input class="ipt email2" type="text" name="email-addr2" readonly value="${email2}">
                <select class="domain" id="select-domain" onchange="changeDomain()">
                    <option value="">
                        이메일 선택
                    </option>
                    <option value="naver.com" <c:if test="${email2 eq 'naver.com'}">selected</c:if>>
                        naver.com
                    </option>
                    <option value="gmai.com" <c:if test="${email2 eq 'gmai.com'}">selected</c:if>>
                        gmail.com
                    </option>
                    <option value="kakao.com" <c:if test="${email2 eq 'kakao.com'}">selected</c:if>>
                        kakao.com
                    </option>
                    <option value="daum.net" <c:if test="${email2 eq 'daum.net'}">selected</c:if>>
                        daum.net
                    </option>
                </select>
            </div>
        </div>
        <div class="info">
            <span class="fnt-gray">비밀번호 분실시 사용됩니다.</span>
            <span class="fnt-gray">E-mail 주소는 암호화되어 DB에 저장되므로 유출될 염려가 없습니다.</span>
        </div>
    </div>

    <div class="account-btn-area">
        <button class="btn-info" onclick="changeInfo()">회원정보 수정</button>
        <button class="btn-pwd" onclick="moveChangePwd()">비밀번호 변경</button>
    </div>
</div>

<input type="hidden" name="pageNum" value="${search.pageNum}">
<input type="hidden" name="pageSize" value="${search.pageSize}">
<input type="hidden" name="keyword" value="${search.keyword}">

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/pc/base.js"></script>
<script type="text/javascript" src="/js/pc/menu/account.js"></script>
</body>
</html>
