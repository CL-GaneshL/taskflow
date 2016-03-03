
<!-- ================================================== -->
<!-- -nav bar header                                    -->
<!-- ================================================== -->  
<div class="navbar-header">

    <a class="sidebar-mobile-toggler pull-left hidden-md hidden-lg btn btn-navbar sidebar-toggle" 
       href="#"
       ng-click="toggle('sidebar')" > 
        <i class="ti-align-justify"></i>
    </a>

    <a class="navbar-brand" ui-sref="app.dashboard">
        <img ng-src="<%app.layout.logo%>" /> 
    </a>

</div>

<!-- ================================================== -->
<!-- -nav bar dropdown                                  -->
<!-- ================================================== -->  
<div class="navbar-collapse collapse" uib-collapse="navbarCollapsed" 
     ng-init="navbarCollapsed = true" off-click="navbarCollapsed = true" 
     off-click-if='!navbarCollapsed' off-click-filter="#menu-toggler">

    <ul class="nav navbar-right" ct-fullheight="window"
        data-ct-fullheight-exclusion="header" data-ct-fullheight-if="isSmallDevice">

        <!-- ================================================== -->
        <!-- -user options dropdown                             -->
        <!-- ================================================== -->  
        <li class="dropdown current-user" 
            uib-dropdown on-toggle="toggled(open)"
            >

            <a href class="dropdown-toggle" uib-dropdown-toggle> 

                <!-- ================================================== -->
                <!-- -user avatar and name                              -->
                <!-- ================================================== --> 
                <img src="<% user.avatar %>"> 

                <span class="username"><%user.fullName%> 
                    <i class="ti-angle-down"></i>
                </span> 
            </a>

            <!-- ================================================== -->
            <!-- -user options                                      -->
            <!-- ================================================== --> 
            <ul class="dropdown-menu dropdown-dark">
                <li>
                    <a ui-sref="app.profile"> Profile </a>
                </li>
                <li>
                    <a ui-sref="logout.logout"> Log Out </a>
                </li>
            </ul>
            <!-- ================================================== --> 
            
        </li>
        <!-- ================================================== -->  
        
    </ul>

</div>


