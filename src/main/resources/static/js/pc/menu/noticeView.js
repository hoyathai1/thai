const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const type = urlParams.get('type');

$(document).ready(function () {
    $(".createDate").html(getBoardTime($(".createDate").html()));
});

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();

    return encodeURI("pageNum=" + pageNum);
}

function goNotice() {
    location.href = "/pc/menu/notice?" + makeQueryUrl();
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

function goCategory(pCategory) {
    location.href = encodeURI("/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&keyword=all&content=");
}

function goList() {
    location.href = "/pc/menu/notice?" + makeQueryUrl();
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

