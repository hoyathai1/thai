const editor = document.getElementById('editor');
const btnBold = document.getElementById('btn-bold');
const btnItalic = document.getElementById('btn-italic');
const btnUnderline = document.getElementById('btn-underline');
const btnStrike = document.getElementById('btn-strike');
const btnImage = document.getElementById('btn-img');
const imageSelector = document.getElementById('img-selector');

$(document).ready(function () {

});

function btnRegister() {
    var title = $("input[name=title]").val();
    var name = $("input[name=name]").val();
    var password = $("input[name=password]").val();
    var contents = $("#editor").html();
    var contentsTxt = "";

    if (isEmpty(title) || lengthCheck(title, 1)) {
        alert("제목을 입력해주세요.")
        return;
    }

    if (isEmpty(name) || lengthCheck(name, 1)) {
        alert("닉네임을 입력해주세요.")
        return;
    }

    if (isEmpty(password)) {
        alert("비밀번호를 입력해주세요.")
        return;
    }

    if (lengthCheck(password, 4)) {
        alert("비밀번호를 최소 4자리 이상 입력하셔야 합니다. 쉬운 비밀번호는 타인이 수정 또는 삭제하기 쉬우니, 어려운 비밀번호를 입력해 주세요.");
    }

    if (isEmpty(contents) || lengthCheck(title, 1)) {
        alert("내을 입력해주세요.")
        return;
    }

    contentsTxt = $("#editor").text();

    $.ajax({
        type : 'post',
        url : '/board/register',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            title: title,
            author: name,
            password: password,
            contents : contents,
            contentsTxt : contentsTxt,
            type: "thai"
        }),
        success : function () {
            location.href="/board/list";
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

    console.log("size is " + size);

    if (!!files) {

        for (var i=0; i < files.length; i++) {
            insertImageDate(files[i]);
        }
    }
});

function insertImageDate(file) {
    const reader = new FileReader();
    reader.addEventListener('load', function (e) {
        focusEditor();
        document.execCommand('insertImage', false, `${reader.result}`);
    });
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

function goList() {
    location.href="/board/list?" + makeQueryUrl();
}

function makeQueryUrl() {
    var pageNum = $("input[name=pageNum]").val();
    var pageSize = $("input[name=pageSize]").val();
    var keyword = $("input[name=keyword]").val();
    var content = $("input[name=content]").val();

    return "pageNum=" + pageNum + "&pageSize=" + pageSize + "&keyword=" + keyword + "&content=" + content;
}