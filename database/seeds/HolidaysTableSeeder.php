<?php

use Illuminate\Database\Seeder;
// composer require laracasts/testdummy
use Laracasts\TestDummy\Factory as TestDummy;

class HolidaysTableSeeder extends Seeder {

    public function run() {

        $columns = "INSERT INTO holidays ( title, employee_id, start_date, end_date)";

        for ($employee_id = 1; $employee_id <= 28; $employee_id++) {

            if ($employee_id === 1) {
                //  3 days ago
                $time = mktime(0, 0, 0) + (-3 * 24 * 60 * 60);
                $start_date = date('Y-m-d', $time);
                $end_date = date('Y-m-d', $time);
                $title = "'Day off.'";
                $values = "VALUES (" . $title . ", " . $employee_id . ", '" . $start_date . "', '" . $end_date . "');";

                $sql = $columns . $values;
                DB::unprepared($sql);

                //  in 10 days
                $time = mktime(0, 0, 0) + (10 * 24 * 60 * 60);
                $start_date = date('Y-m-d', $time);
                $end_date = date('Y-m-d', $time);
                $title = "'Day off.'";
                $values = "VALUES (" . $title . ", " . $employee_id . ", '" . $start_date . "', '" . $end_date . "');";

                $sql = $columns . $values;
                DB::unprepared($sql);

                // in 5 days
                $time = mktime(0, 0, 0) + ( 5 * 24 * 60 * 60);
                $start_date = date('Y-m-d', $time);
                $time = mktime(0, 0, 0) + ( (5 + 4) * 24 * 60 * 60); // 4 days off
                $end_date = date('Y-m-d', $time);
                $title = "'Annual Leaves.'";
                $values = "VALUES (" . $title . ", " . $employee_id . ", '" . $start_date . "', '" . $end_date . "');";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }

            if ($employee_id === 3) {

                // in 5 days
                $time = mktime(0, 0, 0) + ( 5 * 24 * 60 * 60);
                $start_date = date('Y-m-d', $time);
                $time = mktime(0, 0, 0) + ( (5 + 4) * 24 * 60 * 60); // 4 days off
                $end_date = date('Y-m-d', $time);
                $title = "'Annual Leaves.'";
                $values = "VALUES (" . $title . ", " . $employee_id . ", '" . $start_date . "', '" . $end_date . "');";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }

            if ($employee_id === 10) {

                // in 3 days
                $time = mktime(0, 0, 0) + ( 3 * 24 * 60 * 60);
                $start_date = date('Y-m-d', $time);
                $time = mktime(0, 0, 0) + ( (5 + 4) * 24 * 60 * 60); // 4 days off
                $end_date = date('Y-m-d', $time);
                $title = "'Annual Leaves.'";
                $values = "VALUES (" . $title . ", " . $employee_id . ", '" . $start_date . "', '" . $end_date . "');";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }
        }
    }

}
