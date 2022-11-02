const editor = document.getElementById('editor');
const btnBold = document.getElementById('btn-bold');
const btnItalic = document.getElementById('btn-italic');
const btnUnderline = document.getElementById('btn-underline');
const btnStrike = document.getElementById('btn-strike');
const btnImage = document.getElementById('btn-img');
const imageSelector = document.getElementById('img-selector');

$(document).ready(function () {

});

function btnModify() {
    var id = $("input[name=boardNum]").val();
    var title = $("input[name=title]").val();
    var password = $("input[name=password]").val();
    var contents = $("#editor").html();

    if (isEmpty(title) || lengthCheck(title, 1)) {
        alert("제목을 입력해주세요.")
        return;
    }

    if (isEmpty(contents) || lengthCheck(title, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    contentsTxt = $("#editor").text();

    $.ajax({
        type : 'post',
        url : '/board/modify',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            id: id,
            title: title,
            password: password,
            contents : contents,
            contentsTxt : contentsTxt
        }),
        success : function (data) {
            goBack();
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
    const reader = new FileReader();

    // reader.addEventListener('load', function (e) {
    //     focusEditor();
    //     document.execCommand('insertImage', false, `${reader.result}`);
    // });

    reader.onload = (base64) => {
        const image = new Image();

        image.src = base64.target.result;

        image.onload = (e) => {
            const $canvas = document.createElement(`canvas`);
            const ctx = $canvas.getContext(`2d`);

            $canvas.width = e.target.width;
            $canvas.height = e.target.height;

            ctx.drawImage(e.target, 0, 0);

            // 용량이 줄어든 base64 이미지
            focusEditor();
            document.execCommand('insertImage', false, $canvas.toDataURL(`image/jpeg`, 0.5));
        }
    }

    reader.readAsDataURL(file);
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

function goBack() {
    var boardNum = $("input[name=boardNum]").val();
    location.href="/board/view?boardNum=" + boardNum + "&" + makeQueryUrl();
}

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();
    var keyword = $("input[name=keyword]").val();
    var content = $("input[name=content]").val();

    return "pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content;
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