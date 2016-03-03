<!DOCTYPE html>
<html lang="en" data-ng-app="TaskFlowApp">
    <head>

        <title data-ng-bind="pageTitle()">Caratlane - Task Allocation Tool</title>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="description" content="Caratlane - Task Allocation Tool">
        <meta name="keywords" content="Caratlane - Task Allocation Tool">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
        <meta name="HandheldFriendly" content="true" /> 
        <meta name="apple-touch-fullscreen" content="yes" />

        <base href="{{ asset(Config::get('caratlane.constants.CARATLANE_PATH'))}}" > 

        <!-- favicon -->

        <!-- Standard Favicon -->
        <link rel="icon" href="{{ asset('img/icons/caratlane/favicon.ico')}}" type="img/x-icon" >

        <!-- Firefox, Chrome, Safari, IE 11+ and Opera. -->
        <link rel="icon" href="{{ asset('img/icons/caratlane/favicon.png')}}" type="image/png">
        <link rel="icon" href="{{ asset('img/icons/caratlane/favicon-196.png')}}" sizes="196x196" type="image/png">

        <!-- fonts -->
        <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Lato:300,400,400italic,600,700|Raleway:300,400,500,600,700|Crete+Round:400italic" />

        <!-- css -->
        <link rel="stylesheet" type="text/css" href="{{ asset('css/caratlane/bootstrap.css')}}" />
        <link rel="stylesheet" type="text/css" href="{{ asset('css/caratlane/font-awesome.css')}}" />
        <link rel="stylesheet" type="text/css" href="{{ asset('css/caratlane/themify-icons.css')}}" />
        <link rel="stylesheet" type="text/css" href="{{ asset('css/caratlane/angular-loading-bar.css')}}" />
        <link rel="stylesheet" type="text/css" href="{{ asset('css/caratlane/animate.css')}}" />
        <link rel="stylesheet" type="text/css" href="{{ asset('css/caratlane/styles.css')}}" />
        <link rel="stylesheet" type="text/css" href="{{ asset('css/caratlane/plugins.css')}}" />
        <link rel="stylesheet" type="text/css" href="{{ asset('css/caratlane/theme-1.css')}}" />

    </head>

    <body ng-controller="appCtrl">

        <div 
            ui-view id="app" ng-class="
        {
            'app-mobile' : app.isMobile, 
            'app-navbar-fixed' : app.layout.isNavbarFixed, 
            'app-sidebar-fixed' : app.layout.isSidebarFixed,
            'app-sidebar- closed':app.layout.isSidebarClosed, 
            'app-footer - fixed':app.layout.isFooterFixed
        }" >  

        </div>


        <!-- libraries -->
        <script type="text/javascript" src="{{ asset('js/caratlane/jquery.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/fastclick.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/angular.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/angular-cookies.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/angular-animate.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/angular-touch.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/angular-sanitize.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/angular-ui-router.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/ngStorage.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/ocLazyLoad.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/angular-breadcrumb.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/ui-bootstrap-tpls-1.0.3.min.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/angular-loading-bar.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/angular-scroll.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/spin.js')}} "></script>    
        <script type="text/javascript" src="{{ asset('js/caratlane/angular-spinner.js')}} "></script>    
        <script type="text/javascript" src="{{ asset('js/caratlane/satellizer.js')}} "></script>

        <!-- Webapp Scripts -->
        <script type="text/javascript" src="{{ asset('js/caratlane/app.js')}} "></script>			
        <script type="text/javascript" src="{{ asset('js/caratlane/services/modalSrvc.js')}} "></script>
        <script type="text/javascript" src="{{ asset('js/caratlane/services/profileSrvc.js')}} "></script>			
        <script type="text/javascript" src="{{ asset('js/caratlane/controllers/appCtrl.js')}} "></script>

    </body>
</html>

