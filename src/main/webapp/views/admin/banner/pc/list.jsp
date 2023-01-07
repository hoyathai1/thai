<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>관리자</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome Icons -->
    <link rel="stylesheet" href="/plugins/fontawesome-free/css/all.min.css">
    <!-- IonIcons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/dist/css/adminlte.min.css">
    <link rel="stylesheet" href="/css/admin/editorForm.css">
</head>
<body class="wrapper">
    <jsp:include page="/views/admin/navi.jsp"></jsp:include>
    <jsp:include page="/views/admin/side-bar.jsp"></jsp:include>

    <jsp:useBean id="now" class="java.util.Date" />
    <fmt:formatDate pattern="yyyyMMdd" value="${ now }" var="today" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1>PC 배너</h1>
                    </div>
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="/main">Home</a></li>
                            <li class="breadcrumb-item active">카테고리 관리</li>
                        </ol>
                    </div>
                </div>
            </div><!-- /.container-fluid -->
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-6">
                        <div class="col-lg-3">
                            <div class="form-group">
                                <select class="form-control" id="category" onchange="changeCategoty()">
                                    <c:forEach items="${allCategory}" var="category">
                                        <option value="${category.id}" <c:if test="${search.category eq category.id}">selected</c:if>>${category.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-6">
                    </div>
                    <div class="col-6">
                        <div class="card card-primary">
                            <div class="card-header">
                                <h3 class="card-title">상단배너 (720px x 100px)</h3>
                            </div>

                            <div class="card-body">
                                <div class="form-group">
                                    <label for="topBannerLink">상단배너 링크 주소</label>
                                    <input type="text" class="form-control" id="topBannerLink" placeholder="www." value="${dto.topBanner.link}">
                                </div>
                                <div class="form-group">
                                    <label for="topImg">배너 이미지</label>
                                    <div class="input-group">
                                        <div class="custom-file">
                                            <form enctype="multipart/form-data" id="topForm">
                                                <input type="file" class="custom-file-input" accept="image/*" name="uploadFile" id="topImg" onchange="topImgChange()">
                                                <label class="custom-file-label" for="topImg" id="topImgTxt">${dto.topBanner.fileName}</label>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-check">
                                    <input type="checkbox" class="form-check-input" id="topBannerIsBoard" <c:if test="${dto.topBanner.board eq true}">checked</c:if>>
                                    <label class="form-check-label" for="topBannerIsBoard">외부링크</label>
                                </div>
                            </div>

                            <div class="card-footer">
                                <c:if test="${dto.topBanner.show eq true}">
                                    <button type="submit" class="btn btn-danger" onclick="hiddenBanner('PC', 'TOP')">비활성화</button>
                                </c:if>
                                <c:if test="${dto.topBanner.show eq false}">
                                    <button type="submit" class="btn btn-primary" onclick="showBanner('PC', 'TOP')">활성화</button>
                                </c:if>
                                <button type="submit" class="btn btn-primary" onclick="bannerBtn('PC', 'TOP')">저장</button>
                            </div>
                        </div>
                    </div>  <%--.col-6--%>
                    <div class="col-6">
                        <div class="card card-primary">
                            <div class="card-header">
                                <h3 class="card-title">하단배너 (720px x 100px)</h3>
                            </div>

                            <div class="card-body">
                                <div class="form-group">
                                    <label for="bottomBannerLink">하단배너 링크 주소</label>
                                    <input type="text" class="form-control" id="bottomBannerLink" placeholder="www." value="${dto.bottomBanner.link}">
                                </div>
                                <div class="form-group">
                                    <label for="bottomImg">배너 이미지</label>
                                    <div class="input-group">
                                        <div class="custom-file">
                                            <form enctype="multipart/form-data" id="bottomForm">
                                                <input type="file" class="custom-file-input" accept="image/*" name="uploadFile" id="bottomImg" onchange="bottomImgChange()">
                                                <label class="custom-file-label" for="bottomImg" id="bottomImgTxt">${dto.bottomBanner.fileName}</label>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-check">
                                    <input type="checkbox" class="form-check-input" id="bottomBannerIsBoard" <c:if test="${dto.bottomBanner.board eq true}">checked</c:if>>
                                    <label class="form-check-label" for="bottomBannerIsBoard">외부링크</label>
                                </div>
                            </div>

                            <div class="card-footer">
                                <c:if test="${dto.bottomBanner.show eq true}">
                                    <button type="submit" class="btn btn-danger" onclick="hiddenBanner('PC', 'BOTTOM')">비활성화</button>
                                </c:if>
                                <c:if test="${dto.bottomBanner.show eq false}">
                                    <button type="submit" class="btn btn-primary" onclick="showBanner('PC', 'BOTTOM')">활성화</button>
                                </c:if>
                                <button type="submit" class="btn btn-primary" onclick="bannerBtn('PC', 'BOTTOM')">저장</button>
                            </div>
                        </div>
                    </div>  <%--.col-6--%>
                    <div class="col-6">
                        <div class="card card-success">
                            <div class="card-header">
                                <h3 class="card-title">좌측배너 (150px x 600px)</h3>
                            </div>

                            <div class="card-body">
                                <div class="form-group">
                                    <label for="leftBannerLink">좌측배너 링크 주소</label>
                                    <input type="text" class="form-control" id="leftBannerLink" placeholder="www." value="${dto.leftBanner.link}">
                                </div>
                                <div class="form-group">
                                    <label for="leftImg">배너 이미지</label>
                                    <div class="input-group">
                                        <div class="custom-file">
                                            <form enctype="multipart/form-data" id="leftForm">
                                                <input type="file" class="custom-file-input" accept="image/*" name="uploadFile" id="leftImg" onchange="leftImgChange()">
                                                <label class="custom-file-label" for="leftImg" id="leftImgTxt">${dto.leftBanner.fileName}</label>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-check">
                                    <input type="checkbox" class="form-check-input" id="leftBannerIsBoard" <c:if test="${dto.leftBanner.board eq true}">checked</c:if>>
                                    <label class="form-check-label" for="leftBannerIsBoard">외부링크</label>
                                </div>
                            </div>

                            <div class="card-footer">
                                <c:if test="${dto.leftBanner.show eq true}">
                                    <button type="submit" class="btn btn-danger" onclick="hiddenBanner('PC', 'LEFT')">비활성화</button>
                                </c:if>
                                <c:if test="${dto.leftBanner.show eq false}">
                                    <button type="submit" class="btn btn-primary" onclick="showBanner('PC', 'LEFT')">활성화</button>
                                </c:if>
                                <button type="submit" class="btn btn-primary" onclick="bannerBtn('PC', 'LEFT')">저장</button>
                            </div>
                        </div>
                    </div>  <%--.col-6--%>
                    <div class="col-6">
                        <div class="card card-success">
                            <div class="card-header">
                                <h3 class="card-title">우측배너 (150px x 600px)</h3>
                            </div>

                            <div class="card-body">
                                <div class="form-group">
                                    <label for="rightBannerLink">우측배너 링크 주소</label>
                                    <input type="text" class="form-control" id="rightBannerLink" placeholder="www." value="${dto.rightBanner.link}">
                                </div>
                                <div class="form-group">
                                    <label for="rightImg">배너 이미지</label>
                                    <div class="input-group">
                                        <div class="custom-file">
                                            <form enctype="multipart/form-data" id="rightForm">
                                                <input type="file" class="custom-file-input" accept="image/*" name="uploadFile" id="rightImg" onchange="rightImgChange()">
                                                <label class="custom-file-label" for="rightImg" id="rightImgTxt">${dto.rightBanner.fileName}</label>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-check">
                                    <input type="checkbox" class="form-check-input" id="rightBannerIsBoard" <c:if test="${dto.rightBanner.board eq true}">checked</c:if>>
                                    <label class="form-check-label" for="rightBannerIsBoard">외부링크</label>
                                </div>
                            </div>

                            <div class="card-footer">
                                <c:if test="${dto.rightBanner.show eq true}">
                                    <button type="submit" class="btn btn-danger" onclick="hiddenBanner('PC', 'RIGHT')">비활성화</button>
                                </c:if>
                                <c:if test="${dto.rightBanner.show eq false}">
                                    <button type="submit" class="btn btn-primary" onclick="showBanner('PC', 'RIGHT')">활성화</button>
                                </c:if>
                                <button type="submit" class="btn btn-primary" onclick="bannerBtn('PC', 'RIGHT')">저장</button>
                            </div>
                        </div>
                    </div>  <%--.col-6--%>
                </div>
                <div class="row">
                    <div class="col-12">
                        <button type="button" class="btn btn btn-info float-right" onclick="goView()">
                            미리보기
                        </button>
                        <button type="button" class="btn btn btn-info float-right" onclick="initCache()" style="margin-right: 5px">
                            반영
                        </button>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/dist/js/adminlte.js"></script>
    <script type="text/javascript" src="/js/m.base.js"></script>
    <script type="text/javascript" src="/js/admin/banner/pc/list.js"></script>
</body>
</html>