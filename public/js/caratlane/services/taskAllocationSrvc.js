'use strict';
/**
 * Allows communication beetween Login and profile modals.
 * 
 */

app.factory("taskAllocationSrvc", function ($log, $http) {

    var FACTORY_NAME = 'taskAllocationSrvc';

    // --------------------------------------------------------
    // - allocate tasks
    // --------------------------------------------------------
    var allocate = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/allocate'
                }
        ).then(function (response) {

            return {
                xxxxx: response.data.xxxxxx
            };
        });
    };

    // --------------------------------------------------------
    // - reset allocation
    // --------------------------------------------------------
    var reset = function () {

        return $http(
                {
                    method: "POST",
                    url: '/taskflow/apis/v1/reset'
                }
        ).then(function (response) {

            return {
                xxxxx: response.data.xxxxxx
            };
        });
    };

    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------

    return {
        allocate: allocate,
        reset: reset
    };
    // ==================================================
});