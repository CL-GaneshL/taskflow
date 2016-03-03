'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'logoutCtrl',
        [
            '$log',
            '$rootScope',
            '$interval',
            '$state',
            'signinSrvc',
            function ($log, $rootScope, $interval, $state, signinSrvc) {

                var CONTROLLER_NAME = 'logoutCtrl';

                signinSrvc.setCredentials(null);
                localStorage.removeItem('user');
                $rootScope.authenticated = false;

                $rootScope.user = {
                    fullName: '',
                    avatar: '../../img/caratlane/avatar-150.png'
                };

                // --------------------------------------------------------
                $log.debug(CONTROLLER_NAME + " : logout ");
                // --------------------------------------------------------

                // add a short delay ...
                $interval(function () {
                    // go back to the signin page
                    $state.go('login.signin', {});
                }, 1000, 1);

                // ==================================================
            }
        ]
        );