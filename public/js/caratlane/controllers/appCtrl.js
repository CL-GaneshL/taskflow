'use strict';

app.config(function ($logProvider) {

    $logProvider.debugEnabled(true);

}).controller(
        'appCtrl',
        [
            '$rootScope',
            '$scope',
            '$state',
            '$localStorage',
            '$window',
            '$document',
            'cfpLoadingBar',
            'profileSrvc',
            'modalSrvc',
            function (
                    $rootScope,
                    $scope,
                    $state,
                    $localStorage,
                    $window,
                    $document,
                    cfpLoadingBar,
                    profileSrvc,
                    modalSrvc
                    )
            {

                var CONTROLLER_NAME = 'appCtrl';

                // -----------------------------------
                // Loading bar transition
                // -----------------------------------
                var $win = $($window);

                $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
                    cfpLoadingBar.start();
                });

                $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {

                    //stop loading bar on stateChangeSuccess
                    event.targetScope.$watch("$viewContentLoaded", function () {
                        cfpLoadingBar.complete();
                    });

                    // scroll top the page on change state
                    $('#app .main-content').css({
                        position: 'relative',
                        top: 'auto'
                    });

                    $('footer').show();

                    window.scrollTo(0, 0);

                    // Save the route title
                    $rootScope.currTitle = $state.current.title;
                });

                // -----------------------------------
                // State not found
                // -----------------------------------
                $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {

                    // --------------------------------------------------------
                    console.log("Unfound State To : " + unfoundState.to);
                    console.log("Unfound State ToParams : " + unfoundState.toParams);
                    console.log("Unfound State Options : " + unfoundState.options);
                    // --------------------------------------------------------
                });

                // -------------------------------
                // browser's tab title
                // -------------------------------
                $rootScope.pageTitle = function () {

                    var title = $rootScope.app.name;

                    if ($rootScope.currTitle !== undefined
                            && $rootScope.currTitle !== null
                            && $rootScope.currTitle !== '')
                    {
                        title += ' - ' + $rootScope.currTitle;
                    }

                    return title;
                };
                // -------------------------------

                // save settings to local storage
                if (angular.isDefined($localStorage.layout)) {
                    $scope.app.layout = $localStorage.layout;

                } else {
                    $localStorage.layout = $scope.app.layout;
                }
                $scope.$watch('app.layout', function () {
                    // save to local storage
                    $localStorage.layout = $scope.app.layout;
                }, true);

                //global function to scroll page up
                $scope.toTheTop = function () {
                    $document.scrollTopAnimated(0, 600);
                };

                // -------------------------------
                // browsing
                // -------------------------------

                // Function that find the exact height and width of the viewport in a cross-browser way
                var viewport = function () {
                    var e = window, a = 'inner';
                    if (!('innerWidth' in window)) {
                        a = 'client';
                        e = document.documentElement || document.body;
                    }
                    return {
                        width: e[a + 'Width'],
                        height: e[a + 'Height']
                    };
                };

                // function that adds information in a scope of the height and width of the page
                $scope.getWindowDimensions = function () {
                    return {
                        'h': viewport().height,
                        'w': viewport().width
                    };
                };

                // Detect when window is resized and set some variables
                $scope.$watch($scope.getWindowDimensions, function (newValue, oldValue) {
                    $scope.windowHeight = newValue.h;
                    $scope.windowWidth = newValue.w;

                    if (newValue.w >= 992) {
                        $scope.isLargeDevice = true;
                    } else {
                        $scope.isLargeDevice = false;
                    }
                    if (newValue.w < 992) {
                        $scope.isSmallDevice = true;
                    } else {
                        $scope.isSmallDevice = false;
                    }
                    if (newValue.w <= 768) {
                        $scope.isMobileDevice = true;
                    } else {
                        $scope.isMobileDevice = false;
                    }
                }, true);

                // Apply on resize
                $win.on('resize', function () {

                    $scope.$apply();
                    if ($scope.isLargeDevice) {
                        $('#app .main-content').css({
                            position: 'relative',
                            top: 'auto',
                            width: 'auto'
                        });
                        $('footer').show();
                    }
                });

                // ------------------------------------------
                // init the app for authentication
                // ------------------------------------------
                $rootScope.$on('$stateChangeStart', function (event, toState) {

                    // Grab the user from local storage and parse it to an object
                    var user = JSON.parse(localStorage.getItem('user'));

                    // --------------------------------------------------------
                    console.debug(CONTROLLER_NAME + " : user = " + JSON.stringify(user));
                    // --------------------------------------------------------

                    // If there is any user data in local storage then the user is quite
                    // likely authenticated. If their token is expired, or if they are
                    // otherwise not actually authenticated, they will be redirected to
                    // the signin state because of the rejected request anyway.
                    if (user) {

                        // get the employee.id so we can query the db
                        var employeeId = user.employeeId;

                        var profilePromise = profileSrvc.getUserProfile(employeeId);
                        profilePromise.then(
                                function (response) {

                                    // ==================================================
                                    // - we just got the employee profile
                                    // ==================================================
                                    $rootScope.authenticated = true;

                                    var profile = response.profile;
                                    profileSrvc.setUserProfile(profile);

                                    var fullname = profile.employee.fullName;
                                    var avatar = profile.employee.avatar;

                                    // Putting the user's data on $rootScope allows
                                    // us to access it anywhere across the app, i.e.
                                    // from the top nav bav user profile.
                                    $rootScope.user = {
                                        fullName: fullname,
                                        avatar: avatar
                                    };

                                    // --------------------------------------------------------
                                    // console.debug(CONTROLLER_NAME + " : toState = " + JSON.stringify(toState));
                                    // --------------------------------------------------------

                                    // If the user is logged in and we hit the signin route we don't need
                                    // to stay there and can send the user to the main state
                                    if (toState.name === 'login.signin' || toState.name === 'login.authentication') {

                                        // Preventing the default behavior allows us 
                                        // to use $state.go to change states.
                                        event.preventDefault();

                                        // --------------------------------------------------------
                                        // console.debug(CONTROLLER_NAME + " : re direct to app.profile.");
                                        // --------------------------------------------------------

                                        // redirect to the user profile page
                                        $state.go('app.profile');
                                    }
                                    else {
                                        // --------------------------------------------------------
                                        // console.debug(CONTROLLER_NAME + " : no redirection.");
                                        // --------------------------------------------------------
                                    }

                                },
                                function (response) {

                                    // ==================================================
                                    // - retrieving user's profile failed
                                    // ==================================================

                                    // signinSrvc.setCredentials(null);
                                    localStorage.removeItem('user');
                                    $rootScope.authenticated = false;

//                                    var status = response.status;
//                                    var message = response.statusText;
//                                    modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);

                                    // redirect to signin page
                                    $state.go('login.signin');
                                }
                        );
                    }

                });
                // ==============================================
            }
        ]
        );
