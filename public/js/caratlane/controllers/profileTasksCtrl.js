
'use strict';

angular.module('profileTasks', ['mwl.calendar', 'ui.bootstrap', 'ngTouch', 'ngAnimate'])
        .controller('profileTasksCtrl',
                [
                    '$log',
                    "$scope",
                    "$uibModal",
                    "modalSrvc",
                    'profileSrvc',
                    'pageSrvc',
                    'employeesSrvc',
                    'holidaysSrvc',
                    'tasksSrvc',
                    function ($log, $scope, $uibModal, modalSrvc, profileSrvc, pageSrvc, employeesSrvc, holidaysSrvc, tasksSrvc) {

                        var CONTROLLER_NAME = 'profileTasksCtrl';

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
                        $scope.events = [];

                        var nwds = null;
                        var holidays = null;
                        var tasks = null;

                        // ==================================================
                        // - check if this controller is used for the User
                        // - Profile page or for the Employee page
                        // ==================================================
                        var isProfilePage = pageSrvc.isProfilePage();

                        var employee = profileSrvc.getEmployee();
                        var isProjectManger = employee.isProjectManager;

                        // ==================================================
                        // - profile page
                        // ==================================================
                        if (isProfilePage) {

                            nwds = profileSrvc.getNonWorkingDays();
                            holidays = profileSrvc.getHolidays();
                            tasks = profileSrvc.getTasks();

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : profile nwds = " + JSON.stringify(nwds));
                            // --------------------------------------------------------

                            buildEventList(nwds, holidays, tasks);

                        }
                        else {
                            // ==================================================
                            // - employee page
                            // ==================================================

                            var dataPromise = employeesSrvc.getEmployee();
                            dataPromise.then(
                                    function (response) {

                                        nwds = response.non_working_days;
                                        holidays = response.holidays;
                                        tasks = response.tasks;

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : employee nwds = " + JSON.stringify(nwds));
                                        // --------------------------------------------------------

                                        buildEventList(nwds, holidays, tasks);

                                    },
                                    function (response) {

                                        // ==================================================
                                        // - retrieving employee's profile data failed
                                        // ==================================================

                                        var status = response.status;
                                        var message = response.statusText;
                                        modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                    });
                        }

                        // ==================================================
                        // - when user click on the event's edit icon
                        // ==================================================
                        $scope.editEvent = function (calendarEvent) {

                            // --------------------------------------------------------
                            $log.debug(CONTROLLER_NAME + " : calendarEvent = " + JSON.stringify(calendarEvent));
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
                                if (isProjectManger) {
                                    $scope.editHolidayEvent(calendarEvent);
                                }
                                else {
                                    var message = 'You do not have the rights to edit Holidays events.';
                                    modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                    return;
                                }
                            }
                            else if (isTask) {

                                $scope.updateTask(calendarEvent);
                            }
                        };

                        // ==================================================
                        // - when user click on the create new task button
                        // ==================================================
//                        $scope.createTask = function () {
//
//                            var project_nb_products = calendarEvent.project_nb_products;
//                            var durationInMins = calendarEvent.duration;
//
//                            var original_id = calendarEvent.original_id;
//                            var task_id = calendarEvent.task_id;
//                            var title = calendarEvent.original_title;
//                            var duration = tasksSrvc.formatDuration(calendarEvent.duration);
//                            var completion = tasksSrvc.formatCompletion(calendarEvent.completion);
//                            var employee_id = calendarEvent.employee_id;
//                            var completed = calendarEvent.completed;
//                            var start_date = calendarEvent.start_date;
//                            var nb_products_completed = calendarEvent.nb_products_completed;
//                            var completion_choices = tasksSrvc.getCompletionChoices(durationInMins);
//                            var nb_products_choices = tasksSrvc.getNbProductsChoices(project_nb_products);
//
//                            $uibModal.open({
//                                templateUrl: 'taskflow/fragments/modal_task_create',
//                                controller: function ($scope, $uibModalInstance) {
//                                    $scope.$modalInstance = $uibModalInstance;
//                                    $scope.title = title;
//                                    $scope.duration = duration;
//                                    $scope.task_id = task_id;
//                                    $scope.employee_id = employee_id;
//                                    $scope.start_date = start_date;
//                                    $scope.completion = completion;
//                                    $scope.completed = completed;
//                                    $scope.nb_products_completed = nb_products_completed;
//                                    $scope.completion_choices = completion_choices;
//                                    $scope.nb_products_choices = nb_products_choices;
//
//                                    $scope.cancel = function () {
//                                        $uibModalInstance.dismiss('cancel');
//                                    };
//
//                                    // ==================================================
//                                    // - create a new non working day
//                                    // ==================================================
//                                    $scope.create = function () {
//                                        
//                                        var duration_in_mins = xxxxxx($scope.duration);
//
//                                        var taskToCreate = {
//                                            task_id: $scope.task_id,
//                                            employee_id: $scope.employee_id,
//                                            start_date: $scope.start_date,
//                                            completion: 0,
//                                            nb_products_completed: 0,
//                                            completed: 0,
//                                            duration: duration_in_mins
//                                        };
//
//                                        // --------------------------------------------------------                                     
//                                        // $log.debug(CONTROLLER_NAME + " : taskToCreate = " + JSON.stringify(taskToCreate));
//                                        // --------------------------------------------------------
//
//                                        var taskPromise = tasksSrvc.createTaskAllocation(taskToCreate);
//                                        taskPromise.then(
//                                                function (response) {
//
////                                                    var updatedTask = response.task;
////
////                                                    // --------------------------------------------------------                                     
////                                                    // $log.debug(CONTROLLER_NAME + " : updatedTask = " + JSON.stringify(updatedTask));
////                                                    // --------------------------------------------------------
////
////                                                    // remove the event from the calendar's list of events 
////                                                    tasks = tasks.filter(function (task) {
////                                                        return task.id !== updatedTask.id;
////                                                    });
////
////                                                    // replace by the new updated holiday event
////                                                    tasks.push(updatedTask);
////
////                                                    // and rebuild the list
////                                                    buildEventList(nwds, holidays, tasks, getFilterProject(), getFilterEmployee());
////
////                                                    var message = 'Task : ' + title + ', was successfuly updated!';
////                                                    modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);
//
//                                                },
//                                                function (response) {
//                                                    // --------------------------------------------------------
//                                                    // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
//                                                    // --------------------------------------------------------
//
//                                                    var status = response.status;
//                                                    var message = response.statusText;
//                                                    modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
//                                                }
//                                        );
//
//                                        $uibModalInstance.dismiss('cancel');
//                                    };
//                                }
//                            });
//
//                        };

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
                                if (isProjectManger) {
                                    $scope.deleteHolidayEvent(calendarEvent);
                                }
                                else {
                                    var message = 'You do not have the rights to delete Holidays events.';
                                    modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                    return;
                                }
                            }
                            else if (isTask) {

                                if (isProjectManger) {
                                    $scope.deletateTask(calendarEvent);
                                }
                                else {
                                    var message = 'You do not have the rights to delete Task events.';
                                    modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                    return;
                                }
                            }

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
                                'end_date': calendarEvent.end_date,
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
                                            end_date: formatJS2MySQLDate(new Date($scope.event.end_date))
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
                                                    buildEventList(nwds, holidays, tasks);

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

                            // --------------------------------------------------------                                     
                            // $log.debug(CONTROLLER_NAME + " : calendarEvent = " + JSON.stringify(calendarEvent));
                            // --------------------------------------------------------

                            var project_nb_products = calendarEvent.project_nb_products;
                            var durationInMins = calendarEvent.duration;

                            var original_id = calendarEvent.original_id;
                            var task_id = calendarEvent.task_id;
                            var title = calendarEvent.original_title;
                            var duration = tasksSrvc.formatDuration(durationInMins);
                            var completion = tasksSrvc.formatCompletion(calendarEvent.completion);
                            var completed = calendarEvent.completed;
                            var employee_id = calendarEvent.employee_id;
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
                                                    $log.debug(CONTROLLER_NAME + " : updatedTask = " + JSON.stringify(updatedTask));
                                                    // --------------------------------------------------------

                                                    // remove the event from the calendar's list of events 
                                                    tasks = tasks.filter(function (task) {
                                                        return task.id !== updatedTask.id;
                                                    });

                                                    // replace by the new updated holiday event
                                                    tasks.push(updatedTask);

                                                    // and rebuild the list
                                                    buildEventList(nwds, holidays, tasks);

                                                    var title = updatedTask.title;
                                                    var message = 'Task : ' + title + ', was successfuly updated!';
                                                    modalSrvc.showSuccessMessageModal2(CONTROLLER_NAME, message);

                                                },
                                                function (response) {
                                                    // --------------------------------------------------------
                                                    // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                                    // --------------------------------------------------------

                                                    var status = response.status;
                                                    // var message = response.statusText;
                                                    var message = response.data.message;
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
                                'end_date': calendarEvent.end_date
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

                                                    buildEventList(nwds, holidays, tasks);

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
                        $scope.deletateTask = function (calendarEvent) {

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

                                                    buildEventList(nwds, holidays, tasks);

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
                        // - dump the calendar's event list
                        // - for debugging purpose
                        // ==================================================
                        function dumpEventList(key) {

                            // --------------------------------------------------------    
                            $log.debug(CONTROLLER_NAME + " : === $scope.events ============================= ");

                            angular.forEach($scope.events, function (event, nb) {

                                switch (key) {
                                    case 'holidays' :
                                        if (event.type === holidays_type) {
                                            $log.debug(CONTROLLER_NAME + " : ==== $scope.events[" + nb + "] = " + JSON.stringify(event));
                                        }
                                        break;
                                    case 'task' :
                                        if (event.type === task_type) {
                                            $log.debug(CONTROLLER_NAME + " : === $scope.events[" + nb + "] = " + JSON.stringify(event));
                                        }
                                        break;
                                    case 'non-working' :
                                        if (event.type === non_working_type) {
                                            $log.debug(CONTROLLER_NAME + " : === $scope.events[" + nb + "] = " + JSON.stringify(event));
                                        }
                                        break;
                                }
                            });

                            $log.debug(CONTROLLER_NAME + " : =============================================== ");
                            // --------------------------------------------------------  
                        }

                        // ==================================================
                        // - build the list of events for display 
                        // ==================================================
                        function buildEventList(nwds, holidays, tasks) {

                            $scope.events = null;
                            var newEvents = [];
                            event_id_water_mark = 0;

                            addNonWorkingDaysEvent(newEvents, nwds);
                            addHolidaysEvent(newEvents, holidays);
                            addTasksEvent(newEvents, tasks);

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

                                var startsAt = tasksSrvc.getNwdStartsAt(
                                        formatMySQL2JSDate(nwds[index].date));

                                var endsAt = tasksSrvc.getNwdEndsAt(
                                        formatMySQL2JSDate(nwds[index].date));

                                var date = nwds[index].date;

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
                        function addHolidaysEvent(events, holidays) {

                            var index = 0;

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : holidays = " + JSON.stringify(holidays));
                            // --------------------------------------------------------

                            for (index = 0; index < holidays.length; index++) {

                                var id = holidays[index].id;
                                var title = holidays[index].title;
                                var employee_id = holidays[index].employee_id;
                                var start_date = holidays[index].start_date;
                                var end_date = holidays[index].end_date;
                                var startsAt = tasksSrvc.getHolidaysStartsAt(new Date(holidays[index].start_date));
                                var endsAt = tasksSrvc.getHolidaysEndsAt(new Date(holidays[index].end_date));

                                var event = {
                                    'id': event_id_water_mark,
                                    'title': title,
                                    'type': holidays_type,
                                    'startsAt': startsAt,
                                    'endsAt': endsAt,
                                    'draggable': false,
                                    'resizable': true,
                                    'original_id': id,
                                    'employee_id': employee_id,
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
                        function addTasksEvent(events, tasks) {

                            var index = 0;

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : tasks = " + JSON.stringify(tasks));
                            // --------------------------------------------------------

                            for (index = 0; index < tasks.length; index++) {

                                var id = tasks[index].id;
                                var original_title = tasks[index].title;
                                var employee_id = tasks[index].employee_id;
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

                                var title = '<span class="text-primary">';
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
                ]);

