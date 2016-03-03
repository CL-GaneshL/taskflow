<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateGetTitleFct extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        $script_path = base_path('caratlane/mysql');
        $script_full_path = $script_path . '/fct_get_title.sql';
        DB::unprepared(file_get_contents($script_full_path));
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        $sql = 'DROP FUNCTION IF EXISTS getTitle';
        DB::unprepared($sql);
    }

}
