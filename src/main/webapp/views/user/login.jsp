<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/views/common/user-header.jsp"/>
<link rel="stylesheet"  type="text/css" href="/css/m.user.css">

<body>
    <div class="container">
        <div class="login">
            <form role="form">
                <div class="id">
                    <input class="ipt" type="text" name="username" id="username"  placeholder="아이디" maxlength="20">
                </div>
                <div class="password">
                    <input class="ipt" type="password" name="password" id="password" placeholder="비밀번호" maxlength="20">
                </div>
                <div class="info" style="display:<c:if test="${error eq true}">block;</c:if><c:if test="${error ne true}">none;</c:if>">
                    <c:if test="${errorType eq '1'}">
                        <span class="fnt-red">아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.</span>
                    </c:if>
                    <c:if test="${errorType eq '2'}">
                        <span class="fnt-red">계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.</span>
                    </c:if>
                    <c:if test="${errorType eq '3'}">
                        <span class="fnt-red">내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.</span>
                    </c:if>
                </div>

                <div class="btn-area">
                    <button class=" btn one on" onclick="btnLogin()">로그인</button>
                </div>
                <div class="remember">
                    <input type="checkbox" id="remember-me" name="remember-me" checked>
                    <label for="remember-me">로그인유지</label>
                </div>
            </form>

            <div class="user-btn">
                <div class="signUp-btn" onclick="goSignUpBtn()">회원가입</div>
                <div class="find-pwd-btn" onclick="goFindPwd()">비밀번호 찾기</div>
            </div>
        </div>
    </div>
</body>


<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/user/m.login.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>
<script src="https://www.google.com/recaptcha/api.js?render=6LeIHAIjAAAAAAmC25HnQn-fNgwQy-z0S4bpP9Nd"></script>
<script type="text/javascript" src="/js/common/captcha.js"></script>
<jsp:include page="/views/common/footer.jsp"/>