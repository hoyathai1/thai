const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const type = urlParams.get('type');

var isValidPassword1 = false;
var isValidPassword2 = false;

$(document).ready(function () {

    if (best == 'Y') {
        $("#best").addClass("on");
    } else {
        $("#" + type).addClass("on");
    }

});

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();
    var content = $(".search-input").val();
    var keyword = $("input[name=keyword]").val();

    return "type=" + type + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content;
}

function goCategory(pCategory) {
    var pageSize = $("input[name=pageSize]").val();

    location.href="/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&pageSize=" + pageSize + "&keyword=all&content=";
}

function goType(pType) {
    var pageSize = $("input[name=pageSize]").val();

    location.href="/pc/board/list?type=" + pType + "&best=&category=" + category + "&pageNum=0&pageSize=" + pageSize + "&keyword=all&content=";
}

function goBest() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();

    location.href="/pc/board/list?type=all&best=Y&category=" + category + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=all&content=";
}

function search() {
    var content = $(".search-input").val();

    location.href = "/pc/board/list?type=" + type + "&best=&category=" + category + "&pageNum=0&keyword=all&content=" + content;
}

function goSignUp() {
    location.href="/pc/signUp?" + makeQueryUrl();
}

function goLogin() {
    location.href="/pc/login";
}

function goLogout() {
    location.href="/logout";
}

function goMyAccount() {
    location.href = "/pc/menu/account?" + makeQueryUrl();
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
                openModal("비밀번호가 수정되었습니다.", "type4", function () {
                    goMyAccount()
                });

            } else {
                openModal("변경에 실패했습니다.<br> 다시 시도 해주세요.", "type1", null);
            }
        }
    });
}
