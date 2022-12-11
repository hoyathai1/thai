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
                                <h3 class="card-title">카테고리 관리</h3>
                            </div>

                            <div class="card-body table-responsive p-0">
                                <table class="table table-hover text-nowrap">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>이름</th>
                                        <th>수정</th>
                                        <th>타입</th>
                                        <th>삭제</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="category">
                                        <tr>
                                            <td>${category.id}</td>
                                            <td>${category.name}</td>
                                            <td>
                                                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-sm" onclick="categoryModifyModal('${category.id}')">
                                                    수정
                                                </button>
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#modal-sm" onclick="goType('${category.id}')">
                                                    타입
                                                </button>
                                            </td>
                                            <c:if test="${category.use eq true}">
                                                <td>
                                                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modal-sm" onclick="unuseBtn('${category.id}')">
                                                        비활성화
                                                    </button>
                                                </td>
                                            </c:if>
                                            <c:if test="${category.use eq false}">
                                                <td>
                                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal-sm" onclick="useBtn('${category.id}')">
                                                        활성화
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
                        <button type="button" class="btn btn btn-info float-right" onclick="categoryRegisterModal()">
                            카테고리 추가
                        </button>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <div class="modal fade" id="category-register-modal" style="display: none;" aria-hidden="true" data-id="">
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
                        <label for="category_id" class="col-sm-2 col-form-label">ID</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="category_id" placeholder="아이디 입력">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="category_name" class="col-sm-2 col-form-label">NAME</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="category_name" placeholder="NAME 입력">
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

    <div class="modal fade" id="category-mod-modal" style="display: none;" aria-hidden="true" data-id="">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">카테고리 수정</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeModal()">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <label for="category_mod_id" class="col-sm-2 col-form-label">ID</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="category_mod_id" placeholder="아이디 입력" readonly="true">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="category_mod_name" class="col-sm-2 col-form-label">NAME</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="category_mod_name" placeholder="NAME 입력">
                        </div>
                    </div>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">취소</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="modifyBtn()">등록</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/dist/js/adminlte.js"></script>
    <script type="text/javascript" src="/js/m.base.js"></script>
    <script type="text/javascript" src="/js/admin/category/category.js"></script>
</body>
</html>