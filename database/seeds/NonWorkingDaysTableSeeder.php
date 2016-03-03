<?php

use Illuminate\Database\Seeder;
// composer require laracasts/testdummy
use Laracasts\TestDummy\Factory as TestDummy;

class NonWorkingDaysTableSeeder extends Seeder {

    public function run() {

        $columns = "INSERT INTO non_working_days ( title, type, date, morning_shift, afternoon_shift ) ";

        $TEN_YEARS = 10 * 365;

        for ($nbdays = -100; $nbdays <= $TEN_YEARS; $nbdays++) {

            // midnight that day
            $time = mktime(0, 0, 0) + ( $nbdays * 24 * 60 * 60);

            // ========================================================
            // - weekend non working days for the next 10 years
            // ========================================================
            // get weekend days
            $weekday = date('w', $time);
            $isSaturday = $weekday === '6';
            $isSunday = $weekday === '0';

            if ($isSaturday) {

                // saturday weekend starts a 12pm
                $type = "'WEEKEND'";
                $time = mktime(14, 0, 0) + ( $nbdays * 24 * 60 * 60); // noon that day
                $date = date('Y-m-d', $time);
                $morning_shift = 0;
                $afternoon_shift = 1;
                $title = "'" . (date('D, j M Y', $time)) . " - Saturday afternoon shift." . "'";
                $values = "VALUES (" . $title . ", " . $type . ", '" . $date . "', " . $morning_shift . ", " . $afternoon_shift . ");";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }

            if ($isSunday) {

                // starts at midnight
                $type = "'WEEKEND'";
                $time = mktime(0, 0, 0) + ( $nbdays * 24 * 60 * 60);  // midnight that day
                $date = date('Y-m-d', $time);
                $morning_shift = 1;
                $afternoon_shift = 1;
                $title = "'" . (date('D, j M Y', $time)) . " - Sunday." . "'";
                $values = "VALUES (" . $title . ", " . $type . ", '" . $date . "', " . $morning_shift . ", " . $afternoon_shift . ");";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }

            // ========================================================
            // - Indian public holidays for the next 10 years
            // ========================================================

            $day = date('d', $time);
            $month = date('m', $time);

            // ---------------------------------------------
            // - Republic Day 26 / 01
            // ---------------------------------------------
            if ($day === '26' && ( $month === '01' || $month === '1')) {

                $type = "'NON-WORKING'";
                $time = mktime(0, 0, 0) + ( $nbdays * 24 * 60 * 60);  // midnight that day
                $date = date('Y-m-d', $time);
                $morning_shift = 1;
                $afternoon_shift = 1;
                $title = "'Republic Day.'";
                $values = "VALUES (" . $title . ", " . $type . ", '" . $date . "', " . $morning_shift . ", " . $afternoon_shift . ");";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }

            // ---------------------------------------------
            // - Independance Day 15 / 08
            // ---------------------------------------------
            if ($day === '15' && ( $month === '08' || $month === '8')) {

                $type = "'NON-WORKING'";
                $time = ( $nbdays * 24 * 60 * 60) + mktime(0, 0, 0);  // midnight that day
                $date = date('Y-m-d', $time);
                $morning_shift = 1;
                $afternoon_shift = 1;
                $title = "'Independance Day.'";
                $values = "VALUES (" . $title . ", " . $type . ", '" . $date . "', " . $morning_shift . ", " . $afternoon_shift . ");";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }

            // ---------------------------------------------
            // - Mahatma Ghandi's Birthday 02 / 10
            // ---------------------------------------------
            if (($day === '2' || $day === '02' ) && $month === '10') {

                $type = "'NON-WORKING'";
                $time = ( $nbdays * 24 * 60 * 60) + mktime(0, 0, 0);  // midnight that day
                $date = date('Y-m-d', $time);
                $morning_shift = 1;
                $afternoon_shift = 1;
                $title = "'Mahatma Ghandi\'s Birthday.'";
                $values = "VALUES (" . $title . ", " . $type . ", '" . $date . "', " . $morning_shift . ", " . $afternoon_shift . ");";

                $sql = $columns . $values;
                DB::unprepared($sql);
            }
        }
    }

}
