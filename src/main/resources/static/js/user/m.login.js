$(document).ready(function () {
    if(!detectMobileDevice(window.navigator.userAgent)) {
        var hUrl = new URL(location.href);
        location.href = encodeURI("/pc" + hUrl.pathname + hUrl.search);
    }
});

function btnLogin() {
    var id = $("input[name=username]").val();
    var password = $("input[name=password]").val();

    if (isEmpty(id)) {
        alert("아이디를 입력해주세요.");
        return;
    }

    if (lengthCheckUnder(id, 2)) {
        alert("아이디는 2자리 이상입니다.");
        return;
    }

    if (checkKor(id)) {
        alert("아이디는 영문자, 숫자만 입력 가능합니다.");
        return;
    }

    if (isEmpty(password)) {
        alert("패스워를 입력해주세요.");
        return;
    }

    if (lengthCheckUnder(password, 4)) {
        alert("패스워드는 4자리 이상입니다.");
        return;
    }

    var formObj = $("form[role='form']");
    formObj.attr("method", "POST");
    formObj.attr("action", "/login");
    formObj.submit();
}

function goSignUpBtn() {
    location.href = "/signUp";
}

function goFindPwd() {
    location.href = "/find/pwd"
}