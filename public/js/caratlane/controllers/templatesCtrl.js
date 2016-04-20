'use strict';

app.controller(
        "templatesCtrl",
        [
            '$log',
            '$scope',
            '$uibModal',
            'templatesSrvc',
            'modalSrvc',
            function ($log, $scope, $uibModal, templatesSrvc, modalSrvc) {

                var CONTROLLER_NAME = 'templatesCtrl';

                // ==================================================
                // - initialization
                // ==================================================
                $scope.newTemplateReference = '';
                $scope.newTemplateDesignation = '';
                $scope.templates = null;
                $scope.skills = null;

                var templatePromise = templatesSrvc.getAllTemplates();
                templatePromise.then(
                        function (response) {

                            // list of all templates
                            $scope.templates = response.templates;
                            $scope.skills = response.skills;

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : $scope.templates = " + JSON.stringify($scope.templates));
                            // --------------------------------------------------------
                        },
                        function (response) {

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                            // --------------------------------------------------------

                            // ==================================================
                            // - retrieving all templates failed
                            // ==================================================

                            var status = response.status;
                            var message = response.statusText;
                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                        }
                );

                // ==================================================
                // - create a new product in the database 
                // ==================================================
                $scope.createTemplate = function () {

                    var valid = true;
                    var message = null;

                    // check the reference
                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : reference = " + $scope.newTemplateReference);
                    // --------------------------------------------------------
                    if ($scope.newTemplateReference === "") {
                        valid = false;
                        message = "Invalid Template reference.";
                    }

                    // check the designation
                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : designation = " + $scope.newTemplateDesignation);
                    // --------------------------------------------------------
                    if ($scope.newTemplateDesignation === "") {
                        valid = false;
                        message = "Invalid Template designation.";
                    }

                    if (valid === false) {
                        modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                    }
                    else {

                        // data are valid, display the modal

                        validCreateTemplateModal(
                                $scope.newTemplateReference,
                                $scope.newTemplateDesignation,
                                function (reference, designation) {

                                    var templatePromise = templatesSrvc.createTemplate(reference, designation);
                                    templatePromise.then(
                                            function (response) {

                                                var newTemplate = response.template;
                                                $scope.templates.push(newTemplate);

                                                var message = 'Template : '
                                                        + newTemplate.reference + ', '
                                                        + newTemplate.designation
                                                        + ', was successfuly created!';

                                                modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);
                                            },
                                            function (response) {

                                                // --------------------------------------------------------
                                                // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                                // --------------------------------------------------------

                                                // ==================================================
                                                // - create Template failed
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
                // - edit a Template function
                // ==================================================
                $scope.editTemplate = function (template_id) {

                    var templatePromise = templatesSrvc.getTemplateSkills(template_id);
                    templatePromise.then(
                            function (response) {

                                var skills = response.skills;
                                var templateToEdit = $scope.findTemplate(template_id);

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : skills = " + JSON.stringify(skills));
                                // $log.debug(CONTROLLER_NAME + " : templateToEdit = " + JSON.stringify(templateToEdit));
                                // --------------------------------------------------------

                                editTemplateModal(templateToEdit, skills, $scope.skills);

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
                // - close a template main function 
                // ==================================================
                $scope.closeTemplate = function (template_id) {

                    var templateToClose = $scope.findTemplate(template_id);
                    validCloseTemplateModal(
                            templateToClose,
                            function (template_id) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : template_id = " + template_id);
                                // --------------------------------------------------------

                                var templatePromise = templatesSrvc.closeTemplate(template_id);
                                templatePromise.then(
                                        function (response) {

                                            $scope.templates = $scope.removeRow(template_id);

                                            var message = 'Template : '
                                                    + templateToClose.reference + ', '
                                                    + templateToClose.designation
                                                    + ', was successfuly closed!';

                                            modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);
                                        },
                                        function (response) {

                                            // --------------------------------------------------------
                                            // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                            // --------------------------------------------------------

                                            // ==================================================
                                            // - close Template failed
                                            // ==================================================

                                            var status = response.status;
                                            var message = response.statusText;
                                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                        }
                                );
                            });
                };

                // ==================================================
                // - edit a Template
                // ==================================================
                function editTemplateModal(templateToEdit, skills, allSkills) {

                    var initialSkills = [];

                    // preserve the original list
                    angular.forEach(skills, function (skill, key) {
                        initialSkills.push(skill);
                    });

                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : initialSkills = " + JSON.stringify(initialSkills));
                    // --------------------------------------------------------

                    // ==================================================
                    // - fond out if a Skill is in the list stack
                    // ==================================================
                    var contains = function (stack, skill) {

                        var found = false;

                        angular.forEach(stack, function (sk, key) {
                            if (skill.id === sk.id) {
                                found = true;
                            }
                        });

                        return found;
                    };


                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_edit_template',
                        placement: 'center',
                        modal: true,
                        controller: function ($scope, $uibModalInstance) {
                            $scope.newEmployee = null;
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.newSkill = null;
                            $scope.skills = skills;
                            $scope.selectionSkills = allSkills;
                            $scope.toEditTemplateReference = templateToEdit.reference;
                            $scope.toEditTemplateDesignation = templateToEdit.designation;

                            // ==================================================
                            // - close modal
                            // ==================================================
                            $scope.save = function () {

                                if ($scope.skills.length === 0) {

                                    // remove all skills
                                    angular.forEach(initialSkills, function (skill, key) {

                                        var templatePromise = templatesSrvc.removeSkill(templateToEdit.id, skill.id);
                                        templatePromise.then(
                                                function (response) {

                                                    // --------------------------------------------------------
                                                    // $log.debug(CONTROLLER_NAME + " : $scope.skills = " + JSON.stringify($scope.skills));
                                                    // --------------------------------------------------------

                                                    //var message = 'Skill ' + skill.designation + ' successfully removed.';
                                                    //modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);

                                                },
                                                function (response) {

                                                    // ==================================================
                                                    // - remove skill failed
                                                    // ==================================================

//                                                    var status = response.status;
//                                                    var message = response.statusText;
                                                    //modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                                }
                                        );
                                    });
                                }
                                else {

                                    angular.forEach($scope.skills, function (skill, key) {

                                        if (!contains(initialSkills, skill)) {

                                            var templatePromise = templatesSrvc.addSkill(templateToEdit.id, skill.id);
                                            templatePromise.then(
                                                    function (response) {

                                                        // --------------------------------------------------------
                                                        // $log.debug(CONTROLLER_NAME + " : add skill = " + JSON.stringify(skill));
                                                        // --------------------------------------------------------

                                                        //var message = 'Skill ' + skill.designation + ' successfully added.';
                                                        //modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);

                                                    },
                                                    function (response) {

                                                        // ==================================================
                                                        // - add skill failed
                                                        // ==================================================

//                                                        var status = response.status;
//                                                        var message = response.statusText;
                                                        //modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                                    }
                                            );
                                        }
                                    });

                                    angular.forEach(initialSkills, function (initSkill, key) {

                                        if (!contains($scope.skills, initSkill)) {

                                            var templatePromise = templatesSrvc.removeSkill(templateToEdit.id, initSkill.id);
                                            templatePromise.then(
                                                    function (response) {

                                                        // --------------------------------------------------------
                                                        // $log.debug(CONTROLLER_NAME + " : remove skill = " + JSON.stringify(initSkill));
                                                        // --------------------------------------------------------

                                                        //message = 'Skill ' + initSkill.designation + ' successfully removed.';
                                                        //modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);

                                                    },
                                                    function (response) {

                                                        // ==================================================
                                                        // - remove skill failed
                                                        // ==================================================

//                                                        var status = response.status;
//                                                        var message = response.statusText;
                                                        // modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                                    }
                                            );
                                        }
                                    });
                                }

                                $uibModalInstance.dismiss('cancel');
                            };

                            // ==================================================
                            // - save template modal
                            // ==================================================
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };

                            // ==================================================
                            // - add a Skill to the list of Template skills
                            // ==================================================
                            $scope.addSkill = function () {

                                if ($scope.newSkill !== null) {

                                    if (!contains($scope.skills, $scope.newSkill)) {
                                        $scope.skills.push($scope.newSkill);
                                    }
                                }
                            };

                            // ==================================================
                            // - remove a Skill from the list of Template skills
                            // ==================================================
                            $scope.removeSkill = function (skill_id) {

                                $scope.skills = $scope.skills.filter(function (skill) {
                                    return skill.id !== skill_id;
                                });
                            };
                        }
                    });
                }

                // ==================================================
                // - valid closing a Template
                // ==================================================
                function validCloseTemplateModal(
                        templateToClose,
                        closeTemplateFct
                        ) {

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_valid_close_template',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.templateReference = templateToClose.reference;
                            $scope.templateDesignation = templateToClose.designation;
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.closeTemplate = function () {
                                closeTemplateFct(templateToClose.id);
                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                }

                // ==================================================
                // - valid the creation of a new Template
                // ==================================================
                function validCreateTemplateModal(
                        reference,
                        designation,
                        newTemplateFct
                        ) {

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_valid_create_template',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.templateReference = reference;
                            $scope.templateDesignation = designation;
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.createTemplate = function () {
                                newTemplateFct(reference, designation);
                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                }

                // ==================================================
                // - find a row in the Template table
                // ==================================================
                $scope.findTemplate = function (id) {

                    var template = null;

                    var templates = $scope.templates.filter(function (template) {
                        return template.id === id;
                    });

                    if (templates.length === 1) {
                        template = templates[0];
                    }

                    return template;
                };

                // ==================================================
                // - delete a row from the Template table
                // ==================================================
                $scope.removeRow = function (id) {

                    var templates = $scope.templates.filter(function (template) {
                        return template.id !== id;
                    });

                    return templates;
                };

                // ==================================================
            }]);