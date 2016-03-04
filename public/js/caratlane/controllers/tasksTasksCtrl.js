'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'tasksTasksCtrl',
        [
            '$log',
            '$scope',
            '$uibModal',
            'holidaysSrvc',
            'tasksSrvc',
            'projectsTasksSrvc',
            'modalSrvc',
            function (
                    $log,
                    $scope,
                    $uibModal,
                    holidaysSrvc,
                    tasksSrvc,
                    projectsTasksSrvc,
                    modalSrvc
                    ) {

                var CONTROLLER_NAME = 'tasksTasksCtrl';

                var task_type = 'job';
                var holidays_type = 'cancelled';
                var weekend_type = 'off-site-work';
                var non_working_type = 'to-do';

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
                // - 
                // ==================================================
                var getFilterEmployee = function () {
                    return $scope.filter_employee;
                };

                // ==================================================
                // - 
                // ==================================================
                var getFilterProject = function () {
                    return $scope.filter_project;
                };

                // ==================================================
                // - 
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

                        },
                        function (response) {

                            // ==================================================
                            // - retrieving employee's profile data failed
                            // ==================================================

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

                    // cannot edit company's non working days.
                    var type = calendarEvent.type;
                    if (type === non_working_type || type === weekend_type) {
                        var message = 'Cannot edit company\'s non working days from this page.';
                        modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                        return;
                    }

                    var isTask = calendarEvent.type === task_type;
                    var isHolidays = calendarEvent.type === holidays_type;

                    if (isHolidays) {

                        $scope.editHolidayEvent(calendarEvent);
                    }
                    else if (isTask) {

                        $scope.updateTask(calendarEvent);
                    }
                };

                // ==================================================
                // - when user click on the event's delete icon
                // ==================================================
                $scope.deleteEvent = function (calendarEvent) {

                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : calendarEvent = " + JSON.stringify(calendarEvent));
                    // --------------------------------------------------------

                    // cannot delete company's non working days.
                    var type = calendarEvent.type;
                    if (type === non_working_type || type === weekend_type) {
                        var message = 'You cannot delete a Company\'s Non-Working Days from this page.';
                        modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                        return;
                    }

                    var isTask = calendarEvent.type === task_type;
                    var isHolidays = calendarEvent.type === holidays_type;

                    if (isHolidays) {

                        $scope.deleteHolidayEvent(calendarEvent);
                    }
                    else if (isTask) {

                        $scope.deleteTask(calendarEvent);
                    }

                };

                // ==================================================
                // - when user click on the create new task button
                // ==================================================
                $scope.createTask = function (calendarEvent) {

                    var newEvent = {};

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_task_create',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.event = newEvent;

                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };

                            // ==================================================
                            // - create a new non working day
                            // ==================================================
                            $scope.create = function () {


                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });

                };

                // ==================================================
                // - 
                // ==================================================
                $scope.editHolidayEvent = function (calendarEvent) {

                    // --------------------------------------------------------
                    // - initialization of the date and time pickers
                    // --------------------------------------------------------

                    // tomorrow at 0am
                    var tomorrow = new Date();
                    tomorrow.setHours(24, 0, 0, 0); // next midnignt

                    // the day after tomorrow at 0am
                    var tmrwPlusOneDay = new Date();
                    tmrwPlusOneDay.setHours(2 * 24, 0, 0, 0); // next midnignt

                    // allowes date picker from min_date = now 
                    // to max_date one year ahead
                    var min_date = tomorrow;
                    var max_date = new Date(tomorrow);
                    max_date.setDate(max_date.getDate() + 365);

                    var toEditEvent = {
                        'id': calendarEvent.id,
                        'title': calendarEvent.title,
                        'startsAt': calendarEvent.startsAt,
                        'endsAt': calendarEvent.endsAt,
                        'original_id': calendarEvent.original_id,
                        'employee_id': calendarEvent.employee_id,
                        'start_date': calendarEvent.start_date,
                        'start_morning_shift': calendarEvent.start_morning_shift,
                        'start_afternoon_shift': calendarEvent.start_afternoon_shift,
                        'end_date': calendarEvent.end_date,
                        'end_morning_shift': calendarEvent.end_morning_shift,
                        'end_afternoon_shift': calendarEvent.end_afternoon_shift,
                        'min_date': min_date,
                        'max_date': max_date,
                        'data_hour_step': 1,
                        'data_minute_step': 30
                    };

                    // ==================================================
                    // - edit event modal
                    // ==================================================
                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/profile_calendar_edit_holiday',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.event = toEditEvent;

                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };

                            $scope.update = function () {

                                // are the new values valid ?
                                if ($scope.validHolidayEvent($scope.event) === false) {
                                    // validHolidayEvent will open up modal for the user
                                    // to read a message, so just close the update modal.
                                    $uibModalInstance.dismiss('cancel');
                                    return;
                                }

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : $scope.event = " + JSON.stringify($scope.event));
                                // --------------------------------------------------------

                                var toUpdateHoliday = {
                                    id: $scope.event.original_id,
                                    title: $scope.event.title,
                                    employee_id: $scope.event.employee_id,
                                    start_date: formatJS2MySQLDate(new Date($scope.event.start_date)),
                                    start_morning_shift: formatBoolean2MySQL($scope.event.start_morning_shift),
                                    start_afternoon_shift: formatBoolean2MySQL($scope.event.start_afternoon_shift),
                                    end_date: formatJS2MySQLDate(new Date($scope.event.end_date)),
                                    end_morning_shift: formatBoolean2MySQL($scope.event.end_morning_shift),
                                    end_afternoon_shift: formatBoolean2MySQL($scope.event.end_afternoon_shift)
                                };

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : toUpdateHoliday = " + JSON.stringify(toUpdateHoliday));
                                // --------------------------------------------------------

                                var holidayPromise = holidaysSrvc.updateHoliday(toUpdateHoliday);
                                holidayPromise.then(
                                        function (response) {

                                            var updatedHoliday = response.holiday;

                                            // --------------------------------------------------------
                                            // $log.debug(CONTROLLER_NAME + " : updatedHoliday = " + JSON.stringify(updatedHoliday));
                                            // --------------------------------------------------------

                                            // remove the event from the calendar's list of events 
                                            holidays = holidays.filter(function (holiday) {
                                                return holiday.id !== updatedHoliday.id;
                                            });

                                            // replace by the new updated holiday event
                                            holidays.push(updatedHoliday);

                                            // and rebuild the list
                                            buildEventList(nwds, holidays, tasks, getFilterProject(), getFilterEmployee());

                                            var title = updatedHoliday.title;
                                            var message = 'Holiday event : ' + title + ', was successfuly updated!';
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


                            // ==================================================
                            // - valid a holiday event
                            // ================================================== 
                            $scope.validHolidayEvent = function (event) {

                                // -------------------------------------------------
                                // - the name must be defined properly
                                // -------------------------------------------------
                                var title = event.title;
                                if (title === null || title === undefined || title.length === 0) {
                                    var message = "The field Title must be defined.";
                                    modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                    return false;
                                }

                                var startAt = event.startsAt;
                                var endAt = event.endsAt;
                                // -------------------------------------------------
                                // - the startAt date must be greater than today's date
                                // -------------------------------------------------
                                var today = new Date();
                                today.setHours(24, 0, 0, 0);
                                if (startAt.getTime() <= today.getTime()) {
                                    var message = "Only Holiday event in the future can be edited.";
                                    modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                    return false;
                                }

                                // -------------------------------------------------
                                // - the endAt date must be greater than startAt date
                                // -------------------------------------------------
                                if (endAt.getTime() <= startAt.getTime()) {
                                    var message = "The End Date occurs before the Start Date.";
                                    modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                    return false;
                                }

                                // -------------------------------------------------
                                // - start and end dates for weekends 
                                // -------------------------------------------------
                                // TODO

                                return true;
                            };
                        }
                    });

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
                $scope.deleteHolidayEvent = function (calendarEvent) {

                    var toDeleteEvent = {
                        'id': calendarEvent.id,
                        'title': calendarEvent.title,
                        'startsAt': calendarEvent.startsAt,
                        'endsAt': calendarEvent.endsAt,
                        'original_id': calendarEvent.original_id,
                        'employee_id': calendarEvent.employee_id,
                        'start_date': calendarEvent.start_date,
                        'start_morning_shift': calendarEvent.start_morning_shift,
                        'start_afternoon_shift': calendarEvent.start_afternoon_shift,
                        'end_date': calendarEvent.end_date,
                        'end_morning_shift': calendarEvent.end_morning_shift,
                        'end_afternoon_shift': calendarEvent.end_afternoon_shift
                    };

                    $uibModal.open({
                        templateUrl: 'taskflow/fragments/profile_calendar_delete_holidays',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.event = toDeleteEvent;

                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };

                            $scope.delete = function () {

                                var title = $scope.event.title;
                                var original_id = $scope.event.original_id;

                                // ==================================================
                                // - deleting a holiday event
                                // ==================================================
                                var nwdPromise = holidaysSrvc.deleteHoliday(original_id);
                                nwdPromise.then(
                                        function (response) {

                                            // --------------------------------------------------------
                                            // $log.debug(CONTROLLER_NAME + " : response = " + JSON.stringify(response));
                                            // --------------------------------------------------------

                                            // remove the event from the calendar's list of events 
                                            holidays = holidays.filter(function (holiday) {
                                                return holiday.id !== original_id;
                                            });

                                            buildEventList(nwds, holidays, tasks, getFilterProject(), getFilterEmployee());

                                            var message = 'Holiday event ' + title + ', was successfuly deleted!';
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
                // - format a JS date to a MySQL date
                // ==================================================
                function  formatJS2MySQLDate(js_date) {

                    var year = js_date.getFullYear();
                    var month = js_date.getMonth() + 1;
                    var day = js_date.getDate();

                    var monthStr = month;
                    if (month < 10) {
                        monthStr = '0' + monthStr;
                    }

                    var dayStr = day;
                    if (day < 10) {
                        dayStr = '0' + dayStr;
                    }

                    return year + '-' + monthStr + '-' + dayStr;
                }

                // ==================================================
                // - get a boolean value from mysql
                // ==================================================
                function formatMySQL2Boolean(val) {
                    if (val === 0) {
                        return false;
                    }
                    else {
                        return true;
                    }
                }

                // ==================================================
                // - format a boolean value for mysql
                // ==================================================
                function formatBoolean2MySQL(val) {
                    if (val === false) {
                        return 0;
                    }
                    else {
                        return 1;
                    }
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
                    // $log.debug(CONTROLLER_NAME + " : tasks = " + JSON.stringify(tasks));
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
                        var project_nb_products = tasks[index].project_nb_products;
                        var startsAt = tasksSrvc.getTaskStartsAt(tasks[index].start_date);
                        var endsAt = tasksSrvc.getTaskEndsAt(tasks[index].start_date, duration);

                        var timeline = tasksSrvc.getTimeline(
                                tasks[index].start_date,
                                tasks[index].completion,
                                tasks[index].duration,
                                tasks[index].completed
                                );

                        var title = '<span class="text-muted text-small">';
                        title = title + employee_full_name;
                        title = title + '</span>';
                        title = title + '<br>';
                        title = title + '<span class="text-primary">';
                        title = title + original_title;
                        title = title + '</span>';
                        title = title + '<br>';
                        title = title + timeline;

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

            }
        ]

        );

