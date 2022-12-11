var isValidId = false;
var isValidName = false;

function search(page) {
    var content = $("input[name=table_search]").val();

    if (!isEmpty(content)) {
        location.href="/admin/account/admin/list?page=" + page + "&content="+content;
    } else {
        location.href="/admin/account/admin/list?page=" + page;
    }
}

function deleteModal(id) {
    $("#del-modal").css("display", "block");
    $("#del-modal").addClass("show");

    $("#del-modal").data("id", id);

    $("#del-content").text(id + "를 삭제 하시겠습니까?");
}

function modifyEmailModal(userId, email) {
    $("#modEmail-modal").css("display", "block");
    $("#modEmail-modal").addClass("show");

    $("#modEmail-modal").data("email", email);
    $("#modEmail-modal").data("id", userId);

    $("input[name=email]").val(email);
}

function restoreEmailModal(userId) {
    $("#restore-modal").css("display", "block");
    $("#restore-modal").addClass("show");

    $("#restore-modal").data("id", userId);

    $("#restore-content").text(userId + "를 삭제 복구 하시겠습니까?");
}

function registerAdminModal() {
    $("#register-modal").css("display", "block");
    $("#register-modal").addClass("show");

}

function modifyPwdModal(userId) {
    $("#pwdModify-modal").css("display", "block");
    $("#pwdModify-modal").addClass("show");

    $("#pwdModify-modal").data("id", userId);
}

function closeModal() {
    $(".modal").css("display", "none");
    $(".modal").removeClass("show");
}

function deleteUser() {
    var id = $("#del-modal").data("id");

    $.ajax({
        type : 'post',
        url : '/admin/account/admin/del',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            userId : id
        }),
        beforeSend : function () {

        },
        success : function () {
            search($("input[name=pageNum]").val());
        },
        error : function () {
            console.log("fail");
        }
    });
}

function restoreUser() {
    var id = $("#restore-modal").data("id");

    $.ajax({
        type : 'post',
        url : '/admin/account/admin/restoreUser',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            userId : id
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        },
        error : function () {
            console.log("fail");
        }
    });
}

function sendTempPwd() {
    var id = $("#email-modal").data("id");
    var email = $("#email-modal").data("email");

    $.ajax({
        type : 'post',
        url : '/admin/account/admin/sendTempPwd',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            userId : id,
            email : email
        }),
        success : function () {
            closeModal();
        },
        error : function () {
            console.log("fail");
        }
    });
}

function modifyEmail() {
    var id = $("#modEmail-modal").data("id");
    var email = $("input[name=email]").val();

    $.ajax({
        type : 'post',
        url : '/admin/account/admin/modifyEmail',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            userId : id,
            email : email
        }),
        success : function () {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        },
        error : function () {
            console.log("fail");
        }
    });
}

function checkDuplicateId() {
    var id = $("#admin_id").val();

    $.ajax({
        type : 'post',
        url : '/checkUserId',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        async: false,
        dataType : 'json',
        data : JSON.stringify({
            userId: id
        }),
        success : function (data) {
            if (data) {
                // 중복있음
                isValidId = false;
            } else {
                isValidId = true;
            }
        }
    });
}

function checkDuplicateName() {
    var name = $("#admin_name").val();

    $.ajax({
        type : 'post',
        url : '/checkUserName',
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
            } else {
                isValidName = true;
            }
        }
    });
}

function registerAdmin() {
    var id = $("#admin_id").val();
    var pwd = $("#admin_pwd").val();
    var name = $("#admin_name").val();
    var email = $("#admin_email").val();

    if (isEmpty(id) || lengthCheckUnder(id, 4) ||lengthCheckOver(id, 20)
        || checkKor(id) || checkSpecial(id)) {
        alert("아이디를 확인해주세요");

        return;
    }

    checkDuplicateId();
    if (!isValidId) {
        alert("아이디 중복 확인해주세요");

        return;
    }

    if (isEmpty(pwd) || lengthCheckUnder(pwd, 4) ||lengthCheckOver(pwd, 20)) {
        alert("비밀번호를 확인해주세요");

        return;
    }

    if (isEmpty(name) || lengthCheckUnder(name, 2) ||lengthCheckOver(name, 20) || checkSpecial(name)) {
        alert("닉네임을 확인해주세요");

        return;
    }

    checkDuplicateName();
    if (!isValidName) {
        alert("닉네임 중복 확인해주세요");

        return;
    }

    if (isEmpty(email) || lengthCheckUnder(email, 4) ||lengthCheckOver(email, 20)
        || checkKor(email)) {
        alert("이메일을 확인해주세요");

        return;
    }

    $.ajax({
        type : 'post',
        url : '/admin/account/admin/register',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        async: false,
        dataType : 'json',
        data : JSON.stringify({
            userId : id,
            password : pwd,
            name : name,
            email : email
        }),
        success : function (data) {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }
    });
}

function modifyPwd() {
    var id = $("#pwdModify-modal").data("id");
    var pwd = $("#modify-pwd").val();

    if (isEmpty(pwd) || lengthCheckUnder(pwd, 4) || lengthCheckOver(pwd, 20)) {
        alert("비밀번호를 확인해주세요");

        return;
    }

    $.ajax({
        type : 'post',
        url : '/admin/account/admin/modifyPwd',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        async: false,
        dataType : 'json',
        data : JSON.stringify({
            userId : id,
            password : pwd
        }),
        success : function (data) {
            var pageNum = $("input[name=pageNum]").val();
            search(pageNum);
        }
    });
}