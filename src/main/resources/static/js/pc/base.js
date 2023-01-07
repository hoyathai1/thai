function detectMobileDevice(agent) {
    const mobileRegex = [
        /Android/i,
        /iPhone/i,
        /iPad/i,
        /iPod/i,
        /BlackBerry/i,
        /Windows Phone/i
    ]

    return mobileRegex.some(mobile => agent.match(mobile))
}

function isEmpty(str) {
    if (str == null || str == "" || str == undefined || str == "undefined") {
        return true;
    }

    if (str.replace(/\s| /gi, "").length == 0) {
        return true;
    }

    return false;
}

/*
    길이 체크
    length 보다 짧으면 return true
 */
function lengthCheckUnder(str, length) {
    var replaceStr = str.replace(/\s/g, "");    // 공백 제거

    if (replaceStr.length  < length) {
        return true
    }

    return false
}

/*
    길이 체크
    length 보다 길면 return true
 */
function lengthCheckOver(str, length) {
    var replaceStr = str.replace(/\s/g, "");    // 공백 제거

    if (replaceStr.length  > length) {
        return true
    }

    return false
}

/*
    한글 체크
 */
function checkKor(str) {
    const regExp = /[ㄱ-ㅎㅏ-ㅣ가-힣]/g;
    if(regExp.test(str)){
        return true;
    }else{
        return false;
    }
}


/*
    영어 체크
 */
function checkEng(str) {
    const regExp = /[a-zA-Z]/g; // 영어
    if(regExp.test(str)){
        return true;
    }else{
        return false;
    }
}

/**
 * 숫자체크
 */
function checkNum(str) {
    const regExp = /[0-9]/g;
    if(regExp.test(str)){
        return true;
    }else{
        return false;
    }
}

/**
 * 특수 문자 체크
 * @param str
 * @returns {boolean}
 */
function checkSpecial(str) {
    const regExp = /[`~!@#$%_=+^&*|\\\'\";:\/?]/gi;
    if(regExp.test(str)) {
        return true;
    }else{
        return false;
    }
}

/**
 *  type1 - 확인버튼
 *  type2 - 확인버튼-function
 *  type3 - 삭제
 *
 * @param msg
 * @param type
 * @param callback
 */
function openModal(msg, type, callback) {
    if ($(".modal").length) {
        $(".modal").remove();
    }

    var modalHtml = "";

    if (type == 'type1') {
        $(document).off("click", "#modal-btn");

        modalHtml = "<div class='modal'>" +
            "    <div class='modal-display'>" +
            "        <div class='modal-content'>" +
            "            <b>" + msg + "</b>" +
            "            <div class='modal-btn-area btn-area'>" +
            "                <button class='btn two' onclick='modalClose()'>확인</button>" +
            "            </div>" +
            "        </div>" +
            "    </div>" +
            "</div>";
    } else if (type == 'type2') {
        $(document).off("click", "#modal-btn");

        modalHtml = "<div class='modal'>" +
            "    <div class='modal-display'>" +
            "        <div class='modal-content'>" +
            "            <b>" + msg + "</b>" +
            "            <div class='modal-btn-area btn-area'>" +
            "                <button class='btn two' onclick='modalClose()'>취소</button>" +
            "                <button class='btn right two on' id='modal-btn'>확인</button>" +
            "            </div>" +
            "        </div>" +
            "    </div>" +
            "</div>";

        $(document).on("click", "#modal-btn", callback);
    } else if (type == 'type3') {
        $(document).off("click", "#modal-btn");

        modalHtml = "<div class='modal'>" +
            "    <div class='modal-display'>" +
            "        <div class='modal-content'>" +
            "            <b>" + msg + "</b>" +
            "            <input class='ipt' type='password' name='modal-password'>" +
            "            <div class='modal-btn-area btn-area'>" +
            "                <button class='btn two' onclick='modalClose()'>취소</button>" +
            "                <button class='btn right two on' id='modal-btn'>확인</button>" +
            "            </div>" +
            "        </div>" +
            "    </div>" +
            "</div>";


        $(document).on("click", "#modal-btn", callback);
    } else if (type == 'type4') {
        $(document).off("click", "#modal-btn");

        modalHtml = "<div class='modal'>" +
            "    <div class='modal-display'>" +
            "        <div class='modal-content'>" +
            "            <b>" + msg + "</b>" +
            "            <div class='modal-btn-area btn-area'>" +
            "                <button class='btn two' id='modal-btn'>확인</button>" +
            "            </div>" +
            "        </div>" +
            "    </div>" +
            "</div>";

        $(document).on("click", "#modal-btn", callback);
    }

    $(".container").append(modalHtml);
    $(".modal").css("display", "block");
    setModal();
}

function modalClose() {
    //$(".modal").css("display","none");
    $(".modal").remove();

}

/*
    모달창 배경누르면 닫기
 */
function setModal() {
    document.querySelector('.modal').addEventListener('click', function(e){
        if ( e.target == document.querySelector('.modal') ) {
            $(".modal").css("display","none");
        }
    });
}

function sleep (delay) {
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}


/***
 * 게시판 이미지 리사이즈
 *
 * @param file
 * @param quality
 * @param maxsize
 */
function resizeImage(file, quality, maxsize) {
    const reader = new FileReader();

    reader.onload = (base64) => {
        const image = new Image();

        image.src = base64.target.result;

        image.onload = (e) => {
            const $canvas = document.createElement('canvas');
            const ctx = $canvas.getContext('2d');

            let width = e.target.width;
            let height = e.target.height;

            if (width > height) {
                if (width > maxsize) {
                    height *= maxsize/width;
                    width = maxsize;
                }
            } else {
                if (height > maxsize) {
                    width *= maxsize / height;
                    height = maxsize;
                }
            }

            $canvas.width = width;
            $canvas.height = height;

            ctx.drawImage(e.target, 0, 0, width, height);

            // 용량이 줄어든 base64 이미지
            focusEditor();
            document.execCommand('insertImage', false, $canvas.toDataURL('image/jpeg', quality));
        }

    };

    reader.readAsDataURL(file);
}


/*
* 사이드 바
*
* */
function getNotiList() {
    $.ajax({
        type : 'post',
        url : '/pc/board/boardNoti',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            pageNum : 0
        }),
        success : function (data) {
            setNotiListHtml(data);
        },
        beforeSend : function () {
        },
        complete : function () {
        }
    });
}

function allDeleteNoti() {
    if ($(".alert-modal-content div").length == 0) {
        return;
    }

    $.ajax({
        type : 'post',
        url : '/pc/board/allDeleteNoti',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
        }),
        success : function (data) {
            $(".alert-modal-content").html("");
        },
        beforeSend : function () {
        },
        complete : function () {
        }
    });
}

function deleteNoti(id) {
    $.ajax({
        type : 'post',
        url : '/pc/board/deleteNoti',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            boardNotiId : id
        }),
        success : function (data) {
            $("#" + id).remove();
        },
        beforeSend : function () {
        },
        complete : function () {
        }
    });
}

function setNotiListHtml(data) {
    var listHtml = "";

    $(data.list.content).each(function () {
        listHtml += "<div id='" + this.id + "'>";
        listHtml += "   <div class='option' onclick='deleteNoti(" + this.id + ")'>삭제</div>";
        listHtml += "   <div class='title' onclick='goNoti(" + this.id + "," + this.boardId + "," + this.commentId + ")'>";
        listHtml += "       " + this.title + " - " + this.content;
        listHtml += "   </div>";
        listHtml += "</div>";
    });

    $(".alert-modal-content").html(listHtml);
}

function goNoti(id, boardNum, commenId) {
    deleteNoti(id);

    location.href="/board/view?pageNum=0&type=all&category=" +category + "&boardNum=" + boardNum + "&commentId=" + commenId;
}

function goNotiModal() {
    getNotiList();

    var popT = $(".alert-ico").offset().top; //fe_laypopH 의 top 좌표값
    var popL = $(".alert-ico").offset().left; //fe_laypopH 의 top 좌표값
    $('.alert-modal-display').css('top',popT+20+'px'); //레이어팝업 style="(top 좌표값 - 100)px";
    $('.alert-modal-display').css('left',popL-180+'px'); //레이어팝업 style="(top 좌표값 - 100)px";
    $(".alert-modal").css("display", "block");
    $("#alert-ico-id").removeClass("on");
}

function goUserModal(id, author) {
    $(".user-modal-content").attr("data-author", author);

    var popT = $("#" + id).offset().top; //fe_laypopH 의 top 좌표값
    var popL = $("#" + id).offset().left; //fe_laypopH 의 top 좌표값
    $('.user-modal-display').css('top',popT+20+'px'); //레이어팝업 style="(top 좌표값 - 100)px";
    $('.user-modal-display').css('left',popL+'px'); //레이어팝업 style="(top 좌표값 - 100)px";
    $(".user-modal").css("display", "block");

}

function closeNotiModal() {
    $(".alert-modal").css("display", "none");
}

function closeUserModal() {
    $(".user-modal").css("display", "none");
}

function authorSearch() {
    var author = $(".user-modal-content").data("author");

    location.href = "/pc/board/list?type=all&best=&category=thai&pageNum=0&keyword=author&content=" + author;
}

function clickBanner(url, isBoard) {
    if (isBoard == 'true') {
        location.href = "/pc/board/inform?boardNum=" + url + "&" + makeQueryUrl();
    } else {
        window.open(url);
    }
}