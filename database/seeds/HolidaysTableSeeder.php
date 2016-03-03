<?php

use Illuminate\Database\Seeder;
// composer require laracasts/testdummy
use Laracasts\TestDummy\Factory as TestDummy;

class HolidaysTableSeeder extends Seeder {

    public function run() {

        $columns = "INSERT INTO holidays ( title, employee_id, start_date, start_morning_shift, start_afternoon_shift, end_date, end_morning_shift, end_afternoon_shift )";

        for ($employee_id = 1; $employee_id <= 28; $employee_id++) {

            if ($employee_id === 1) {
                //  3 days ago
                $time = mktime(0, 0, 0) + (-3 * 24 * 60 * 60);
                $start_date = date('Y-m-d', $time);
                $start_morning_shift = 1;
                $start_afternoon_shift = 1;
                $end_date = date('Y-m-d', $time);
                $end_morning_shift = 0;
                $end_afternoon_shift = 0;
                $title = "'Day off.'";
                $values = "VALUES (" . $title . ", " . $employee_id . ", '" . $start_date . "', " . $start_morning_shift . ", " . $start_afternoon_shift . ", '" . $end_date . "', " . $end_morning_shift . ", " . $end_afternoon_shift . ");";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }

            if ($employee_id === 5) {
                //  in 10 days
                $time = mktime(0, 0, 0) + (10 * 24 * 60 * 60);
                $start_date = date('Y-m-d', $time);
                $start_morning_shift = 1;
                $start_afternoon_shift = 1;
                $end_date = date('Y-m-d', $time);
                $end_morning_shift = 0;
                $end_afternoon_shift = 0;
                $title = "'Day off.'";
                $values = "VALUES (" . $title . ", " . $employee_id . ", '" . $start_date . "', " . $start_morning_shift . ", " . $start_afternoon_shift . ", '" . $end_date . "', " . $end_morning_shift . ", " . $end_afternoon_shift . ");";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }

            if ($employee_id === 3) {
                // in 5 days
                $time = mktime(0, 0, 0) + ( 5 * 24 * 60 * 60);
                $start_date = date('Y-m-d', $time);
                $start_morning_shift = 1;
                $start_afternoon_shift = 1;
                $time = mktime(0, 0, 0) + ( (5 + 4) * 24 * 60 * 60); // 5 days off
                $end_date = date('Y-m-d', $time);
                $end_morning_shift = 1;
                $end_afternoon_shift = 1;
                $title = "'Annual Leaves.'";
                $values = "VALUES (" . $title . ", " . $employee_id . ", '" . $start_date . "', " . $start_morning_shift . ", " . $start_afternoon_shift . ", '" . $end_date . "', " . $end_morning_shift . ", " . $end_afternoon_shift . ");";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }
        }
    }

}
