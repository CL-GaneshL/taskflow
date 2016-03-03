'use strict';

app.factory("tasksSrvc", function ($log, $http) {

    var FACTORY_NAME = 'tasksSrvc';

    var CARATLANE_START_ACTIVITY_TIME = 0;
    var CARATLANE_MID_SHIFT_TIME = 4;
    var CARATLANE_END_ACTIVITY_TIME = 8;


    // --------------------------------------------------------
    // - Create a task record in the db.
    // --------------------------------------------------------
    var createTask = function (newTask) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : newTask = " + JSON.stringify(newTask));
        // --------------------------------------------------------

        return $http(
                {
                    method: "POST",
                    url: '/taskflow/apis/v1/tasks/',
                    data: {
                        name: newTask.name,
                        employee_id: newTask.employee_id,
                        skill_id: newTask.skill_id,
                        project_id: newTask.project_id,
                        start: newTask.start,
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

    // --------------------------------------------------------
    // - delete a teask record from the db.
    // --------------------------------------------------------
    var deleteTask = function (task_id) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : task_id = " + JSON.stringify(task_id));
        // --------------------------------------------------------

        return $http(
                {
                    method: "DELETE",
                    url: '/taskflow/apis/v1/tasks/' + task_id
                }
        );
    };

    // --------------------------------------------------------
    // - Update a holiday record in the db.
    // --------------------------------------------------------
    var updateTask = function (toUpdateTask) {

        // --------------------------------------------------------
        // $log.debug(FACTORY_NAME + " : toUpdateHoliday = " + JSON.stringify(toUpdateHoliday));
        // --------------------------------------------------------

        var task_id = toUpdateTask.id;

        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/tasks/' + task_id,
                    data: {
                        id: toUpdateTask.id,
                        name: toUpdateTask.name,
                        employee_id: toUpdateTask.employee_id,
                        skill_id: toUpdateTask.skill_id,
                        project_id: toUpdateTask.project_id,
                        start: toUpdateTask.start,
                        duration: toUpdateTask.duration
                    }
                }

        ).then(function (response) {

            var task = response.data.data;

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response task = " + JSON.stringify(task));
            // --------------------------------------------------------

            return {
                holiday: task
            };
        });
    };

    // --------------------------------------------------------
    // - Update a task's allocation
    // --------------------------------------------------------
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

    var getTimeline = function (start_date, completion, duration, completed) {

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

        var _DAYS = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
        var _MONTHS = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

        var start = formatMySQL2JSDate(start_date);

        var day = start.getDate();
        var year = start.getFullYear();

        var dayStr = _DAYS[start.getDay()];
        var monthStr = _MONTHS[start.getMonth()];

//        var hoursStr = 'hour : ' + shift_hour;
//        var date = dayStr + ', ' + monthStr + ' ' + day + ' ' + year + ' @ ' + hoursStr;

        var date = dayStr + ', ' + monthStr + ' ' + day + ' ' + year;

        var duration_hours = Math.floor(duration / 60);
        var duration_minutes = duration % 60;

        var durationStr = ' (duration:' + duration_hours + ' h';
        if (duration_minutes === 0) {
            durationStr = durationStr + ') ';
        }
        else {
            durationStr = durationStr + ' ' + duration_minutes + ' mins) ';
        }

        if (completed === 0) {
            var completionStr = null;
            if (completion === duration) {
                completionStr = ' - completion 100%.';
            }
            else if (completion === 0) {
                completionStr = '';
            }
            else if (completion < duration) {

                var completion_hours = Math.floor(completion / 60);
                var completion_minutes = completion % 60;

                completionStr = ' - completion : ' + completion_hours + ' h';

                if (completion_minutes === 0) {
                    completionStr = completionStr + '.';
                }
                else {
                    completionStr = completionStr + ' ' + completion_minutes + ' mins';
                }
            }
        }
        else {
            completionStr = ' - completion 100%.';
        }

        var timeline = date + durationStr + completionStr;
        return timeline;
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
    // - list of possible completion choices
    // ==================================================
    var COMPLETION_CHOICES = [
        '0 h 15 mins', '0 h 30 mins', '0 h 45 mins', '1 h 00 mins',
        '1 h 15 mins', '1 h 30 mins', '1 h 45 mins', '2 h 00 mins',
        '2 h 15 mins', '2 h 30 mins', '2 h 45 mins', '3 h 00 mins',
        '3 h 15 mins', '3 h 30 mins', '3 h 45 mins', '4 h 00 mins',
        '4 h 15 mins', '4 h 30 mins', '4 h 45 mins', '5 h 00 mins',
        '5 h 15 mins', '5 h 30 mins', '5 h 45 mins', '6 h 00 mins',
        '6 h 15 mins', '6 h 30 mins', '6 h 45 mins'
    ];

    function getCompletionChoices() {

        var choices = [];
        var index = 0;
        var maxIndex = COMPLETION_CHOICES.length;

        for (index = 0; index <= maxIndex; index++) {
            choices.push(COMPLETION_CHOICES[index]);
        }

        return choices;
    }

    // ==================================================
    // - list of possible nb_products choices
    // ==================================================
    function getNbProductsChoices(max) {

        var choices = [];
        var index = 0;

        for (index = 1; index <= max; index++) {
            choices.push(index);
        }

        return choices;
    }


    // ==================================================
    // - list of possible completion choices
    // ==================================================
    function getDurationChoices() {

        var choices = [];
        var index = 0;
        var maxIndex = COMPLETION_CHOICES.length;

        for (index = 0; index <= maxIndex; index++) {
            choices.push(COMPLETION_CHOICES[index]);
        }

        return choices;
    }

    // ==================================================
    // - 
    // ==================================================
    function getTaskStartsAt(start, shift_hour) {

//        // --------------------------------------------------------
//        $log.debug(FACTORY_NAME + " : getTaskStartsAt = ");
//        $log.debug(FACTORY_NAME + " : start = " + JSON.stringify(start));
//        $log.debug(FACTORY_NAME + " : shift_hour = " + JSON.stringify(shift_hour));
//        // --------------------------------------------------------

        var startDate = new Date(start);
        startDate.setHours(shift_hour);

//        $log.debug(FACTORY_NAME + " : startDate = " + JSON.stringify(startDate));

        return startDate;
    }

    // ==================================================
    // - 
    // ==================================================
    function getTaskEndsAt(start, shift_hour, duration) {

//        $log.debug(FACTORY_NAME + " : getTaskEndsAt = ");
//        $log.debug(FACTORY_NAME + " : start = " + JSON.stringify(start));
//        $log.debug(FACTORY_NAME + " : duration = " + JSON.stringify(duration));

        var endDate = new Date(start);

        var duration_hours = Math.floor(duration / 60);
        var duration_minutes = duration % 60;

//        endDate.setHours(shift_hour+1 + duration_hours);
        endDate.setMinutes((shift_hour + duration_hours) * 60 + duration_minutes);

//        $log.debug(FACTORY_NAME + " : endDate = " + JSON.stringify(endDate));

        return endDate;
    }

    // ==================================================
    // - get the endsAt time for the calendar
    // - this assumes that 
    // -    - morning_shift or afternoon_shift is true
    // -    - both are true
    // ==================================================
    function getNwdStartsAt(date, morning_shift, afternoon_shift) {

        var startDate = new Date(date);

        if (afternoon_shift === true) {
            startDate.setHours(CARATLANE_MID_SHIFT_TIME, 0, 0, 0);
        }

        if (morning_shift === true) {
            startDate.setHours(CARATLANE_START_ACTIVITY_TIME, 0, 0, 0);
        }

        return startDate;
    }

    // ==================================================
    // - get the endsAt time for the calendar
    // - this assumes that 
    // -    - morning_shift or afternoon_shift is true
    // -    - both are true
    // ==================================================
    function getNwdEndsAt(date, morning_shift, afternoon_shift) {

        //default value
        var endDate = new Date(date);

        if (morning_shift === true) {
            endDate.setHours(CARATLANE_MID_SHIFT_TIME, 0, 0, 0);
        }

        if (afternoon_shift === true) {
            endDate.setHours(CARATLANE_END_ACTIVITY_TIME, 0, 0, 0);
        }

        return endDate;
    }

    // ==================================================
    // - 
    // ==================================================
    function getHolidaysEndsAt(end_date, end_morning_shift, end_afternoon_shift) {

        //default value
        var endDate = new Date(end_date);

        if (end_morning_shift === false && end_afternoon_shift === false) {
            // the holidays ended up at midnight the previous day
            // start date = end dvar endDate = new Date(end_date);
            var endDate = new Date(end_date);
            endDate.setHours(CARATLANE_END_ACTIVITY_TIME, 0, 0, 0);

        }
        else {
            if (end_morning_shift === true) {
                endDate.setHours(CARATLANE_MID_SHIFT_TIME, 0, 0, 0);
            }

            if (end_afternoon_shift === true) {
                endDate.setHours(CARATLANE_END_ACTIVITY_TIME, 0, 0, 0);
            }
        }

        return endDate;
    }

    // ==================================================
    // - 
    // ==================================================
    function getHolidaysStartsAt(start_date, start_morning_shift, start_afternoon_shift) {

        var startDate = new Date(start_date);

        if (start_afternoon_shift === true) {
            startDate.setHours(CARATLANE_MID_SHIFT_TIME, 0, 0, 0);
        }

        if (start_morning_shift === true) {
            startDate.setHours(CARATLANE_START_ACTIVITY_TIME, 0, 0, 0);
        }
        return startDate;
    }


    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------
    return {
        createTask: createTask,
        deleteTask: deleteTask,
        updateTask: updateTask,
        updateTaskAllocation: updateTaskAllocation,
        formatCompletion: formatCompletion,
        formatDuration: formatDuration,
        getTimeline: getTimeline,
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