'use strict';
/**
 * Allows communication beetween Login and profile modals.
 * 
 */

app.factory("authenticationSrvc", function ($http) {


    var getAuthenticatedUser = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/authenticate/user'
                }
        ).then(function (response) {

            return {
                user: response.data.user
            };
        });
    };

    var changePassword = function (employee_id, password) {

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/users/' + employee_id,
                    data: {
                        'password': password
                    }
                }
        );
    };


    return {
        getAuthenticatedUser: getAuthenticatedUser,
        changePassword: changePassword
    };
    // ==================================================
});