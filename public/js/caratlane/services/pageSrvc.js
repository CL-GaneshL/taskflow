'use strict';

/**
 * Allows communication beetween Login and profile modals.
 * 
 */

app.factory("pageSrvc", function () {

    var profilePage = false;
    var employeePage = false;

    return {
        isProfilePage: function () {
            return profilePage;
        },
        isEmployeePage: function () {
            return employeePage;
        },
        setProfilePage: function (enable) {
            profilePage = enable;
            employeePage = !enable;
        },
        setEmployeePage: function (enable) {
            profilePage = !enable;
            employeePage = enable;
        }

    };

    // ==================================================
});