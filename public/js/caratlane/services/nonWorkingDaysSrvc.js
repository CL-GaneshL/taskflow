'use strict';

/**
 * Allows communication beetween Teams module modals.
 * 
 */

app.factory("nonWorkingDaysSrvc", function ($log, $http) {

    var FACTORY_NAME = 'nonWorkingDaysSrvc';

    // --------------------------------------------------------
    // - get the list of all non working days 
    // - ( 1 year before - 1 yeay after today )
    // --------------------------------------------------------
    var getNonWorkingDays = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/nonworkingdays/'
                }

        ).then(function (response) {

            var non_working_days = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response non_working_days = " + JSON.stringify(non_working_days));
            // --------------------------------------------------------

            return {
                non_working_days: non_working_days
            };
        });
    };

    // --------------------------------------------------------
    // - Create a non-working day record in the db.
    // --------------------------------------------------------
    var createNonWorkingDay = function (newNonWorkingDay) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : newNonWorkingDay = " + JSON.stringify(newNonWorkingDay));
        // --------------------------------------------------------

        return $http(
                {
                    method: "POST",
                    url: '/taskflow/apis/v1/nonworkingdays/',
                    data: {
                        title: newNonWorkingDay.title,
                        type: newNonWorkingDay.type,
                        date: newNonWorkingDay.date
                    }
                }

        ).then(function (response) {

            var non_working_day = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response non_working_day = " + JSON.stringify(non_working_day));
            // --------------------------------------------------------

            return {
                non_working_day: non_working_day
            };
        });
    };

    // --------------------------------------------------------
    // - delete a non-working day record from the db.
    // --------------------------------------------------------
    var deleteNonWorkingDay = function (id) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : id = " + JSON.stringify(id));
        // --------------------------------------------------------

        return $http(
                {
                    method: "DELETE",
                    url: '/taskflow/apis/v1/nonworkingdays/' + id
                }
        );
    };

// --------------------------------------------------------
    // - Update a non-working day record in the db.
    // --------------------------------------------------------
    var updateNonWorkingDay = function (toUpdateNonWorkingDay) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : newNonWorkingDay = " + JSON.stringify(toUpdateNonWorkingDay));
        // --------------

        var id = toUpdateNonWorkingDay.id;

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/nonworkingdays/' + id,
                    data: {
                        title: toUpdateNonWorkingDay.title,
                        type: toUpdateNonWorkingDay.type,
                        date: toUpdateNonWorkingDay.date
                    }
                }

        ).then(function (response) {

            var non_working_day = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response non_working_day = " + JSON.stringify(non_working_day));
            // --------------------------------------------------------

            return {
                non_working_day: non_working_day
            };
        });
    };

    // ==================================================
    // - 
    // ==================================================
    function getNwdStartsAt(start_date) {
        return new Date(start_date);
    }

    // ==================================================
    // - 
    // ==================================================
    function getNwdEndsAt(end_date) {
        return new Date(end_date);
    }

    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------
    return {
        getNonWorkingDays: getNonWorkingDays,
        createNonWorkingDay: createNonWorkingDay,
        deleteNonWorkingDay: deleteNonWorkingDay,
        updateNonWorkingDay: updateNonWorkingDay,
        getNwdStartsAt: getNwdStartsAt,
        getNwdEndsAt: getNwdEndsAt
    };

    // ==================================================
});