<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/user/signUp.css">
<html>
<head>
    <meta property="og:title" content="헬타이">
    <meta property="og:url" content="http://hellowthai.com/">
    <meta property="og:image" content="/img/logo.png">
    <meta property="og:description" content="태국정보를 공유하는 커뮤님티입니다.">
    <title>헬타이</title>
</head>
<body>
    <div class="header">
        <div class="logo-font">헬로우타이.com</div><div class="login-font">비밀번호 찾기</div>
    </div>

    <div class="container">
        <div class="signUp">
            <div class="id">
                <h4>아이디 입력<span class="fnt-red">*</span></h4>
                <div class="ipt">
                    <input class="ipt" type="text" name="id" id="id-ipt" value="" placeholder="아이디 입력" maxlength="20">
                </div>
            </div>
            <div class="email">
                <h4>이메일 입력</h4>
                <div class="ipt">
                    <input class="ipt email1" type="text" name="email-addr1">
                    <span>@</span>
                    <input class="ipt email2" type="text" name="email-addr2" readonly>
                    <select class="domain" id="select-domain" onchange="changeDomain()">
                        <option value="">
                            이메일 입력
                        </option>
                        <option value="naver.com">
                            naver.com
                        </option>
                        <option value="gmai.com">
                            gmail.com
                        </option>
                        <option value="kakao.com">
                            kakao.com
                        </option>
                        <option value="daum.net">
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
        <div class="btn-area">
            <button class="btn" onclick="sendEmail()">임시비밀번호 받기</button>
        </div>
    </div>

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/pc/base.js"></script>
<script type="text/javascript" src="/js/pc/user/findPwd.js"></script>
</body>
</html>
