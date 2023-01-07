$(document).ready(function () {

});

function goClose() {
    location.href="/close";
}

function goBack() {
    location.href="/menu/back"
}

function goAccount() {
    location.href="/menu/account";
}

function goNotice() {
    location.href="/menu/notice";
}

function goMyList() {
    location.href="/menu/myList?page=0&pageSize=15";
}

function goComment() {
    location.href="/menu/myComment?page=0&pageSize=15";
}

function goMyNoti() {
    location.href="/menu/myNoti?page=0&pageSize=15";
}

function goBookMark() {
    location.href="/menu/bookmark?page=0&pageSize=15";
}

function goLogin() {
    location.href="/login";
}

function goLogout() {
    location.href="/logout";
}

function goSignUp() {
    location.href="/signUp";
}