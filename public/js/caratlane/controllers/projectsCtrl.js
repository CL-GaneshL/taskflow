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

                            $scope.projects = response.projects;
                            $scope.templates = response.templates;

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
                // - create a new product in the database 
                // ==================================================
                $scope.createProject = function () {

                    var newProjectReference = $scope.toCreateProject.template.reference;
                    var newProjectDesignation = $scope.toCreateProject.template.designation;

                    var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

                    var day = (new Date($scope.toCreateProject.start_date)).getDate();
                    var month = (new Date($scope.toCreateProject.start_date)).getMonth();
                    var year = (new Date($scope.toCreateProject.start_date)).getFullYear();

                    var monthStr = monthNames[month];
                    newProjectReference += '_' + monthStr + '_' + year;

                    var startDate = year + '-' + (month + 1) + '-' + day + ' 00:00:00';

                    var newProject = {
                        'reference': newProjectReference,
                        'template_id': $scope.toCreateProject.template.id,
                        'nb_products': $scope.toCreateProject.nb_products,
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
                                    // $log.debug(CONTROLLER_NAME + " : $scope.projects = " + JSON.stringify($scope.projects));
                                    // $log.debug(CONTROLLER_NAME + " : createdProject = " + JSON.stringify(createdProject));
                                    // --------------------------------------------------------

                                    $scope.projects.push(createdProject);

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

                };


                // ==================================================
                // - delete a team main function 
                // ==================================================
                $scope.closeProject = function (project_id) {

                    var projectToClose = $scope.findProject(project_id);
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

                    if (projects.length === 1) {
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