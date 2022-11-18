function modifyName() {
    var name = $("input[name=name]").val();

    if (isEmpty(name) || checkSpecial(name) || lengthCheckUnder(name, 2) || lengthCheckOver(name, 10)) {
        $("#name-info").removeClass("fnt-gray");
        $("#name-info").addClass("fnt-red");

        alert("닉네임을 확인해주세요");

        return;
    }

    $("#name-info").removeClass("fnt-red");
    $("#name-info").addClass("fnt-gray");

    $.ajax({
        type : 'post',
        url : '/checkUserName',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            name: name
        }),
        success : function (data) {
            if (data) {
                // 중복있음
                $("#name-duplicate").css("display", "block");
            } else {
                $("#name-duplicate").css("display", "none");

                modifyNameAjax();
            }
        }
    });
}

function modifyNameAjax() {
    var name = $("input[name=name]").val();

    $.ajax({
        type : 'post',
        url : '/user/modifyName',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            name: name
        }),
        success : function (data) {
            if (data) {
                alert("변경 완료!");
            } else {
                alert("변경에 실패했습니다. 다시 시도 해주세요.");
            }
        }
    });
}

function modifyPwd() {
    var password1 = $("input[name=password1]").val();

    if (isEmpty(password1) || lengthCheckUnder(password1, 4) || lengthCheckOver(password1, 20)) {
        $("#password-info").removeClass("fnt-gray");
        $("#password-info").addClass("fnt-red");

        alert("비밀번호를 확인해주세요.");

        return;
    }

    $("#password-info").removeClass("fnt-red");
    $("#password-info").addClass("fnt-gray");

    var password2 = $("input[name=password2]").val();

    if (isEmpty(password2) || lengthCheckUnder(password2, 4) || lengthCheckOver(password2, 20)) {
        $("#password-info").removeClass("fnt-gray");
        $("#password-info").addClass("fnt-red");

        alert("비밀번호를 확인해주세요.");
        return;
    }

    if (password1 != password2) {
        $("#password-matches").css("display", "block");

        alert("비밀번호가 일치하지 않습니다.");
        return;
    }

    $("#password-info").removeClass("fnt-red");
    $("#password-info").addClass("fnt-gray");
    $("#password-matches").css("display", "none");

    $.ajax({
        type : 'post',
        url : '/user/modifyPwd',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            password: password1
        }),
        success : function (data) {
            if (data) {
                alert("변경 완료!");
            } else {
                alert("변경에 실패했습니다. 다시 시도 해주세요.");
            }
        }
    });
}

function changeDomain() {
    var domain = $("#select-domain option:selected").val();
    $("input[name=email-addr2]").val(domain);
}

function checkEmail() {
    var email1 = $("input[name=email-addr1]").val();
    var email2 = $("input[name=email-addr2]").val();

    if (isEmpty(email1) || lengthCheckUnder(email1, 2) || lengthCheckOver(email1 , 20)) {
        alert("이메일 주소를 확인해주세요.");
        return;
    }

    var addrList = ['naver.com', 'gmai.com', 'kakao.com', 'daum.net'];
    if (isEmpty(email2) || addrList.indexOf(email2) == -1) {
        alert("이메일 주소를 확인해주세요.");
        return;
    }

    var email = email1 + "@" + email2;

    $.ajax({
        type : 'post',
        url : '/user/modifyEmail',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            email: email
        }),
        success : function (data) {
            if (data) {
                alert("변경 완료!");
            } else {
                alert("변경에 실패했습니다. 다시 시도 해주세요.");
            }
        }
    });

    $("input[name=email-addr1]").val("");
    $("input[name=email-addr2]").val("");
}