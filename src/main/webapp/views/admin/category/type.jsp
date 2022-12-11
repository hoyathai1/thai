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

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1>게시판 타입</h1>
                    </div>
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="/admin/category/list">카테고리</a></li>
                            <li class="breadcrumb-item active">게시판 타입 관리</li>
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
                                <h3 class="card-title">게시판 타입 관리</h3>
                            </div>

                            <div class="card-body table-responsive p-0">
                                <table class="table table-hover text-nowrap">
                                    <thead>
                                    <tr>
                                        <th>카테고리</th>
                                        <th>타입</th>
                                        <th>이름</th>
                                        <th>순서</th>
                                        <th>수정</th>
                                        <th>삭제</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="boardType">
                                        <tr>
                                            <td>${boardType.boardCategory.id}</td>
                                            <td>${boardType.type}</td>
                                            <td>${boardType.name}</td>
                                            <td>${boardType.orderBy}</td>
                                            <td>
                                                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#modal-sm" onclick="typeModifyModal('${boardType.id}')">
                                                    수정
                                                </button>
                                            </td>
                                            <c:if test="${boardType.use eq true}">
                                                <td>
                                                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modal-sm" onclick="unuseBtn('${boardType.id}')">
                                                        삭제
                                                    </button>
                                                </td>
                                            </c:if>
                                            <c:if test="${boardType.use eq false}">
                                                <td>
                                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal-sm" onclick="useBtn('${boardType.id}')">
                                                        복구
                                                    </button>
                                                </td>
                                            </c:if>
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
                        <button type="button" class="btn btn btn-info float-right" onclick="typeRegisterModal()">
                            타입 추가
                        </button>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <input type="hidden" name="category" value="${search.category}" />

    <div class="modal fade" id="type-register-modal" style="display: none;" aria-hidden="true" data-id="">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">카테고리 등록</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <label for="type_type" class="col-sm-2 col-form-label">ID</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="type_type" placeholder="아이디 입력">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="type_name" class="col-sm-2 col-form-label">NAME</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="type_name" placeholder="NAME 입력">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="type_order" class="col-sm-2 col-form-label">순서</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="type_order" placeholder="순서 입력">
                        </div>
                    </div>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="registerBtn()">등록</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="type-mod-modal" style="display: none;" aria-hidden="true" data-id="">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">카테고리 수정</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input type="hidden" class="form-control" id="type_mod_id" value="">
                    <div class="form-group row">
                        <label for="type_mod_type" class="col-sm-2 col-form-label">type</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="type_mod_type" placeholder="타입 입력">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="type_mod_name" class="col-sm-2 col-form-label">NAME</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="type_mod_name" placeholder="NAME 입력">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="type_mod_order" class="col-sm-2 col-form-label">순서</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="type_mod_order" placeholder="순서 입력">
                        </div>
                    </div>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="modifyBtn()">수정</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/dist/js/adminlte.js"></script>
    <script type="text/javascript" src="/js/m.base.js"></script>
    <script type="text/javascript" src="/js/admin/category/type.js"></script>
</body>
</html>