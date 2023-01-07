function bannerBtn(device, position) {
    var posLowCase = position.toLowerCase()
    var bannerLink = $("#" + posLowCase + "BannerLink").val();

    if (bannerLink == null || bannerLink == "" || bannerLink == undefined || bannerLink == "undefined") {
        alert("링크 주소를 등록해주세요.");
        return;
    }

    var fileName = $("#" + posLowCase + "Img");
    var uploadFile = $("#" + posLowCase + "Img")[0].files[0];
    if (uploadFile == null || uploadFile == undefined) {
        modifyLink(device, position);
    } else {
        imgRegister(device, position);
    }
}

function imgRegister(device, position) {
    var posLowCase = position.toLowerCase()
    var category = $("#category").val();
    var bannerLink = $("#" + posLowCase + "BannerLink").val();
    var bannerInBoard =  $("#" + posLowCase +  "BannerIsBoard").is(":checked");

    if (bannerLink == null || bannerLink == "" || bannerLink == undefined || bannerLink == "undefined") {
        alert("링크 주소를 등록해주세요.");
        return;
    }

    var fileName = $("#" + posLowCase + "Img");

    if (fileName == null || fileName == undefined) {
        alert("이미지를 등록해주세요.");
        return;
    }

    var form = $("#" + posLowCase + "Form")[0];
    var formData = new FormData(form);

    $.ajax({
        type : 'post',
        url : '/admin/banner/upload',
        enctype: 'multipart/form-data',
        async: false,
        processData: false,
        contentType: false,
        dataType : 'text',
        data : formData,
        success : function () {

        },
        beforeSend : function () {

        }
    });

    sleep(100);

    $.ajax({
        type : 'post',
        url : '/admin/banner/save',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        async: false,
        data : JSON.stringify({
            category: category,
            link : bannerLink,
            device : device,
            position : position,
            isBoard : bannerInBoard
        }),
        success : function () {
            location.href = "/admin/banner/pc?category=" + category;
        },
        beforeSend : function () {

        }
    });
}

function modifyLink(device, position) {
    var posLowCase = position.toLowerCase();
    var category = $("#category").val();
    var bannerLink = $("#" + posLowCase + "BannerLink").val();
    var bannerInBoard =  $("#" + posLowCase +  "BannerIsBoard").is(":checked");

    $.ajax({
        type : 'post',
        url : '/admin/banner/saveLink',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        async: false,
        data : JSON.stringify({
            category: category,
            link : bannerLink,
            device : device,
            position : position,
            board : bannerInBoard
        }),
        success : function () {
            location.href = "/admin/banner/pc?category=" + category;
        },
        beforeSend : function () {

        }
    });
}

function topImgChange() {
    var name = $("#topImg")[0].files[0].name;
    $("#topImgTxt").text(name);
}

function bottomImgChange() {
    var name = $("#bottomImg")[0].files[0].name;
    $("#bottomImgTxt").text(name);
}

function leftImgChange() {
    var name = $("#leftImg")[0].files[0].name;
    $("#leftImgTxt").text(name);
}

function rightImgChange() {
    var name = $("#rightImg")[0].files[0].name;
    $("#rightImgTxt").text(name);
}

function changeCategoty() {
    var category = $("#category").val();
    location.href = "/admin/banner/pc?category=" + category;
}

function goView() {
    var category = $("#category").val();
    window.open("/admin/banner/pc/view?category=" + category);
}

function showBanner(device, position) {
    var category = $("#category").val();

    $.ajax({
        type : 'post',
        url : '/admin/banner/show',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        async: false,
        data : JSON.stringify({
            category: category,
            device : device,
            position : position
        }),
        success : function () {
            location.href = "/admin/banner/pc?category=" + category;
        },
        beforeSend : function () {

        }
    });
}

function hiddenBanner(device, position) {
    var category = $("#category").val();

    $.ajax({
        type : 'post',
        url : '/admin/banner/hidden',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        async: false,
        data : JSON.stringify({
            category: category,
            device : device,
            position : position
        }),
        success : function () {
            location.href = "/admin/banner/pc?category=" + category;
        },
        beforeSend : function () {

        }
    });
}

function initCache() {
    var category = $("#category").val();

    $.ajax({
        type : 'post',
        url : '/admin/banner/pc/init',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        async: false,
        data : "",
        success : function () {
            location.href = "/admin/banner/pc?category=" + category;
        },
        beforeSend : function () {

        }
    });
}