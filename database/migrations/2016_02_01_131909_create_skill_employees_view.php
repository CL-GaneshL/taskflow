<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateSkillEmployeesView extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {

        $script_path = base_path('caratlane/mysql');
        $script_full_path = $script_path . '/view_v_skill_employees.sql';
        DB::unprepared(file_get_contents($script_full_path));
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {

        $sql = 'DROP VIEW IF EXISTS v_skill_employees';
        DB::unprepared($sql);
    }

}
