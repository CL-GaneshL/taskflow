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
                        title: newHoliday.title,
                        employee_id: newHoliday.employee_id,
                        start_date: newHoliday.start_date,
                        end_date: newHoliday.end_date
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
                        end_date: toUpdateHoliday.end_date
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

    // ==================================================
    // - 
    // ==================================================
    function getHolidaysStartsAt(start_date) {
        return new Date(start_date);
    }

    // ==================================================
    // - 
    // ==================================================
    function getHolidaysEndsAt(end_date) {
        var end_date_at_midnight = new Date(end_date);
        end_date_at_midnight.setMinutes(23 * 60 + 59);

        return end_date_at_midnight;
    }

    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------
    return {
        createHoliday: createHoliday,
        deleteHoliday: deleteHoliday,
        updateHoliday: updateHoliday,
        getHolidaysStartsAt: getHolidaysStartsAt,
        getHolidaysEndsAt: getHolidaysEndsAt
    };

    // ==================================================
});