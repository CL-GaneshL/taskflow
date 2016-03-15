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


    // ==================================================
    // - format duration from minutes to hh:mm
    // ==================================================
    var formatDuration = function (duration) {

        var duration_hours = Math.floor(duration / 60);
        var duration_minutes = duration % 60;

        var formated = duration_hours + ' h';
        if (duration_minutes !== 0) {
            formated = formated + ' ' + duration_minutes + ' mins';
        }

        return formated;
    };

    // ==================================================
    // - format duration from  hh:mm to minutes
    // ==================================================
    var formatDuration2Mins = function (duration) {

        var splits = duration.split("h");
        var hours = Number(splits[0].trim());
        var mins = Number(splits[1].replace(' mins', '').trim());

        return hours * 60 + mins;
    };
    // ==================================================

    return {
        getSkills: getSkills,
        addSkill: addSkill,
        deleteSkill: deleteSkill,
        formatDuration: formatDuration,
        formatDuration2Mins: formatDuration2Mins,
    };


    // ==================================================
});