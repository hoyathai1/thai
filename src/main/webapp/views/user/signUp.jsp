<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="/views/common/user-header.jsp"/>
<link rel="stylesheet"  type="text/css" href="/css/m.user.css">

<body>

    <div class="container">
        <div class="signUp">
            <div class="id">
                <h4>아이디 입력<span class="fnt-red">*</span></h4>
                <input class="ipt" type="text" name="id" id="id-ipt" value="" placeholder="아이디 입력" maxlength="20">
            </div>
            <div class="info">
                <span class="fnt-gray" id="id-info">영문자, 숫자만 입력 가능. 최소 4자이상 입력하세요.</span>
                <span class="fnt-red" id="id-duplicate" style="display: none">중복된 아이디가 있습니다.</span>
            </div>
            <div class="password">
                <h4>비밀번호 입력<span class="fnt-red">*</span></h4>
                <input class="ipt" type="password" name="password1" placeholder="비밀번호를 입력해주세요." maxlength="20">
                <input class="ipt" type="password" name="password2" placeholder="비밀번호를 입력해주세요." maxlength="20">
            </div>
            <div class="info">
                <span class="fnt-gray" id="password-info">4~20자이상 입니다.</span>
                <span class="fnt-red" id="password-matches" style="display: none">비밀번호가 일치하지 않습니다.</span>
            </div>
            <div class="name">
                <h4>닉네임 입력<span class="fnt-red">*</span></h4>
                <input class="ipt" type="text" name="name" value="" id="name-ipt" placeholder="닉네임을 입력해주세요." maxlength="10">
            </div>
            <div class="info">
                <span class="fnt-gray" id="name-info">공백없이 한글,영문,숫자만 입력 가능 (2자 이상, 10자 이하)</span>
                <span class="fnt-red" id="name-duplicate" style="display: none">중복된 닉네임이 있습니다.</span>
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
                <span class="fnt-gray">비밀번호 분실시 사용됩니다.</span>
                <span class="fnt-gray">E-mail 주소는 암호화되어 DB에 저장되므로 유출될 염려가 없습니다.</span>
            </div>
            <div class="btn-area">
                <button class="btn one on" onclick="btnSignUp()">가입</button>
            </div>
        </div>
    </div>

</body>

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/user/m.signUp.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>
<script src="https://www.google.com/recaptcha/api.js?render=6LeIHAIjAAAAAAmC25HnQn-fNgwQy-z0S4bpP9Nd"></script>
<script type="text/javascript" src="/js/common/captcha.js"></script>
<jsp:include page="/views/common/footer.jsp"/>
