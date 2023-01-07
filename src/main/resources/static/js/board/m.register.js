const urlParams = new URL(location.href).searchParams;
const category = urlParams.get('category');
const best = urlParams.get('best');
const bType = urlParams.get('type');

const editor = document.getElementById('editor');
const btnBold = document.getElementById('btn-bold');
const btnItalic = document.getElementById('btn-italic');
const btnUnderline = document.getElementById('btn-underline');
const btnStrike = document.getElementById('btn-strike');
const btnImage = document.getElementById('btn-img');
const imageSelector = document.getElementById('img-selector');

var isAjax = false;

$(document).ready(function () {
    if (bType != 'all' && best != 'Y') {
        $(".register-select").val(bType);
    }
});

function btnRegister() {
    if (isAjax == true) {
        return;
    }

    var title = $("input[name=title]").val();
    var name = $("input[name=name]").val();
    var password = $("input[name=password]").val();
    var contents = $("#editor").html();
    var contentsTxt = "";
    var sType = $(".register-select").val();

    if (isEmpty(title) || lengthCheckUnder(title, 1)) {
        alert("제목을 입력해주세요.")
        return;
    }

    if (isEmpty(name) || lengthCheckUnder(name, 1)) {
        alert("닉네임을 입력해주세요.")
        return;
    }

    if (isEmpty(password)) {
        alert("비밀번호를 입력해주세요.")
        return;
    }

    if (lengthCheckUnder(password, 4)) {
        alert("비밀번호를 최소 4자리 이상 입력하셔야 합니다. 쉬운 비밀번호는 타인이 수정 또는 삭제하기 쉬우니, 어려운 비밀번호를 입력해 주세요.");
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
        url : '/board/register',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        async: false,
        data : JSON.stringify({
            title: title,
            author: name,
            password: password,
            contents : contents,
            contentsTxt : contentsTxt,
            category: category,
            type: sType
        }),
        success : function (data) {
            location.href = encodeURI("/board/list?type=" + sType + "&best=N&category=" + category + "&pageNum=0");
        },
        error : function () {

        },
        complete : function () {
            $(".div_load_image").css("display", "none");
        }
    });
}

function btnRegisterLogin() {
    if (isAjax == true) {
        return;
    }

    var title = $("input[name=title]").val();
    var contents = $("#editor").html();
    var sType = $(".register-select").val();
    var contentsTxt = "";

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
        url : '/board/register',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        async: false,
        data : JSON.stringify({
            title: title,
            contents : contents,
            contentsTxt : contentsTxt,
            category: category,
            type: sType,
            best: best
        }),
        success : function (data) {
            location.href = encodeURI("/board/list?type=" + sType + "&best=N&category=" + category + "&pageNum=0");
        },
        error : function () {

        },
        complete : function () {
            $(".div_load_image").css("display", "none");
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
    if (file.type == 'image/gif') {
        if (file.size < 200000) {
            const reader = new FileReader();
            reader.addEventListener('load', function (e) {
                focusEditor();
                document.execCommand('insertImage', false, `${reader.result}`);
            });

            reader.readAsDataURL(file);
        } else {
            alert("gif는 2메가를 넘길수없습니다.");
        }
    } else {
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

function goList() {
    location.href="/board/list?" + makeQueryUrl();
}

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();
    var keyword = $("input[name=keyword]").val();
    var content = $("input[name=content]").val();

    return encodeURI("type=" + bType + "&best=" + best + "&category=" + category + "&pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content);
}

function imageSizeChange( image ) {

    let canvas = document.createElement("canvas"),
        max_size = 1280,
        // 최대 기준을 1280으로 잡음.
        width = image.width,
        height = image.height;
    if (width > height) {
        // 가로가 길 경우
        if (width > max_size) {
            height *= max_size / width;
            width = max_size;
        }
    } else {
        // 세로가 길 경우
        if (height > max_size) {
            width *= max_size / height;
            height = max_size;
        }
    }
    canvas.width = width;
    canvas.height = height;
    canvas.getContext("2d").drawImage(image, 0, 0, width, height);
    this.imgUrl = canvas.toDataURL("image/jpeg", 0.5);
}

function imgToFile() {
    var formData = new FormData();
    var img = $("#editor img");

    // 업로드 이미지 없으면
    if (img.length == 0) {
        return;
    }

    $(".div_load_image").css("display", "block");

    for (var i = 0; i < img.length; i++) {
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
    }

    $("#editor img").addClass("uploaded");

    $.ajax({
        type : 'post',
        url : '/board/upload',
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