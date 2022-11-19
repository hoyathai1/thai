var isValidId = false;
var isValidPassword1 = false;
var isValidPassword2 = false;
var isValidName = false;

$(document).ready(function () {

});

document.getElementById("id-ipt").addEventListener("focusout", checkDuplicateId);
document.getElementById("name-ipt").addEventListener("focusout", checkDuplicateName);

$("input[name=id]").on("keyup", function () {
    checkUserId();
});

$("input[name=password1]").on("keyup", function () {
    checkPassword1();
});

$("input[name=password2]").on("keyup", function () {
    checkPassword2();
});

$("input[name=name]").on("keyup", function () {
    checkName();
    checkDuplicateName();
});

function changeDomain() {
    var domain = $("#select-domain option:selected").val();
    $("input[name=email-addr2]").val(domain);
}

function checkUserId() {
    var id = $("input[name=id]").val();

    if (isEmpty(id) || lengthCheckUnder(id, 4) || lengthCheckOver(id, 20)
            || checkKor(id) || checkSpecial(id)) {
        $("#id-info").removeClass("fnt-gray");
        $("#id-info").addClass("fnt-red");

        isValidId = false;
        return;
    }

    $("#id-info").removeClass("fnt-red");
    $("#id-info").addClass("fnt-gray");
    isValidId = true;

    checkDuplicateId();
}

function checkDuplicateId() {
    var id = $("input[name=id]").val();

    $.ajax({
        type : 'post',
        url : '/checkUserId',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            userId: id
        }),
        success : function (data) {

        }
    });
}

function checkDuplicateName() {
    var name = $("input[name=name]").val();

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
                isValidName = false;
                $("#name-duplicate").css("display", "block");
            } else {
                $("#name-duplicate").css("display", "none");
                isValidName = true;
            }
        }
    });
}

function checkPassword1() {
    var password1 = $("input[name=password1]").val();

    if (isEmpty(password1) || lengthCheckUnder(password1, 4) || lengthCheckOver(password1, 20)) {
        $("#password-info").removeClass("fnt-gray");
        $("#password-info").addClass("fnt-red");

        isValidPassword1 = false;
        return;
    }

    $("#password-info").removeClass("fnt-red");
    $("#password-info").addClass("fnt-gray");
    isValidPassword1 = true;
}

function checkPassword2() {
    var password1 = $("input[name=password1]").val();
    var password2 = $("input[name=password2]").val();

    if (isEmpty(password2) || lengthCheckUnder(password2, 4) || lengthCheckOver(password2, 20)) {
        $("#password-info").removeClass("fnt-gray");
        $("#password-info").addClass("fnt-red");

        isValidPassword2 = false;
        return;
    }

    if (password1 != password2) {
        $("#password-matches").css("display", "block");

        return;
    }

    $("#password-info").removeClass("fnt-red");
    $("#password-info").addClass("fnt-gray");
    $("#password-matches").css("display", "none");
    isValidPassword2 = true;
}

function checkName() {
    var name = $("input[name=name]").val();

    if (isEmpty(name) || checkSpecial(name) || lengthCheckUnder(name, 2) || lengthCheckOver(name, 10)) {
        $("#name-info").removeClass("fnt-gray");
        $("#name-info").addClass("fnt-red");

        isValidName = false;
        return;
    }

    $("#name-info").removeClass("fnt-red");
    $("#name-info").addClass("fnt-gray");
    isValidName = true;

    checkDuplicateName();
}

function btnSignUp() {
    if (!isValidId) {
        alert("아이디를 확인해주세요");
        return;
    }

    if (!isValidPassword1) {
        alert("비밀번호를 확인해주세요");
        return;
    }

    if (!isValidPassword2) {
        alert("비밀번호를 확인해주세요");
        return;
    }

    if (!isValidName) {
        alert("닉네임을 확인해주세요");
        return;
    }

    checkDuplicateId();

    var id = $("input[name=id]").val();
    var password = $("input[name=password1]").val();
    var name = $("input[name=name]").val();
    var email = "";
    var email1 = $("input[name=email-addr1]").val();
    var email2 = $("input[name=email-addr2]").val();
    email = email1 + "@" + email2;

    $.ajax({
        type : 'post',
        url : '/signUp',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            userId: id,
            name: name,
            password: password,
            email : email
        }),
        success : function (data) {
            if (window.location.search == null || window.location.search == '') {
                location.href="/board/list?type=all&best=&category=thai&pageNum=0"
            } else {
                location.href="/board/list" + window.location.search;
            }
        }
    });

}
