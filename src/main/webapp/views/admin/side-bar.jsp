<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<aside class="main-sidebar sidebar-dark-primary elevation-4">

    <a href="/" class="brand-link">
        <img src="/img/elephant.png" alt="HellowThai" class="brand-image img-circle elevation-3" style="opacity: .8">
        <span class="brand-text font-weight-light">HellowThai</span>
    </a>

    <div class="sidebar">

        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <%--<img src="dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">--%>
            </div>
            <div class="info">
                <a href="/admin/main" class="d-block">${principal.username}</a>
            </div>
        </div>

        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">

                <li class="nav-item">
                    <a href="#" class="nav-link">
                        <i class="nav-icon fas fa-copy"></i>
                        <p>
                            계정관리
                            <i class="fas fa-angle-left right"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="/admin/account/user/list?pageNum=0" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>유저 관리</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/admin/account/partner/list?pageNum=0" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>파트너 관리</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/admin/account/admin/list?pageNum=0" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>관리자 관리</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="pages/layout/boxed.html" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>IP 차단 (X)</p>
                            </a>
                        </li>
                    </ul>
                </li>

                <li class="nav-item">
                    <a href="#" class="nav-link">
                        <i class="nav-icon fas fa-edit"></i>
                        <p>
                            게시글 관리
                            <i class="fas fa-angle-left right"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="/admin/board/notice/list?pageNum=0" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>공지사항 관리</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/admin/board/inform/list?pageNum=0" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>게시판 공지글 관리</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/admin/board/board/list?pageNum=0" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>게시글 관리</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/admin/board/comment/list?pageNum=0" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>댓글 관리</p>
                            </a>
                        </li>
                    </ul>
                </li>

                <li class="nav-item">
                    <a href="#" class="nav-link">
                        <i class="nav-icon fas fa-table"></i>
                        <p>
                            배너 관리
                            <i class="fas fa-angle-left right"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="/admin/banner/pc?category=thai" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>PC배너</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/admin/banner/mobile?category=thai" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>모바일배너</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/admin/banner/board?category=thai" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>배너 게시글</p>
                            </a>
                        </li>
                    </ul>
                </li>

                <li class="nav-item">
                    <a href="/admin/image/list?pageNum=0&pageSize=12" class="nav-link">
                        <i class="nav-icon far fa-image"></i>
                        <p>
                            이미지관리
                        </p>
                    </a>
                </li>

                <li class="nav-item">
                    <a href="pages/kanban.html" class="nav-link">
                        <i class="nav-icon fas fa-columns"></i>
                        <p>
                            모니터링 (X)
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="javascript:;" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>미정</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="javascript:;" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>미정</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="javascript:;" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>미정</p>
                            </a>
                        </li>
                    </ul>
                </li>

                <li class="nav-item">
                    <a href="/admin/category/list" class="nav-link">
                        <i class="nav-icon far fa-plus-square"></i>
                        <p>카테고리 관리</p>
                    </a>
                </li>

            </ul>
        </nav>

    </div>

</aside>