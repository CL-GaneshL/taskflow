'use strict';

app.factory("taskAllocationSrvc", function ($log, $http) {

    var FACTORY_NAME = 'taskAllocationSrvc';

    // --------------------------------------------------------
    // - allocate tasks
    // --------------------------------------------------------
    var allocate = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/allocate/'
                }
        ).then(function (response) {

            return {
                status: response.data.status
            };
        });
    };

    // --------------------------------------------------------
    // - test tasks
    // --------------------------------------------------------
    var test = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/allocate/test/'
                }
        ).then(function (response) {

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response = " + JSON.stringify(response));
            // --------------------------------------------------------

            var logs = response.data.data;

            return {
                logs: logs
            };
        });
    };

    // --------------------------------------------------------
    // - reset allocation
    // --------------------------------------------------------
    var reset = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/allocate/reset/'
                }
        ).then(function (response) {

            return {
                status: response.data.status
            };
        });
    };

    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------

    return {
        allocate: allocate,
        test: test,
        reset: reset
    };
    // ==================================================
});