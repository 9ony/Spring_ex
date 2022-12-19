<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container-xxl bg-white p-0">
<!-- Spinner Start -->
<div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
    <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
        <span class="sr-only">Loading...</span>
    </div>
</div>
<!-- Spinner End -->
<!-- Navbar & Hero Start -->
<div class="container-xxl position-relative p-0">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark px-4 px-lg-5 py-3 py-lg-0">
        <a href="" class="navbar-brand p-0">
            <h1 class="text-primary m-0"><i class="fa-solid fa-meteor"></i> Space</h1>
            <!-- <img src="img/logo.png" alt="Logo"> -->
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
            <span class="fa fa-bars"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <div class="navbar-nav ms-auto py-0 pe-4">
                <!-- Home Reservation Services Contact Pages MyPage MyReservation -->
                <li class="nav-item nav-link" id="HomeTab" onclick = "sel_menu('Home')">Home</li>
                
                <li class="nav-item nav-link" id="ReservationTab" onclick = "sel_menu('Reservation')">Reservation</li>

                <li class="nav-item nav-link" id="ServicesTab" onclick = "sel_menu('Services')">Services</li>

                <li class="nav-item nav-link" id="ContactTab" onclick = "sel_menu('Contact')">Contact</li>

                <div class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" id="PagesTab" data-bs-toggle="dropdown">Pages</a>
                    <div class="dropdown-menu m-0">
                    	<li class="dropdown-item" onclick = "location.href='MyPage'">MyPage</li>
                        <a href="team" class="dropdown-item">MyReservation</a>
                    </div>
                </div>
            </div>
            <a href="javascript:sel_menu('Login')" class="btn btn-primary py-2 px-4" >로그인</a>
        </div>
    </nav>

    <div class="container-xxl py-5 bg-dark hero-header mb-5">
        <div class="container text-center my-5 pt-5 pb-4">
            <h1 class="display-3 text-white mb-3 animated slideInDown" id="selmenu">Home</h1>
        </div>
    </div>
</div>