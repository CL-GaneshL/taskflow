'use strict';

/**
 * Allows communication beetween Teams module modals.
 * 
 */

app.factory("teamsSrvc", function ($log, $http) {


    var getAllTeams = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/teams/'
                }

        ).then(function (response) {

            var teams = response.data.data[0];
            var teamLeaders = response.data.data[1];
            var allEmployees = response.data.data[2];

            return {
                teams: teams,
                teamLeaders: teamLeaders,
                allEmployees: allEmployees
            };
        });
    };


    var createTeam = function (teamName, teamLeaderId) {

        return $http(
                {
                    method: "POST",
                    url: '/taskflow/apis/v1/teams/',
                    data: {
                        teamName: teamName,
                        teamLeaderId: teamLeaderId,
                    }
                }

        ).then(function (response) {

            var team = response.data.data[0];

            return {
                team: team
            };
        });
    };

    var deleteTeam = function (teamId) {

        return $http(
                {
                    method: "DELETE",
                    url: '/taskflow/apis/v1/teams/' + teamId
                }
        );
    };

    var getTeamMembers = function (teamId) {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/teams/' + teamId + '/employees'
                }

        ).then(function (response) {

            var members = response.data.data;
            return {
                members: members,
            };
        });
    };


    var addTeamMember = function (employeeId, teamId) {

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/teams/' + teamId + '/employees/' + employeeId,
                    data: {
                        'action': 'add',
                        'employeeId': employeeId,
                        'teamId': teamId
                    }
                }

        ).then(function (response) {

        });
    };


    var deleteTeamMember = function (employeeId, teamId) {

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/teams/' + teamId + '/employees/' + employeeId,
                    data: {
                        'action': 'delete',
                        'employeeId': employeeId,
                        'teamId': teamId
                    }
                }

        ).then(function (response) {

        });
    };




    return {
        getAllTeams: getAllTeams,
        deleteTeam: deleteTeam,
        createTeam: createTeam,
        getTeamMembers: getTeamMembers,
        addTeamMember: addTeamMember,
        deleteTeamMember: deleteTeamMember
    };

    // ==================================================
});