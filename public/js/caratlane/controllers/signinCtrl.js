'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'signinCtrl',
        [
            '$scope',
            '$state',
            'signinSrvc',
            function ($scope, $state, signinSrvc) {

                var CONTROLLER_NAME = 'signinCtrl';

                // ==================================================
                // - initialization
                // ==================================================
                $scope.username = null;
                $scope.password = null;

                // ==================================================
                // - login function
                // ==================================================
                $scope.login = function () {

                    var credentials = {
                        username: $scope.username,
                        password: $scope.password
                    };

                    signinSrvc.setCredentials(credentials);

                    // launch the spinner and start authentication
                    $state.go('login.authentication', {});

                };

                // ==================================================
            }
        ]
        );