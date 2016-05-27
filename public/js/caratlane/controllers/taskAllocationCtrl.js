'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'taskAllocationCtrl',
        [
            '$log',
            '$scope',
            '$uibModal',
            'holidaysSrvc',
            'tasksSrvc',
            'projectsTasksSrvc',
            'taskAllocationSrvc',
            'modalSrvc',
            'usSpinnerService',
            function (
                    $log,
                    $scope,
                    $uibModal,
                    holidaysSrvc,
                    tasksSrvc,
                    projectsTasksSrvc,
                    taskAllocationSrvc,
                    modalSrvc,
                    usSpinnerService
                    ) {

                var CONTROLLER_NAME = 'taskAllocationCtrl';

                // ==================================================
                // - initialization
                // ==================================================

                var task_type = 'job';
                var holidays_type = 'cancelled';
                var weekend_type = 'off-site-work';
                var non_working_type = 'to-do';

                var logs = [];

                // ==================================================
                // - initialize the calendar
                // ==================================================
                $scope.calendarView = 'month';
                $scope.calendarDay = new Date();

                // ==================================================
                // - initialize the events
                // ==================================================
                var event_id_water_mark = 0;
                $scope.events = null;

                var nwds = null;
                var holidays = null;
                var tasks = null;

                $scope.employees = null;
                $scope.projects = null;

                $scope.filter_employee = null;
                $scope.filter_project = null;

                // ==================================================
                // - spinner
                // ==================================================
                $scope.startSpin = function () {
                    usSpinnerService.spin('taskSpinner');
                };

                $scope.stopSpin = function () {
                    usSpinnerService.stop('taskSpinner');
                };

                // ==================================================
                // - employee filter
                // ==================================================
                var getFilterEmployee = function () {
                    return $scope.filter_employee;
                };

                // ==================================================
                // - project filter
                // ==================================================
                var getFilterProject = function () {
                    return $scope.filter_project;
                };

                // ==================================================
                // - helper
                // ==================================================
                var startWith = function (haystack, needle) {
                    return haystack.lastIndexOf(needle, 0) === 0;
                };

                // ==================================================
                // - helper
                // ==================================================
                var toInteger = function (str) {
                    return  parseInt(str);
                };

                // ==================================================
                // - retrieve all tasks, non working days and holidays
                // ==================================================

                // start the spinner
                $scope.startSpin();

                var eventsPromise = projectsTasksSrvc.getProjectsEvents();
                eventsPromise.then(
                        function (response) {

                            nwds = response.non_working_days;
                            holidays = response.holidays;
                            tasks = response.tasks;

                            var all_employees = [{'id': 0, 'fullName': 'All Employees'}];
                            $scope.employees = all_employees.concat(response.employees);
                            $scope.filter_employee = {'id': 0, 'fullName': 'All Employees'};

                            var all_projects = [{'id': 0, 'reference': 'All Projects'}];
                            $scope.projects = all_projects.concat(response.projects);
                            $scope.filter_project = {'id': 0, 'reference': 'All Projects'};

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : $scope.projects = " + JSON.stringify($scope.projects));
                            // $log.debug(CONTROLLER_NAME + " : tasks = " + JSON.stringify(tasks));
                            // --------------------------------------------------------

                            buildEventList(nwds, holidays, tasks, null, null);

                            // end the spinner
                            $scope.stopSpin();

                        },
                        function (response) {

                            // ==================================================
                            // - retrieving employee's profile data failed
                            // ==================================================

                            // end the spinner
                            $scope.stopSpin();

                            var status = response.status;
                            var message = response.statusText;
                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                        });

                // ==================================================
                // - project filter
                // ==================================================
                $scope.changeProject = function () {

                    buildEventList(nwds, holidays, tasks, getFilterProject(), getFilterEmployee());
                };

                // ==================================================
                // - employee filter
                // ==================================================
                $scope.changeEmployee = function () {

                    buildEventList(nwds, holidays, tasks, getFilterProject(), getFilterEmployee());
                };

                // ==================================================
                // - when user click on the event's edit icon
                // ==================================================
                $scope.editEvent = function (calendarEvent) {

                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : calendarEvent = " + JSON.stringify(calendarEvent));
                    // --------------------------------------------------------

                    var isTask = calendarEvent.type === task_type;
                    var isHolidays = calendarEvent.type === holidays_type;
                    var isNWD = calendarEvent.type === non_working_type || calendarEvent.type === weekend_type;

                    // cannot edit company's non working days.
                    if (isNWD) {
                        var message = 'Cannot edit company\'s Non Working Days from this page.';
                        modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                        return;
                    }

                    // cannot edit employee's holiday from this tab..
                    if (isHolidays) {
                        var message = 'Cannot edit Employee\'s Holidays from this tab.';
                        modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                        return;
                    }

                    if (isTask) {
                        $scope.updateTask(calendarEvent);
                    }

                    return;
                };

                // ==================================================
                // - when user click on the event's delete icon
                // ==================================================
                $scope.deleteEvent = function (calendarEvent) {

                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : calendarEvent = " + JSON.stringify(calendarEvent));
                    // --------------------------------------------------------

                    var isTask = calendarEvent.type === task_type;
                    var isHolidays = calendarEvent.type === holidays_type;
                    var isNWD = calendarEvent.type === non_working_type || calendarEvent.type === weekend_type;

                    // cannot delete company's non working days.
                    if (isNWD) {
                        var message = 'You cannot delete a Company\'s Non-Working Days from this page.';
                        modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                        return;
                    }

                    // cannot delete employee's holiday from this tab.
                    if (isHolidays) {
                        var message = 'Cannot delete Employee\'s Holidays from this page.';
                        modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                        return;
                    }

                    if (isTask) {
                        $scope.deleteTask(calendarEvent);
                    }

                };

                // ==================================================
                // - 
                // ==================================================
                $scope.updateTask = function (calendarEvent) {

                    var project_nb_products = calendarEvent.project_nb_products;
                    var durationInMins = calendarEvent.duration;

                    var original_id = calendarEvent.original_id;
                    var task_id = calendarEvent.task_id;
                    var title = calendarEvent.original_title;
                    var duration = tasksSrvc.formatDuration(calendarEvent.duration);
                    var completion = tasksSrvc.formatCompletion(calendarEvent.completion);
                    var employee_id = calendarEvent.employee_id;
                    var completed = calendarEvent.completed;
                    var start_date = calendarEvent.start_date;
                    var nb_products_completed = calendarEvent.nb_products_completed;
                    var nb_products_planned = calendarEvent.nb_products_planned;
                    var completion_choices = tasksSrvc.getCompletionChoices(durationInMins);
                    var nb_products_choices = tasksSrvc.getNbProductsChoices(project_nb_products);

                    // ==================================================
                    // - edit event modal
                    // ==================================================
                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_task_update',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.title = title;
                            $scope.duration = duration;
                            $scope.duration_in_mins = durationInMins;
                            $scope.task_id = task_id;
                            $scope.employee_id = employee_id;
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

                                var taskToUpdate = {
                                    id: original_id,
                                    task_id: $scope.task_id,
                                    employee_id: $scope.employee_id,
                                    start_date: $scope.start_date,
                                    completion: tasksSrvc.formatCompletion2Mins($scope.completion),
                                    nb_products_completed: $scope.nb_products_completed,
                                    completed: $scope.completed,
                                    duration: $scope.duration_in_mins
                                };

                                // --------------------------------------------------------                                     
                                // $log.debug(CONTROLLER_NAME + " : taskToUpdate = " + JSON.stringify(taskToUpdate));
                                // --------------------------------------------------------

                                var taskPromise = tasksSrvc.updateTaskAllocation(taskToUpdate);
                                taskPromise.then(
                                        function (response) {

                                            var updatedTask = response.task;

                                            // --------------------------------------------------------                                     
                                            // $log.debug(CONTROLLER_NAME + " : updatedTask = " + JSON.stringify(updatedTask));
                                            // --------------------------------------------------------

                                            // remove the event from the calendar's list of events 
                                            tasks = tasks.filter(function (task) {
                                                return task.id !== updatedTask.id;
                                            });

                                            // replace by the new updated holiday event
                                            tasks.push(updatedTask);

                                            // and rebuild the list
                                            buildEventList(nwds, holidays, tasks, getFilterProject(), getFilterEmployee());

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
                // - 
                // ==================================================
                $scope.deleteTask = function (calendarEvent) {

                    var project_nb_products = calendarEvent.project_nb_products;
                    var durationInMins = calendarEvent.duration;

                    var original_id = calendarEvent.original_id;
                    var task_id = calendarEvent.task_id;
                    var title = calendarEvent.original_title;
                    var duration = tasksSrvc.formatDuration(calendarEvent.duration);
                    var completion = tasksSrvc.formatCompletion(calendarEvent.completion);
                    var employee_id = calendarEvent.employee_id;
                    var completed = calendarEvent.completed;
                    var start_date = calendarEvent.start_date;
                    var nb_products_completed = calendarEvent.nb_products_completed;
                    var nb_products_planned = calendarEvent.nb_products_planned;
                    var completion_choices = tasksSrvc.getCompletionChoices(durationInMins);
                    var nb_products_choices = tasksSrvc.getNbProductsChoices(project_nb_products);

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_task_delete',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.title = title;
                            $scope.original_id = original_id;
                            $scope.duration = duration;
                            $scope.duration_in_mins = durationInMins;
                            $scope.task_id = task_id;
                            $scope.employee_id = employee_id;
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

                            $scope.delete = function () {

                                var title = $scope.title;
                                var original_id = $scope.original_id;

                                // ==================================================
                                // - deleting a holiday event
                                // ==================================================
                                var taskPromise = tasksSrvc.deleteTaskAllocation(original_id);
                                taskPromise.then(
                                        function (response) {

                                            // --------------------------------------------------------
                                            // $log.debug(CONTROLLER_NAME + " : response = " + JSON.stringify(response));
                                            // --------------------------------------------------------

                                            // remove the event from the calendar's list of events 
                                            tasks = tasks.filter(function (tasks) {
                                                return tasks.id !== original_id;
                                            });

                                            buildEventList(nwds, holidays, tasks, getFilterProject(), getFilterEmployee());

                                            var message = 'Task ' + title + ', was successfuly deleted!';
                                            modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);
                                        },
                                        function (response) {

                                            var status = response.status;
                                            var message = response.statusText;
                                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                        }
                                );

                                $uibModalInstance.close($scope.event, $scope.event);
                            };
                        }
                    });
                };

                // ==================================================
                $scope.toggle = function ($event, field, event) {
                    $event.preventDefault();
                    $event.stopPropagation();

                    event[field] = !event[field];
                };

                // ==================================================
                // - get a JS date from a MySQL datatime
                // ==================================================
                function formatMySQL2JSDate(mysql_date) {

                    var t, result = null;

                    if (typeof mysql_date === 'string')
                    {
                        t = mysql_date.split(/[- :]/);

                        //when t[3], t[4] and t[5] are missing they defaults to zero
                        result = new Date(t[0], t[1] - 1, t[2], t[3] || 0, t[4] || 0, t[5] || 0);
                    }

                    return result;
                }

                // ==================================================
                // - build the list of events for display 
                // ==================================================
                function buildEventList(nwds, holidays, tasks, filter_project, filter_employee) {

                    $scope.events = null;
                    var newEvents = [];
                    event_id_water_mark = 0;

                    addNonWorkingDaysEvent(newEvents, nwds);
                    addHolidaysEvent(newEvents, holidays, filter_employee);
                    addTasksEvent(newEvents, tasks, filter_project, filter_employee);

                    $scope.events = newEvents;
                }

                // ==================================================
                // - add non working day events to the calendar
                // ==================================================
                function addNonWorkingDaysEvent(events, nwds) {

                    var index = 0;

                    for (index = 0; index < nwds.length; index++) {

                        var id = nwds[index].id;
                        var title = nwds[index].title;

                        var type = null;
                        switch (nwds[index].type) {
                            case 'WEEKEND':
                                type = weekend_type;
                                break;
                            case 'NON-WORKING':
                                type = non_working_type;
                                break;
                        }

                        var date = nwds[index].date;
                        var startsAt = tasksSrvc.getNwdStartsAt(formatMySQL2JSDate(nwds[index].date));
                        var endsAt = tasksSrvc.getNwdEndsAt(formatMySQL2JSDate(nwds[index].date));

                        var event = {
                            'id': event_id_water_mark,
                            'title': title,
                            'type': type,
                            'startsAt': startsAt,
                            'endsAt': endsAt,
                            'draggable': false,
                            'resizable': true,
                            'date': date,
                            'original_id': id
                        };

                        // --------------------------------------------------------
                        // $log.debug(CONTROLLER_NAME + " : event = " + JSON.stringify(event));
                        // --------------------------------------------------------

                        events.push(event);
                        event_id_water_mark++;
                    }
                }

                // ==================================================
                // - add holidays events to the calendar
                // ==================================================
                function addHolidaysEvent(events, holidays, filter_employee) {

                    var index = 0;

                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : filter_employee = " + JSON.stringify(filter_employee));
                    // --------------------------------------------------------

                    for (index = 0; index < holidays.length; index++) {

                        // employee filter
                        if (filter_employee !== null && filter_employee.id !== 0) {
                            if (filter_employee.id !== holidays[index].employee_id) {
                                continue;
                            }
                        }

                        var id = holidays[index].id;
                        var original_title = holidays[index].title;
                        var employee_id = holidays[index].employee_id;
                        var employee_full_name = holidays[index].employee_full_name;
                        var start_date = holidays[index].start_date;
                        var end_date = holidays[index].end_date;
                        var startsAt = holidaysSrvc.getHolidaysStartsAt(new Date(holidays[index].start_date));
                        var endsAt = holidaysSrvc.getHolidaysEndsAt(new Date(holidays[index].end_date));

                        var title = '<span class="text-muted text-small">';
                        title = title + employee_full_name;
                        title = title + '</span>';
                        title = title + '<br>';
                        title = title + '<span class="text-primary">';
                        title = title + original_title;
                        title = title + '</span>';

                        var event = {
                            'id': event_id_water_mark,
                            'title': title,
                            'type': holidays_type,
                            'original_title': original_title,
                            'startsAt': startsAt,
                            'endsAt': endsAt,
                            'draggable': false,
                            'resizable': true,
                            'original_id': id,
                            'employee_id': employee_id,
                            'employee_full_name': employee_full_name,
                            'start_date': start_date,
                            'end_date': end_date
                        };

                        // --------------------------------------------------------
                        // $log.debug(CONTROLLER_NAME + " : event = " + JSON.stringify(event));
                        // --------------------------------------------------------

                        events.push(event);
                        event_id_water_mark++;
                    }

                }

                // ==================================================
                // - add task events to the calendar
                // ==================================================
                function addTasksEvent(events, tasks, filter_project, filter_employee) {

                    var index = 0;

                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : tasks = " + JSON.stringify(tasks));
                    // --------------------------------------------------------

                    for (index = 0; index < tasks.length; index++) {

                        // project filter
                        if (filter_project !== null && filter_project.id !== 0) {
                            if (filter_project.id !== tasks[index].project_id) {
                                continue;
                            }
                        }

                        // employee filter
                        if (filter_employee !== null && filter_employee.id !== 0) {
                            if (filter_employee.id !== tasks[index].employee_id) {
                                continue;
                            }
                        }

                        var id = tasks[index].id;
                        var original_title = tasks[index].title;
                        var employee_id = tasks[index].employee_id;
                        var employee_full_name = tasks[index].employee_full_name;
                        var project_id = tasks[index].project_id;
                        var skill_id = tasks[index].skill_id;
                        var task_id = tasks[index].task_id;
                        var start_date = tasks[index].start_date;
                        var duration = tasks[index].duration;
                        var completion = tasks[index].completion;
                        var completed = tasks[index].completed;
                        var open = tasks[index].open;
                        var nb_products_completed = tasks[index].nb_products_completed;
                        var nb_products_planned = tasks[index].nb_products_planned;
                        var project_nb_products = tasks[index].project_nb_products;
                        var startsAt = tasksSrvc.getTaskStartsAt(tasks[index].start_date);
                        var endsAt = tasksSrvc.getTaskEndsAt(tasks[index].start_date, duration);

                        var timeline1 = tasksSrvc.getTimeline1(
                                tasks[index].duration,
                                tasks[index].nb_products_planned
                                );

                        var timeline2 = tasksSrvc.getTimeline2(
                                tasks[index].completion,
                                tasks[index].nb_products_completed
                                );

                        var title = '<span class="text-muted text-small">';
                        title = title + employee_full_name;
                        title = title + '</span>';
                        title = title + '<br>';
                        title = title + '<span class="text-primary">';
                        title = title + original_title;
                        title = title + '</span>';
                        title = title + '<br>';
                        title = title + timeline1;
                        title = title + '<br>';
                        title = title + timeline2;

                        var event = {
                            'id': event_id_water_mark,
                            'title': title,
                            'type': task_type,
                            'startsAt': startsAt,
                            'endsAt': endsAt,
                            'draggable': false,
                            'resizable': true,
                            'original_id': id,
                            'original_title': original_title,
                            'employee_id': employee_id,
                            'project_id': project_id,
                            'skill_id': skill_id,
                            'task_id': task_id,
                            'start_date': start_date,
                            'duration': duration,
                            'completion': completion,
                            'completed': completed,
                            'nb_products_completed': nb_products_completed,
                            'nb_products_planned': nb_products_planned,
                            'project_nb_products': project_nb_products,
                            'open': open
                        };

                        // --------------------------------------------------------
                        // $log.debug(CONTROLLER_NAME + " : event = " + JSON.stringify(event));
                        // --------------------------------------------------------

                        events.push(event);
                        event_id_water_mark++;
                    }

                }

                // ==================================================
                // - kick off task allocation
                // ==================================================
                $scope.allocateTasks = function () {

                    // start the spinner
                    $scope.startSpin();

                    // remove current tasks for thecalendar
                    tasks = {};
                    buildEventList(nwds, holidays, tasks, null, null);

                    var allocatePromise = taskAllocationSrvc.allocate();
                    allocatePromise.then(
                            function (response) {

                                // ==================================================
                                // - retrieve all tasks, non working days and holidays
                                // ==================================================
                                var eventsPromise = projectsTasksSrvc.getProjectsEvents();
                                eventsPromise.then(
                                        function (response) {

                                            nwds = response.non_working_days;
                                            holidays = response.holidays;
                                            tasks = response.tasks;

                                            var all_employees = [{'id': 0, 'fullName': 'All Employees'}];
                                            $scope.employees = all_employees.concat(response.employees);
                                            $scope.filter_employee = {'id': 0, 'fullName': 'All Employees'};

                                            var all_projects = [{'id': 0, 'reference': 'All Projects'}];
                                            $scope.projects = all_projects.concat(response.projects);
                                            $scope.filter_project = {'id': 0, 'reference': 'All Projects'};

                                            // --------------------------------------------------------
                                            // $log.debug(CONTROLLER_NAME + " : $scope.projects = " + JSON.stringify($scope.projects));
                                            // $log.debug(CONTROLLER_NAME + " : tasks = " + JSON.stringify(tasks));
                                            // --------------------------------------------------------

                                            buildEventList(nwds, holidays, tasks, null, null);

                                            // end the spinner
                                            $scope.stopSpin();

                                        },
                                        function (response) {

                                            // ==================================================
                                            // - retrieving employee's profile data failed
                                            // ==================================================

                                            // end the spinner
                                            $scope.stopSpin();

                                            var status = response.status;
                                            var message = response.statusText;
                                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                        });
                            },
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                // --------------------------------------------------------

                                // ==================================================
                                // - allocation failed
                                // ==================================================

                                // end the spinner
                                $scope.stopSpin();

                                var status = response.status;
                                var message = response.statusText;
                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                            }
                    );
                };

                // ==================================================
                // - test task allocation
                // ==================================================
                $scope.testTasks = function () {

                    // start the spinner
                    $scope.startSpin();

                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : test ..... ");
                    // --------------------------------------------------------

                    var testPromise = taskAllocationSrvc.test();
                    testPromise.then(
                            function (response) {

                                var index = 0;
                                var results = {'msg': null, 'warning': 0, 'error': 0};

                                var testmsgs = response.logs;
                                for (index = 0; index < testmsgs.length; index++) {

                                    var msg = null;
                                    var log = testmsgs[index];

                                    // --------------------------------------------------------
                                    // $log.debug(CONTROLLER_NAME + " : log = " + log);
                                    // --------------------------------------------------------

                                    if (startWith(log, 'INFO ') === true) {

                                        var msg = log.replace("INFO ", "");
                                        logs.push({'type': 'INFO', 'msg': msg});

                                    } else if (startWith(log, 'WARNING ') === true) {

                                        var msg = log.replace("WARNING ", "");
                                        logs.push({'type': 'WARNING', 'msg': msg});

                                    } else if (startWith(log, 'SEVERE ') === true) {

                                        var msg = log.replace("SEVERE ", "");
                                        logs.push({'type': 'ERROR', 'msg': msg});

                                    } else if (startWith(log, 'RESULTS_WARNINGS ') === true) {

                                        var nb = log.replace("RESULTS_WARNINGS : ", "");
                                        results.warning = toInteger(nb);

                                    } else if (startWith(log, 'RESULTS_ERRORS ') === true) {

                                        var nb = log.replace("RESULTS_ERRORS : ", "");
                                        results.error = toInteger(nb);

                                    } else {
                                        // --------------------------------------------------------
                                        $log.debug(CONTROLLER_NAME + " : log = " + log);
                                        // --------------------------------------------------------
                                    }
                                }

                                results.msg = 'Nb Errors : ' + results.error + ', Nb Warnings : ' + results.warning;

                                // end the spinner
                                $scope.stopSpin();

                                $uibModal.open({
                                    animation: $scope.animationsEnabled,
                                    templateUrl: 'taskflow/fragments/modal_test_allocation',
                                    placement: 'center',
                                    size: 'lg',
                                    controller: function ($scope, $uibModalInstance) {
                                        $scope.$modalInstance = $uibModalInstance;
                                        $scope.logs = logs;
                                        $scope.results = results;
                                        $scope.cancel = function () {
                                            $uibModalInstance.dismiss('cancel');
                                        };
                                    }
                                });

                            },
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                // --------------------------------------------------------

                                // ==================================================
                                // - allocation failed
                                // ==================================================

                                // end the spinner
                                $scope.stopSpin();

                                var status = response.status;
                                var message = response.statusText;
                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                            }
                    );
                };

                // ==================================================
                // - reset task allocation ( for testing purpose )
                // ==================================================
                $scope.resetTasks = function () {

                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : reset ..... ");
                    // --------------------------------------------------------

//                    $scope.generate_message = '';
//                    $scope.reset_message = 'Resetting database ...';

                    var allocatePromise = taskAllocationSrvc.reset();
                    allocatePromise.then(
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : xxxxxxx = " + JSON.stringify(xxxxxxx));
                                $log.debug(CONTROLLER_NAME + " : reset done ! ");
                                // --------------------------------------------------------

//                                $scope.generate_message = '';
//                                $scope.reset_message = '... database reset.';

                            },
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                // --------------------------------------------------------

                                // ==================================================
                                // - reset failed
                                // ==================================================

                                var status = response.status;
                                var message = response.statusText;
                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);

//                                $scope.generate_message = '';
//                                $scope.reset_message = 'Error occured during reset of the database.';
                            }
                    );
                };

            }]);

