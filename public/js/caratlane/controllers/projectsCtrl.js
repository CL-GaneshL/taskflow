'use strict';

app.controller(
        "projectsCtrl",
        [
            '$log',
            '$scope',
            '$uibModal',
            'projectsSrvc',
            'modalSrvc',
            function ($log, $scope, $uibModal, projectsSrvc, modalSrvc) {

                var CONTROLLER_NAME = 'projectsCtrl';
                // ==================================================
                // - initialization
                // ==================================================
                $scope.toCreateProject = {
                    'template': null,
                    'nb_products': null,
                    'priority': null,
                    'start_date': null
                };
                $scope.today = function () {
                    $scope.toCreateProject.start_date = new Date();
                };
                $scope.projects = null;
                $scope.templates = null;
                var projectPromise = projectsSrvc.getAllProjects();
                projectPromise.then(
                        function (response) {

                            $scope.templates = response.templates;
                            // display of the end date if null
                            $scope.projects = response.projects.filter(function (project) {
                                if (project.end_date === null) {
                                    project.end_date = "-";
                                }
                                return true;
                            });
                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : $scope.projects = " + JSON.stringify($scope.projects));
                            // $log.debug(CONTROLLER_NAME + " : $scope.templates = " + JSON.stringify($scope.templates));
                            // --------------------------------------------------------
                        },
                        function (response) {

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
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
                // - create a new project in the database 
                // ==================================================
                $scope.createProject = function () {

                    var valid = true;
                    var message = null;

                    // --------------------------------------------------------
                    // check the template
                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : template = " + $scope.toCreateProject.template);
                    // --------------------------------------------------------
                    if ($scope.toCreateProject.template === undefined || $scope.toCreateProject.template === null) {
                        valid = false;
                        message = "You must select a Project Template.";
                    }

                    // --------------------------------------------------------
                    // check the start date
                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : start_date = " + $scope.toCreateProject.start_date);
                    // --------------------------------------------------------
                    if ($scope.toCreateProject.start_date === undefined || $scope.toCreateProject.start_date === null) {
                        valid = false;
                        message = "Start Date must be defined.";
                    }

                    // --------------------------------------------------------
                    // check the nb of products
                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : nb_products = " + $scope.toCreateProject.nb_products);
                    // --------------------------------------------------------
                    if ($scope.toCreateProject.nb_products === undefined || $scope.toCreateProject.nb_products === null) {
                        valid = false;
                        message = "Nb Products must be defined.";
                    }

                    if (parseInt($scope.toCreateProject.nb_products, 10) === 'NaN') {
                        valid = false;
                        message = "Nb Products must be an integer.";
                    }

                    // --------------------------------------------------------
                    // check the priority
                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : priority = " + $scope.toCreateProject.priority);
                    // --------------------------------------------------------
                    if ($scope.toCreateProject.priority === undefined || $scope.toCreateProject.priority === null) {
                        valid = false;
                        message = "You must select a Priority.";
                    }

                    if (valid === false) {
                        modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                    } else {

                        // data are valid, display the modal

                        var newProjectReference = $scope.toCreateProject.template.reference;
                        var newProjectDesignation = $scope.toCreateProject.template.designation;
                        var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
                            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
                        var day = (new Date($scope.toCreateProject.start_date)).getDate();
                        var month = (new Date($scope.toCreateProject.start_date)).getMonth();
                        var year = (new Date($scope.toCreateProject.start_date)).getFullYear();
                        var monthStr = monthNames[month];
                        newProjectReference += '_' + monthStr + '_' + year;

                        if (day < 10) {
                            day = '0' + day;
                        }

                        month = month + 1;
                        if (month < 10) {
                            month = '0' + month;
                        }

                        var startDate = year + '-' + month + '-' + day + ' 00:00:00';

                        // string to be converted to integer
                        var nb_products = parseInt($scope.toCreateProject.nb_products, 10);

                        var newProject = {
                            'reference': newProjectReference,
                            'template_id': $scope.toCreateProject.template.id,
                            'nb_products': nb_products,
                            'priority': $scope.toCreateProject.priority,
                            'start_date': startDate,
                            'project_designation': newProjectDesignation
                        };
                        validCreateProjectModal(newProject, function (newProject)
                        {
                            var projectPromise = projectsSrvc.createProject(newProject);
                            projectPromise.then(
                                    function (response) {

                                        var createdProject = response.project;
                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : createdProject = " + JSON.stringify(createdProject));
                                        // --------------------------------------------------------

                                        createdProject.end_date = "-";
                                        $scope.projects.push(createdProject);

                                        // re-sort the list priority DESC, start_date ASC
                                        $scope.projects = $scope.projects.sort(function (p1, p2) {

                                            var ret = false;
                                            if (p1.priority < p2.priority) {
                                                ret = true;
                                            } else if (p1.priority === p2.priority) {

                                                if (new Date(p1.start_date).getTime()
                                                        <= new Date(p2.start_date).getTime()) {
                                                    return true;
                                                }
                                            }

                                            return ret;
                                        });

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : $scope.projects = " + JSON.stringify($scope.projects));
                                        // --------------------------------------------------------

                                        var message = 'Project : ' + createdProject.reference + ', was successfuly created!';
                                        modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);
                                    },
                                    function (response) {

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
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
                // - update a product in the database 
                // ==================================================
                $scope.updateProject = function (project_id) {

                    var projectToUpdate = $scope.findProject(project_id);
                    validUpdateProjectModal(projectToUpdate, function (toUpdateProject)
                    {

                        var valid = true;
                        var message = null;
                        // --------------------------------------------------------
                        // check the reference
                        // --------------------------------------------------------
                        // $log.debug(CONTROLLER_NAME + " : reference = " + toUpdateProject.reference);
                        // --------------------------------------------------------
                        if (toUpdateProject.reference === undefined || toUpdateProject.reference === '' || toUpdateProject.reference === null) {
                            valid = false;
                            message = "You must select a Project Reference.";
                        }

                        // --------------------------------------------------------
                        // check the start date
                        // --------------------------------------------------------
                        // $log.debug(CONTROLLER_NAME + " : start_date = " + toUpdateProject.start_date);
                        // $log.debug(CONTROLLER_NAME + " : old_start_date = " + toUpdateProject.old_start_date);
                        // --------------------------------------------------------
                        if (toUpdateProject.start_date === undefined || toUpdateProject.start_date === null) {
                            valid = false;
                            message = "Start Date must be defined.";
                        }

                        // --------------------------------------------------------
                        // check the nb of products
                        // --------------------------------------------------------
                        // $log.debug(CONTROLLER_NAME + " : nb_products = " + toUpdateProject.nb_products);
                        // $log.debug(CONTROLLER_NAME + " : old_nb_products = " + toUpdateProject.old_nb_products);
                        // --------------------------------------------------------                        
                        if (toUpdateProject.nb_products === undefined || toUpdateProject.nb_products === null) {
                            valid = false;
                            message = "Nb Products must be defined.";
                        }

//                        if (toUpdateProject.nb_products !== parseInt(toUpdateProject.nb_products, 10)) {
                        if (parseInt(toUpdateProject.nb_products, 10) === 'NaN') {
                            valid = false;
                            message = "Nb Products must be an integer.";
                        }

                        // --------------------------------------------------------
                        // check the priority
                        // --------------------------------------------------------
                        // $log.debug(CONTROLLER_NAME + " : priority = " + toUpdateProject.priority);
                        // $log.debug(CONTROLLER_NAME + " : old_priority = " + toUpdateProject.old_priority);                     
                        // --------------------------------------------------------
                        if (toUpdateProject.priority === null) {
                            valid = false;
                            message = "You must select a Priority.";
                        }

                        if (valid === false) {
                            modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                        } else {

                            // the project's end date has to be re-estimated 
                            // if crutial data have been changed.
                            var endDataHasChanged = false;
                            if (toUpdateProject.priority !== toUpdateProject.old_priority) {
                                endDataHasChanged = true;
                            }

                            if (toUpdateProject.nb_products !== toUpdateProject.nb_products) {
                                endDataHasChanged = true;
                            }

                            if (new Date(toUpdateProject.start_date).getTime()
                                    !== new Date(toUpdateProject.start_date).getTime()) {
                                endDataHasChanged = true;
                            }

                            if (endDataHasChanged === true) {
                                toUpdateProject.end_date = null;
                            }

                            var day = (new Date(toUpdateProject.start_date)).getDate();
                            var month = (new Date(toUpdateProject.start_date)).getMonth() + 1;
                            var year = (new Date(toUpdateProject.start_date)).getFullYear();

                            if (day < 10) {
                                day = '0' + day;
                            }

                            if (month < 10) {
                                month = '0' + month;
                            }

                            var startDate = year + '-' + month + '-' + day;
                            toUpdateProject.start_date = startDate;
                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : toUpdateProject = " + JSON.stringify(toUpdateProject));
                            // --------------------------------------------------------

                            var projectPromise = projectsSrvc.updateProject(toUpdateProject);
                            projectPromise.then(
                                    function (response) {

                                        var updatedProject = response.project;
                                        // --------------------------------------------------------
                                        // log.debug(CONTROLLER_NAME + " : upatedProject = " + JSON.stringify(updatedProject));
                                        // --------------------------------------------------------

                                        $scope.projects = $scope.removeRow(updatedProject.id);
                                        if (updatedProject.end_date === null) {
                                            updatedProject.end_date = "-";
                                        }

                                        $scope.projects.push(updatedProject);
                                        // re-sort the list priority DESC, start_date ASC
                                        $scope.projects = $scope.projects.sort(function (p1, p2) {

                                            var ret = false;
                                            if (p1.priority < p2.priority) {
                                                ret = true;
                                            } else if (p1.priority === p2.priority) {

                                                if (new Date(p1.start_date).getTime()
                                                        <= new Date(p2.start_date).getTime()) {
                                                    return true;
                                                }
                                            }

                                            return ret;
                                        });
                                        var message = 'Project : ' + updatedProject.reference + ', was successfuly updated!';
                                        modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);
                                    },
                                    function (response) {

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                        // --------------------------------------------------------

                                        // ==================================================
                                        // - create team failed
                                        // ==================================================

                                        var status = response.status;
                                        var message = response.statusText;
                                        modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                    }
                            );
                        }
                    });
                };
                // ==================================================
                // - delete a team main function 
                // ==================================================
                $scope.closeProject = function (project_id) {

                    var projectToClose = $scope.findProject(project_id);
                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : projectToClose = " + JSON.stringify(projectToClose));
                    // --------------------------------------------------------

                    validCloseProjectModal(projectToClose, function () {

                        var projectPromise = projectsSrvc.closeProject(project_id);
                        projectPromise.then(
                                function (response) {

                                    var closedProject = response.project;
                                    $scope.projects = $scope.removeRow(closedProject.id);
                                    var message = 'Project ' + closedProject.reference + ' was successfuly closed!';
                                    modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);
                                },
                                function (response) {

                                    // ==================================================
                                    // - delete Project failed
                                    // ==================================================

                                    var status = response.status;
                                    var message = response.statusText;
                                    modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                }
                        );
                    });
                };
                // ==================================================
                // - valid the deletion of a Project
                // ==================================================
                function validCloseProjectModal(projectToClose, closeProjectFct) {

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_valid_close_project',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.toCloseProjectReference = projectToClose.reference;
                            $scope.toCloseProjectDesignation = projectToClose.designation;
                            $scope.toCloseProjectPriority = projectToClose.priority;
                            $scope.toCloseNbProducts = projectToClose.nb_products;
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.closeProject = function () {
                                closeProjectFct();
                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                }

                // ==================================================
                // - valid the update of a Project
                // ==================================================
                function validUpdateProjectModal(projectToUpdate, updateProjectFct) {

                    var index = 0;
                    var priorityChoices = [];
                    // priority ranges from 1 to 10
                    for (index = 1; index <= 10; index++) {
                        priorityChoices.push(index);
                    }

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_valid_update_project',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.toUpdateProjectId = projectToUpdate.id;
                            $scope.toUpdateProjectReference = projectToUpdate.reference;
                            $scope.toUpdateTemplateId = projectToUpdate.template_id;
                            $scope.toUpdateStartDate = projectToUpdate.start_date;
                            $scope.toUpdateOldStartDate = projectToUpdate.start_date;
                            $scope.toUpdateEndDate = projectToUpdate.end_date;
                            $scope.toUpdateProjectPriority = projectToUpdate.priority;
                            $scope.toUpdateProjectOldPriority = projectToUpdate.priority;
                            $scope.toUpdateNbProducts = projectToUpdate.nb_products;
                            $scope.toUpdateOldNbProducts = projectToUpdate.nb_products;
                            $scope.priorityChoices = priorityChoices;
                            
                                // ==================================================
                    // - date picker 
                    // ==================================================
//                    $scope.today();
//                    $scope.clear = function () {
//                        $scope.newProject.start_date = null;
//                    };
//                    // do we allow week selection ?
//                    // or only weekdays and saturday morning ?
//                    // Disable weekend selection
////                $scope.disabled = function (date, mode) {
////                    return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
////                };
//
//                    $scope.toggleMin = function () {
//                        $scope.minDate = $scope.minDate ? null : new Date();
//                    };
//                    $scope.toggleMin();
//                    $scope.maxDate = new Date(2020, 5, 22);
//                    $scope.open = function ($event) {
//                        $event.preventDefault();
//                        $event.stopPropagation();
//                        $scope.opened = !$scope.opened;
//                    };
//                    $scope.endOpen = function ($event) {
//                        $event.preventDefault();
//                        $event.stopPropagation();
//                        $scope.startOpened = false;
//                        $scope.endOpened = !$scope.endOpened;
//                    };
//                    $scope.startOpen = function ($event) {
//                        $event.preventDefault();
//                        $event.stopPropagation();
//                        $scope.endOpened = false;
//                        $scope.startOpened = !$scope.startOpened;
//                    };
//                    $scope.dateOptions = {
//                        formatYear: 'yy',
//                        startingDay: 1
//                    };
//                    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
//                    $scope.format = $scope.formats[0];
//                    $scope.hstep = 1;
//                    $scope.mstep = 15;
                    // ==================================================
                    
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.updateProject = function () {

                                var toUpdateProject = {
                                    'id': $scope.toUpdateProjectId,
                                    'reference': $scope.toUpdateProjectReference,
                                    'template_id': $scope.toUpdateTemplateId,
                                    'start_date': $scope.toUpdateStartDate,
                                    'old_start_date': $scope.toUpdateOldStartDate,
                                    'end_date': $scope.toUpdateEndDate,
                                    'nb_products': $scope.toUpdateNbProducts,
                                    'old_nb_products': $scope.toUpdateOldNbProducts,
                                    'priority': $scope.toUpdateProjectPriority,
                                    'old_priority': $scope.toUpdateProjectOldPriority
                                };
                                updateProjectFct(toUpdateProject);
                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                }

                // ==================================================
                // - valid the creation of a new Project
                // ==================================================
                function validCreateProjectModal(newProject, newProjectFct) {

                    // remove last ' 00:00:00' for display
                    var longDate = newProject.start_date;
                    var shortDate = longDate.replace(' 00:00:00', '');
                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_valid_create_project',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.projectReference = newProject.reference;
                            $scope.projectDesignation = newProject.project_designation;
                            $scope.nbProducts = newProject.nb_products;
                            $scope.priority = newProject.priority;
                            $scope.startDate = shortDate;
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.createProject = function () {
                                newProjectFct(newProject);
                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                }

                // ==================================================
                // - find a row in the Project table
                // ==================================================
                $scope.findProject = function (id) {

                    var project = null;
                    var projects = $scope.projects.filter(function (project) {
                        return project.id === id;
                    });
                    if (projects.length >= 1) {
                        project = projects[0];
                    }

                    return project;
                };
                // ==================================================
                // - delete a row from the Project table
                // ==================================================
                $scope.removeRow = function (id) {

                    var projects = $scope.projects.filter(function (project) {
                        return project.id !== id;
                    });
                    return projects;
                };
                // ==================================================
                // - date picker 
                // ==================================================
                $scope.today();
                $scope.clear = function () {
                    $scope.newProject.start_date = null;
                };
                // do we allow week selection ?
                // or only weekdays and saturday morning ?
                // Disable weekend selection
//                $scope.disabled = function (date, mode) {
//                    return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
//                };

                $scope.toggleMin = function () {
                    $scope.minDate = $scope.minDate ? null : new Date();
                };
                $scope.toggleMin();
                $scope.maxDate = new Date(2020, 5, 22);
                $scope.open = function ($event) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.opened = !$scope.opened;
                };
                $scope.endOpen = function ($event) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.startOpened = false;
                    $scope.endOpened = !$scope.endOpened;
                };
                $scope.startOpen = function ($event) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.endOpened = false;
                    $scope.startOpened = !$scope.startOpened;
                };
                $scope.dateOptions = {
                    formatYear: 'yy',
                    startingDay: 1
                };
                $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
                $scope.format = $scope.formats[0];
                $scope.hstep = 1;
                $scope.mstep = 15;
                // ==================================================
            }]);