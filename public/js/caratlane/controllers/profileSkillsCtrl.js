'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'profileSkillsCtrl',
        [
            '$log',
            '$scope',
            '$uibModal',
            'profileSrvc',
            'pageSrvc',
            'employeesSrvc',
            'skillsSrvc',
            'modalSrvc',
            function ($log,
                    $scope,
                    $uibModal,
                    profileSrvc,
                    pageSrvc,
                    employeesSrvc,
                    skillsSrvc,
                    modalSrvc)
            {

                var CONTROLLER_NAME = 'profileSkillsCtrl';

                $scope.skills = null;
                $scope.allSkills = null;
                $scope.newSkillDesignation = null;

                // check if this controller is used for the User 
                // Profile page or for the Employee page
                var isProfilePage = pageSrvc.isProfilePage();

                // list of all skills for the dropdown menu
                var skillsPromise = skillsSrvc.getSkills();
                skillsPromise.then(
                        function (response) {
                            $scope.allSkills = response.allSkills;

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : $scope.allSkills = " + JSON.stringify($scope.allSkills));
                            // --------------------------------------------------------
                        },
                        function (response) {

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                            // --------------------------------------------------------

                            // ==================================================
                            // - addd a Skill failed
                            // ==================================================

                            var status = response.status;
                            var message = response.statusText;
                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                        }
                );


                if (isProfilePage) {

                    var index = 0;
                    var skills = profileSrvc.getSkills();
                    $scope.skills = [];
                    for (index = 0; index < skills.length; index++) {
                        $scope.skills.push(
                                {
                                    'id': skills[index].id,
                                    'reference': skills[index].reference,
                                    'designation': skills[index].designation,
                                    'duration': skillsSrvc.formatDuration(skills[index].duration)
                                }
                        );
                    }

                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : $scope.skills = " + JSON.stringify($scope.skills));
                    // --------------------------------------------------------

                }
                else {
                    var dataPromise = employeesSrvc.getEmployee();
                    dataPromise.then(
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug("profileSkillsCtrl : response.skills = " + JSON.stringify(response.skills));
                                // --------------------------------------------------------

                                var index = 0;
                                var skills = response.skills;
                                $scope.skills = [];
                                for (index = 0; index < skills.length; index++) {
                                    $scope.skills.push(
                                            {
                                                'id': skills[index].id,
                                                'reference': skills[index].reference,
                                                'designation': skills[index].designation,
                                                'duration': skillsSrvc.formatDuration(skills[index].duration)
                                            }
                                    );
                                }

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : $scope.skills = " + JSON.stringify($scope.skills));
                                // --------------------------------------------------------

                            },
                            function (response) {
                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                // --------------------------------------------------------

                                // ==================================================
                                // - failed to get the employee's data
                                // ==================================================

                                var status = response.status;
                                var message = response.statusText;
                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                            }
                    );
                }

                // ==================================================
                // - add a Skill to the employee's list of Skills
                // ==================================================

                $scope.addSkill = function () {

                    var skillId = $scope.newSkillDesignation.id;
                    var employeeId = employeesSrvc.getEmployeeId();

                    var skillsPromise = skillsSrvc.addSkill(employeeId, skillId);
                    skillsPromise.then(
                            function (response) {

                                $scope.skills.push(
                                        {
                                            'id': $scope.newSkillDesignation.id,
                                            'reference': $scope.newSkillDesignation.reference,
                                            'designation': $scope.newSkillDesignation.designation,
                                            'duration': skillsSrvc.formatDuration2Mins($scope.newSkillDesignation.duration)
                                        }
                                );

                                var message = 'Skill successfully added !';
                                modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);

                            },
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                // --------------------------------------------------------

                                // ==================================================
                                // - adding a Skill failed
                                // ==================================================

                                var status = response.status;
                                var message = response.statusText;
                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                            }
                    );
                };

                // ==================================================
                // remove a Skill from the Employee's list of Skills
                // ==================================================

                $scope.removeSkill = function (id) {

                    var skillId = id;
                    var employeeId = employeesSrvc.getEmployeeId();

                    var skillsPromise = skillsSrvc.deleteSkill(employeeId, skillId);
                    skillsPromise.then(
                            function (response) {

                                $scope.skills = $scope.skills.filter(function (skill) {
                                    return skill.id !== id;
                                });

                                var message = 'Skill successfully removed !';
                                modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);

                            },
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                // --------------------------------------------------------

                                // ==================================================
                                // - removing a Skill failed
                                // ==================================================

                                var status = response.status;
                                var message = response.statusText;
                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                            }
                    );

                };

            }]);