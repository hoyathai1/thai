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