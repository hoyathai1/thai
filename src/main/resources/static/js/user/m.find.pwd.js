$(document).ready(function () {
    if(!detectMobileDevice(window.navigator.userAgent)) {
        var hUrl = new URL(location.href);
        location.href = encodeURI("/pc" + hUrl.pathname + hUrl.search);
    }

});

function changeDomain() {
    var domain = $("#select-domain option:selected").val();
    $("input[name=email-addr2]").val(domain);
}

function sendEmail() {
    var userId = $("input[name=id]").val();
    var email1 = $("input[name=email-addr1]").val();
    var email2 = $("input[name=email-addr2]").val();

    if (isEmpty(userId)) {
        alert("아이디를 입력해주세요");
        return;
    }

    if (isEmpty(email1)) {
        alert("이메일을 입력해주세요");
        return;
    }

    if (isEmpty(email2)) {
        alert("이메일 주소를 선택해주세요");
        return;
    }

    var emailAddr = email1 + "@" + email2;

    checkBot();

    $.ajax({
        type : 'post',
        url : '/tempPwdEmail',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            userId : userId,
            email : emailAddr
        }),
        success : function () {
            alert("임시비밀번호를 전송했습니다.");
            location.href="/login";
        }, error : function (data) {
            alert("메일전송에 실패했습니다.");
        }
    });
}

