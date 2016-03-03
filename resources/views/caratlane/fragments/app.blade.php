
<!-- ================================================== -->
<!-- - toaster directive                                -->
<!-- ================================================== -->
<toaster-container toaster-options="{'position-class': 'toast-top-right', 'close-button':true}">    
</toaster-container>

<!-- ================================================== -->
<!-- - sidebar                                          -->
<!-- ================================================== -->
<div class="sidebar app-aside hidden-print" id="sidebar" toggleable parent-active-class="app-slide-off" >
    <div perfect-scrollbar wheel-propagation="false" suppress-scroll-x="true" class="sidebar-container" >
        <div data-ng-include="'taskflow/fragments/app_sidebar'"></div>
    </div>
</div>

<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->
<div class="app-content" ng-class="{loading: loading}">

    <!-- ================================================== -->
    <!-- - top navbar                                       -->
    <!-- ================================================== -->
    <header data-ng-include="'taskflow/fragments/app_top_navbar'"
            class="navbar navbar-default navbar-static-top hidden-print" >                
    </header>

    <!-- ================================================== -->
    <!-- - main content                                     -->
    <!-- ================================================== -->
    <div data-ng-include="'taskflow/fragments/app_main_content'"
         class="main-content" >             
    </div>

</div>

<!-- ================================================== -->
<!-- - footer                                           -->
<!-- ================================================== -->
<footer data-ng-include="'taskflow/fragments/app_footer'"
        class="hidden-print">            
</footer>


