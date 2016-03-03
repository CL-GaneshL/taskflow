<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateEmployeesHaveSkillsTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('employees_have_skills', function (Blueprint $table) {

            $table->integer('employeeId')->unsigned();
            $table->integer('skillId')->unsigned();

            $table->unique(array('employeeId', 'skillId'));
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('employees_have_skills');
    }

}
