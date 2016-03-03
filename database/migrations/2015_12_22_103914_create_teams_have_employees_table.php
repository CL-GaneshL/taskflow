<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTeamsHaveEmployeesTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('teams_have_employees', function (Blueprint $table) {

            $table->integer('teamId')->unsigned();
            $table->integer('employeeId')->unsigned();

            $table->unique(array('teamId', 'employeeId'));
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('teams_have_employees');
    }

}
