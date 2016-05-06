<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateGetEVProc extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        $script_path = base_path('caratlane/mysql');
        $script_full_path = $script_path . '/proc_get_EV.sql';
        DB::unprepared(file_get_contents($script_full_path));
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        $sql = 'DROP FUNCTION IF EXISTS getEV';
        DB::unprepared($sql);
    }

}
