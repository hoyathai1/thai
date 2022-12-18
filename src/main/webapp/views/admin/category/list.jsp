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
                        <h1>카테고리</h1>
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
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h3 class="card-title">게시판 카테고리/타입</h3>
                            </div>

                            <div class="card-body p-0">
                                <table class="table table-hover">
                                    <tbody>
                                    <c:forEach items="${list}" var="category">
                                        <tr data-widget="expandable-table" aria-expanded="false">
                                            <td>
                                                <c:if test="${category.use eq true}">
                                                    <button type="button" class="btn btn-primary p-0">
                                                        <i class="expandable-table-caret fas fa-caret-right fa-fw"></i>
                                                    </button>
                                                </c:if>
                                                <c:if test="${category.use eq false}">
                                                    <button type="button" class="btn btn-danger p-0">
                                                        <i class="expandable-table-caret fas fa-caret-right fa-fw"></i>
                                                    </button>
                                                </c:if>
                                                <b>
                                                ${category.name} [${category.id}]
                                                </b>
                                            </td>
                                        </tr>
                                        <tr class="expandable-body d-none">
                                            <td>
                                                <div class="p-0" style="display: none;">
                                                    <table class="table table-hover">
                                                        <tbody>
                                                            <c:forEach items="${category.boardTypes}" var="types">
                                                                <tr>
                                                                    <c:if test="${types.use eq true}">
                                                                        <td>${types.name} [${types.type}]</td>
                                                                    </c:if>
                                                                    <c:if test="${types.use eq false}">
                                                                        <td style="text-decoration: line-through">${types.name} [${types.type}]</td>
                                                                    </c:if>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <button type="button" class="btn btn btn-info float-right" onclick="goCategory()">
                            카테고리
                        </button>
                        <button type="button" class="btn btn btn-info float-right" onclick="categorySessionInit()" style="margin-right: 5px">
                            카테고리 세션 초기화
                        </button>
                        <button type="button" class="btn btn btn-info float-right" onclick="typeSessionInit()" style="margin-right: 5px;">
                            타입 세션 초기화
                        </button>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <div class="modal fade" id="category-modal" style="display: none;" aria-hidden="true" data-id="">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">카테고리</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p id="category-content"></p>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
                    <button type="button" class="btn btn-primary" onclick="">수정</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="type-modal" style="display: none;" aria-hidden="true" data-id="">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">타입</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p id="type-content"></p>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
                    <button type="button" class="btn btn-primary" onclick="">수정</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/dist/js/adminlte.js"></script>
    <script type="text/javascript" src="/js/m.base.js"></script>
    <script type="text/javascript" src="/js/admin/category/list.js"></script>
</body>
</html>