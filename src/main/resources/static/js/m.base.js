function isEmpty(str) {
    if (str.replace(/\s| /gi, "").length == 0) {
        return true;
    }

    if (str == null || str == "" || str == undefined || str == "undefined") {
        return true;
    }

    return false;
}

/*
    길이 체크
    length 보다 짧으면 return true
 */
function lengthCheck(str, length) {
    var replaceStr = str.replace(/\s/g, "");    // 공백 제거

    if (replaceStr.length  < length) {
        return true
    }

    return false
}

/**
 *
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
            "            <input class='ipt' type='password' name='modal-password'>" +
            "            <div class='modal-btn-area btn-area'>" +
            "                <button class='btn two' onclick='modalClose()'>취소</button>" +
            "                <button class='btn right two on' id='modal-btn'>확인</button>" +
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

