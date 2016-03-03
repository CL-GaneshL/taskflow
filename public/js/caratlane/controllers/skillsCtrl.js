'use strict';
/**
 * controllers for dynamic table
 * Remove/delete a table row dynamically 
 */

app.controller(
        "skillsCtrl", ['$log', '$scope', '$http', '$uibModal','tasksSrvc', 'modalSrvc',
            function ($log, $scope, $http, $uibModal,tasksSrvc, modalSrvc) {

                var CONTROLLER_NAME = 'skillsCtrl';

                // ==================================================
                // - initialization
                // ==================================================
                $scope.skills = [];
                $scope.newSkillReference = '';
                $scope.newSkillResignation = '';
                $scope.newSkillDuration = '';

                $scope.duration_choices = tasksSrvc.getDurationChoices();

                // --------------------------------------------------------
                // - API : Retrieve all skills from DB
                // --------------------------------------------------------
                // - Method = GET|HEAD
                // - URI = taskflow/apis/v1/skills
                // - Controller = skills.index
                // --------------------------------------------------------
                $http.get('/taskflow/apis/v1/skills')
                        // --------------------------------------------------------
                        // - success
                        // --------------------------------------------------------
                        .success(function (response) {

                            var index = 0;
                            var skills = response.data;

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : skills = " + JSON.stringify(skills));
                            // --------------------------------------------------------

                            for (index = 0; index < skills.length; index++) {

                                var skill = {
                                    id: skills[index].id,
                                    reference: skills[index].reference,
                                    designation: skills[index].designation,
                                    duration: formatDuration(skills[index].duration)
                                };

                                $scope.skills.push(skill);
                            }

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : $scope.skills = " + JSON.stringify($scope.skills));
                            // --------------------------------------------------------

                        })
                        // --------------------------------------------------------
                        // - error
                        // --------------------------------------------------------
                        .error(function (data, status, headers, config) {

                            var status = '500';
                            var message = 'Internal server error.';
                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                        });
                        
                // ==================================================
                // - add a row in the skills table
                // ==================================================
                $scope.createSkill = function () {

                    var newSkill = {
                        reference: $scope.newSkillReference,
                        designation: $scope.newSkillDesignation,
                        duration: formatDuration2Mins($scope.newSkillDuration)
                    };

                    if (isFormValid(newSkill.reference, newSkill.designation, newSkill.duration)) {

                        // --------------------------------------------------------
                        // $log.debug(CONTROLLER_NAME + " : newSkill = " + JSON.stringify(newSkill));
                        // --------------------------------------------------------

                        validCreateSkillModal(newSkill, function () {

                            // --------------------------------------------------------
                            // - API : Store a new skill in DB 
                            // --------------------------------------------------------
                            // - Method = POST
                            // - URI = taskflow/apis/v1/skills
                            // - Controller = skills.store
                            // --------------------------------------------------------
                            $http.post('/taskflow/apis/v1/skills', newSkill)
                                    // --------------------------------------------------------
                                    // - success, modify the list by appending the new skill
                                    // --------------------------------------------------------
                                    .success(function (response) {

                                        var createdSkill = response.data;

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : createdSkill = " + JSON.stringify(createdSkill));
                                        // --------------------------------------------------------

                                        modalSrvc.showSuccessMessageModal('Skill ' + newSkill.reference + ' was successfuly created!');
                                        $scope.skills.push(
                                                {
                                                    'id': createdSkill.id,
                                                    'reference': createdSkill.reference,
                                                    'designation': createdSkill.designation,
                                                    'duration': formatDuration(createdSkill.duration)
                                                }
                                        );
                                    })
                                    // --------------------------------------------------------
                                    // - error
                                    // --------------------------------------------------------
                                    .error(function (data, status, headers, config) {

                                        // --------------------------------------------------------
                                        // $log.error("skillsCtrl : addSkill : error status = " + JSON.stringify(status));
                                        // $log.error("skillsCtrl : addSkill : error data = " + JSON.stringify(data));
                                        // --------------------------------------------------------

                                        var msg = 'Internal server error status = 500!';
                                        modalSrvc.showErrorMessageModal(msg);
                                    });
                        });
                    }
                };

                // ==================================================
                // - close a skill main function 
                // ==================================================
                $scope.closeSkill = function (id) {

                    var skillToClose = $scope.findSkill(id);

                    // ask for validation and delete the Skill in the database
                    validCloseSkillModal(skillToClose, function () {

                        // --------------------------------------------------------
                        // - API : Delete a Skill from the database
                        // --------------------------------------------------------
                        // - Method = DELETE
                        // - URI = taskflow/apis/v1/skills (reference)
                        // - Controller = skills.delete
                        // --------------------------------------------------------
                        $http.delete('/taskflow/apis/v1/skills/' + skillToClose.id)
                                // --------------------------------------------------------
                                // - success, modify the list by appending the new skill
                                // --------------------------------------------------------
                                .success(function (response) {

                                    modalSrvc.showSuccessMessageModal('Skill ' + skillToClose.reference + ' was successfuly closed!');
                                    $scope.skills = $scope.removeRow(skillToClose.id);
                                })
                                // --------------------------------------------------------
                                // - error
                                // --------------------------------------------------------
                                .error(function (data, status, headers, config) {

                                    var msg = 'Internal server error status = 500!';
                                    modalSrvc.showErrorMessageModal(msg);
                                });
                    });
                };

                // ==================================================
                // - confirm Close Skill message modal
                // ==================================================
                function validCloseSkillModal(skillToClose, closeSkillFct) {

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_valid_close_skill',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.toCloseSkillReference = skillToClose.reference;
                            $scope.toCloseSkillDesignation = skillToClose.designation;
                            $scope.toCloseSkillDuration = skillToClose.duration;
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.closeSkill = function () {
                                closeSkillFct();
                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                }

                // ==================================================
                // - confirm message modal
                // ==================================================
                function validCreateSkillModal(newSkill, newSkillFct) {

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_valid_create_skill',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.newSkillReference = newSkill.reference;
                            $scope.newSkillDesignation = newSkill.designation;
                            $scope.newSkillDuration = formatDuration(newSkill.duration);
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.create = function () {
                                newSkillFct();
                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                }

                // ==================================================
                // - check the fields validity
                // ==================================================
                function isFormValid(reference, designation, duration) {

                    var valid = true;
                    // --------------------------------------------------------
                    // $log.debug("skillsCtrl : isFormValid : reference = " + reference);
                    // $log.debug("skillsCtrl : isFormValid : designation = " + designation);
                    // $log.debug("skillsCtrl : isFormValid : duration = " + duration);
                    // --------------------------------------------------------

                    if (reference === '') {
                        showErrorMessageModal('Reference is required.');
                        valid = false;
                    }

                    if (valid === true) {
                        if (reference.length > 7) {
                            showErrorMessageModal('Reference must have a maximum length of 7 characters.');
                            valid = false;
                        }
                    }

                    if (valid === true) {
                        if (reference.length > 40) {
                            showErrorMessageModal('Designation must have a maximum length of 40 characters.');
                            valid = false;
                        }
                    }

                    return valid;
                }

                // ==================================================
                // - delete a row from the skills table
                // ==================================================
                $scope.removeRow = function (id) {

                    var skills = $scope.skills.filter(function (skill) {
                        return skill.id !== id;
                    });

                    return skills;
                };

                // ==================================================
                // - find a row in the skills table
                // ==================================================
                $scope.findSkill = function (id) {

                    var skill = null;

                    var skills = $scope.skills.filter(function (skill) {
                        return skill.id === id;
                    });

                    if (skills.length === 1) {
                        skill = skills[0];
                    }

                    return skill;
                };

                // ==================================================
                // - format duration from minutes to hh:mm
                // ==================================================
                var formatDuration = function (duration) {

                    var duration_hours = Math.floor(duration / 60);
                    var duration_minutes = duration % 60;

                    var formated = duration_hours + ' h';
                    if (duration_minutes !== 0) {
                        formated = formated + ' ' + duration_minutes + ' mins';
                    }

                    return formated;
                };

                // ==================================================
                // - format duration from  hh:mm to minutes
                // ==================================================
                var formatDuration2Mins = function (duration) {

                    var splits = duration.split("h");
                    var hours = Number(splits[0].trim());
                    var mins = Number(splits[1].replace(' mins', '').trim());

                    return hours * 60 + mins;
                };

                // ==================================================
            }]);