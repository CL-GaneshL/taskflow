'use strict';

app.factory("holidaysSrvc", function ($log, $http) {

    var FACTORY_NAME = 'holidaysSrvc';

    // --------------------------------------------------------
    // - Create a holiday record in the db.
    // --------------------------------------------------------
    var createHoliday = function (newHoliday) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : newHoliday = " + JSON.stringify(newHoliday));
        // --------------------------------------------------------

        return $http(
                {
                    method: "POST",
                    url: '/taskflow/apis/v1/holidays/',
                    data: {
                        name: newHoliday.name,
                        employee_id: newHoliday.employee_id,
                        start: newHoliday.start,
                        duration: newHoliday.duration
                    }
                }

        ).then(function (response) {

            var holiday = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response holiday = " + JSON.stringify(holiday));
            // --------------------------------------------------------

            return {
                holiday: holiday
            };
        });
    };

    // --------------------------------------------------------
    // - delete a holiday record from the db.
    // --------------------------------------------------------
    var deleteHoliday = function (id) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : id = " + JSON.stringify(id));
        // --------------------------------------------------------

        return $http(
                {
                    method: "DELETE",
                    url: '/taskflow/apis/v1/holidays/' + id
                }
        );
    };

    // --------------------------------------------------------
    // - Update a holiday record in the db.
    // --------------------------------------------------------
    var updateHoliday = function (toUpdateHoliday) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : toUpdateHoliday = " + JSON.stringify(toUpdateHoliday));
        // --------------------------------------------------------

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/holidays/' + toUpdateHoliday.id,
                    data: {
                        id: toUpdateHoliday.id,
                        title: toUpdateHoliday.title,
                        employee_id: toUpdateHoliday.employee_id,
                        start_date: toUpdateHoliday.start_date,
                        start_morning_shift: toUpdateHoliday.start_morning_shift,
                        start_afternoon_shift: toUpdateHoliday.start_afternoon_shift,
                        end_date: toUpdateHoliday.end_date,
                        end_morning_shift: toUpdateHoliday.end_morning_shift,
                        end_afternoon_shift: toUpdateHoliday.end_afternoon_shift
                    }
                }

        ).then(function (response) {

            var holiday = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response holiday = " + JSON.stringify(holiday));
            // --------------------------------------------------------

            return {
                holiday: holiday
            };
        });
    };


    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------
    return {
        createHoliday: createHoliday,
        deleteHoliday: deleteHoliday,
        updateHoliday: updateHoliday
    };

    // ==================================================
});