
'use strict';

angular.module('profileHolidays', ['mwl.calendar', 'ui.bootstrap', 'ngTouch', 'ngAnimate'])
        .controller('profileHolidaysCtrl',
                [
                    '$log',
                    "$scope",
                    "$uibModal",
                    "modalSrvc",
                    'profileSrvc',
                    'pageSrvc',
                    'employeesSrvc',
                    'holidaysSrvc',
                    'nonWorkingDaysSrvc',
                    function (
                            $log,
                            $scope,
                            $uibModal,
                            modalSrvc,
                            profileSrvc,
                            pageSrvc,
                            employeesSrvc,
                            holidaysSrvc,
                            nonWorkingDaysSrvc
                            ) {

                        var CONTROLLER_NAME = 'profileHolidaysCtrl';

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
                            buildEventList(nwds, holidays);

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
                                        buildEventList(nwds, holidays);

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
                        // - when user click the create holiday button
                        // ==================================================
                        $scope.createHoliday = function () {

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

                            var data_hour_step = 1;
                            var data_minute_step = 30;

                            var employee_id = employee.id;

                            $uibModal.open({
                                templateUrl: 'taskflow/fragments/modal_holiday_create',
                                controller: function ($scope, $uibModalInstance) {
                                    $scope.$modalInstance = $uibModalInstance;
                                    $scope.min_date = min_date;
                                    $scope.max_date = max_date;
                                    $scope.data_hour_step = data_hour_step;
                                    $scope.data_minute_step = data_minute_step;
                                    $scope.title = null;
                                    $scope.employee_id = employee_id;
                                    $scope.start_date = null;
                                    $scope.end_date = null;

                                    $scope.cancel = function () {
                                        $uibModalInstance.dismiss('cancel');
                                    };

                                    $scope.save = function () {

                                        var valid = true;
                                        var message = null;

                                        // check the title
                                        // --------------------------------------------------------
                                        $log.debug(CONTROLLER_NAME + " : title = " + $scope.title);
                                        // --------------------------------------------------------
                                        if ($scope.title === undefined || $scope.title === null) {
                                            valid = false;
                                            message = "You must select a Title.";
                                        }

                                        // check the start date
                                        // --------------------------------------------------------
                                        $log.debug(CONTROLLER_NAME + " : start_date = " + $scope.start_date);
                                        // --------------------------------------------------------
                                        if ($scope.start_date === undefined || $scope.start_date === null) {
                                            valid = false;
                                            message = "Start Date must be defined.";
                                        }

                                        // check the end date
                                        // --------------------------------------------------------
                                        $log.debug(CONTROLLER_NAME + " : end_date = " + $scope.end_date);
                                        // --------------------------------------------------------
                                        if ($scope.end_date === undefined || $scope.end_date === null) {
                                            valid = false;
                                            message = "End Date must be defined.";
                                        }

                                        if (valid === false) {
                                            modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                        }
                                        else {
                                            var title = $scope.title;
                                            var employee_id = $scope.employee_id;
                                            var start_date = formatJS2MySQLDate(new Date($scope.start_date));
                                            var end_date = formatJS2MySQLDate(new Date($scope.end_date));

                                            var newHoliday = {
                                                'title': title,
                                                'employee_id': employee_id,
                                                'start_date': start_date,
                                                'end_date': end_date
                                            };

                                            var holidayPromise = holidaysSrvc.createHoliday(newHoliday);
                                            holidayPromise.then(
                                                    function (response) {

                                                        var createdHoliday = response.holiday;

                                                        // --------------------------------------------------------
                                                        $log.debug(CONTROLLER_NAME + " : createdHoliday = " + JSON.stringify(createdHoliday));
                                                        // --------------------------------------------------------

                                                        // add the new updated holiday event
                                                        holidays.push(createdHoliday);

                                                        // and rebuild the list
                                                        buildEventList(nwds, holidays);

                                                        var title = createdHoliday.title;
                                                        var message = 'Holiday : ' + title + ', was successfuly created!';
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
                                        }
                                        $uibModalInstance.dismiss('cancel');
                                    };
                                }
                            });
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

                            if (isProjectManger) {
                                $scope.deleteHolidayEvent(calendarEvent);
                            }
                            else {
                                var message = 'You do not have the rights to delete Holidays events.';
                                modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                return;
                            }
                        };

                        // ==================================================
                        // - 
                        // ==================================================
                        $scope.deleteHolidayEvent = function (calendarEvent) {

                            // --------------------------------------------------------
                            $log.debug(CONTROLLER_NAME + " : calendarEvent = " + JSON.stringify(calendarEvent));
                            // --------------------------------------------------------

                            var title = calendarEvent.title;
                            var original_id = calendarEvent.original_id;
                            var start_date = calendarEvent.start_date;
                            var end_date = calendarEvent.end_date;
                            var employee_id = calendarEvent.employee_id;

                            $uibModal.open({
                                templateUrl: 'taskflow/fragments/modal_holiday_delete',
                                placement: 'center',
                                controller: function ($scope, $uibModalInstance) {
                                    $scope.$modalInstance = $uibModalInstance;
                                    $scope.title = title;
                                    $scope.employee_id = employee_id;
                                    $scope.start_date = start_date;
                                    $scope.end_date = end_date;
                                    $scope.original_id = original_id;

                                    $scope.cancel = function () {
                                        $uibModalInstance.dismiss('cancel');
                                    };

                                    $scope.delete = function () {

                                        var title = $scope.title;
                                        var original_id = $scope.original_id;

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

                                                    buildEventList(nwds, holidays);

                                                    var message = 'Holiday : ' + title + ', was successfuly deleted!';
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
                        // - build the list of events for display 
                        // ==================================================
                        function buildEventList(nwds, holidays) {

                            $scope.events = null;
                            var newEvents = [];
                            event_id_water_mark = 0;

                            addNonWorkingDaysEvent(newEvents, nwds);
                            addHolidaysEvent(newEvents, holidays);

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

                                var startsAt = nonWorkingDaysSrvc.getNwdStartsAt(
                                        formatMySQL2JSDate(nwds[index].date));

                                var endsAt = nonWorkingDaysSrvc.getNwdEndsAt(
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

                                var startsAt = holidaysSrvc.getHolidaysStartsAt(
                                        new Date(holidays[index].start_date));

                                var endsAt = holidaysSrvc.getHolidaysEndsAt(
                                        new Date(holidays[index].end_date));

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

                    }
                ]);

