
<!-- ================================================== -->
<!-- authenticated user role and privileges             -->
<!-- ================================================== -->

@inject('authUser', 'App\Helpers\AuthenticatedUser')


<!-- ================================================== -->
<!-- navigation bar                                     -->
<!-- ================================================== -->
<ul class="main-navigation-menu">

    <!-- ================================================== -->
    <!-- my profile                                         -->
    <!-- ================================================== -->

    @if ( ! $authUser->hasRole('ADMIN_ROLE') )

    <li ui-sref-active="active">
        <a ui-sref="app.profile">
            <div class="item-content">
                <div class="item-media">
                    <i class="fa fa-user"></i>
                </div>
                <div class="item-inner">
                    <span class="title">My profile</span>
                </div>
            </div>
        </a>
    </li> 

    @else

    <div class="item-content">
        <div class="item-media">
            <i class="fa fa-user"></i>
        </div>
        <div class="item-inner">
            <span class="title">My profile</span>
        </div>
    </div>

    @endif

    <!-- ================================================== -->
    <!-- employees                                          -->
    <!-- ================================================== -->

    @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE')  || $authUser->hasRole('TEAM_LEAD_ROLE') )

    <li ui-sref-active="active">
        <a ui-sref="app.employees">
            <div class="item-content">
                <div class="item-media">
                    <i class="fa fa-users"></i>
                </div>
                <div class="item-inner">
                    <span class="title">Employees</span>
                </div>
            </div>
        </a>
    </li>

    @else

    <div class="item-content">
        <div class="item-media">
            <i class="fa fa-users"></i>
        </div>
        <div class="item-inner">
            <span class="title">Employees</span>
        </div>
    </div>

    @endif


    <!-- ================================================== -->
    <!-- skills                                             -->
    <!-- ================================================== -->

    @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE') )

    <li ui-sref-active="active">
        <a ui-sref="app.skills">
            <div class="item-content">
                <div class="item-media">
                    <i class="fa fa-wrench"></i>
                </div>
                <div class="item-inner">
                    <span class="title">Skills</span>
                </div>
            </div>
        </a>
    </li>

    @else

    <div class="item-content">
        <div class="item-media">
            <i class="fa fa-wrench"></i>
        </div>
        <div class="item-inner">
            <span class="title">Skills</span>
        </div>
    </div>

    @endif


    <!-- ================================================== -->
    <!-- teams                                              -->
    <!-- ================================================== -->

    @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE')  )

    <li ui-sref-active="active">
        <a ui-sref="app.teams">
            <div class="item-content">
                <div class="item-media">
                    <i class="fa fa-sitemap"></i>
                </div>
                <div class="item-inner">
                    <span class="title">Teams</span>
                </div>
            </div>
        </a>
    </li>

    @else

    <div class="item-content">
        <div class="item-media">
            <i class="fa fa-sitemap"></i>
        </div>
        <div class="item-inner">
            <span class="title">Teams</span>
        </div>
    </div>

    @endif


    <!-- ================================================== -->
    <!-- projects                                           -->
    <!-- ================================================== -->

    @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE') )

    <li ui-sref-active="active">
        <a ui-sref="app.projects">
            <div class="item-content">
                <div class="item-media">
                    <i class="fa fa-cubes"></i>
                </div>
                <div class="item-inner">
                    <span class="title">Projects</span>
                </div>
            </div>
        </a>
    </li>

    @else

    <div class="item-content">
        <div class="item-media">
            <i class="fa fa-cubes"></i>
        </div>
        <div class="item-inner">
            <span class="title">Projects</span>
        </div>
    </div>

    @endif
    
     <!-- ================================================== -->
    <!-- Tasks                                           -->
    <!-- ================================================== -->

    @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE') )

    <li ui-sref-active="active">
        <a ui-sref="app.tasks">
            <div class="item-content">
                <div class="item-media">
                    <i class="fa fa-tasks"></i>
                </div>
                <div class="item-inner">
                    <span class="title">Tasks</span>
                </div>
            </div>
        </a>
    </li>

    @else

    <div class="item-content">
        <div class="item-media">
            <i class="fa fa-tasks"></i>
        </div>
        <div class="item-inner">
            <span class="title">Tasks</span>
        </div>
    </div>

    @endif


    <!-- ================================================== -->
    <!-- Templates                                           -->
    <!-- ================================================== -->

    @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE') )

    <li ui-sref-active="active">
        <a ui-sref="app.templates">
            <div class="item-content">
                <div class="item-media">
                    <i class="fa fa-tags"></i>
                </div>
                <div class="item-inner">
                    <span class="title">Templates</span>
                </div>
            </div>
        </a>
    </li>

    @else

    <div class="item-content">
        <div class="item-media">
            <i class="fa fa-tags"></i>
        </div>
        <div class="item-inner">
            <span class="title">Templates</span>
        </div>
    </div>

    @endif

    <!-- ================================================== -->
    <!-- metrics                                            -->
    <!-- ================================================== -->

    @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE') )

    <li ui-sref-active="active">
        <a ui-sref="app.metrics">
            <div class="item-content">
                <div class="item-media">
                    <i class="fa fa-line-chart"></i>
                </div>
                <div class="item-inner">
                    <span class="title">Metrics</span>
                </div>
            </div>
        </a>
    </li>


    @else

    <div class="item-content">
        <div class="item-media">
            <i class="fa fa-line-chart"></i>
        </div>
        <div class="item-inner">
            <span class="title">Metrics</span>
        </div>
    </div>

    @endif

    <!-- ================================================== -->
    <!-- settings                                            -->
    <!-- ================================================== -->

    @if ( $authUser->hasRole('ADMIN_ROLE') || $authUser->hasRole('PROJECT_MANAGER_ROLE') )

    <li ui-sref-active="active">
        <a ui-sref="app.settings">
            <div class="item-content">
                <div class="item-media">
                    <i class="fa fa-cog"></i>
                </div>
                <div class="item-inner">
                    <span class="title">Settings</span>
                </div>
            </div>
        </a>
    </li>

    @else

    <div class="item-content">
        <div class="item-media">
            <i class="fa fa-cog"></i>
        </div>
        <div class="item-inner">
            <span class="title">Settings</span>
        </div>
    </div>

    @endif


    <!-- ================================================== -->
</ul>



