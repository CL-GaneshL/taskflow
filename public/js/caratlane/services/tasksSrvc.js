'use strict';

app.factory("tasksSrvc", function ($log, $http) {

    var FACTORY_NAME = 'tasksSrvc';

    // ==================================================
    // - Create a task record in the db.
    // ==================================================
    var createTaskAllocation = function (newTask) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : newTask = " + JSON.stringify(newTask));
        // --------------------------------------------------------

        return $http(
                {
                    method: "POST",
                    url: '/taskflow/apis/v1/task-allocations/',
                    data: {
                        task_id: newTask.task_id,
                        employee_id: newTask.employee_id,
                        start_date: newTask.start_date,
                        completion: newTask.completion,
                        nb_products_completed: newTask.nb_products_completed,
                        completed: newTask.completed,
                        duration: newTask.duration
                    }
                }

        ).then(function (response) {

            var task = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response task = " + JSON.stringify(task));
            // --------------------------------------------------------

            return {
                task: task
            };
        });
    };

    // ==================================================
    // - delete a task allocation in the db
    // ==================================================
    var deleteTaskAllocation = function (allocation_id) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : allocation_id = " + JSON.stringify(allocation_id));
        // --------------------------------------------------------

        return $http(
                {
                    method: "DELETE",
                    url: '/taskflow/apis/v1/task-allocations/' + allocation_id
                }
        );
    };

    // ==================================================
    // - Update a task's allocation
    // ==================================================
    var updateTaskAllocation = function (allocationToUpdate) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : toUpdateHoliday = " + JSON.stringify(toUpdateHoliday));
        // --------------------------------------------------------

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/employees/' + allocationToUpdate.employee_id + '/tasks/' + allocationToUpdate.id,
                    data: {
                        id: allocationToUpdate.id,
                        task_id: allocationToUpdate.task_id,
                        employee_id: allocationToUpdate.employee_id,
                        start_date: allocationToUpdate.start_date,
                        completion: allocationToUpdate.completion,
                        nb_products_completed: allocationToUpdate.nb_products_completed,
                        completed: allocationToUpdate.completed,
                        duration: allocationToUpdate.duration
                    }
                }

        ).then(function (response) {

            var task = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response task = " + JSON.stringify(task));
            // --------------------------------------------------------

            return {
                task: task
            };
        });
    };

    // ==================================================
    // - get a JS date from a MySQL datatime
    // ==================================================
    function formatMySQL2JSDate(mysql_date) {

        var t, result = null;

        if (typeof mysql_date === 'string')
        {
            t = mysql_date.split(/[- :]/);

            //when t[3], t[4] and t[5] are missing they defaults to zero
            result = new Date(t[0], t[1] - 1, t[2], t[3] || 0, t[4] || 0, t[5] || 0);
        }

        return result;
    }

    // ==================================================
    // - format completion from minutes to hh:mm
    // ==================================================
    var formatCompletion = function (completion) {

        var completion_hours = Math.floor(completion / 60);
        var completion_minutes = completion % 60;

        var formated = completion_hours + ' h';
        if (completion_minutes !== 0) {
            formated = formated + ' ' + completion_minutes + ' mins';
        }

        return formated;
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
    // - 
    // ==================================================

    var getTimeline0 = function (start_date, title) {

        var _DAYS = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
        var _MONTHS = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

        var start = formatMySQL2JSDate(start_date);

        var day = start.getDate();
        var year = start.getFullYear();

        var dayStr = _DAYS[start.getDay()];
        var monthStr = _MONTHS[start.getMonth()];

        var date = dayStr + ', ' + monthStr + ' ' + day + ' ' + year;

        return  date + ", " + title + '. ';
    };

    // ==================================================
    // - 
    // ==================================================

    var getTimeline1 = function (duration, nb_products_planned) {

        var duration_hours = Math.floor(duration / 60);
        var duration_minutes = duration % 60;

        var durationStr = 'Duration : ' + duration_hours + ' h';
        if (duration_minutes === 0) {
            durationStr = durationStr + ' ';
        } else {
            durationStr = durationStr + ' ' + duration_minutes + ' mins ';
        }

        var plannedStr = 'Products : ' + nb_products_planned;

        return  "Planned : " + durationStr + ', ' + plannedStr + '. ';
    };

    // ==================================================
    // - 
    // ==================================================

    var getTimeline2 = function (completion, nb_products_completed)
    {

        var completionStr = null;
        var productsStr = null;

        var duration_hours = Math.floor(completion / 60);
        var duration_minutes = completion % 60;

        var completionStr = 'Duration : ' + duration_hours + ' h';
        if (duration_minutes === 0) {
            completionStr = completionStr + ' ';
        } else {
            completionStr = completionStr + ' ' + duration_minutes + ' mins';
        }

        var productsStr = 'Products : ' + nb_products_completed;

        return "Completed : " + completionStr + ', ' + productsStr + '. ';
    };


    // ==================================================
    // - 
    // ==================================================

    var formatDuration2HoursMins = function (duration) {

        var duration_hours = Math.floor(duration / 60);
        var duration_minutes = duration % 60;

        var durationStr = duration_hours + ' h';
        if (duration_minutes === 0) {
            durationStr = durationStr + ' ';
        } else {
            durationStr = durationStr + ' ' + duration_minutes + ' mins ';
        }

        return   durationStr;
    };


    // ==================================================
    // - format completion from  hh:mm to minutes
    // ==================================================
    function formatCompletion2Mins(completion) {

        var splits = completion.split("h");
        var hours = Number(splits[0].trim());
        var mins = Number(splits[1].replace(' mins', '').trim());

        return hours * 60 + mins;
    }

    // ==================================================
    // - list of possible duration/completion choices
    // ==================================================

    // duration and completion are discret values stepping by 15 mins
    var DURATION_STEP_IN_MINS = 15;

    // we allow a max task duration of 8 hours
    var MAX_DURATION_IN_HOURS = 8;

    // excess completion of 2 hours
    var EXCESS_COMPLETION_IN_HOURS = 2;

    // list of possible duration/completion times
    var COMPLETION_CHOICES = [
        '0 h 00 mins',
        '0 h 15 mins', '0 h 30 mins', '0 h 45 mins', '1 h 00 mins',
        '1 h 15 mins', '1 h 30 mins', '1 h 45 mins', '2 h 00 mins',
        '2 h 15 mins', '2 h 30 mins', '2 h 45 mins', '3 h 00 mins',
        '3 h 15 mins', '3 h 30 mins', '3 h 45 mins', '4 h 00 mins',
        '4 h 15 mins', '4 h 30 mins', '4 h 45 mins', '5 h 00 mins',
        '5 h 15 mins', '5 h 30 mins', '5 h 45 mins', '6 h 00 mins',
        '6 h 15 mins', '6 h 30 mins', '6 h 45 mins', '7 h 00 mins',
        '7 h 15 mins', '7 h 30 mins', '7 h 45 mins', '8 h 00 mins', // <== MAX_DURATION_IN_HOURS
        '8 h 15 mins', '8 h 30 mins', '8 h 45 mins', '9 h 00 mins',
        '9 h 15 mins', '9 h 30 mins', '9 h 45 mins', '10 h 00 mins'
    ];

    // ==================================================
    // - list of possible completion choices
    // - task duration is limitted to 8 hours
    // - we allow completion to be the duration 
    // - plus 2 hours
    // ==================================================
    function getCompletionChoices(durationInMins) {

        var index = 0;
        var choices = [];

        var EXCESS_MINS = EXCESS_COMPLETION_IN_HOURS * 60;
        var maxIndex = Math.floor((durationInMins + EXCESS_MINS) / DURATION_STEP_IN_MINS);

        for (index = 0; index <= maxIndex; index++) {
            choices.push(COMPLETION_CHOICES[index]);
        }

        return choices;
    }

    // ==================================================
    // - list of possible duration choices
    // - we limit the duration to 8 hours
    // ==================================================
    function getDurationChoices() {

        var index = 0;
        var choices = [];

        var MAX_MINS = MAX_DURATION_IN_HOURS * 60;
        var maxIndex = Math.floor(MAX_MINS / DURATION_STEP_IN_MINS);

        for (index = 0; index <= maxIndex; index++) {
            choices.push(COMPLETION_CHOICES[index]);
        }

        return choices;
    }

    // ==================================================
    // - list of possible nb_products choices
    // ==================================================
    function getNbProductsChoices(max) {

        var index = 0;
        var choices = [];

        for (index = 0; index <= max; index++) {
            choices.push(index);
        }

        return choices;
    }

    // ==================================================
    // - 
    // ==================================================
    function getTaskStartsAt(start_date) {
        return formatMySQL2JSDate(start_date);
    }

    // ==================================================
    // - 
    // ==================================================
    function getTaskEndsAt(end_date, duration) {

        var endDate = formatMySQL2JSDate(end_date);
        endDate.setMinutes(duration);
        return endDate;
    }

    // ==================================================
    // - 
    // ==================================================
    function getNwdStartsAt(start_date) {
        return new Date(start_date);
    }

    // ==================================================
    // - 
    // ==================================================
    function getNwdEndsAt(end_date) {
        return new Date(end_date);
    }

    // ==================================================
    // - 
    // ==================================================
    function getHolidaysEndsAt(end_date) {
        return new Date(end_date);
    }

    // ==================================================
    // - 
    // ==================================================
    function getHolidaysStartsAt(start_date) {
        return new Date(start_date);
    }


    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------
    return {
        createTaskAllocation: createTaskAllocation,
        deleteTaskAllocation: deleteTaskAllocation,
        updateTaskAllocation: updateTaskAllocation,
        formatCompletion: formatCompletion,
        formatDuration: formatDuration,
        getTimeline0: getTimeline0,
        getTimeline1: getTimeline1,
        getTimeline2: getTimeline2,
        formatDuration2HoursMins: formatDuration2HoursMins,
        formatCompletion2Mins: formatCompletion2Mins,
        getCompletionChoices: getCompletionChoices,
        getNbProductsChoices: getNbProductsChoices,
        getDurationChoices: getDurationChoices,
        getTaskStartsAt: getTaskStartsAt,
        getTaskEndsAt: getTaskEndsAt,
        getNwdStartsAt: getNwdStartsAt,
        getNwdEndsAt: getNwdEndsAt,
        getHolidaysEndsAt: getHolidaysEndsAt,
        getHolidaysStartsAt: getHolidaysStartsAt

    };

    // ==================================================
});