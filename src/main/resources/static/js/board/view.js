var currentCommentPage = 0;

$(document).ready(function () {
    $(".createDate").html(getBoardTime($(".createDate").html()));

    getCommentList(currentCommentPage);

});

function goList() {
    location.href = "/board/list?" + makeQueryUrl();
}

function goRegister() {
    location.href = "/board/register";
}



function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();
    var keyword = $("input[name=keyword]").val();
    var content = $("input[name=content]").val();

    return "pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content;
}

function getCommentList(page) {
    var boardNum = $("input[name=boardNum]").val();

    $.ajax({
        type : 'post',
        url : '/board/search/comment',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            boardNum : boardNum,
            pageNum : page,
            pageSize : 50
        }),
        success : function (result) {
            setListHtml(result);
        }
    });
}

function setListHtml(data) {
    var listHtml = "";

    $(data.list.content).each(function () {
        listHtml += "<div class='comment' id='comment_" + this.id + "' data-id='" + this.id + "' onclick='setCommentChidrenForm(" + this.id + ")'>";
        listHtml += "<div class='x-btn' onclick='deleteCommentBtn(" + this.id + ")'></div>";
        listHtml += "   <div class='author'>" + this.author + "</div>";
        listHtml += "   <div class='content'>" + this.content + "</div>";
        listHtml += "   <div class='createDate'>" + getReplyTime(this.createDate) + "</div>";
        listHtml += "</div>";

        $(this.children).each(function (e) {
            listHtml += "<div class='comment add'>";
            listHtml += "<div class='x-btn' onclick='deleteCommentBtn(" + this.id + ")'></div>";
            listHtml += "    <div class='add-ico'></div>"
            listHtml += "   <div class='author add'>" + this.author + "</div>";
            listHtml += "   <div class='content add'>" + this.content + "</div>";
            listHtml += "   <div class='createDate add'>" + getReplyTime(this.createDate) + "</div>";
            listHtml += "</div>";

        });
    });

    $(".comment-list").html(listHtml);

    var total_comment_cnt = data.totalCount;
    $(".comment-cnt").html("[" + total_comment_cnt + "]");
}

function setCommentChidrenForm(id) {
    if (id == $(".add-comment-form").data("id")) {
        $(".add-comment-form").remove();
        return;
    }

    $(".add-comment-form").remove();

    var strHtml = "";

    strHtml += "<div class='add-comment-form' data-id='" + id + "'>";
    strHtml += "    <div class='add-ico'></div>";
    strHtml += "    <div class='add-user-info'>";
    strHtml += "        <input type='text' class='ipt' name='add-nickname' placeholder='닉네임'> ";
    strHtml += "        <input type='password' class='ipt' name='add-password' placeholder='비밀번호'>";
    strHtml += "    </div>";
    strHtml += "    <div class='add-comment-conetent'>";
    strHtml += "        <textarea name='add-commentContent' maxlength='200' placeholder='내용'></textarea>";
    strHtml += "    </div>";
    strHtml += "    <div class='btn-area'>";
    strHtml += "        <button class='btn on small' onclick='registerCommentToComment(" + id + ")'>등록</button>";
    strHtml += "    </div>";
    strHtml += "</div>";

    $("#comment_" + id).after(strHtml);
}

function registerComment () {
    var boardId = $("input[name=boardNum]").val();
    var nickname = $("input[name=nickname]").val();
    var commentPassword = $("input[name=commentPassword]").val();
    var commentContent = $("textarea[name=commentContent]").val();

    if (isEmpty(nickname) || lengthCheck(nickname, 1)) {
        alert("닉네임을 입력해주세요.")
        return;
    }

    if (isEmpty(commentPassword)) {
        alert("비밀번호를 입력해주세요.")
        return;
    }

    if (lengthCheck(commentPassword, 4)) {
        alert("비밀번호를 최소 4자리 이상 입력하셔야 합니다. 쉬운 비밀번호는 타인이 수정 또는 삭제하기 쉬우니, 어려운 비밀번호를 입력해 주세요.");
        return;
    }

    if (isEmpty(commentContent) || lengthCheck(commentContent, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    $.ajax({
        type : 'post',
        url : '/board/save/comment',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            author : nickname,
            password : commentPassword,
            content : commentContent,
            boardId : boardId
        }),
        success : function () {
            getCommentList(currentCommentPage);

            $("input[name=nickname]").val("");
            $("input[name=commentPassword]").val("");
            $("textarea[name=commentContent]").val("");

        }
    });
}

function registerCommentToComment (parent_id) {
    var boardId = $("input[name=boardNum]").val();
    var nickname = $("input[name=add-nickname]").val();
    var commentPassword = $("input[name=add-password]").val();
    var commentContent = $("textarea[name=add-commentContent]").val();

    if (isEmpty(nickname) || lengthCheck(nickname, 1)) {
        alert("닉네임을 입력해주세요.")
        return;
    }

    if (isEmpty(commentPassword)) {
        alert("비밀번호를 입력해주세요.")
        return;
    }

    if (lengthCheck(commentPassword, 4)) {
        alert("비밀번호를 최소 4자리 이상 입력하셔야 합니다. 쉬운 비밀번호는 타인이 수정 또는 삭제하기 쉬우니, 어려운 비밀번호를 입력해 주세요.");
        return;
    }

    if (isEmpty(commentContent) || lengthCheck(commentContent, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    $.ajax({
        type : 'post',
        url : '/board/save/comment',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            author : nickname,
            password : commentPassword,
            content : commentContent,
            boardId : boardId,
            parentId : parent_id
        }),
        success : function () {
            getCommentList(currentCommentPage);

            $(".add-comment-form").remove();
        }
    });
}

function modifyBtn() {
    openModal('비밀번호를 입력하세요.', 'type1', function () {
        var boardNum = $("input[name=boardNum]").val();
        var password = $("input[name=modal-password]").val();

        if (isEmpty(password)) {
            modalClose();

            alert("비밀번호를 입력해주세요.");
            return;
        }

        $.ajax({
            type : 'post',
            url : '/board/check',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            data : JSON.stringify({
                boardNum : boardNum,
                password : password,
                type: "modify"
            }),
            success : function (data) {
                if (data.result) {
                    location.href=data.redirect + "&" + makeQueryUrl();
                } else {
                    modalClose();
                    alert ("비밀번호가 틀렸습니다.");
                }

            }
        });
    });
}

function deleteBtn() {
    openModal('비밀번호를 입력하세요.', 'type1', function () {
        var boardNum = $("input[name=boardNum]").val();
        var password = $("input[name=modal-password]").val();

        if (isEmpty(password)) {
            modalClose();

            alert("비밀번호를 입력해주세요.");
            return;
        }

        $.ajax({
            type : 'post',
            url : '/board/check',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            data : JSON.stringify({
                boardNum : boardNum,
                password : password,
                type: "delete"
            }),
            success : function (data) {
                if (data.result) {
                    location.href=data.redirect;
                } else {
                    modalClose();
                    alert ("비밀번호가 틀렸습니다.");
                }

            }
        });
    });
}

function deleteCommentBtn(commentNum) {
    event.stopPropagation();

    openModal('비밀번호를 입력하세요.', 'type1', function () {
        var password = $("input[name=modal-password]").val();

        if (isEmpty(password)) {
            modalClose();

            alert("비밀번호를 입력해주세요.");
            return;
        }

        $.ajax({
            type : 'post',
            url : '/board/delete/comment',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            data : JSON.stringify({
                commentNum : commentNum,
                password : password
            }),
            success : function (data) {
                if (data) {
                    getCommentList(currentCommentPage);
                    modalClose();
                } else {
                    modalClose();

                    alert("비밀번호가 틀립니다.");
                }

            }
        });
    });
}

function getBoardTime(timeValue) {
    var dateObj = new Date(timeValue);

    var year = dateObj.getFullYear();
    var month = dateObj.getMonth();
    var date = dateObj.getDate();
    var hours = ('0' + dateObj.getHours()).slice(-2);
    var minutes = ('0' + dateObj.getMinutes()).slice(-2);
    var seconds = ('0' + dateObj.getSeconds()).slice(-2);

    return year + "." + month + "." + date + " " + hours + ":" + minutes;
}

function getReplyTime(timeValue) {
    var dateObj = new Date(timeValue);
    var today = new Date();

    var tYear = today.getFullYear();
    var tMonth = today.getMonth();
    var tDate = today.getDate();
    var todayDate = tYear+tMonth+tDate;

    var year = dateObj.getFullYear();
    var month = dateObj.getMonth();
    var date = dateObj.getDate();
    var hours = ('0' + dateObj.getHours()).slice(-2);
    var minutes = ('0' + dateObj.getMinutes()).slice(-2);
    var seconds = ('0' + dateObj.getSeconds()).slice(-2);
    var boardDate = year+month+date;


    return month + '.' + date + " " + hours + ':' + minutes;
}