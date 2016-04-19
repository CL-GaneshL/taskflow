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
    // - 
    // --------------------------------------------------------
//    var getTaskflowConfiguration = function () {
//
//        return $http(
//                {
//                    method: "GET",
//                    url: '/taskflow/apis/v1/allocate/taskflow-configuration/'
//                }
//        ).then(function (response) {
//
//            // --------------------------------------------------------
//            $log.debug(FACTORY_NAME + " : response = " + JSON.stringify(response));
//            // --------------------------------------------------------
//
//            var data = response.data.data;
//
//            return {
//                data: data
//            };
//        });
//    };

    // --------------------------------------------------------
    // - 
    // --------------------------------------------------------
//    var getJavaConfiguration = function () {
//
//        return $http(
//                {
//                    method: "GET",
//                    url: '/taskflow/apis/v1/allocate/java-configuration/'
//                }
//        ).then(function (response) {
//
//            // --------------------------------------------------------
//            // $log.debug(FACTORY_NAME + " : response = " + JSON.stringify(response));
//            // --------------------------------------------------------
//
//            var data = response.data.data;
//
//            return {
//                data: data
//            };
//        });
//    };

    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------

    return {
        allocate: allocate,
        reset: reset,
//        getTaskflowConfiguration: getTaskflowConfiguration,
//        getJavaConfiguration: getJavaConfiguration
    };
    // ==================================================
});