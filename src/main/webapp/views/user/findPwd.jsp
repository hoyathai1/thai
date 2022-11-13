<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/views/common/user-header.jsp"/>
<link rel="stylesheet"  type="text/css" href="/css/m.user.css">

<body>
    <div class="container">
        <div class="find-pwd">
            <div class="id">
                <h4>아이디 입력</h4>
                <input class="ipt" type="text" name="id" id="id-ipt" value="" placeholder="아이디 입력" maxlength="20">
            </div>

            <div class="email">
                <h4>이메일 입력</h4>
                <input class="ipt email1" type="text" name="email-addr1">
                <span>@</span>
                <input class="ipt email2" type="text" name="email-addr2">
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
                    <option value="content">
                        kakao.com
                    </option>
                    <option value="daum.net">
                        daum.net
                    </option>
                    <option value="">
                        직접입력
                    </option>
                </select>
            </div>
            <div class="info">
                <span class="fnt-gray">회원가입 시 등록하신 이메일 주소를 입력해 주세요. 해당 이메일로 아이디와 비밀번호 정보를 보내드립니다.</span>
            </div>

            <div class="btn-area">
                <button class="btn one on" onclick="sendEmail()">임시비밀번호 받기</button>
            </div>
        </div>
    </div>
</body>

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/user/m.find.pwd.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>
<script src="https://www.google.com/recaptcha/api.js?render=6LeIHAIjAAAAAAmC25HnQn-fNgwQy-z0S4bpP9Nd"></script>
<script type="text/javascript" src="/js/common/captcha.js"></script>
<jsp:include page="/views/common/footer.jsp"/>