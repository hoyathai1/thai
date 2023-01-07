const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const type = urlParams.get('type');

var isValidName = false;
var isValidEmail = true;

$(document).ready(function () {

    if (best == 'Y') {
        $("#best").addClass("on");
    } else {
        $("#" + type).addClass("on");
    }

});

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var content = $(".search-input").val();
    var keyword = $("input[name=keyword]").val();

    return encodeURI("type=" + type + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&keyword=" + keyword + "&content=" + content);
}

function goCategory(pCategory) {
    location.href = encodeURI("/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&keyword=all&content=");
}

function goSignUp() {
    location.href = "/pc/signUp?" + makeQueryUrl();
}

function goLogin() {
    location.href = "/pc/login";
}

function goLogout() {
    location.href = "/logout";
}

function goMyAccount() {
    location.href = "/pc/menu/account";
}

function goMyList() {
    location.href = "/pc/menu/myList?pageNum=0";
}

function goBookmark() {
    location.href = "/pc/menu/myBookmark?pageNum=0";
}

function goMyComment() {
    location.href = "/pc/menu/myComment?pageNum=0";
}

function changeDomain() {
    var domain = $("#select-domain option:selected").val();
    $("input[name=email-addr2]").val(domain);
}

function checkDuplicateName() {
    var name = $("input[name=name]").val();

    $.ajax({
        type : 'post',
        url : '/pc/checkUserName',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        async: false,
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

    checkDuplicateName();
}

function checkEmail() {
    var email1 = $("input[name=email-addr1]").val();
    var email2 = $("input[name=email-addr2]").val();

    if (isEmpty(email1) || lengthCheckOver(email1, 20)
        || checkKor(email1) || checkSpecial(email1)) {
        isValidEmail = false;
        return;
    }

    if (isEmpty(email2) || lengthCheckOver(email2, 20)
        || checkKor(email2)) {
        isValidEmail = false;
        return;
    }

    isValidEmail = true;
}

function changeInfo() {
    checkName();

    if (!isValidName) {
        alert("닉네임을 확인해주세요");
        return;
    }

    var name = $("input[name=name]").val();
    var email = "";
    var email1 = $("input[name=email-addr1]").val();
    var email2 = $("input[name=email-addr2]").val();

    if (!isEmpty(email1)) {
        checkEmail();
    }

    if (!isValidEmail) {
        alert("이메일을 확인해주세요");
        return;
    }

    email = email1 + "@" + email2;

    $.ajax({
        type : 'post',
        url : '/pc/user/modify',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            name: name,
            email : email
        }),
        success : function (data) {
            openModal("회원정보가 수정되었습니다.", "type1", null);
        }
    });

}

function moveChangePwd() {
    location.href = "/pc/menu/accountPwd?" + makeQueryUrl();
}

function goNotice() {
    location.href = "/pc/menu/notice?" + makeQueryUrl();
}