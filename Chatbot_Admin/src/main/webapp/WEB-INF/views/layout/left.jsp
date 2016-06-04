<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="active" value="${ACTIVE}" />
        <!-- navbar side -->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <!-- sidebar-collapse -->
            <div class="sidebar-collapse">
                <!-- side-menu -->
                <ul class="nav" id="side-menu">
                    <li class="nav-inner">
                        <!-- user image section-->
                        <div class="user-section">
                            <div class="user-section-inner">
                                <img src="resources/assets/img/user.jpg" alt="">
                            </div>
                            <div class="user-info">
                                <div>Huynh <strong>Dat</strong> adasda ${current}</div>
                                <div class="user-text-online">
                                    <span class="user-circle-online btn btn-success btn-circle "></span>&nbsp;Online
                                </div>
                            </div>
                        </div>
                        <!--end user image section-->
                    </li>
                   
                    <li class="${current == 'dataConfig' ? 'selected' : ''}">
                        <a href="dataConfig"><i class="fa fa-dashboard fa-fw"></i>Data Config</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>Training Bot<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li class="${current == 'lexicalCategory' ? 'selected' : ''}">
                                <a href="lexical">Lexical Category</a>
                            </li>
                            <li>
                                <a href="example">Example</a>
                            </li>
                        </ul>
                        <!-- second-level-items -->
                    </li>
                     <li>
                        <a href="manageLog"><i class="fa fa-flask fa-fw"></i>Manage log</a>
                    </li>
                    
                    
                    <li>
                        <a href="#"><i class="fa fa-files-o fa-fw"></i>Manage Information<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="product">Product</a>
                            </li>
                            <li>
                                <a href="synonym">Synonym</a>
                            </li>
                        </ul>
                        <!-- second-level-items -->
                    </li>
                </ul>
                <!-- end side-menu -->
            </div>
            <!-- end sidebar-collapse -->
        </nav>
        <!-- end navbar side -->