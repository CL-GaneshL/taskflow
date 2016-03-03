'use strict';

app.factory("profileSrvc", function ($log, $http) {

    var FACTORY_NAME = 'profileSrvc';

    var userProfile = null;

    // ==================================================
    // - retrieve the user profile from the DB
    // ==================================================
    var getUserProfile = function (employeeId) {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/users/' + employeeId
                }
        ).then(function (response) {

            var user = response.data.data[0];
            var employee = response.data.data[1][0];
            var team = response.data.data[2][0];
            var recentTasks = response.data.data[3];
            var holidays = response.data.data[4];
            var tasks = response.data.data[5];
            var skills = response.data.data[6];
            var non_working_days = response.data.data[7];

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : recentTasks = " + JSON.stringify(recentTasks));
            // --------------------------------------------------------

            var profile = {
                user: user,
                employee: employee,
                team: team,
                recentTasks: recentTasks,
                holidays: holidays,
                tasks: tasks,
                skills: skills,
                non_working_days: non_working_days
            };

            return {
                profile: profile
            };
        });

    };

    // ==================================================
    // - return functions
    // ==================================================
    return {
        getUserProfile: getUserProfile,
        setUserProfile: function (up) {
            userProfile = up;
        },
        getUser: function () {
            return userProfile.user;
        },
        getEmployee: function () {
            return userProfile.employee;
        },
        getTeam: function () {
            return userProfile.team;
        },
        getRecentTasks: function () {
            return userProfile.recentTasks;
        },
        getHolidays: function () {
            return userProfile.holidays;
        },
        getTasks: function () {
            return userProfile.tasks;
        },
        getSkills: function () {
            return userProfile.skills;
        },
        getNonWorkingDays: function () {
            return userProfile.non_working_days;
        }

    };

    // ==================================================
});