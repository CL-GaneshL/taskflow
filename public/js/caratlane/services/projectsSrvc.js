'use strict';

/**
 * Allows communication beetween Teams module modals.
 * 
 */

app.factory("projectsSrvc", function ($log, $http) {

    var FACTORY_NAME = 'projectsSrvc';

    // --------------------------------------------------------
    // - return the list of all Projects from the db.
    // --------------------------------------------------------
    var getAllProjects = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/projects/'
                }

        ).then(function (response) {

            var projects = response.data.data[0];
            var templates = response.data.data[1];

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response projects = " + JSON.stringify(projects));
            // $log.debug(FACTORY_NAME + " : response templates = " + JSON.stringify(templates));
            // --------------------------------------------------------

            return {
                projects: projects,
                templates: templates
            };
        });
    };

    // --------------------------------------------------------
    // - create a new Project in the db.
    // --------------------------------------------------------
    var createProject = function (newProject) {

        return $http(
                {
                    method: "POST",
                    url: '/taskflow/apis/v1/projects/',
                    data: {
                        reference: newProject.reference,
                        template_id: newProject.template_id,
                        nb_products: newProject.nb_products,
                        priority: newProject.priority,
                        start_date: newProject.start_date
                    }
                }

        ).then(function (response) {

            var project = response.data.data[0];

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : createProject response = " + JSON.stringify(response));
            // --------------------------------------------------------

            return {
                project: project
            };
        });
    };

    // --------------------------------------------------------
    // - update a Project in the db.
    // --------------------------------------------------------
    var updateProject = function (projectToUpdate) {

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/projects/' + projectToUpdate.id,
                    data: {
                        reference: projectToUpdate.reference,
                        template_id: projectToUpdate.template_id,
                        nb_products: projectToUpdate.nb_products,
                        priority: projectToUpdate.priority,
                        start_date: projectToUpdate.start_date
                    }
                }

        ).then(function (response) {

            var project = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : updateProject response = " + JSON.stringify(response));
            // --------------------------------------------------------

            return {
                project: project
            };
        });
    };

    // --------------------------------------------------------
    // - close a Project
    // --------------------------------------------------------
    var closeProject = function (project_id) {

        return $http(
                {
                    method: "DELETE",
                    url: '/taskflow/apis/v1/projects/' + project_id
                }
        ).then(function (response) {

            var project = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : closeProject response = " + JSON.stringify(response));
            // --------------------------------------------------------

            return {
                project: project
            };
        });
    };

    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------
    return {
        getAllProjects: getAllProjects,
        closeProject: closeProject,
        updateProject: updateProject,
        createProject: createProject
    };

    // ==================================================
});