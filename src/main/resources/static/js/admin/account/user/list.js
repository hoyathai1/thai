function search(page) {
    var content = $("input[name=table_search]").val();

    if (!isEmpty(content)) {
        location.href="/admin/account/user/list?page=" + page + "&content="+content;
    } else {
        location.href="/admin/account/user/list?page=" + page;
    }
}

function deleteModal(id) {
    $("#del-modal").css("display", "block");
    $("#del-modal").addClass("show");

    $("#del-modal").data("id", id);

    $("#del-content").text(id + "를 삭제 하시겠습니까?");
}

function emailModal(userId, email) {
    $("#sendTempPwdId").css("display", "block");
    $("#email-modal").css("display", "block");
    $("#email-modal").addClass("show");

    $("#email-modal").data("email", email);
    $("#email-modal").data("id", userId);

    if ('@' == email) {
        $("#email-content").text("이메일이 등록되지 않은 계정입니다.");
        $("#sendTempPwdId").css("display", "none");
    } else {
        $("#email-content").text(email + "의 이메일로 비밀번호 초기화 메일을 전송하시겠습니까?");
    }
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

function closeModal() {
    $(".modal").css("display", "none");
    $(".modal").removeClass("show");
}

function deleteUser() {
    var id = $("#del-modal").data("id");

    $.ajax({
        type : 'post',
        url : '/admin/account/user/del',
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

function restoreUser() {
    var id = $("#restore-modal").data("id");

    $.ajax({
        type : 'post',
        url : '/admin/account/user/restoreUser',
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
        url : '/admin/account/user/sendTempPwd',
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
        url : '/admin/account/user/modifyEmail',
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