<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateProjectSkillsView extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {

        $script_path = base_path('caratlane/mysql');
        $script_full_path = $script_path . '/view_v_project_skills.sql';
        DB::unprepared(file_get_contents($script_full_path));
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        
        $sql = 'DROP VIEW IF EXISTS v_project_skills';
        DB::unprepared($sql);
    }

}
