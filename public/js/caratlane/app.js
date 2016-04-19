'use strict';

// ------------------------------------------
// App declaration
// ------------------------------------------
var app = angular.module('TaskFlowApp', ['TaskFlow']);

angular.module("TaskFlow",
        [
            'ngAnimate',
            'ngCookies',
            'ngStorage',
            'ngSanitize',
            'ngTouch',
            'ui.router',
            'ui.bootstrap',
            'oc.lazyLoad',
            'cfp.loadingBar',
            'ncy-angular-breadcrumb',
            'duScroll',
            'satellizer',
            'angularSpinner'
        ]
        );

// ------------------------------------------
// App constants
// ------------------------------------------

app.constant('APP_MEDIAQUERY', {
    'desktopXL': 1200,
    'desktop': 992,
    'tablet': 768,
    'mobile': 480
});

// ------------------------------------------
// App constants
// ------------------------------------------

app.constant('JS_REQUIRES', {
    //*** Scripts
    scripts: {
        //*** Javascript Plugins
        'modernizr': ['../../js/caratlane/modernizr.js'],
        'moment': ['../../js/caratlane/moment.min.js'],
        //*** jQuery Plugins
        'perfect-scrollbar-plugin': [
            '../../js/caratlane/perfect-scrollbar.jquery.js',
            '../../css/caratlane/perfect-scrollbar.css'
        ],
        'ladda': [
            '../../js/caratlane/ladda.js',
            '../../css/caratlane/ladda-themeless.min.css'
        ],
        'chartjs': '../../js/caratlane/Chart.js',
        'jquery-sparkline': '../../js/caratlane/jquery.sparkline.js',
        'ckeditor-plugin': '../../js/caratlane/ckeditor.js',
        'jquery-nestable-plugin': ['../../js/caratlane/jquery.nestable.js'],
        'touchspin-plugin': [
            '../../js/caratlane/jquery.bootstrap-touchspin.js',
            '../../css/caratlane/jquery.bootstrap-touchspin.css'
        ],
        //*** Controllers
        'profileCtrl': ['../../js/caratlane/controllers/profileCtrl.js'],
        'signinCtrl': ['../../js/caratlane/controllers/signinCtrl.js'],
        'logoutCtrl': ['../../js/caratlane/controllers/logoutCtrl.js'],
        'authenticationCtrl': ['../../js/caratlane/controllers/authenticationCtrl.js'],
        'profileOverviewCtrl': ['../../js/caratlane/controllers/profileOverviewCtrl.js'],
        'profileEditCtrl': ['../../js/caratlane/controllers/profileEditCtrl.js'],
        'profileSkillsCtrl': ['../../js/caratlane/controllers/profileSkillsCtrl.js'],
        'profileTasksCtrl': '../../js/caratlane/controllers/profileTasksCtrl.js',
        'profileHolidaysCtrl': '../../js/caratlane/controllers/profileHolidaysCtrl.js',
        'skillsCtrl': '../../js/caratlane/controllers/skillsCtrl.js',
        'employeeProjectsCtrl': '../../js/caratlane/controllers/employeeProjectsCtrl.js',
        'projectsCtrl': '../../js/caratlane/controllers/projectsCtrl.js',
        'teamsCtrl': '../../js/caratlane/controllers/teamsCtrl.js',
        'templatesCtrl': '../../js/caratlane/controllers/templatesCtrl.js',
        'employeesCtrl': '../../js/caratlane/controllers/employeesCtrl.js',
        'employeesListCtrl': '../../js/caratlane/controllers/employeesListCtrl.js',
        'taskAllocationCtrl': '../../js/caratlane/controllers/taskAllocationCtrl.js',
        'settingsNonWorkingDaysCtrl': '../../js/caratlane/controllers/settingsNonWorkingDaysCtrl.js',
        'settingsTaskGeneratorCtrl': '../../js/caratlane/controllers/settingsTaskGeneratorCtrl.js',
        // ----
        //*** Services
        'teamsSrvc': '../../js/caratlane/services/teamsSrvc.js',
        'templatesSrvc': '../../js/caratlane/services/templatesSrvc.js',
        'projectsSrvc': '../../js/caratlane/services/projectsSrvc.js',
        'skillsSrvc': '../../js/caratlane/services/skillsSrvc.js',
        'modalSrvc': '../../js/caratlane/services/modalSrvc.js',
        'profileSrvc': '../../js/caratlane/services/profileSrvc.js',
        'pageSrvc': '../../js/caratlane/services/pageSrvc.js',
        'userSrvc': '../../js/caratlane/services/userSrvc.js',
        'signinSrvc': '../../js/caratlane/services/signinSrvc.js',
        'logoutSrvc': '../../js/caratlane/services/logoutSrvc.js',
        'authenticationSrvc': '../../js/caratlane/services/authenticationSrvc.js',
        'employeesSrvc': '../../js/caratlane/services/employeesSrvc.js',
        'taskAllocationSrvc': '../../js/caratlane/services/taskAllocationSrvc.js',
        'settingsSrvc': '../../js/caratlane/services/settingsSrvc.js',
        'nonWorkingDaysSrvc': '../../js/caratlane/services/nonWorkingDaysSrvc.js',
        'holidaysSrvc': '../../js/caratlane/services/holidaysSrvc.js',
        'tasksSrvc': '../../js/caratlane/services/tasksSrvc.js',
        'projectsTasksSrvc': '../../js/caratlane/services/projectsTasksSrvc.js',
        // ----
        //*** Other Controllers
        'htmlToPlaintext': '../../js/caratlane/filters/htmlToPlaintext.js',
    },
    //*** angularJS Modules
    modules: [{
            name: 'angularMoment',
            files: ['../../js/caratlane/angular-moment.js']
        }, {
            name: 'toaster',
            files: [
                '../../js/caratlane/toaster.js',
                '../../css/caratlane/toaster.css'
            ]
        },
        {
            name: 'angularBootstrapNavTree',
            files: [
                '../../js/caratlane/abn_tree_directive.js',
                '../../css/caratlane/abn_tree.css'
            ]
        },
        {
            name: 'angular-ladda',
            files: ['../../js/caratlane/angular-ladda.js']
        }, {
            name: 'ngTable',
            files: [
                '../../js/caratlane/ng-table.js',
                '../../css/caratlane/ng-table.css'
            ]
        }, {
            name: 'ui.select',
            files: [
                '../../js/caratlane/select.js',
                '../../css/caratlane/select.css',
                '../../css/caratlane/select2.min.css',
                '../../css/caratlane/select2-bootstrap.css',
                '../../css/caratlane/selectize.bootstrap3.css'
            ]
        },
        {
            name: 'ngImgCrop',
            files: [
                '../../js/caratlane/ng-img-crop.js',
                '../../css/caratlane/ng-img-crop.css'
            ]
        }, {
            name: 'angularFileUpload',
            files: ['../../js/caratlane/angular-file-upload.js']
        }, {
            name: 'ngAside',
            files: [
                '../../js/caratlane/angular-aside.js',
                '../../css/caratlane/angular-aside.css'
            ]
        }, {
            name: 'truncate',
            files: ['../../js/caratlane/truncate.js']
        },
        {
            name: 'monospaced.elastic',
            files: ['../../js/caratlane/elastic.js']
        },
        {
            name: 'tc.chartjs',
            files: ['../../js/caratlane/tc-angular-chartjs.js']
        }, {
            name: 'flow',
            files: ['../../js/caratlane/ng-flow-standalone.js']
        }, {
            name: 'uiSwitch',
            files: [
                '../../js/caratlane/angular-ui-switch.js',
                '../../css/caratlane/angular-ui-switch.css'
            ]
        }, {
            name: 'ckeditor',
            files: ['../../js/caratlane/angular-ckeditor.js']
        }, {
            name: 'mwl.calendar',
            files: [
                '../../js/caratlane/angular-bootstrap-calendar-tpls-modified.js',
                '../../css/caratlane/angular-bootstrap-calendar.css'
            ]
        }, {
            name: 'ng-nestable',
            files: ['../../js/caratlane/angular-nestable.js']
        }, {
            name: 'vAccordion',
            files: [
                '../../js/caratlane/v-accordion.js',
                '../../css/caratlane/v-accordion.css'
            ]
        },
        {
            name: 'angular-notification-icons',
            files: [
                '../../js/caratlane/angular-notification-icons.js',
                '../../css/caratlane/angular-notification-icons.css'
            ]
        }
    ]
});

// -----------------------------------------
// Config for the router
// -----------------------------------------
app.config([
    '$stateProvider',
    '$urlRouterProvider',
    '$controllerProvider',
    '$compileProvider',
    '$filterProvider',
    '$provide',
    '$ocLazyLoadProvider',
    '$locationProvider',
    '$interpolateProvider',
    '$authProvider',
    'JS_REQUIRES',
    function (
            $stateProvider,
            $urlRouterProvider,
            $controllerProvider,
            $compileProvider,
            $filterProvider,
            $provide,
            $ocLazyLoadProvider,
            $locationProvider,
            $interpolateProvider,
            $authProvider,
            jsRequires
            )
    {

        app.controller = $controllerProvider.register;
        app.directive = $compileProvider.directive;
        app.filter = $filterProvider.register;
        app.factory = $provide.factory;
        app.service = $provide.service;
        app.constant = $provide.constant;
        app.value = $provide.value;

        // -----------------------------------------
        // re-defined the Angular interpolators
        // in order to avoid any conflict with Blade
        // -----------------------------------------
        $interpolateProvider.startSymbol('<%');
        $interpolateProvider.endSymbol('%>');

        // -----------------------------------------
        // Lazy modules
        // -----------------------------------------
        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
            modules: jsRequires.modules
        });

        // -----------------------------------
        // Satellizer configuration that specifies which API
        // route the JWT should be retrieved from
        // -----------------------------------
        $authProvider.loginUrl = '/taskflow/apis/v1/authenticate';

        // -----------------------------------
        // For any unmatched url, redirect to /app/dashboard initial page
        // -----------------------------------------
        $urlRouterProvider.otherwise('/taskflow/signin');

        // -----------------------------------
        // use the HTML5 History API, help to remove 
        // the angular the infamous final "/" on urls
        $locationProvider.html5Mode(true);
        // -----------------------------------

        // -----------------------------------
        // Set up the states
        // -----------------------------------
        $stateProvider
                // -----------------------------------------
                // login and authentication pages
                // -----------------------------------------                 
                .state('login', {
                    url: '/taskflow',
                    template: '<div ui-view class="fade-in-right-big smooth"></div>',
                    abstract: true
                })
                .state('login.signin', {
                    url: '/signin',
                    templateUrl: "taskflow/fragments/signin",
                    resolve: loadSequence(
                            'signinSrvc',
                            'signinCtrl'
                            )
                })
                .state('login.authentication', {
                    url: '/authentication',
                    templateUrl: "taskflow/fragments/signin_authentication",
                    resolve: loadSequence(
                            'modalSrvc',
                            'signinSrvc',
                            'profileSrvc',
                            'authenticationSrvc',
                            'authenticationCtrl'
                            )
                })
                // -----------------------------------------
                // logout page
                // -----------------------------------------       
                .state('logout', {
                    url: '/taskflow',
                    template: '<div ui-view class="fade-in-left-big smooth"></div>',
                    abstract: true
                })
                .state('logout.logout', {
                    url: '/logout',
                    templateUrl: "taskflow/fragments/logout",
                    resolve: loadSequence(
                            'signinSrvc',
                            'logoutSrvc',
                            'logoutCtrl'
                            )
                })
                // -----------------------------------------
                // app
                // -----------------------------------------        
                .state('app', {
                    url: "/taskflow",
                    templateUrl: "taskflow/fragments/app",
                    resolve: loadSequence(
                            'modernizr',
                            'moment',
                            'angularMoment',
                            'uiSwitch',
                            'perfect-scrollbar-plugin',
                            'toaster',
                            'ngAside',
                            'vAccordion',
                            'chartjs',
                            'tc.chartjs',
                            'truncate',
                            'htmlToPlaintext',
                            'angular-notification-icons',
                            'flow'
                            ),
                    abstract: true
                })
                // -----------------------------------------
                // user profile
                // ----------------------------------------- 
                .state('app.profile', {
                    url: '/profile',
                    templateUrl: "taskflow/fragments/profile",
                    resolve: loadSequence(
                            'flow',
                            'pageSrvc',
                            'profileSrvc',
                            'employeesSrvc',
                            'skillsSrvc',
                            'holidaysSrvc',
                            'nonWorkingDaysSrvc',
                            'tasksSrvc',
                            'modalSrvc',
                            'profileCtrl',
                            'profileEditCtrl',
                            'profileOverviewCtrl',
                            'profileSkillsCtrl',
                            'profileTasksCtrl',
                            'profileHolidaysCtrl',
                            'authenticationSrvc'
                            ),
                    title: 'My Profile',
                    ncyBreadcrumb: {
                        label: 'Employee Profile'
                    }
                })
                // -----------------------------------------
                // employees states
                // -----------------------------------------   
                .state('app.employees', {
                    url: '/employees',
                    templateUrl: "taskflow/fragments/employees",
                    resolve: loadSequence(
                            'flow',
                            'angularBootstrapNavTree',
                            'pageSrvc',
                            'modalSrvc',
                            'employeesSrvc',
                            'employeesCtrl'
                            ),
                    title: 'Employees'
                })
                // -----------------------------------------
                // employee state
                // ----------------------------------------- 
                .state('app.employee', {
                    url: '/employee',
                    templateUrl: "taskflow/fragments/employee",
                    resolve: loadSequence(
                            'flow',
                            'angularBootstrapNavTree',
                            'profileSrvc',
                            'pageSrvc',
                            'modalSrvc',
                            'employeesSrvc',
                            'skillsSrvc',
                            'holidaysSrvc',
                            'nonWorkingDaysSrvc',
                            'tasksSrvc',
                            'employeesCtrl',
                            'employeeProjectsCtrl',
                            'profileCtrl',
                            'profileEditCtrl',
                            'profileOverviewCtrl',
                            'profileSkillsCtrl',
                            'profileTasksCtrl',
                            'profileHolidaysCtrl',
                            'authenticationSrvc'
                            ),
                    title: 'Employee'
                })
                // -----------------------------------------
                // skills states
                // -----------------------------------------   
                .state('app.skills', {
                    url: '/skills',
                    templateUrl: "taskflow/fragments/skills",
                    resolve: loadSequence(
                            'modalSrvc',
                            'tasksSrvc',
                            'skillsCtrl'
                            ),
                    title: 'Skills'
                })
                // -----------------------------------------
                // teams states
                // -----------------------------------------   
                .state('app.teams', {
                    url: '/teams',
                    templateUrl: "taskflow/fragments/teams",
                    resolve: loadSequence(
                            'modalSrvc',
                            'teamsSrvc',
                            'employeesListCtrl',
                            'teamsCtrl'
                            ),
                    title: 'Teams'
                })
                // -----------------------------------------
                // projects states
                // -----------------------------------------   
                .state('app.projects', {
                    url: '/projects',
                    templateUrl: "taskflow/fragments/projects",
                    resolve: loadSequence(
                            'modalSrvc',
                            'projectsSrvc',
                            'projectsCtrl',
                            'templatesSrvc',
                            'templatesCtrl'
                            ),
                    title: 'Projects'
                })
                // -----------------------------------------
                // projects states
                // ----------------------------------------- 
                .state('app.tasks', {
                    url: '/tasks',
                    templateUrl: "taskflow/fragments/tasks",
                    resolve: loadSequence(
                            'modalSrvc',
                            'taskAllocationSrvc',
                            'taskAllocationCtrl',
                            'nonWorkingDaysSrvc',
                            'holidaysSrvc',
                            'tasksSrvc',
                            'projectsTasksSrvc'
                            ),
                    title: 'Tasks'
                })
                // -----------------------------------------
                // templates states
                // -----------------------------------------   
                .state('app.templates', {
                    url: '/templates',
                    templateUrl: "taskflow/fragments/templates",
                    resolve: loadSequence(
                            'modalSrvc',
                            'templatesSrvc',
                            'templatesCtrl'
                            ),
                    title: 'Templates'
                })

                // -----------------------------------------
                // settings states
                // -----------------------------------------   
                .state('app.settings', {
                    url: '/settings',
                    templateUrl: "taskflow/fragments/settings",
                    resolve: loadSequence(
                            'modalSrvc',
                            'nonWorkingDaysSrvc',
                            'settingsSrvc',
                            'settingsNonWorkingDaysCtrl',
                            'settingsTaskGeneratorCtrl'
                            ),
                    title: 'Templates'
                })

                // -----------------------------------------
                // metrics states
                // -----------------------------------------   
                .state('app.metrics', {
                    url: '/metrics',
                    templateUrl: "taskflow/fragments/metrics",
                    title: 'Metrics'
                })
                // -----------------------------------------
                ; // end of state definitions
        // -----------------------------------------

        // ---------------------------------------------------------------------------
        // Generates a resolve object previously configured in constant.JS_REQUIRES
        // ---------------------------------------------------------------------------
        function loadSequence() {
            var _args = arguments;
            return {
                deps: ['$ocLazyLoad', '$q',
                    function ($ocLL, $q) {
                        var promise = $q.when(1);
                        for (var i = 0, len = _args.length; i < len; i++) {
                            promise = promiseThen(_args[i]);
                        }
                        return promise;

                        function promiseThen(_arg) {
                            if (typeof _arg === 'function')
                                return promise.then(_arg);
                            else
                                return promise.then(function () {
                                    var nowLoad = requiredData(_arg);
                                    if (!nowLoad)
                                        return $.error('Route resolve: Bad resource name [' + _arg + ']');
                                    return $ocLL.load(nowLoad);
                                });
                        }

                        function requiredData(name) {
                            if (jsRequires.modules)
                                for (var m in jsRequires.modules)
                                    if (jsRequires.modules[m].name && jsRequires.modules[m].name === name)
                                        return jsRequires.modules[m];
                            return jsRequires.scripts && jsRequires.scripts[name];
                        }
                    }]
            };
        }
    }
]);

// ------------------------------------------
// Angular-Loading-Bar 
// ------------------------------------------

app.config(['cfpLoadingBarProvider',
    function (cfpLoadingBarProvider) {
        cfpLoadingBarProvider.includeBar = true;
        cfpLoadingBarProvider.includeSpinner = false;
    }
]);

// -----------------------------------------
// Redirecting the user when logged out
// -----------------------------------------

app.config([
    '$stateProvider',
    '$urlRouterProvider',
    '$authProvider',
    '$httpProvider',
    '$provide',
    function ($stateProvider, $urlRouterProvider, $authProvider, $httpProvider, $provide) {

        function redirectWhenLoggedOut($q, $injector) {

            return {
                responseError: function (rejection) {

                    // --------------------------------------------------------
                    // console.debug("rejection interceptor : rejection = " + JSON.stringify(rejection));
                    // --------------------------------------------------------

                    // Need to use $injector.get to bring in $state 
                    // or else we get a circular dependency error
                    var $state = $injector.get('$state');

                    // check for the specific rejection reasons
                    var rejectionReasons = [
                        'token_not_provided',
                        'token_expired',
                        'token_absent',
                        'token_invalid'
                    ];

                    // Loop through each rejection reason and redirect to the login
                    // state if one is encountered
                    angular.forEach(rejectionReasons, function (value, key) {

                        if (rejection.data.error === value) {

                            // If we get a rejection corresponding to one of the reasons
                            // in our array, we know we need to authenticate the user so 
                            // we can remove the current user from local storage
                            localStorage.removeItem('user');

                            // redirect to signin page
//                            $state.go('login.signin');
                            $state.go('logout.logout');
                        }
                    });

                    return $q.reject(rejection);
                }
            };
        }

        // Setup for the $httpInterceptor
        $provide.factory('redirectWhenLoggedOut', redirectWhenLoggedOut);

        // Push the new factory onto the $http interceptor array
        $httpProvider.interceptors.push('redirectWhenLoggedOut');

        $authProvider.loginUrl = '/taskflow/apis/v1/authenticate';
    }
]);

// ------------------------------------------
// start the App
// ------------------------------------------
app.run([
    '$rootScope',
    '$state',
    '$stateParams',
    function (
            $rootScope,
            $state,
            $stateParams
            )
    {

        // ------------------------------------------
        // Attach Fastclick for eliminating the 300ms delay between a 
        // physical tap and the firing of a click event on mobile browsers
        // ------------------------------------------
        FastClick.attach(document.body);

        // ------------------------------------------
        // Set some reference to access them from any scope
        // ------------------------------------------
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;

        // ------------------------------------------
        // global App scope
        // ------------------------------------------
        $rootScope.app = {
            name: 'TaskFlow',
            author: 'Caratlane',
            description: 'Caratlane - Task Allocation Tool',
            version: '1.0.0',
            year: ((new Date()).getFullYear()), // automatic current year (for copyright information)
            isMobile: (function () {// true if the browser is a mobile device
                var check = false;
                if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                    check = true;
                }
                return check;
            })(),
            layout: {
                isNavbarFixed: false,
                isSidebarFixed: true,
                isSidebarClosed: false,
                isFooterFixed: false,
                theme: 'theme-1',
                logo: '../../img/caratlane/logo.png',
            }
        };

        // ------------------------------------------
        // user profile
        // ------------------------------------------
        $rootScope.user = {
            fullName: '',
            // default avatar
            avatar: '../../img/caratlane/avatar-150.png'
        };

    }
]);


