<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTasksTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('tasks', function (Blueprint $table) {

            // primary key ------------------
            $table->increments('id');
            // ------------------------------

            $table->integer('skill_id')->unsigned();
            $table->integer('project_id')->unsigned();

            // ------------------------------------------
            // - foreign keys
            // ------------------------------------------       
            $table->foreign('skill_id')->references('id')->on('skills');
            $table->foreign('project_id')->references('id')->on('projects');
            // ------------------------------------------
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('tasks');
    }

}
