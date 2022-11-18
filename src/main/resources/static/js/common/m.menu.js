$(document).ready(function () {

});

function goMenuClose() {
    history.back();
}

function goClose() {
    history.back();
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

function goMyNoti() {
    location.href="/menu/myNoti?page=0&pageSize=15";
}