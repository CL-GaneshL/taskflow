'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'profileOverviewCtrl',
        [
            '$log',
            '$scope',
            '$uibModal',
            'flowFactory',
            'profileSrvc',
            'pageSrvc',
            'employeesSrvc',
            'tasksSrvc',
            'modalSrvc',
            function ($log, $scope, $uibModal, flowFactory, profileSrvc, pageSrvc, employeesSrvc, tasksSrvc, modalSrvc) {

                var CONTROLLER_NAME = 'profileOverviewCtrl';

                var RECENT_TASKS_NB_MAX = 15;

                $scope.isTeamLeader = false;
                $scope.isProjectManager = false;
                $scope.employement = '';
                $scope.profile = null;
                $scope.taskAllocations = [];

                // ==================================================
                // - helpers, used to highlight task duration
                // ==================================================
                $scope.isDurationSuccess = function (completed, expected, actual) {

                    var success = false;

                    if (completed === 1) {
                        return (actual <= expected);
                    }

                    return success;
                };

                $scope.isDurationWarning = function (completed, expected, actual) {

                    var success = false;

                    if (completed === 1) {
                        return (actual > expected);
                    }

                    return success;
                };

                // ==================================================
                // - helpers, used to highlight product completion
                // ==================================================
                $scope.isProductSuccess = function (expected, actual) {
                    return (actual >= expected);
                };

                $scope.isProductWarning = function (expected, actual) {
                    return (actual < expected);
                };

                // check if this controller is used for the User 
                // Profile page or for the Employee page
                var isProfilePage = pageSrvc.isProfilePage();

                if (isProfilePage) {

                    var employee = $scope.profile = profileSrvc.getEmployee();
                    var employee_id = employee.id;

                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : employee = " + JSON.stringify(employee));
                    // --------------------------------------------------------

                    var dataPromise = profileSrvc.getUserProfile(employee_id);

                    dataPromise.then(
                            function (response) {

                                $scope.profile = response.profile.employee;
                                var recentTasks = response.profile.recentTasks;

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : profile recentTasks = " + JSON.stringify(recentTasks));
                                // --------------------------------------------------------

                                var index = 0;
                                for (index = 0; index < RECENT_TASKS_NB_MAX && index < recentTasks.length; index++) {
                                    $scope.taskAllocations.push(
                                            {
                                                'id': recentTasks[index].id,
                                                'start_date': recentTasks[index].start_date,
                                                'title': recentTasks[index].title,
                                                'duration': recentTasks[index].duration,
                                                'completion': recentTasks[index].completion,
                                                'completed': recentTasks[index].completed,
                                                'employee_id': recentTasks[index].employee_id,
                                                'open': recentTasks[index].open,
                                                'task_id': recentTasks[index].task_id,
                                                'nb_products_completed': recentTasks[index].nb_products_completed,
                                                'nb_products_planned': recentTasks[index].nb_products_planned,
                                                'project_nb_products': recentTasks[index].project_nb_products,
                                                'timeline0': tasksSrvc.getTimeline0(recentTasks[index].start_date, recentTasks[index].title),
                                                'timeline1': tasksSrvc.getTimeline1(recentTasks[index].duration, recentTasks[index].nb_products_planned),
                                                'duration_hm': tasksSrvc.formatDuration2HoursMins(recentTasks[index].completion)
                                            }
                                    );
                                }

                                $scope.isTeamLeader = $scope.profile.isTeamLeader === 1;
                                $scope.isProjectManager = $scope.profile.isProjectManager === 1;
                            });
                } else {

                    var dataPromise = employeesSrvc.getEmployee();
                    dataPromise.then(
                            function (response) {

                                $scope.profile = response.employee;
                                var recentTasks = response.recentTasks;

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : employee recentTasks = " + JSON.stringify(response.recentTasks));
                                // --------------------------------------------------------

                                var index = 0;
                                for (index = 0; index < RECENT_TASKS_NB_MAX && index < recentTasks.length; index++) {
                                    $scope.taskAllocations.push(
                                            {
                                                'id': recentTasks[index].id,
                                                'start_date': recentTasks[index].start_date,
                                                'title': recentTasks[index].title,
                                                'duration': recentTasks[index].duration,
                                                'completion': recentTasks[index].completion,
                                                'completed': recentTasks[index].completed,
                                                'employee_id': recentTasks[index].employee_id,
                                                'open': recentTasks[index].open,
                                                'task_id': recentTasks[index].task_id,
                                                'nb_products_completed': recentTasks[index].nb_products_completed,
                                                'nb_products_planned': recentTasks[index].nb_products_planned,
                                                'project_nb_products': recentTasks[index].project_nb_products,
                                                'timeline1': tasksSrvc.getTimeline0(
                                                        recentTasks[index].start_date,
                                                        recentTasks[index].duration,
                                                        recentTasks[index].nb_products_planned
                                                        ),
                                                'duration_hm': tasksSrvc.formatDuration2HoursMins(recentTasks[index].completion)
                                            }
                                    );
                                }

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : employeesSrvc $scope.taskAllocations = " + JSON.stringify($scope.taskAllocations));
                                // --------------------------------------------------------

                                $scope.isTeamLeader = $scope.profile.isTeamLeader === 1;
                                $scope.isProjectManager = $scope.profile.isProjectManager === 1;

                            });
                }

                // ==================================================
                // - update the task's completion
                // ==================================================
                $scope.updateTaskAllocation = function (taskId) {

                    var allocationToUpdate = findTaskAllocation(taskId);

                    // --------------------------------------------------------                                     
                    // $log.debug(CONTROLLER_NAME + " : taskId = " + taskId);
                    // $log.debug(CONTROLLER_NAME + " : allocationToUpdate = " + JSON.stringify(allocationToUpdate));
                    // --------------------------------------------------------

                    var project_nb_products = allocationToUpdate.project_nb_products;
                    var durationInMins = allocationToUpdate.duration;

                    var id = allocationToUpdate.id;
                    var title = allocationToUpdate.title;
                    var duration = tasksSrvc.formatDuration(durationInMins);
                    var duration_in_mins = allocationToUpdate.duration;
                    var completion = tasksSrvc.formatCompletion(allocationToUpdate.completion);
                    var completed = allocationToUpdate.completed;
                    var employee_id = allocationToUpdate.employee_id;
                    var task_id = allocationToUpdate.task_id;
                    var start_date = allocationToUpdate.start_date;
                    var nb_products_completed = allocationToUpdate.nb_products_completed;
                    var nb_products_planned = allocationToUpdate.nb_products_planned;
                    var completion_choices = tasksSrvc.getCompletionChoices(durationInMins);
                    var nb_products_choices = tasksSrvc.getNbProductsChoices(project_nb_products);

                    // ==================================================
                    // - edit event modal
                    // ==================================================
                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_task_update',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.id = id;
                            $scope.title = title;
                            $scope.duration = duration;
                            $scope.duration_in_mins = duration_in_mins;
                            $scope.employee_id = employee_id;
                            $scope.task_id = task_id;
                            $scope.start_date = start_date;
                            $scope.completion = completion;
                            $scope.completed = completed;
                            $scope.nb_products_completed = nb_products_completed;
                            $scope.nb_products_planned = nb_products_planned;
                            $scope.completion_choices = completion_choices;
                            $scope.nb_products_choices = nb_products_choices;

                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };

                            $scope.update = function () {

                                var open = $scope.open;

                                if (open === 0) {
                                    // reject the update if the project
                                    // related to that task is already closed.
                                    var message = 'Project closed, this task cannot be updated!';
                                    modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                    $uibModalInstance.dismiss('cancel');
                                    return;
                                }

                                var title = $scope.title;

                                var allocationToUpdate = {
                                    id: $scope.id,
                                    task_id: $scope.task_id,
                                    employee_id: $scope.employee_id,
                                    start_date: $scope.start_date,
                                    completion: tasksSrvc.formatCompletion2Mins($scope.completion),
                                    nb_products_completed: $scope.nb_products_completed,
                                    nb_products_planned: $scope.nb_products_planned,
                                    completed: $scope.completed,
                                    duration: $scope.duration_in_mins
                                };

                                // --------------------------------------------------------                                     
                                // $log.debug(CONTROLLER_NAME + " : allocationToUpdate = " + JSON.stringify(allocationToUpdate));
                                // --------------------------------------------------------

                                var taskPromise = tasksSrvc.updateTaskAllocation(allocationToUpdate);
                                taskPromise.then(
                                        function (response) {

                                            var updatedAllocation = response.task;

                                            // --------------------------------------------------------                                     
                                            // $log.debug(CONTROLLER_NAME + " : updatedAllocation = " + JSON.stringify(updatedAllocation));
                                            // --------------------------------------------------------

                                            removeTaskAllocation(updatedAllocation.id);

                                            var task =
//                                            addTaskAllocation(
                                                    {
                                                        'id': updatedAllocation.id,
                                                        'start_date': updatedAllocation.start_date,
                                                        'title': updatedAllocation.title,
                                                        'duration': updatedAllocation.duration,
                                                        'completion': updatedAllocation.completion,
                                                        'completed': updatedAllocation.completed,
                                                        'employee_id': updatedAllocation.employee_id,
                                                        'open': updatedAllocation.open,
                                                        'task_id': updatedAllocation.task_id,
                                                        'nb_products_completed': updatedAllocation.nb_products_completed,
                                                        'nb_products_planned': updatedAllocation.nb_products_planned,
                                                        'project_nb_products': updatedAllocation.project_nb_products,
                                                        'timeline1': tasksSrvc.getTimeline0(
                                                                updatedAllocation.start_date,
                                                                updatedAllocation.duration,
                                                                updatedAllocation.nb_products_planned
                                                                ),
                                                        'duration_hm': tasksSrvc.formatDuration2HoursMins(updatedAllocation.completion)
                                                    };

                                            addTaskAllocation(task);

                                            var message = 'Task : ' + title + ', was successfuly updated!';
                                            modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);

                                        },
                                        function (response) {
                                            // --------------------------------------------------------
                                            // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                            // --------------------------------------------------------

                                            var status = response.status;
                                            var message = response.statusText;
                                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                        }
                                );

                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                };

                // ==================================================
                // - find a task in the list of recent tasks
                // ==================================================
                function findTaskAllocation(id) {

                    var allocation = null;

                    var allocations = $scope.taskAllocations.filter(function (allocation) {
                        return allocation.id === id;
                    });

                    if (allocations.length === 1) {
                        allocation = allocations[0];
                    }

                    return allocation;
                }

                // ==================================================
                // - remove a non working day event from the calendar
                // ==================================================
                function removeTaskAllocation(id) {
                    $scope.taskAllocations = $scope.taskAllocations.filter(function (allocation) {
                        return allocation.id !== id;
                    });
                }

                // ==================================================
                // - add a non working day event to the calendar
                // ==================================================
                function addTaskAllocation(allocation) {

                    $scope.taskAllocations.push(allocation);

                    // re-sort the list
                    $scope.taskAllocations.sort(function (t1, t2) {
                        return t1.id < t2.id;
                    });
                }

                // ==================================================
                // needed to display the avatar
                $scope.obj = new Flow();

                // ==================================================

            }
        ]
        );