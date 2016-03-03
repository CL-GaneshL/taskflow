'use strict';

/**
 * 
 * 
 */

app.factory("skillsSrvc", function ($http, $log) {

    var getSkills = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/skills'
                }
        ).then(function (response) {

            var allSkills = response.data.data;

            return {
                allSkills: allSkills

            };
        });

    };

    var addSkill = function (employeeId, skillId) {

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/employees/' + employeeId + '/skills/' + skillId,
                    data: {
                        'action': 'add',
                        'employeeId': employeeId,
                        'skillId': skillId
                    }
                }
        ).then(function (response) {

            var allSkills = response.data.data;
        });

    };

    var deleteSkill = function (employeeId, skillId) {

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/employees/' + employeeId + '/skills/' + skillId,
                    data: {
                        'action': 'delete',
                        'employeeId': employeeId,
                        'skillId': skillId
                    }
                }
        ).then(function (response) {

            var allSkills = response.data.data;
        });

    };



    return {
        getSkills: getSkills,
        addSkill: addSkill,
        deleteSkill: deleteSkill
    };


    // ==================================================
});