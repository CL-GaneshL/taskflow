'use strict';

app.controller(
        'logoutCtrl',
        [
            '$log',
            '$rootScope',
            '$interval',
            '$state',
            '$auth',
            'signinSrvc',
            'logoutSrvc',
            function (
                    $log,
                    $rootScope,
                    $interval,
                    $state,
                    $auth,
                    signinSrvc,
                    logoutSrvc
                    )
            {

                var CONTROLLER_NAME = 'logoutCtrl';

                var dataPromise = logoutSrvc.logout();
                dataPromise.then(
                        function (response) {

                            $auth.logout().then(function () {

                                signinSrvc.setCredentials(null);
                                localStorage.removeItem('user');
                                $rootScope.authenticated = false;

                                $rootScope.user = {
                                    fullName: '',
                                    avatar: '../../img/caratlane/avatar-150.png'
                                };
                               
                                // add a short delay ...
                                $interval(function () {
                                    // go back to the signin page
                                    $state.go('login.signin');

                                    // --------------------------------------------------------
                                    $log.debug(CONTROLLER_NAME + " : Successfully logged out.");
                                    // --------------------------------------------------------

                                }, 1000, 1);

                            });
                        });

                // ==================================================
            }
        ]
        );