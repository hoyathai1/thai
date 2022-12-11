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