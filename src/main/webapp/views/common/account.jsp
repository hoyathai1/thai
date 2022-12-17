<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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

    <div class="menu-header-top">
        <div class="prev" onclick="goBack()">
            <div class="prev-ico"></div>
        </div>
        <div class="header-logo-font"><span>헬타이</span></div>
            <div class="header-info">
                <div class="close" onclick="goClose()">
                    <div class="close-ico"></div>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="account">
            <div class="title">
                내 프로필
            </div>
            <div class="content">
                <div class="name">
                    <h4>닉네임</h4>
                    <input class="ipt" type="text" name="name" value="${user.name}" id="name-ipt" placeholder="닉네임을 입력해주세요." maxlength="10">
                </div>
                <div class="info">
                    <span class="fnt-gray" id="name-info">공백없이 한글,영문,숫자만 입력 가능 (2자 이상, 10자 이하)</span>
                    <span class="fnt-red" id="name-duplicate" style="display: none">중복된 닉네임이 있습니다.</span>
                </div>
                <div class="btn-area">
                    <button class="btn one on" onclick="modifyName()">닉네임 변경</button>
                </div>

                <div class="password">
                    <h4>새로운 비밀번호 입력</h4>
                    <input class="ipt" type="password" name="password1" placeholder="비밀번호를 입력해주세요." maxlength="20">
                    <input class="ipt" type="password" name="password2" placeholder="확인 비밀번호를 입력해주세요." maxlength="20">
                </div>
                <div class="info">
                    <span class="fnt-gray" id="password-info">4~20자이상 입니다.</span>
                    <span class="fnt-red" id="password-matches" style="display: none">비밀번호가 일치하지 않습니다.</span>
                </div>
                <div class="btn-area">
                    <button class="btn one on" onclick="modifyPwd()">패스워드 변경</button>
                </div>

                <div class="email">
                    <h4>이메일 입력</h4>
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
                <div class="info">
                    <span class="fnt-gray">비밀번호 분실시 사용됩니다.</span>
                    <span class="fnt-gray">E-mail 주소는 암호화되어 DB에 저장되므로 유출될 염려가 없습니다.</span>
                </div>
                <div class="btn-area">
                    <button class="btn one on" onclick="checkEmail()">이메일 변경</button>
                </div>
            </div>
        </div>
    </div>

</body>
<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>
<script type="text/javascript" src="/js/common/m.menu.js"></script>
<script type="text/javascript" src="/js/common/m.account.js"></script>

<jsp:include page="/views/common/footer.jsp"></jsp:include>