'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'authenticationCtrl',
        [
            '$log',
            '$rootScope',
            '$auth',
            '$state',
            'modalSrvc',
            'profileSrvc',
            'signinSrvc',
            'authenticationSrvc',
            function ($log, $rootScope, $auth, $state, modalSrvc, profileSrvc, signinSrvc, authenticationSrvc) {

                var CONTROLLER_NAME = 'authenticationCtrl';

                // ==================================================
                // - initialization
                // ==================================================
                var credentials = signinSrvc.getCredentials();

                // ==================================================
                // - authentication
                // ==================================================
                $auth.login(credentials).then(
                        function (data) {

                            // ==================================================
                            // - successful authentication 
                            // ==================================================

                            // Return an $http request for the now authenticated
                            // user so that we can flatten the promise chain
                            var userPromise = authenticationSrvc.getAuthenticatedUser();
                            userPromise.then(
                                    function (response) {

                                        // ==================================================
                                        // - retrieve user's profile 
                                        // - store authenticated user
                                        // ==================================================

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : authentication : response = " + JSON.stringify(response));
                                        // --------------------------------------------------------

                                        // Stringify the returned data to prepare it to go into local storage
                                        var userStr = JSON.stringify(response.user);

                                        // get the employee.id so we can query the db
                                        var employeeId = response.user.employeeId;

                                        var profilePromise = profileSrvc.getUserProfile(employeeId);
                                        profilePromise.then(
                                                function (response) {

                                                    // ==================================================
                                                    // - we just got the employee profile
                                                    // ==================================================

                                                    localStorage.setItem('user', userStr);
                                                    $rootScope.authenticated = true;
//                                                    $rootScope.currentUser = response.user;

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
                                                    // $log.debug(CONTROLLER_NAME + " : authentication : profile = " + JSON.stringify(profile));
                                                    // --------------------------------------------------------

                                                    // login is successful, redirect to the user profile page
                                                    $state.go('app.profile');

                                                },
                                                function (response) {

                                                    // ==================================================
                                                    // - retrieving user's profile failed
                                                    // ==================================================

                                                    // --------------------------------------------------------
                                                    $log.debug(CONTROLLER_NAME + " : retrieving user's profile failed : response = " + JSON.stringify(response));
                                                    // --------------------------------------------------------

                                                    signinSrvc.setCredentials(null);
                                                    localStorage.removeItem('user');
                                                    $rootScope.authenticated = false;

//                                                    var status = response.status;
//                                                    var message = response.statusText;
//                                                    modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);

                                                    // redirect to signin page
//                                                    $state.go('login.signin');
                                                    $state.go('logout.logout');
                                                }
                                        );

                                    },
                                    function (response) {

                                        // ==================================================
                                        // - failed the get the authenticated user 
                                        // ==================================================

                                        // --------------------------------------------------------
                                        $log.debug(CONTROLLER_NAME + " : failed to get the authenticated user : response = " + JSON.stringify(response));
                                        // --------------------------------------------------------

                                        signinSrvc.setCredentials(null);
                                        localStorage.removeItem('user');
                                        $rootScope.authenticated = false;

                                        var serverMsg = response.data.error;
                                        if (serverMsg === "token_expired") {

                                            var message = "Session Timeout.";
                                            modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                        }
//                                        else {
//
//                                            var status = response.status;
//                                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, serverMsg);
//                                        }

                                        // redirect to signin page
//                                        $state.go('login.signin');
                                        $state.go('logout.logout');
                                    }
                            );

                        },
                        function (response) {

                            // ==================================================
                            // - authentication failed
                            // ==================================================

                            // --------------------------------------------------------
                            $log.debug(CONTROLLER_NAME + " : authentication failed : response = " + JSON.stringify(response));
                            // --------------------------------------------------------

                            signinSrvc.setCredentials(null);
                            localStorage.removeItem('user');
                            $rootScope.authenticated = false;

                            // login failed
                            var message = 'Unauthorized User';
                            modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);

                            // redirect to signin page
                            $state.go('login.signin');
                        }

                );

                // ==================================================
            }
        ]
        );