'use strict';

/**
 * Allows communication beetween Teams module modals.
 * 
 */

app.factory("templatesSrvc", function ($log, $http) {

    var FACTORY_NAME = 'templatesSrvc';

    // --------------------------------------------------------
    // - return the list of all Templates from the db.
    // --------------------------------------------------------
    var getAllTemplates = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/templates/'
                }

        ).then(function (response) {

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response = " + JSON.stringify(response));
            // --------------------------------------------------------

            var templates = response.data.data[0];
            var skills = response.data.data[1];

            return {
                templates: templates,
                skills: skills
            };
        });
    };

    // --------------------------------------------------------
    // - Create the a new Template.
    // --------------------------------------------------------
    var createTemplate = function (reference, designation) {

        return $http(
                {
                    method: "POST",
                    url: '/taskflow/apis/v1/templates/',
                    data: {
                        reference: reference,
                        designation: designation,
                    }
                }

        ).then(function (response) {

            var template = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response = " + JSON.stringify(response));
            // --------------------------------------------------------

            return {
                template: template
            };
        });
    };

    // --------------------------------------------------------
    // - Close an existing Template.
    // --------------------------------------------------------
    var closeTemplate = function (template_id) {

        return $http(
                {
                    method: "DELETE",
                    url: '/taskflow/apis/v1/templates/' + template_id
                }
        );
    };

    // --------------------------------------------------------
    // - Get the list of all skills for that Template
    // --------------------------------------------------------
    var getTemplateSkills = function (template_id) {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/templates/' + template_id
                }

        ).then(function (response) {

            var skills = response.data.data;
            return {
                skills: skills
            };
        });
    };

    // --------------------------------------------------------
    // - add a new Skill to that Template
    // --------------------------------------------------------
    var addSkill = function (template_id, skill_id) {

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/templates/' + template_id,
                    data: {
                        'action': 'add',
                        'template_id': template_id,
                        'skill_id': skill_id
                    }
                }
        );
    };

    // --------------------------------------------------------
    // - remove a Skill from the Template Skill list
    // --------------------------------------------------------
    var removeSkill = function (template_id, skill_id) {

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/templates/' + template_id,
                    data: {
                        'action': 'delete',
                        'template_id': template_id,
                        'skill_id': skill_id
                    }
                }
        );
    };


    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------

    return {
        getAllTemplates: getAllTemplates,
        closeTemplate: closeTemplate,
        createTemplate: createTemplate,
        getTemplateSkills: getTemplateSkills,
        addSkill: addSkill,
        removeSkill: removeSkill
    };

    // ==================================================
});