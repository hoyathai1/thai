const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const type = urlParams.get('type');

const editor = document.getElementById('editor');
const btnBold = document.getElementById('btn-bold');
const btnItalic = document.getElementById('btn-italic');
const btnUnderline = document.getElementById('btn-underline');
const btnStrike = document.getElementById('btn-strike');
const btnImage = document.getElementById('btn-img');
const imageSelector = document.getElementById('img-selector');

var isAjax = false;

$(document).ready(function () {

    if(detectMobileDevice(window.navigator.userAgent)) {
        var hUrl = new URL(location.href);
        location.href = "/board/modify" + hUrl.search;
    }

    if (best == 'Y') {
        $("#best").addClass("on");
    } else {
        $("#" + type).addClass("on");
    }
});

function btnModify() {
    if (isAjax == true) {
        return;
    }

    var id = $("input[name=boardNum]").val();
    var title = $("input[name=title]").val();
    var password = $("input[name=password]").val();
    var contents = $("#editor").html();
    var sType = $(".register-select").val();

    if (isEmpty(title) || lengthCheckUnder(title, 1)) {
        alert("제목을 입력해주세요.")
        return;
    }

    if (isEmpty(contents) || lengthCheckUnder(title, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    if ($("#editor img").length > 10) {
        alert("이미지는 최대 10개까지 등록 가능합니다.")
        return;
    }

    contentsTxt = $("#editor").text();

    isAjax = true;
    imgToFile();
    contents = $("#editor").html();

    $.ajax({
        type : 'post',
        url : '/board/modify',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        async: false,
        dataType : 'text',
        data : JSON.stringify({
            id: id,
            title: title,
            password: password,
            contents : contents,
            contentsTxt : contentsTxt,
            type: sType
        }),
        success : function (data) {
            goBack();
        },
        complete : function () {
            $(".div_load_image").css("display", "none");
            isAjax = false;
        }
    });
}


function focusEditor() {
    editor.focus({preventScroll: true});
}
btnBold.addEventListener('click', function () {
    setStyle('bold');
});

btnItalic.addEventListener('click', function () {
    setStyle('italic');
});

btnUnderline.addEventListener('click', function () {
    setStyle('underline');
});

btnStrike.addEventListener('click', function () {
    setStyle('strikeThrough')
});

btnImage.addEventListener('click', function () {
    imageSelector.click();
});

imageSelector.addEventListener('change', function (e) {
    const files = e.target.files;
    var size = files[0].size || files[0].fileSize;

    if (!!files) {
        for (var i=0; i < files.length; i++) {
            insertImageDate(files[i]);
        }
    }
});

function insertImageDate(file) {
    if (file.size < 50000) {
        const reader = new FileReader();
        reader.addEventListener('load', function (e) {
            focusEditor();
            document.execCommand('insertImage', false, `${reader.result}`);
        });

        reader.readAsDataURL(file);
    } else if (file.size < 200000) {
        resizeImage(file, 0.9, 850);
    } else if (file.size < 500000) {
        resizeImage(file, 0.9, 850);
    } else if (file.size < 1000000) {
        resizeImage(file, 0.8, 850);
    } else if (file.size < 2000000) {
        resizeImage(file, 0.7, 850);
    } else if (file.size < 5000000) {
        resizeImage(file, 0.6, 850);
    } else {
        resizeImage(file, 0.5, 850);
    }
}

function setStyle(style) {
    document.execCommand(style);
    focusEditor();
    checkStyle();
}

function isStyle(style) {
    return document.queryCommandState(style);
}

function checkStyle() {
    if (isStyle('bold')) {
        btnBold.classList.add('active');
    } else {
        btnBold.classList.remove('active');
    }
    if (isStyle('italic')) {
        btnItalic.classList.add('active');
    } else {
        btnItalic.classList.remove('active');
    }
    if (isStyle('underline')) {
        btnUnderline.classList.add('active');
    } else {
        btnUnderline.classList.remove('active');
    }
    if (isStyle('strikeThrough')) {
        btnStrike.classList.add('active');
    } else {
        btnStrike.classList.remove('active');
    }
}

function imgToFile() {
    var formData = new FormData();
    var img = $("#editor img");

    // 업로드 이미지 없으면
    if (img.length == 0) {
        return;
    }

    $(".div_load_image").css("display", "block");

    var modifyImgCount = 0;
    for (var i = 0; i < img.length; i++) {
        if ('uploaded' == $("#editor img")[i].className) {
            var imgArr = $("#editor img")[i].src.split("/");
            var imgName = imgArr[imgArr.length-1];
            $("#editor img")[i].src = imgName;
        } else {
            sleep(10);

            var bstr = atob(img[i].src.split(",")[1]);
            var n = bstr.length;
            var u8arr = new Uint8Array(n);

            while(n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }

            var fileName = getFileNameDate() + ".jpg"
            var file = new File([u8arr], fileName, {type:"image/jpeg"});

            formData.append("uploadFile", file);

            $("#editor img")[i].src = fileName;
            $("#editor img")[i].className="uploaded";
            modifyImgCount += 1;
        }
    }

    if (modifyImgCount > 0) {
        $.ajax({
            type : 'post',
            url : '/pc/board/upload',
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
    }
}

function getFileNameDate() {
    var today = new Date();

    var tYear = today.getFullYear().toString();
    var tMonth = today.getMonth().toString();
    var tDate = today.getHours().toString()
    var tHour = today.getDay().toString();
    var tMin = today.getMinutes().toString();
    var tSec = today.getSeconds().toString();
    var tMil = today.getMilliseconds().toString()
    var todayDate = tYear + tMonth + tDate + tHour + tMin + tSec + tMil;

    return todayDate;
}

function goList() {
    location.href="/pc/board/list?" + makeQueryUrl();
}

function goBack() {
    var boardNum = $("input[name=boardNum]").val();
    location.href="/pc/board/view?boardNum=" + boardNum + "&" + makeQueryUrl();
}

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var keyword = $("input[name=keyword]").val();
    var content = $("input[name=content]").val();

    return "type=" + type + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&keyword=" + keyword + "&content=" + content;
}

function goSignUp() {
    location.href="/pc/signUp?" + makeQueryUrl();
}

function goLogin() {
    location.href="/pc/login";
}

function goLogout() {
    location.href="/logout";
}

function goCategory(pCategory) {
    location.href="/pc/board/list?type=all&best=&category=" + pCategory + "&pageNum=0&keyword=all&content=";
}

function goType(pType) {
    location.href="/pc/board/list?type=" + pType + "&best=&category=" + category + "&pageNum=0&keyword=all&content=";
}

function goBest() {
    var pageNum = $("input[name=pageNum]").val();
    location.href="/pc/board/list?type=all&best=Y&category=" + category + "&pageNum=" + pageNum + "&keyword=all&content=";
}

function search() {
    var content = $(".search-input").val();

    location.href = "/pc/board/list?type=" + type + "&best=&category=" + category + "&pageNum=0&keyword=all&content=" + content;
}
