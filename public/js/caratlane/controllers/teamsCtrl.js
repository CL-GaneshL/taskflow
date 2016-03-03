'use strict';

/**
 * 
 * 
 */

app.controller(
        "teamsCtrl",
        [
            '$log',
            '$scope',
            '$uibModal',
            'teamsSrvc',
            'modalSrvc',
            function ($log, $scope, $uibModal, teamsSrvc, modalSrvc) {

                var CONTROLLER_NAME = 'teamsCtrl';

                // ==================================================
                // - initialization
                // ==================================================
                $scope.members = null;

                $scope.newTeamName = '';
                $scope.newTeamLeader = '';

                $scope.teams = null;
                $scope.teamLeaders = null;
                $scope.allEmployees = null;

                var teamPromise = teamsSrvc.getAllTeams();
                teamPromise.then(
                        function (response) {

                            $scope.teams = response.teams;
                            $scope.teamLeaders = response.teamLeaders;
                            $scope.allEmployees = response.allEmployees;
                        },
                        function (response) {

                            // --------------------------------------------------------
                            $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                            // --------------------------------------------------------

                            // ==================================================
                            // - retrieving all teams failed
                            // ==================================================

                            var status = response.status;
                            var message = response.statusText;
                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                        }
                );

                // ==================================================
                // - create a new team in the database 
                // ==================================================
                $scope.createTeam = function () {

                    var newTeam = {
                        teamName: $scope.newTeamName,
                        teamLeaderId: $scope.newTeamLeader.id,
                        teamLeaderFullName: $scope.newTeamLeader.fullName
                    };

                    if (isFormValid(newTeam.teamName, newTeam.teamLeaderFullName)) {

                        validCreateTeamModal(newTeam, function () {

                            var teamPromise = teamsSrvc.createTeam(newTeam.teamName, newTeam.teamLeaderId);
                            teamPromise.then(
                                    function (response) {

                                        var newTeam = response.team;
                                        $scope.teams.push(newTeam);

                                        var message = 'Team ' + newTeam.teamName + ' was successfuly created!';
                                        modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);
                                    },
                                    function (response) {

                                        // --------------------------------------------------------
                                        $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                        // --------------------------------------------------------

                                        // ==================================================
                                        // - create team failed
                                        // ==================================================

                                        var status = response.status;
                                        var message = response.statusText;
                                        modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                    }
                            );
                        });
                    }
                };


                // ==================================================
                // - delete a team main function 
                // ==================================================
                $scope.deleteTeam = function (teamId) {

                    var teamToDelete = $scope.findTeam(teamId);
                    validDeleteTeamModal(teamToDelete, function () {

                        // --------------------------------------------------------
                        $log.debug(CONTROLLER_NAME + " : deleteTeam : teamToDelete = " + JSON.stringify(teamToDelete));
                        // --------------------------------------------------------

                        var teamPromise = teamsSrvc.deleteTeam(teamId);
                        teamPromise.then(
                                function (response) {

                                    // ==================================================
                                    // - successful delete team
                                    // ==================================================

                                    $scope.teams = $scope.removeRow(teamToDelete.id);

                                    var message = 'Team ' + teamToDelete.teamName + ' was successfuly deleted!';
                                    modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);

                                },
                                function (response) {

                                    // ==================================================
                                    // - delete team failed
                                    // ==================================================

                                    var status = response.status;
                                    var message = response.statusText;
                                    modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                }
                        );
                    });
                };


                // ==================================================
                // - edit a team main function
                // ==================================================
                $scope.editTeam = function (teamId) {

                    var teamToEdit = $scope.findTeam(teamId);
                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : editTeam : teamToEdit = " + JSON.stringify(teamToEdit));
                    // --------------------------------------------------------

                    var teamPromise = teamsSrvc.getTeamMembers(teamId);
                    teamPromise.then(
                            function (response) {

                                // get the team members
                                var members = response.members;
                                editTeamModal(teamToEdit, members, $scope.allEmployees);

                            },
                            function (response) {

                                // ==================================================
                                // - edit team failed
                                // ==================================================

                                var status = response.status;
                                var message = response.statusText;
                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                            }
                    );
                };


                // ==================================================
                // - valid the edition of a team
                // ==================================================
                function editTeamModal(teamToEdit, teamMembers, allEMployees) {

                    var modalInstance = $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_edit_team',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.newEmployee = null;
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.members = teamMembers;
                            $scope.allEMployees = allEMployees;
                            $scope.toUpdateTeamName = teamToEdit.teamName;
                            $scope.toUpdateTeamLeaderFullName = teamToEdit.teamLeaderFullName;

                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };

                            $scope.addEmployee = function () {

                                var teamId = teamToEdit.id;
                                var employeeId = $scope.newEmployee.id;

                                var teamPromise = teamsSrvc.addTeamMember(employeeId, teamId);
                                teamPromise.then(
                                        function (response) {

                                            $scope.members.push(
                                                    {
                                                        'id': $scope.newEmployee.id,
                                                        'employeeId': $scope.newEmployee.employeeId,
                                                        'fullName': $scope.newEmployee.fullName
                                                    });

                                        },
                                        function (response) {

                                            // ==================================================
                                            // - add team member failed
                                            // ==================================================

                                            var status = response.status;
                                            var message = response.statusText;
                                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                        }
                                );
                            };

                            $scope.removeEmployee = function (employeeId) {


                                var teamId = teamToEdit.id;

                                var teamPromise = teamsSrvc.deleteTeamMember(employeeId, teamId);
                                teamPromise.then(
                                        function (response) {

                                            $scope.members = $scope.members.filter(function (member) {
                                                return member.id !== employeeId;
                                            });

                                        },
                                        function (response) {

                                            // ==================================================
                                            // - remove team member failed
                                            // ==================================================

                                            var status = response.status;
                                            var message = response.statusText;
                                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                        }
                                );
                            };
                        }
                    });
                }

                // ==================================================
                // - valid the deletion of a team
                // ==================================================
                function validDeleteTeamModal(teamToDelete, deleteTeamFct) {

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_valid_delete_team',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.toDeleteTeamName = teamToDelete.teamName;
                            $scope.toDeleteTeamLeaderFullName = teamToDelete.teamLeaderFullName;
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.delete = function () {
                                deleteTeamFct();
                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                }

                // ==================================================
                // - valid the creation ofa new Team
                // ==================================================
                function validCreateTeamModal(newTeam, newTeamFct) {

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_valid_create_team',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.newTeamName = newTeam.teamName;
                            $scope.newTeamLeaderFullName = newTeam.teamLeaderFullName;
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.create = function () {
                                newTeamFct();
                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                }

                // ==================================================
                // - check the field's validity
                // ==================================================
                function isFormValid(teamName, teamLeader) {

                    var valid = true;
                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : isFormValid : teamName = " + teamName);
                    $log.debug(CONTROLLER_NAME + " : isFormValid : teamLeader = " + teamLeader);
                    // --------------------------------------------------------

                    if (teamName === '') {
                        modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, 'Team Name is required.');
                        valid = false;
                    }

                    if (valid === true) {
                        if (teamLeader === '') {
                            modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, 'Team Leader is required.');
                            valid = false;
                        }
                    }

                    return valid;
                }

                // ==================================================
                // - find a row in the team table
                // ==================================================
                $scope.findTeam = function (id) {

                    var team = null;

                    var teams = $scope.teams.filter(function (team) {
                        return team.id === id;
                    });

                    if (teams.length === 1) {
                        team = teams[0];
                    }

                    return team;
                };

                // ==================================================
                // - delete a row from the skills table
                // ==================================================
                $scope.removeRow = function (id) {

                    var teams = $scope.teams.filter(function (team) {
                        return team.id !== id;
                    });

                    return teams;
                };
                // ==================================================
            }]);