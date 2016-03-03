'use strict';
/**
 * Allows communication beetween Login and profile modals.
 * 
 */

app.factory("signinSrvc", function ($http, $log) {

    var credentials = null;

    return {
        getCredentials: function () {
            return credentials;
        },
        setCredentials: function (c) {
            credentials = c;
        }

    };
    // ==================================================
});