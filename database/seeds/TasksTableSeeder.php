<?php

use Illuminate\Database\Seeder;

class TasksTableSeeder extends Seeder {

    public function run() {

        for ($employee_id = 1; $employee_id <= 28; $employee_id++) {

            for ($nbdays = -60; $nbdays <= 100; $nbdays++) {

                // midnight that day
                $time = ( $nbdays * 24 * 60 * 60) + mktime(0, 0, 0);

                // ========================================================
                // - weekend
                // ========================================================
                // get weekend days
                $weekday = date('w', $time);
                $isSaturday = $weekday === '6';
                $isSunday = $weekday === '0';

                if ($isSaturday || $isSunday) {
                    continue;
                }

                // ========================================================
                // - non working days
                // ========================================================
                $day = date('d', $time);
                $month = date('m', $time);

                if ($day === '26' && ( $month === '01' || $month === '1')) {
                    // - Republic Day 26 / 01
                    continue;
                }

                if ($day === '15' && ( $month === '08' || $month === '8')) {
                    // - Independance Day 15 / 08
                    continue;
                }

                if (($day === '2' || $day === '02' ) && $month === '10') {
                    // - Mahatma Ghandi's Birthday 02 / 10
                    continue;
                }

                // ========================================================
                // - holidays
                // ========================================================
                $today = ( 0 * 24 * 60 * 60) + mktime(0, 0, 0); // today
                $three_days_ago = (-3 * 24 * 60 * 60) + mktime(0, 0, 0); // today
                $in_10_days = (10 * 24 * 60 * 60) + mktime(0, 0, 0); // today

                if ($employee_id === 1) {
                    if ($time === $three_days_ago) {
                        // day off, see holidays seeder
                        continue;
                    }
                }

                if ($employee_id === 5) {
                    if ($time === $in_10_days) {
                        // day off, see holidays seeder
                        continue;
                    }
                }

                // in 5 days for 5 days
                $found = false;
                if ($employee_id === 3) {
                    $inFiveDays = ( 5 * 24 * 60 * 60) + mktime(0, 0, 0);
                    for ($index = 0; $index < 5; $index++) {

                        $dayOff = $inFiveDays + ( $index * 24 * 60 * 60);
                        if ($time === $dayOff) {
                            $found = true;
                            break;
                        }
                    }
                }

                if ($found === true) {
                    continue;
                }

                if ($time < $today) {

                    // =======================================================
                    $project_id = rand(1, 5);

                    $skills = \DB::table('v_project_skills')
                            ->where('project_id', '=', $project_id)
                            ->get();

                    $max_skills = sizeof($skills) - 1;
                    $skill_nb = rand(0, $max_skills);
                    $skill = $skills[$skill_nb];
                    $skill_id = $skill->id;
                    $duration = $skill->duration;
                    $completion = $duration;

                    $task_id = \DB::table('tasks')
                            ->insertGetId(
                            array(
                                'skill_id' => $skill_id,
                                'project_id' => $project_id,
                                'completed' => 1
                            )
                    );

                    \DB::table('task_allocations')
                            ->insert(
                                    array(
                                        'task_id' => $task_id,
                                        'employee_id' => $employee_id,
                                        'start_date' => date('Y-m-d H:i:s', $time),
                                        'completion' => $completion,
                                        'completed' => 1,
                                        'duration' => $duration
                                    )
                    );

                    // =======================================================

                    $project_id = rand(1, 5);

                    $skills = \DB::table('v_project_skills')
                            ->where('project_id', '=', $project_id)
                            ->get();

                    $max_skills = sizeof($skills) - 1;
                    $skill_nb = rand(0, $max_skills);
                    $skill = $skills[$skill_nb];
                    $skill_id = $skill->id;
                    $duration = $skill->duration;
                    $completion = $duration;

                    $task_id = \DB::table('tasks')
                            ->insertGetId(
                            array(
                                'skill_id' => $skill_id,
                                'project_id' => $project_id,
                                'completed' => 1
                            )
                    );

                    \DB::table('task_allocations')
                            ->insert(
                                    array(
                                        'employee_id' => $employee_id,
                                        'task_id' => $task_id,
                                        'start_date' => date('Y-m-d H:i:s', $time),
                                        'completion' => $completion,
                                        'completed' => 1,
                                        'duration' => $duration
                                    )
                    );

                    // =======================================================
                } else if ($time === $today) {

                    // =======================================================
                    $project_id = rand(1, 5);

                    $skills = \DB::table('v_project_skills')
                            ->where('project_id', '=', $project_id)
                            ->get();

                    $max_skills = sizeof($skills) - 1;
                    $skill_nb = rand(0, $max_skills);
                    $skill = $skills[$skill_nb];
                    $skill_id = $skill->id;
                    $duration = $skill->duration;

                    $task_id = \DB::table('tasks')
                            ->insertGetId(
                            array(
                                'skill_id' => $skill_id,
                                'project_id' => $project_id,
                                'completed' => 0
                            )
                    );

                    \DB::table('task_allocations')
                            ->insert(
                                    array(
                                        'employee_id' => $employee_id,
                                        'task_id' => $task_id,
                                        'start_date' => date('Y-m-d H:i:s', $time),
                                        'completion' => $duration - 30,
                                        'completed' => 0,
                                        'duration' => $duration
                                    )
                    );

                    // =======================================================

                    $project_id = rand(1, 5);

                    $skills = \DB::table('v_project_skills')
                            ->where('project_id', '=', $project_id)
                            ->get();

                    $max_skills = sizeof($skills) - 1;
                    $skill_nb = rand(0, $max_skills);
                    $skill = $skills[$skill_nb];
                    $skill_id = $skill->id;
                    $duration = $skill->duration;
                    $completion = $duration;

                    $task_id = \DB::table('tasks')
                            ->insertGetId(
                            array(
                                'skill_id' => $skill_id,
                                'project_id' => $project_id,
                                'completed' => 0
                            )
                    );

                    \DB::table('task_allocations')
                            ->insert(
                                    array(
                                        'employee_id' => $employee_id,
                                        'task_id' => $task_id,
                                        'start_date' => date('Y-m-d H:i:s', $time),
                                        'completion' => $duration - 30,
                                        'completed' => 0,
                                        'duration' => $duration
                                    )
                    );

                    // =======================================================
                } else {

                    // =======================================================
                    $project_id = rand(1, 5);

                    $skills = \DB::table('v_project_skills')
                            ->where('project_id', '=', $project_id)
                            ->get();

                    $max_skills = sizeof($skills) - 1;
                    $skill_nb = rand(0, $max_skills);
                    $skill = $skills[$skill_nb];
                    $skill_id = $skill->id;
                    $duration = $skill->duration;

                    $task_id = \DB::table('tasks')
                            ->insertGetId(
                            array(
                                'skill_id' => $skill_id,
                                'project_id' => $project_id,
                                'completed' => 0
                            )
                    );

                    \DB::table('task_allocations')
                            ->insert(
                                    array(
                                        'task_id' => $task_id,
                                        'employee_id' => $employee_id,
                                        'start_date' => date('Y-m-d H:i:s', $time),
                                        'completion' => 0,
                                        'completed' => 0,
                                        'duration' => 2 * 60
                                    )
                    );

                    // =======================================================

                    $project_id = rand(1, 5);

                    $skills = \DB::table('v_project_skills')
                            ->where('project_id', '=', $project_id)
                            ->get();

                    $max_skills = sizeof($skills) - 1;
                    $skill_nb = rand(0, $max_skills);
                    $skill = $skills[$skill_nb];
                    $skill_id = $skill->id;
                    $duration = $skill->duration;

                    $task_id = \DB::table('tasks')
                            ->insertGetId(
                            array(
                                'skill_id' => $skill_id,
                                'project_id' => $project_id,
                                'completed' => 0
                            )
                    );

                    \DB::table('task_allocations')
                            ->insert(
                                    array(
                                        'employee_id' => $employee_id,
                                        'task_id' => $task_id,
                                        'start_date' => date('Y-m-d H:i:s', $time),
                                        'completion' => 0,
                                        'duration' => 2 * 60,
                                        'completed' => 0,
                                    )
                    );

                    // =======================================================
                }
            }
        }
    }

}
