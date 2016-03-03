'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'profileEditCtrl',
        [
            '$log',
            '$scope',
            'flowFactory',
            '$uibModal',
            'profileSrvc',
            'pageSrvc',
            'employeesSrvc',
            'authenticationSrvc',
            'modalSrvc',
            function ($log,
                    $scope,
                    flowFactory,
                    $uibModal,
                    profileSrvc,
                    pageSrvc,
                    employeesSrvc,
                    authenticationSrvc,
                    modalSrvc
                    )
            {

                var CONTROLLER_NAME = 'profileEditCtrl';

                $scope.profile = null;
                $scope.manager = '';

                // check if this controller is used for the User 
                // Profile page or for the Employee page
                var isProfilePage = pageSrvc.isProfilePage();

                if (isProfilePage) {
                    // the User overview page
                    $scope.profile = profileSrvc.getEmployee();

                    if ($scope.profile.isTeamLeader === 1) {
                        $scope.manager = 'teamLeader';
                    }
                    else if ($scope.profile.isProjectManager === 1) {
                        $scope.manager = 'projectManager';
                    }
                    else {
                        $scope.manager = 'teamMember';
                    }

                    $scope.employeeEmployementType = $scope.profile.employementType;
                }
                else {

                    var dataPromise = employeesSrvc.getEmployee();
                    dataPromise.then(
                            function (response) {

                                $scope.profile = response.employee;

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : employee profile = " + JSON.stringify($scope.profile));
                                // --------------------------------------------------------

                                $scope.employeeEmployementType = $scope.profile.employementType;

                                if ($scope.profile.isTeamLeader === 1) {
                                    $scope.manager = 'teamLeader';
                                }
                                else if ($scope.profile.isProjectManager === 1) {
                                    $scope.manager = 'projectManager';
                                }
                                else {
                                    $scope.manager = 'teamMember';
                                }
                            });
                }

                // needed to display the avatar
                $scope.obj = new Flow();

                // the employement type select menu
                $scope.employementTypes = [
                    {type: 'Intern'},
                    {type: 'FTE'}
                ];

                // ==================================================
                // update an employee
                // ==================================================
                $scope.updateProfile = function () {

                    if ($scope.manager === 'teamLeader') {
                        $scope.profile.isTeamLeader = 1;
                        $scope.profile.isProjectManager = 0;
                    }
                    else if ($scope.manager === 'projectManager') {
                        $scope.profile.isTeamLeader = 0;
                        $scope.profile.isProjectManager = 1;
                    }
                    else {
                        $scope.profile.isTeamLeader = 0;
                        $scope.profile.isProjectManager = 0;
                    }

                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : profile to update = " + JSON.stringify($scope.profile));
                    // --------------------------------------------------------

                    var dataPromise = employeesSrvc.updateEmployee($scope.profile);
                    dataPromise.then(
                            function (response) {

                                var msg = 'Employee successfully updated.';
                                modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, msg);
                            },
                            function (response) {

                                var msg = 'Internal server error.';
                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, 500, msg);

                            }
                    );
                };

                // ==================================================
                // change password modal
                // ==================================================
                $scope.editPassword = function () {

                    var employee_id = $scope.profile.id;

                    $uibModal.open(
                            {
                                templateUrl: 'taskflow/fragments/profile_password',
                                placement: 'center',
                                size: 'sm',
                                controller: function ($scope, $uibModalInstance) {
                                    $scope.newPassword = null;
                                    $scope.$modalInstance = $uibModalInstance;

                                    $scope.cancel = function () {
                                        $uibModalInstance.dismiss('cancel');
                                    };

                                    $scope.changePassword = function () {

                                        var authPromise = authenticationSrvc.changePassword(employee_id, $scope.newPassword);
                                        authPromise.then(
                                                function (response) {

                                                    var msg = 'Password changed.';
                                                    modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, msg);

                                                },
                                                function (response) {
          
                                                    // ==================================================
                                                    // - failed to change password 
                                                    // ==================================================

                                                    var status = response.status;
                                                    var message = response.statusText;
                                                    modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                                }
                                        );

                                        $uibModalInstance.dismiss('changePassword');
                                    };
                                }
                            }
                    );
                };
                // ==================================================

            }
        ]);
        