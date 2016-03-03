
'use strict';

angular.module('calendar', ['mwl.calendar', 'ui.bootstrap', 'ngTouch', 'ngAnimate'])

        .controller('nonWorkingDaysCtrl',
                [
                    '$log',
                    "$scope",
                    "$uibModal",
                    "nonWorkingDaysSrvc",
                    "modalSrvc",
                    function ($log, $scope, $uibModal, nonWorkingDaysSrvc, modalSrvc) {

                        var CONTROLLER_NAME = 'nonWorkingDaysCtrl';

                        // caratlane 1 shift starting time : 6am
                        var CARATLANE_START_ACTIVITY_TIME = 6;
                        // caratlane 2nd shift ending time : 10pm
                        var CARATLANE_END_ACTIVITY_TIME = 22;
                        // caratlane mid shif time : 2pm
                        var CARATLANE_MID_SHIFT_TIME = 14;

                        var weekend_class = 'off-site-work';
                        var non_working_class = 'to-do';

                        // ==================================================
                        // - initialize the calendar
                        // ==================================================
                        $scope.calendarView = 'month';
                        $scope.calendarDay = new Date();

                        // ==================================================
                        // - initialize the events
                        // ==================================================
                        var index = 0;
                        $scope.events = [];

                        // ---------------------------------------------------
                        // - get the list of all non working days 
                        // - ( 1 year before - 1 yeay after today )
                        // ---------------------------------------------------
                        var nwdPromise = nonWorkingDaysSrvc.getNonWorkingDays();
                        nwdPromise.then(
                                function (response) {

                                    var nwd = response.non_working_days;

                                    // --------------------------------------------------------
                                    // $log.debug(CONTROLLER_NAME + " : nwd = " + JSON.stringify(nwd));
                                    // --------------------------------------------------------

                                    for (index = 0; index < nwd.length; index++) {

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : nwd[index] = " + JSON.stringify(nwd[index]));
                                        // --------------------------------------------------------

                                        var id = nwd[index].id;
                                        var title = nwd[index].title;

                                        var type = null;
                                        switch (nwd[index].type) {
                                            case 'WEEKEND':
                                                type = weekend_class;
                                                break;
                                            case 'NON-WORKING':
                                                type = non_working_class;
                                                break;
                                        }

                                        var startsAt = getStartsAt(
                                                formatMySQL2JSDate(nwd[index].date),
                                                formatMySQL2Boolean(nwd[index].morning_shift),
                                                formatMySQL2Boolean(nwd[index].afternoon_shift)
                                                );

                                        var endsAt = getEndsAt(
                                                formatMySQL2JSDate(nwd[index].date),
                                                formatMySQL2Boolean(nwd[index].morning_shift),
                                                formatMySQL2Boolean(nwd[index].afternoon_shift)
                                                );

                                        var date = nwd[index].date;
                                        var morning_shift = formatMySQL2Boolean(nwd[index].morning_shift);
                                        var afternoon_shift = formatMySQL2Boolean(nwd[index].afternoon_shift);

                                        addEvent(
                                                {
                                                    'id': id,
                                                    'title': title,
                                                    'type': type,
                                                    'startsAt': startsAt,
                                                    'endsAt': endsAt,
                                                    'draggable': false,
                                                    'resizable': true,
                                                    'date': date,
                                                    'morning_shift': morning_shift,
                                                    'afternoon_shift': afternoon_shift
                                                }
                                        );
                                    }
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
                        // ==================================================
                        // - when user click on the event's edit icon
                        // ==================================================
                        $scope.editNonWorkingDay = function (calendarEvent) {

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
                                'type': 'NON-WORKING',
                                'date': calendarEvent.date,
                                'morning_shift': calendarEvent.morning_shift,
                                'afternoon_shift': calendarEvent.afternoon_shift,
                                'startsAt': calendarEvent.startsAt,
                                'endsAt': calendarEvent.endsAt,
                                'min_date': min_date,
                                'max_date': max_date
                            };

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : toEditEvent = " + JSON.stringify(toEditEvent));
                            // --------------------------------------------------------

                            // ==================================================
                            // - edit event modal
                            // ==================================================
                            $uibModal.open({
                                templateUrl: 'taskflow/fragments/projects_non_working_day_edit_nwd',
                                controller: function ($scope, $uibModalInstance) {
                                    $scope.$modalInstance = $uibModalInstance;
                                    $scope.event = toEditEvent;

                                    $scope.cancel = function () {
                                        $uibModalInstance.dismiss('cancel');
                                    };

                                    $scope.update = function () {

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : $scope.event = " + JSON.stringify($scope.event));
                                        // --------------------------------------------------------

                                        var toUpdateNonWorkingDay = {
                                            'id': $scope.event.id,
                                            'title': $scope.event.title,
                                            'type': $scope.event.type,
                                            'date': formatJS2MySQLDate(new Date($scope.event.date)),
                                            'morning_shift': formatBoolean2MySQL($scope.event.morning_shift),
                                            'afternoon_shift': formatBoolean2MySQL($scope.event.afternoon_shift)
                                        };

                                        if ($scope.validNonWorkingDayEvent(toUpdateNonWorkingDay) === false) {
                                            $uibModalInstance.dismiss('cancel');
                                            return;
                                        }

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : toUpdateNonWorkingDay = " + JSON.stringify(toUpdateNonWorkingDay));
                                        // --------------------------------------------------------

                                        var nwdPromise = nonWorkingDaysSrvc.updateNonWorkingDay(toUpdateNonWorkingDay);
                                        nwdPromise.then(
                                                function (response) {

                                                    // --------------------------------------------------------
                                                    // $log.debug(CONTROLLER_NAME + " : response = " + JSON.stringify(response));
                                                    // --------------------------------------------------------

                                                    var non_working_day = response.non_working_day;

                                                    var id = non_working_day.id;
                                                    var title = non_working_day.title;

                                                    var type = null;
                                                    switch (non_working_day.type) {
                                                        case 'WEEKEND':
                                                            type = weekend_class;
                                                            break;
                                                        case 'NON-WORKING':
                                                            type = non_working_class;
                                                            break;
                                                    }

                                                    var startsAt = getStartsAt(
                                                            formatMySQL2JSDate(non_working_day.date),
                                                            formatMySQL2Boolean(non_working_day.morning_shift),
                                                            formatMySQL2Boolean(non_working_day.afternoon_shift)
                                                            );

                                                    var endsAt = getEndsAt(
                                                            formatMySQL2JSDate(non_working_day.date),
                                                            formatMySQL2Boolean(non_working_day.morning_shift),
                                                            formatMySQL2Boolean(non_working_day.afternoon_shift)
                                                            );

                                                    var date = non_working_day.date;
                                                    var morning_shift = formatMySQL2Boolean(non_working_day.morning_shift);
                                                    var afternoon_shift = formatMySQL2Boolean(non_working_day.afternoon_shift);


                                                    // remove first from the calendar
                                                    removeEvent(id);

                                                    // replace with the update one
                                                    addEvent({
                                                        'id': id,
                                                        'title': title,
                                                        'type': type,
                                                        'startsAt': startsAt,
                                                        'endsAt': endsAt,
                                                        'draggable': false,
                                                        'resizable': true,
                                                        'date': date,
                                                        'morning_shift': morning_shift,
                                                        'afternoon_shift': afternoon_shift
                                                    });

                                                    var name = non_working_day.name;
                                                    var message = 'Non-Working Day : ' + name + ', was successfuly updated!';
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
                                    // - valid a Non Working Day event
                                    // ================================================== 
                                    $scope.validNonWorkingDayEvent = function (event) {

                                        // -------------------------------------------------
                                        // - the name must be defined properly
                                        // -------------------------------------------------
                                        var title = event.title;
                                        if (title === null || title === undefined || title.length === 0) {
                                            var message = "The Tile field must be defined.";
                                            modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                            return false;
                                        }

                                        // -------------------------------------------------
                                        // - date must be greater than today's date
                                        // -------------------------------------------------
                                        // tomorrow at 0am
                                        var tomorrow = new Date();
                                        tomorrow.setHours(24, 0, 0, 0);
                                        if ((new Date(event.date)).getTime() < tomorrow.getTime()) {
                                            var message = "Only Non-Working Days in the future can be modified.";
                                            modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                            return false;
                                        }

                                        // -------------------------------------------------
                                        // - morning and afternoon shifts
                                        // -------------------------------------------------
                                        if (event.morning_shift === false & event.afternoon_shift) {
                                            var message = "A morning and/or an afternoon shift must be selected.";
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
                        // - when user click on the event's delete icon
                        // ==================================================
                        $scope.deleteNonWorkingDay = function (calendarEvent) {

                            var type = null;
                            switch (calendarEvent.type) {
                                case weekend_class:
                                    type = 'WEEKEND';
                                    break;
                                case non_working_class:
                                    type = 'NON-WORKING';
                                    break;
                            }

                            var toDeleteEvent = {
                                'id': calendarEvent.id,
                                'title': calendarEvent.title,
                                'type': type,
                                'date': calendarEvent.date,
                                'morning_shift': calendarEvent.morning_shift,
                                'afternoon_shift': calendarEvent.afternoon_shift
                            };

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : toDeleteEvent = " + JSON.stringify(toDeleteEvent));
                            // --------------------------------------------------------

                            // pre check before opening the nodal
                            if (!validPreDeleteWorkingDayEvent(toDeleteEvent)) {
                                return;
                            }

                            $uibModal.open({
                                templateUrl: 'taskflow/fragments/projects_non_working_day_delete_nwd',
                                placement: 'center',
                                controller: function ($scope, $uibModalInstance) {
                                    $scope.$modalInstance = $uibModalInstance;
                                    $scope.event = toDeleteEvent;

                                    $scope.cancel = function () {
                                        $uibModalInstance.dismiss('cancel');
                                    };

                                    $scope.delete = function () {

                                        var id = $scope.event.id;
                                        var name = $scope.event.title;

                                        var nwdPromise = nonWorkingDaysSrvc.deleteNonWorkingDay(id);
                                        nwdPromise.then(
                                                function (response) {

                                                    // --------------------------------------------------------
                                                    // $log.debug(CONTROLLER_NAME + " : response = " + JSON.stringify(response));
                                                    // --------------------------------------------------------

                                                    removeEvent(id);

                                                    var message = 'Non-Working Day ' + name + ', was successfuly deleted!';
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


                            // ==================================================
                            // - valid the deletion of a Non Working Day event
                            // ================================================== 
                            function validPreDeleteWorkingDayEvent(event) {

                                // -------------------------------------------------
                                // - date must be greater than today's date
                                // -------------------------------------------------
                                // tomorrow at 0am
                                var tomorrow = new Date();
                                tomorrow.setHours(24, 0, 0, 0);
                                if ((new Date(event.date)).getTime() < tomorrow.getTime()) {
                                    var message = "Only Non_Working Days in the future can be deleted.";
                                    modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                    return false;
                                }

                                return true;
                            }

                        };

                        // ==================================================
                        // - non working day modal 
                        // ==================================================
                        $scope.addNonWorkingDay = function () {

                            // --------------------------------------------------------
                            // - initialization of the data
                            // --------------------------------------------------------

                            // tomorrow at 0am
                            var tomorrow = new Date();
                            tomorrow.setHours(24, 0, 0, 0); // next midnignt

                            // the day after tomorrow at 0am
                            var tmrwPlusOneDay = new Date();
                            tmrwPlusOneDay.setHours(2 * 24, 0, 0, 0); // next midnignt

                            var startsAt = tomorrow;
                            var endsAt = tmrwPlusOneDay;

                            // allowes date picker from min_date = now 
                            // to max_date one year ahead
                            var min_date = tomorrow;
                            var max_date = new Date(tomorrow);
                            max_date.setDate(max_date.getDate() + 365);

                            var newEvent = {
                                'title': null,
                                'type': non_working_class,
                                'date': tomorrow,
                                'morning_shift': false,
                                'afternoon_shift': false,
                                'startsAt': startsAt,
                                'endsAt': endsAt,
                                'min_date': min_date,
                                'max_date': max_date
                            };

                            $uibModal.open({
                                templateUrl: 'taskflow/fragments/projects_non_working_day_new_nwd',
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

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : $scope.event = " + JSON.stringify($scope.event));
                                        // --------------------------------------------------------

                                        var type = null;
                                        switch ($scope.event.type) {
                                            case weekend_class:
                                                type = 'WEEKEND';
                                                break;
                                            case non_working_class:
                                                type = 'NON-WORKING';
                                                break;
                                        }

                                        var newNonWorkingDay = {
                                            'id': $scope.event.id,
                                            'title': $scope.event.title,
                                            'type': type,
                                            'date': formatJS2MySQLDate(new Date($scope.event.date)),
                                            'morning_shift': formatBoolean2MySQL($scope.event.morning_shift),
                                            'afternoon_shift': formatBoolean2MySQL($scope.event.afternoon_shift)
                                        };

                                        if ($scope.validNonWorkingDayEvent(newNonWorkingDay) === false) {
                                            $uibModalInstance.dismiss('cancel');
                                            return;
                                        }

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : newNonWorkingDay = " + JSON.stringify(newNonWorkingDay));
                                        // --------------------------------------------------------

                                        var nwdPromise = nonWorkingDaysSrvc.createNonWorkingDay(newNonWorkingDay);
                                        nwdPromise.then(
                                                function (response) {

                                                    // --------------------------------------------------------
                                                    // $log.debug(CONTROLLER_NAME + " : response = " + JSON.stringify(response));
                                                    // --------------------------------------------------------

                                                    var non_working_day = response.non_working_day;

                                                    // --------------------------------------------------------
                                                    // $log.debug(CONTROLLER_NAME + " : non_working_day = " + JSON.stringify(non_working_day));
                                                    // --------------------------------------------------------

                                                    var id = non_working_day.id;
                                                    var title = non_working_day.title;

                                                    var type = null;
                                                    switch (non_working_day.type) {
                                                        case 'WEEKEND':
                                                            type = weekend_class;
                                                            break;
                                                        case 'NON-WORKING':
                                                            type = non_working_class;
                                                            break;
                                                    }

                                                    var startsAt = getStartsAt(
                                                            formatMySQL2JSDate(non_working_day.date),
                                                            formatMySQL2Boolean(non_working_day.morning_shift),
                                                            formatMySQL2Boolean(non_working_day.afternoon_shift)
                                                            );

                                                    var endsAt = getEndsAt(
                                                            formatMySQL2JSDate(non_working_day.date),
                                                            formatMySQL2Boolean(non_working_day.morning_shift),
                                                            formatMySQL2Boolean(non_working_day.afternoon_shift)
                                                            );

                                                    var date = non_working_day.date;
                                                    var morning_shift = formatMySQL2Boolean(non_working_day.morning_shift);
                                                    var afternoon_shift = formatMySQL2Boolean(non_working_day.afternoon_shift);

                                                    // replace with the update one
                                                    addEvent({
                                                        'id': id,
                                                        'title': title,
                                                        'type': type,
                                                        'startsAt': startsAt,
                                                        'endsAt': endsAt,
                                                        'draggable': false,
                                                        'resizable': true,
                                                        'date': date,
                                                        'morning_shift': morning_shift,
                                                        'afternoon_shift': afternoon_shift
                                                    });

                                                    var title = non_working_day.title;
                                                    var message = 'Non-Working Day : ' + title + ', was successfuly created!';
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
                                    // - valid a Non Working Day event
                                    // ================================================== 
                                    $scope.validNonWorkingDayEvent = function (event) {

                                        // -------------------------------------------------
                                        // - the name must be defined properly
                                        // -------------------------------------------------
                                        var title = event.title;
                                        if (title === null || title === undefined || title.length === 0) {
                                            var message = "The Title field must be defined.";
                                            modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                            return false;
                                        }

                                        // -------------------------------------------------
                                        // - date must be greater than today's date
                                        // -------------------------------------------------
                                        // tomorrow at 0am
                                        var tomorrow = new Date();
                                        tomorrow.setHours(24, 0, 0, 0);
                                        if ((new Date(event.date)).getTime() < tomorrow.getTime()) {
                                            var message = "Only Non-Working Days in the future can be created.";
                                            modalSrvc.showInformationMessageModal2(CONTROLLER_NAME, message);
                                            return false;
                                        }

                                        // -------------------------------------------------
                                        // - morning and afternoon shifts
                                        // -------------------------------------------------
                                        if (event.morning_shift === false & event.afternoon_shift) {
                                            var message = "A morning and/or an afternoon shift must be selected.";
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
                        $scope.toggle = function ($event, field, event) {

                            $event.preventDefault();
                            $event.stopPropagation();
                            event[field] = !event[field];
                        };

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
                        // - get a MySQL datatime from a JS Date
                        // ==================================================
                        function  formatJS2MySQLDate(js_date) {

                            var year = js_date.getFullYear();
                            var month = js_date.getMonth() + 1;
                            var day = js_date.getDate();
                            var hours = js_date.getHours();
                            var mins = js_date.getMinutes();
                            var secs = js_date.getSeconds();

                            return year + '-' + month + '-' + day + ' ' + hours + ':' + mins + ':' + secs;
                        }

                        // ==================================================
                        // - get the endsAt time for the calendar
                        // - this assumes that 
                        // -    - morning_shift or afternoon_shift is true
                        // -    - both are true
                        // ==================================================
                        function getStartsAt(date, morning_shift, afternoon_shift) {

                            var startDate = new Date(date);

                            if (afternoon_shift === true) {
                                startDate.setHours(CARATLANE_MID_SHIFT_TIME, 0, 0, 0);
                            }

                            if (morning_shift === true) {
                                startDate.setHours(CARATLANE_START_ACTIVITY_TIME, 0, 0, 0);
                            }

                            return startDate;
                        }

                        // ==================================================
                        // - get the endsAt time for the calendar
                        // - this assumes that 
                        // -    - morning_shift or afternoon_shift is true
                        // -    - both are true
                        // ==================================================
                        function getEndsAt(date, morning_shift, afternoon_shift) {

                            //default value
                            var endDate = new Date(date);

                            if (morning_shift === true) {
                                endDate.setHours(CARATLANE_MID_SHIFT_TIME, 0, 0, 0);
                            }

                            if (afternoon_shift === true) {
                                endDate.setHours(CARATLANE_END_ACTIVITY_TIME, 0, 0, 0);
                            }

                            return endDate;
                        }

                        // ==================================================
                        // - remove a non working day event from the calendar
                        // ==================================================
                        function removeEvent(id) {
                            $scope.events = $scope.events.filter(function (nwd) {
                                return nwd.id !== id;
                            });
                        }

                        // ==================================================
                        // - add a non working day event to the calendar
                        // ==================================================
                        function addEvent(event) {
                            $scope.events.push(event);
                        }

                    }
                ]);

