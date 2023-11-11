<%--
  Created by IntelliJ IDEA.
  User: robin
  Date: 03/11/2023
  Time: 01:16
  To change this template use File | Settings | File Templates.
--%>

<header style="background-color: #9C36B5;" class="py-1 px-5 mb-3 border-bottom">
    <nav class="navbar navbar-light navbar-expand-lg row">
        <a href="${pageContext.request.contextPath}/index.jsp" class="navbar-brand col-2" title="Home">
            <img class="d-inline-block align-top" style="height:80px; width:80px;" src="${pageContext.request.contextPath}/img/logo_boarder_games.png" alt="logo_boarder_games">
        </a>
        <form class="form-inline my-2 my-lg-0 col-4 mx-5" action="browse-products" method="get">
            <input id="name" name="name" class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
        </form>
        <ul class="navbar-nav mr-auto d-flex justify-content-end col">
            <li class="nav-item mx-2" title="Cart">
                <a href="cart" class="nav-link">
                    <span style="font-size:60px" class="material-symbols-outlined text-white">shopping_cart</span>
                </a>
            </li>
            <li class="nav-item mx-2" title="Account">
                <a href="#" class="nav-link">
                    <span style="font-size:60px" class="material-symbols-outlined text-white">account_circle</span>
                </a>
            </li>
            <li class="nav-item mx-2" title="HomeDark / Light mode">
                <input type="checkbox" id="dark-mode-button" onclick="switchTheme();">
                <label for="dark-mode-button" class="toggle-dark-mode mt-3">
                    <span class="text-center toggle-dark-mode-button material-symbols-outlined">dark_mode</span>
                </label>
            </li>
        </ul>
    </nav>
</header>