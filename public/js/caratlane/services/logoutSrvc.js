'use strict';
/**
 * Allows communication beetween Login and profile modals.
 * 
 */

app.factory("logoutSrvc", function ($http, $log) {

    var FACTORY_NAME = 'logoutSrvc';

    var logout = function () {

        // --------------------------------------------------------
        $log.debug(FACTORY_NAME + " : sending auth logout to server ... ");
        // --------------------------------------------------------

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/logout/'
                }
        );
    };


    return {
        logout: logout
    };
    // ==================================================
});