
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

                        } else {
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
                                var message = 'Cannot delete Employee\'s Holidays from this tab.';
                                modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                return;
                            }

                            if (isTask) {

                                if (isProjectManger) {
                                    $scope.deleteTask(calendarEvent);
                                } else {
                                    var message = 'You do not have the rights to delete Tasks.';
                                    modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                    return;
                                }
                            }

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
                                    $scope.nb_products_planned= nb_products_planned;
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
                                            nb_products_planned: $scope.nb_products_planned,
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

                                var title = '<span class="text-primary">';
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
                    }
                ]);

