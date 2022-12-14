const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const bType = urlParams.get('type');

var currentCommentPage = 0;
var isRunning = false;

var leftBanner = "";
var isLeftShow = "";
var leftIndex = 0;

var rightBanner = "";
var isRightShow = "";
var rightIndex = 0;

$(document).ready(function () {
    $(".createDate").html(getBoardTime($(".createDate").html()));

    getCommentList(currentCommentPage);

    getList(0);

    isLeftShow = $("input[name=leftBannerShow]").val();
    if (isLeftShow == 'true') {
        var bannerLink = $("input[name=leftBannerLink]").val();
        var bannerUrl = $("input[name=leftBannerUrl]").val();
        var bannerBoard = $("input[name=leftBannerBoard]").val();
        leftBanner += "<div class='comment listBanner' style='background: url(" + bannerUrl + ") no-repeat; background-size: contain; background-position: center;' onclick='clickBanner(\"" + bannerLink + "\", \"" + bannerBoard + "\")'></div>";
    }

    isRightShow = $("input[name=rightBannerShow]").val();
    if (isRightShow == 'true') {
        var bannerLink = $("input[name=rightBannerLink]").val();
        var bannerUrl = $("input[name=rightBannerUrl]").val();
        var bannerBoard = $("input[name=rightBannerBoard]").val();
        rightBanner += "<div class='board listBanner' style='background: url(" + bannerUrl + ") no-repeat; background-size: contain; background-position: center;' onclick='clickBanner(\"" + bannerLink + "\", \"" + bannerBoard + "\")'></div>";
    }
});

function moveScrollToComment() {
    var paramCommentId = urlParams.get('commentId');
    if (!isEmpty(paramCommentId)) {
        urlParams.delete("commentId")
        var byId = 'comment_' + paramCommentId;
        const moveComment = document.getElementById(byId);
        window.scrollBy({top: moveComment.getBoundingClientRect().top, behavior: 'smooth'});

        var bc = $("#" + byId).css("background-color");
        $("#" + byId).css("background-color", "#FD8B8B");

        setTimeout(function () {
            $("#" + byId).css("background-color", bc);
        }, 3000);
    }
}

function goList() {
    location.href = "/board/list?" + makeQueryUrl();
}

function goRegister() {
    location.href = "/board/register?" + makeQueryUrl();
}



function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();
    var keyword = $("input[name=keyword]").val();
    var content = $("input[name=content]").val();

    return encodeURI("type=" + bType + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content);
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
            moveScrollToComment();
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");

        },
        complete : function () {
            $(".div_load_image").css("display", "none");
        }
    });
}

function setListHtml(data) {
    var listHtml = "";
    var isLogin = $("input[name=isLogin]").val();
    var loginId = $("input[name=loginId]").val();

    $(data.list.content).each(function () {
        listHtml += "<div class='comment' id='comment_" + this.id + "' data-id='" + this.id + "' onclick='setCommentChidrenForm(" + this.id + ")'>";
        if (this.user) {
            if (isLogin === 'true' && this.userId == loginId) {
                // ????????? = ?????????
                listHtml += "   <div class='x-btn' onclick='deleteCommentBtnByOwner(" + this.id + ")'></div>";
                listHtml += "   <div class='author'>" + this.author + "</div>";
            } else {
                // ?????? ??????
                listHtml += "   <div class='author'>" + this.author + "</div>";
            }
        } else {
            // ????????? ??????
            if (isLogin === 'true') {
                listHtml += "   <div class='author'>" + this.author + " (" + this.ip + ")</div>";
            } else {
                listHtml += "   <div class='x-btn' onclick='deleteCommentBtn(" + this.id + ")'></div>";
                listHtml += "   <div class='author'>" + this.author + " (" + this.ip + ")</div>";
            }
        }

        listHtml += "   <div class='content'>" + this.content + "</div>";
        listHtml += "   <div class='createDate'>" + getReplyTime(this.createDate) + "</div>";
        listHtml += "</div>";

        $(this.children).each(function (e) {
            listHtml += "<div class='comment add' id='comment_" + this.id + "'>";

            if (this.user) {
                if (isLogin === 'true' && this.userId == loginId) {
                    // ????????? = ?????????
                    listHtml += "   <div class='x-btn' onclick='deleteCommentBtnByOwner(" + this.id + ")'></div>";
                    listHtml += "    <div class='add-ico'></div>"
                    listHtml += "   <div class='author add'>" + this.author + "</div>";
                } else {
                    // ?????? ??????
                    listHtml += "    <div class='add-ico'></div>"
                    listHtml += "   <div class='author add'>" + this.author + "</div>";
                }
            } else {
                // ????????? ??????
                if (isLogin === 'true') {
                    listHtml += "    <div class='add-ico'></div>"
                    listHtml += "   <div class='author add'>" + this.author + " (" + this.ip + ")</div>";
                } else {
                    listHtml += "   <div class='x-btn' onclick='deleteCommentBtn(" + this.id + ")'></div>";
                    listHtml += "    <div class='add-ico'></div>"
                    listHtml += "   <div class='author add'>" + this.author + " (" + this.ip + ")</div>";
                }
            }

            listHtml += "   <div class='content add'>" + this.content + "</div>";
            listHtml += "   <div class='createDate add'>" + getReplyTime(this.createDate) + "</div>";
            listHtml += "</div>";

            if (leftIndex == 15 && isLeftShow == 'true') {
                listHtml += leftBanner;
                leftIndex = 0;
            }

            leftIndex++;
        });

        if (leftIndex == 15 && isLeftShow == 'true') {
            listHtml += leftBanner;
            leftIndex = 0;
        }

        leftIndex++;
    });

    $(".comment-list").html(listHtml);

    var total_comment_cnt = data.totalCount;
    $(".comment-cnt").html("[" + total_comment_cnt + "]");
    $(".view-replay-cnt").text(total_comment_cnt);
}

function setCommentChidrenForm(id) {
    if (id == $(".add-comment-form").data("id")) {
        $(".add-comment-form").remove();
        return;
    }

    $(".add-comment-form").remove();

    var isLogin = $("input[name=isLogin]").val();
    var strHtml = "";

    strHtml += "<div class='add-comment-form' data-id='" + id + "'>";
    strHtml += "    <div class='add-ico'></div>";
    if (isLogin === 'false') {
        strHtml += "    <div class='add-user-info'>";
        strHtml += "        <input type='text' class='ipt' name='add-nickname' placeholder='?????????'> ";
        strHtml += "        <input type='password' class='ipt' name='add-password' placeholder='????????????'>";
        strHtml += "    </div>";
    }
    strHtml += "    <div class='add-comment-conetent'>";
    strHtml += "        <textarea name='add-commentContent' maxlength='200' placeholder='??????'></textarea>";
    strHtml += "    </div>";
    strHtml += "    <div class='btn-area'>";
    if (isLogin === 'false') {
        strHtml += "        <button class='btn on small' onclick='registerCommentToComment(" + id + ")'>??????</button>";
    } else {
        // ????????? ??????
        strHtml += "        <button class='btn on small' onclick='registerCommentToCommentByUser(" + id + ")'>??????</button>";
    }
    strHtml += "    </div>";
    strHtml += "</div>";

    $("#comment_" + id).after(strHtml);
}

function registerComment () {
    if (isRunning == true) {
        return;
    }

    var boardId = $("input[name=boardNum]").val();
    var nickname = $("input[name=nickname]").val();
    var commentPassword = $("input[name=commentPassword]").val();
    var commentContent = $("textarea[name=commentContent]").val();

    if (isEmpty(nickname) || lengthCheckUnder(nickname, 1)) {
        alert("???????????? ??????????????????.");
        return;
    }

    if (isEmpty(commentPassword)) {
        alert("??????????????? ??????????????????.");
        return;
    }

    if (lengthCheckUnder(commentPassword, 4)) {
        alert("??????????????? ?????? 4?????? ?????? ??????????????? ?????????. ?????? ??????????????? ????????? ?????? ?????? ???????????? ?????????, ????????? ??????????????? ????????? ?????????.");
        return;
    }

    if (isEmpty(commentContent) || lengthCheckUnder(commentContent, 1)) {
        alert("?????? ??????????????????.");
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
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

function registerCommentByOwner () {
    if (isRunning == true) {
        return;
    }

    var boardId = $("input[name=boardNum]").val();
    var commentContent = $("textarea[name=commentContent]").val();

    if (isEmpty(commentContent) || lengthCheckUnder(commentContent, 1)) {
        alert("?????? ??????????????????.")
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
            content : commentContent,
            boardId : boardId
        }),
        success : function () {
            getCommentList(currentCommentPage);

            $("textarea[name=commentContent]").val("");
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

function registerCommentToComment (parent_id) {
    if (isRunning == true) {
        return;
    }

    var boardId = $("input[name=boardNum]").val();
    var nickname = $("input[name=add-nickname]").val();
    var commentPassword = $("input[name=add-password]").val();
    var commentContent = $("textarea[name=add-commentContent]").val();

    if (isEmpty(nickname) || lengthCheckUnder(nickname, 1)) {
        alert("???????????? ??????????????????.")
        return;
    }

    if (isEmpty(commentPassword)) {
        alert("??????????????? ??????????????????.")
        return;
    }

    if (lengthCheckUnder(commentPassword, 4)) {
        alert("??????????????? ?????? 4?????? ?????? ??????????????? ?????????. ?????? ??????????????? ????????? ?????? ?????? ???????????? ?????????, ????????? ??????????????? ????????? ?????????.");
        return;
    }

    if (isEmpty(commentContent) || lengthCheckUnder(commentContent, 1)) {
        alert("?????? ??????????????????.")
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
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

function registerCommentToCommentByUser (parent_id) {
    if (isRunning == true) {
        return;
    }

    var boardId = $("input[name=boardNum]").val();
    var commentContent = $("textarea[name=add-commentContent]").val();
    var isLogin = $("input[name=isLogin]").val();

    if (isLogin === 'false') {
        alert("????????????????????????.")
        return;
    }

    if (isEmpty(commentContent) || lengthCheckUnder(commentContent, 1)) {
        alert("?????? ??????????????????.")
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
            content : commentContent,
            boardId : boardId,
            parentId : parent_id
        }),
        success : function () {
            getCommentList(currentCommentPage);

            $(".add-comment-form").remove();
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

function modifyBtn() {
    openModal('??????????????? ???????????????.', 'type1', function () {
        if (isRunning == true) {
            return;
        }

        var boardNum = $("input[name=boardNum]").val();
        var password = $("input[name=modal-password]").val();

        if (isEmpty(password)) {
            modalClose();

            alert("??????????????? ??????????????????.");
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
                    alert ("??????????????? ???????????????.");
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
            }
        });
    });
}

function likesBtn() {
    if (isRunning == true) {
        return;
    }

    var boardNum = $("input[name=boardNum]").val();

    $.ajax({
        type : 'post',
        url : '/board/likes',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            boardId : boardNum
        }),
        success : function (data) {
            $(".likes-cnt").text(data.likes);
            $(".view-recom").text(data.likes);

            if (data.isLikes) {
                $(".like-ico").addClass("on");
            } else {
                $(".like-ico").removeClass("on")
            }
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

function deleteBtn() {
    openModal('??????????????? ???????????????.', 'type1', function () {
        if (isRunning == true) {
            return;
        }

        var boardNum = $("input[name=boardNum]").val();
        var password = $("input[name=modal-password]").val();

        if (isEmpty(password)) {
            modalClose();

            alert("??????????????? ??????????????????.");
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
                    goList();
                } else {
                    modalClose();
                    alert ("??????????????? ???????????????.");
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
            }

        });
    });
}

function deleteBtnByOwner() {
    openModal('????????? ???????????????????', 'type2', function () {
        if (isRunning == true) {
            return;
        }

        var boardNum = $("input[name=boardNum]").val();

        $.ajax({
            type : 'post',
            url : '/board/deleteByOwner',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            data : JSON.stringify({
                boardNum : boardNum,
                type: "delete"
            }),
            success : function (data) {
                if (data.result) {
                    goList();
                } else {
                    modalClose();
                    alert ("??????????????? ???????????????.");
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
            }
        });
    });
}

function modifyBtnByOwner() {
    openModal('?????? ???????????????????', 'type2', function () {
        if (isRunning == true) {
            return;
        }

        var boardNum = $("input[name=boardNum]").val();

        $.ajax({
            type : 'post',
            url : '/board/modifyByOwner',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            async: false,
            data : JSON.stringify({
                boardNum : boardNum,
                type: "delete"
            }),
            success : function (data) {
                if (data.result) {
                    location.href=data.redirect + "&" + makeQueryUrl();
                } else {
                    modalClose();
                    alert ("??????????????? ???????????????.");
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
            }

        });
    });
}

function deleteCommentBtn(commentNum) {
    event.stopPropagation();

    openModal('??????????????? ???????????????.', 'type1', function () {
        if (isRunning == true) {
            return;
        }

        var password = $("input[name=modal-password]").val();

        if (isEmpty(password)) {
            modalClose();

            alert("??????????????? ??????????????????.");
            return;
        }

        $.ajax({
            type: 'post',
            url: '/board/delete/comment',
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                commentNum: commentNum,
                password: password
            }),
            success: function (data) {
                if (data) {
                    getCommentList(currentCommentPage);
                    modalClose();
                } else {
                    modalClose();

                    alert("??????????????? ????????????.");
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
            }
        });
    });
}

function deleteCommentBtnByOwner(commentNum) {
    event.stopPropagation();

    openModal('????????? ?????????????????????????', 'type2', function () {
        if (isRunning == true) {
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
            async: false,
            data : JSON.stringify({
                commentNum : commentNum
            }),
            success : function (data) {
                if (data) {
                    getCommentList(currentCommentPage);
                }
            },
            beforeSend : function () {
                $(".div_load_image").css("display", "block");
                isRunning = true;
            },
            complete : function () {
                $(".div_load_image").css("display", "none");
                isRunning = false;
                modalClose();
            }
        });
    });
}

function setBookMark() {
    var boardNum = $("input[name=boardNum]").val();

    if (isRunning == true) {
        return;
    }

    $.ajax({
        type : 'post',
        url : '/board/bookmark',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        async: false,
        data : JSON.stringify({
            board_id : boardNum
        }),
        success : function (data) {
            if (data.result) {
                if ($(".bookmark-ico").hasClass("off")) {
                    $(".bookmark-ico").removeClass("off");
                    $(".bookmark-ico").addClass("on");

                    openModal('?????? ???????????? <br>??????????????? ??????????????????.', 'type3', null);
                } else {
                    $(".bookmark-ico").removeClass("on");
                    $(".bookmark-ico").addClass("off");

                    openModal("?????? ???????????? <br>?????????????????? ??????????????????.", "type3", null);
                }
            }
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
            isRunning = true;
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isRunning = false;
        }
    });
}

function getBoardTime(timeValue) {
    var dateObj = new Date(timeValue);

    var year = dateObj.getFullYear();
    var month = dateObj.getMonth() + 1;
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
    var tMonth = today.getMonth() + 1;
    var tDate = today.getDate();
    var todayDate = tYear+tMonth+tDate;

    var year = dateObj.getFullYear();
    var month = dateObj.getMonth() + 1;
    var date = dateObj.getDate();
    var hours = ('0' + dateObj.getHours()).slice(-2);
    var minutes = ('0' + dateObj.getMinutes()).slice(-2);
    var seconds = ('0' + dateObj.getSeconds()).slice(-2);
    var boardDate = year+month+date;


    return month + '.' + date + " " + hours + ':' + minutes;
}

function getList(page) {
    var content = $("input[name=content]").val();
    var keyword = $("input[name=keyword]").val();
    var boardNum = $("input[name=boardNum]").val();

    $.ajax({
        type : 'post',
        url : '/board/detailList',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            best : best,
            type : bType,
            category : category,
            keyword: keyword,
            content: content,
            pageNum : page,
            boardNum : boardNum
        }),
        success : function (result) {
            setListHtmlForBoard(result);
        },
        beforeSend : function () {
            $(".div_load_image").css("display", "block");
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
        }
    });
}

function setListHtmlForBoard(data) {
    var listHtml = "";

    $(data.list.content).each(function () {
        listHtml += "<div class='board' data-id='" + this.id + "' onclick='goView(" + this.id + ")'>";
        listHtml += "   <div class='content'>";
        listHtml += "       <div class='title'>";

        if (this.img) {
            listHtml += "<div class='img-ico'></div>";
        } else {
            listHtml += "<div class='talk-ico'></div>";
        }

        listHtml += this.title;
        listHtml += "</div>";
        listHtml += "       <div class='info'>";

        if (this.user) {
            listHtml += "           <div><b>" + this.username + "</b></div>";
        } else {
            listHtml += "           <div>" + this.author + "(" + this.ip + ")</div>";
        }

        listHtml += "           <div>" + getBoardTime(this.createDate) + "</div>";
        listHtml += "           <div class='view-count'>" + this.view + "</div>";
        listHtml += "           <div class='likes-count'>" + this.likes + "</div>";
        listHtml += "       </div>";
        listHtml += "   </div>";
        listHtml += "   <div class='reply'>" + this.commentCount + "</div>";
        listHtml += "</div>";

        if (rightIndex == 15 && isRightShow == 'true') {
            listHtml += rightBanner;
            rightIndex=0;
        }

        rightIndex++;
    });

    $(".board-list").append(listHtml);

    //  ????????? ??????
    if (data.list.last != true) {
        $(".more-btn").remove();

        var page = Number(data.list.pageable.pageNumber) + 1;
        var pageSize = Number(15);

        var moreBtnHtml = "";
        moreBtnHtml += "<div class='more-btn' onclick='getList(" + page + ")'>";
        moreBtnHtml += "<div class='btn_img'></div>";
        moreBtnHtml += "</div>";

        $(".board-list").append(moreBtnHtml);
    } else {
        $(".more-btn").remove();
    }
}

function goView(id) {
    location.href = "/board/view?boardNum=" + id + "&" + makeQueryUrl();
}

/**
 * URL ?????? ??????
 */
function shareBtn() {
    var boardNum = $("input[name=boardNum]").val();
    var url = '';
    var textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    url = encodeURI(window.document.location.origin + "/board/view?boardNum=" + boardNum + "&type=all&best=&category=" + category + "&pageNum=0&keyword=all&content=");
    textarea.value = url;
    textarea.select();
    document.execCommand("copy");
    document.body.removeChild(textarea);
    openModal("URL??? ?????????????????????.", "type3", null);
}

function goUserModal(id, author) {
    $(".user-modal-content").attr("data-author", author);

    var popT = $("#" + id).offset().top; //fe_laypopH ??? top ?????????
    var popL = $("#" + id).offset().left; //fe_laypopH ??? top ?????????
    $('.user-modal-display').css('top',popT+20+'px'); //??????????????? style="(top ????????? - 100)px";
    $('.user-modal-display').css('left',popL+'px'); //??????????????? style="(top ????????? - 100)px";
    $(".user-modal").css("display", "block");

}

function closeUserModal() {
    $(".user-modal").css("display", "none");
}

function authorSearch() {
    var author = $(".user-modal-content").data("author");

    location.href = encodeURI("/pc/board/list?type=all&best=&category=thai&pageNum=0&keyword=author&content=" + author);
}